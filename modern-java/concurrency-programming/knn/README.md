knn的效果取决于数据和k个样本。数据我们不能决定，但是优化不同的K是可以。但是在这个案例中，我们关注的是Java的并发处理。所以我们这里固定了k=10。

1. 加载训练数据和测试数据
2. 因为固定了K,所以直接拿每一条测试数据与训练的所有数据计算欧式距离。
   1. 从小到大取出前K条
   2. 统计前K条中tag出现最多的tag，就是预测的tag
   3. 拿预测的tag与测试数据的真实tag进行比较。

[knn](./code/)

```sh
├── core
│   ├── KnnI.java   # 接口
│   ├── parallel
│   │   ├── KnnClassifierParallelGroup.java  # 并发分组粒度
│   │   └── KnnClassifierParallelIndividual.java # 并发最细粒度
│   └── serial
│       └── KnnClassifier.java   # 串行
├── loader
│   └── BankMarketingLoader.java # 加载数据
├── main.java  # 入口
├── model
│   ├── BankMarketing.java  # 对应数据每一行
│   ├── Distance.java   # 存储计算结构的容器
│   └── Sample.java  # 接口
├── task
│   ├── GroupDistanceTask.java   # 并发分组粒度任务
│   └── IndividualDistanceTask.java # 并发最细粒度任务
└── util
    └── EuclideanDistanceCalculator.java  # 欧式距离计算
```

入口[main.java](./code/main.java)

```java
static final String TRAIN_DATAS_PATH = "/home/pkmer/projects/advprog/modern-java/concurrency-programming/knn/resources/bank.data";
static final String TEST_DATAS_PATH = "/home/pkmer/projects/advprog/modern-java/concurrency-programming/knn/resources/bank.test";
static int K = 10;

void main(String... args) throws Exception {
	var loader = new BankMarketingLoader();
	var trainSamples = loader.load(TRAIN_DATAS_PATH);
	var testSamples = loader.load(TEST_DATAS_PATH);

	var classifies = List.of(new KnnClassifier(trainSamples, K),
			new KnnClassifierParallelIndividual(trainSamples, K),
			new KnnClassifierParallelGroup(trainSamples, K));

	for (KnnI knnModel : classifies) {
		Instant start = Instant.now();
		int success = 0, mistakes = 0;

		for (var testSample : testSamples) {
			String predictTag = knnModel.classifyPredict(testSample);
			String actualTag = testSample.getTag();

			if (actualTag.equals(predictTag)) {
				success += 1;
			} else {
				mistakes += 1;
			}
		}

		long duration = Duration.between(start, Instant.now()).toMillis();
		System.out.println("%s 耗时: %sms(%.2fs)".formatted(
				knnModel.getClass().getSimpleName(),
				duration, duration / 1_000.0));
		System.out.printf("Accuracy(准确率): %.2f%% , Success: %d , Mistakes: %d\n",
				(double) success / testSamples.size() * 100, success,
				mistakes);

		System.out.println("─".repeat(60));
	}

	classifies.forEach(KnnI::close);

}

```

运行结果

```sh
KnnClassifier 耗时: 36920ms(36.92s)
Accuracy(准确率): 90.97% , Success: 1873 , Mistakes: 186
────────────────────────────────────────────────────────────
KnnClassifierParallelIndividual 耗时: 51738ms(51.74s)
Accuracy(准确率): 90.97% , Success: 1873 , Mistakes: 186
────────────────────────────────────────────────────────────
KnnClassifierParallelGroup 耗时: 26310ms(26.31s)
Accuracy(准确率): 90.97% , Success: 1873 , Mistakes: 186
────────────────────────────────────────────────────────────
```

# 串行版本

串型的版本基本就是按照上面的思路进行实现的

[KnnClassifier.java](./code/core/serial/KnnClassifier.java)

```java
Distance[] distances = new Distance[dataset.size()];

for (int i = 0; i < this.dataset.size(); i++) {
   double distance = EuclideanDistanceCalculator.calculate(this.dataset.get(i), sample);
   distances[i] = new Distance(i, distance);
}
```

# 最细粒度

基本上每个测试数据与训练的数据的每一条计算距离，都创建了一个任务，提交到线程池中。

[KnnClassifierParallelIndividual.java](./code/core/parallel/KnnClassifierParallelIndividual.java)

```java
Distance[] distances = new Distance[datasets.size()];
var endController = new CountDownLatch(this.datasets.size());
for (int i = 0; i < distances.length; i++) {
   Sample trainSample = this.datasets.get(i);
   var task = new IndividualDistanceTask(distances, i, trainSample, sample, endController);
   executor.submit(task);
}

endController.await();
```

# 分组粗粒度

[KnnClassifierParallelGroup.java](./code/core/parallel/KnnClassifierParallelGroup.java)

```java
Distance[] distances = new Distance[datasets.size()];
int numThreads = this.executor.getCorePoolSize();
int length = datasets.size() / numThreads;
int startIndex = 0, endIndex = length;

// 设置为线程的数量，因为是以每个线程为一组进行分配任务的
var endController = new CountDownLatch(numThreads);
for (int i = 0; i < length; i++) {
   // 最后一次取到直接取完
   if (i == length - 1) {
      endIndex += datasets.size();
   }
   var task = new GroupDistanceTask(distances, startIndex, endIndex, this.datasets, sample, endController);
   executor.submit(task);
   startIndex = endIndex;
   endIndex += length;
}

endController.await();
```
