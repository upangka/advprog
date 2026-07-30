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