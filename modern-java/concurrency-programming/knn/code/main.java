///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//SOURCES ./**/*.java

import java.time.Duration;
import java.time.Instant;

import core.serial.KnnClassifier;
import loader.BankMarketingLoader;

static final String TRAIN_DATAS_PATH = "/home/pkmer/projects/advprog/modern-java/concurrency-programming/knn/resources/bank.data";
static final String TEST_DATAS_PATH = "/home/pkmer/projects/advprog/modern-java/concurrency-programming/knn/resources/bank.test";
static int K = 10;

void main(String... args) throws Exception{
	var loader = new BankMarketingLoader();
	var trainSamples = loader.load(TRAIN_DATAS_PATH);
	var testSamples = loader.load(TEST_DATAS_PATH);

	var classifies = List.of(new KnnClassifier(trainSamples, K));

	for (KnnClassifier knnModel : classifies) {
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

}
