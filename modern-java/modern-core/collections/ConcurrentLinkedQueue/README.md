# ConcurrentLinkedQueue

创建一个名为 `queue` 的 `ConcurrentLinkedQueue` 实例。
定义两个生产者线程（`producer1` 和 `producer2`），它们使用 `offer()` 方法向队列中添加元素。
每个生产者线程在每次添加元素之间间隔 `1` 秒。
我们定义两个消费者线程（`consumer1` 和 `consumer2`），它们使用 `poll()` 方法从队列中移除元素。
每个消费者线程在每次移除元素之间间隔 `2` 秒。
我们同时启动所有生产者和消费者线程。
当元素被添加和移除时，生产者和消费者线程会打印消息，指明它们添加或移除了哪些元素。

原始版本[exercise_01.java](./code/exercise_01.java)
优化版本[exercise_02.java](./code/exercise_02.java)

优化版本使用*毒丸*来优雅的停止消费者线程

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//JAVA_OPTIONS -ea

import java.util.Random;
import java.util.PrimitiveIterator.OfInt;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 生产者-消费者演示程序
 * - 2个生产者各生产5个随机数（每秒1个）
 * - 2个消费者消费队列中的数（每2秒消费1个）
 * - 使用毒丸(POISON_PILL)优雅停止消费者
 */
void main(String... args) throws InterruptedException {
	var factory = new ThreadFactoryProvider();
	var queue = new ConcurrentLinkedQueue<Integer>();
	var latch = new CountDownLatch(4);

	Runnable producerTask = createProducerTask(queue, latch);
	Runnable consumerTask = createConsumerTask(queue, latch);

	Thread p1 = factory.newProducerThread(producerTask);
	Thread p2 = factory.newProducerThread(producerTask);
	Thread c1 = factory.newConsumerThread(consumerTask);
	Thread c2 = factory.newConsumerThread(consumerTask);

	startAll(p1, p2, c1, c2);

	latch.await();
	assert queue.isEmpty() : "队列应该为空，但还有 " + queue.size() + " 个元素";

	System.out.println("✅ 所有任务完成，程序正常退出");
}

class SimpleThreadFactory implements ThreadFactory {
	private final AtomicInteger id = new AtomicInteger(0);
	private final String prefixName;

	SimpleThreadFactory(String prefixName) {
		this.prefixName = prefixName;
	}

	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r, prefixName + "-" + id.incrementAndGet());
	}
}

class ThreadFactoryProvider {
	private final ThreadFactory producerFactory = new SimpleThreadFactory("producer");
	private final ThreadFactory consumerFactory = new SimpleThreadFactory("consumer");

	Thread newProducerThread(Runnable task) {
		return producerFactory.newThread(task);
	}

	Thread newConsumerThread(Runnable task) {
		return consumerFactory.newThread(task);
	}
}

final Integer POISON_PILL = -1;
final Random random = new Random();

Runnable createProducerTask(ConcurrentLinkedQueue<Integer> queue,
		CountDownLatch latch) {

	return () -> {
		String threadName = Thread.currentThread().getName();
		OfInt iterator = random.ints(5, 1, 50).iterator();

		while (iterator.hasNext()) {
			Integer num = iterator.next();
			queue.offer(num);
			System.out.println("%s 生产 >>> %d".formatted(threadName, num));
			sleep(1000);
		}

		// 放入毒丸，通知消费者停止
		queue.offer(POISON_PILL);
		System.out.println("%s 已生产完毕".formatted(threadName));
		latch.countDown();
	};
}

Runnable createConsumerTask(ConcurrentLinkedQueue<Integer> queue,
		CountDownLatch latch) {

	return () -> {
		String threadName = Thread.currentThread().getName();
		int consumedCount = 0;

		while (true) {
			Integer num = queue.poll();

			if (num == POISON_PILL) {
				System.out.println("%s 收到停止信号，共消费 %d 个元素".formatted(threadName, consumedCount));
				break;
			}

			if (num != null) {
				consumedCount++;
				System.out.println("%s 消费 <<< %d".formatted(threadName, num));
				sleep(2000);
			} else {
				// 队列为空，短暂等待避免空转
				sleep(50);
			}
		}

		System.out.println("%s 已停止".formatted(threadName));
		latch.countDown();
	};
}

void startAll(Thread... threads) {
	for (Thread t : threads) {
		t.start();
	}
	System.out.println("🚀 所有线程已启动");
}

void sleep(long millis) {
	try {
		Thread.sleep(millis);
	} catch (InterruptedException e) {
		Thread.currentThread().interrupt(); // 恢复中断状态
	}
}
```

[out.txt](./code/out.txt)

```txt
🚀 所有线程已启动
producer-1 生产 >>> 19
producer-2 生产 >>> 31
consumer-1 消费 <<< 31
consumer-2 消费 <<< 19
producer-1 生产 >>> 20
producer-2 生产 >>> 39
producer-2 生产 >>> 22
producer-1 生产 >>> 26
consumer-2 消费 <<< 20
consumer-1 消费 <<< 39
producer-2 生产 >>> 40
producer-1 生产 >>> 14
producer-1 生产 >>> 37
producer-2 生产 >>> 15
consumer-2 消费 <<< 26
consumer-1 消费 <<< 22
producer-1 已生产完毕
producer-2 已生产完毕
consumer-2 消费 <<< 14
consumer-1 消费 <<< 40
consumer-2 消费 <<< 15
consumer-1 消费 <<< 37
consumer-2 收到停止信号，共消费 5 个元素
consumer-1 收到停止信号，共消费 5 个元素
consumer-2 已停止
consumer-1 已停止
✅ 所有任务完成，程序正常退出
```
