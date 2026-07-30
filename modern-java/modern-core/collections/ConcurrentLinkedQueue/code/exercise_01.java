///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//JAVA_OPTIONS -ea

import java.util.Random;
import java.util.PrimitiveIterator.OfInt;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

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

public static void sleep(int millis) {
	try {
		Thread.sleep(millis);
	} catch (InterruptedException e) {
	}
}

/**
 *
 * We create a ConcurrentLinkedQueue named queue.
 * We define two producer threads (producer1 and producer2) that add elements to the queue using the offer() method.
 * Each producer thread adds elements to the queue with a delay of 1 second between each addition.
 * We define two consumer threads (consumer1 and consumer2) that remove elements from the queue using the poll() method.
 * Each consumer thread removes elements from the queue with a delay of 2 seconds between each removal.
 * We start all producer and consumer threads concurrently.
 * As elements are added and removed from the queue, the producer and consumer threads print messages indicating the elements they add or remove.
 *
 */
void main(String... args) throws InterruptedException {
	CountDownLatch latch = new CountDownLatch(4);
	final Integer POISON_PILL = -1; // 毒丸标记

	ThreadFactory producerFactory = new SimpleThreadFactory("producer");
	ThreadFactory consumerFactory = new SimpleThreadFactory("consumer");

	Random random = new Random();
	ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

	Runnable produceNumber = () -> {
		String threadName = Thread.currentThread().getName();
		OfInt iterator = random.ints(5, 1, 50).iterator();
		while (iterator.hasNext()) {
			Integer num = iterator.next();
			queue.offer(num);
			System.out.println("%s offer >>> %d".formatted(threadName, num));
			sleep(1000);
		}

		queue.offer(POISON_PILL);

		System.out.println("%s over".formatted(threadName));
		latch.countDown();
	};

	Runnable consumeNumber = () -> {
		String threadName = Thread.currentThread().getName();

		while (true) {
			Integer num = queue.poll();
			if (num == POISON_PILL)
				break;

			if (num != null) {
				System.out.println("%s poll <<< %d".formatted(threadName, num));
				sleep(2000);
			} else {
				sleep(30); // 防止CPU空转
			}
		}

		System.out.println("%s over".formatted(threadName));
		latch.countDown();
	};

	Thread p1 = producerFactory.newThread(produceNumber);
	Thread p2 = producerFactory.newThread(produceNumber);

	Thread c1 = consumerFactory.newThread(consumeNumber);
	Thread c2 = consumerFactory.newThread(consumeNumber);

	p1.start();
	p2.start();
	c1.start();
	c2.start();

	latch.await();
	assert queue.size() == 0 : "添加元素失败";

	System.out.println("main over");
	System.exit(0);
}
