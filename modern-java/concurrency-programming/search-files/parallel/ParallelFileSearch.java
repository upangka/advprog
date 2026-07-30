///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package parallel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;

import factory.SearchFilesThreadFactory;
import model.FileSearch;
import model.Result;

public class ParallelFileSearch implements FileSearch {
	private final ThreadFactory threadFactory = new SearchFilesThreadFactory();
    // IO 密集型配置
	private final int cpuCores = Runtime.getRuntime().availableProcessors();
	// 经验公式：IO密集型线程数 = 核心数 × 2 到 核心数 × 8
	private final int numThreads = cpuCores * 4;  // 12核 → 48线程

	@Override
	public void searchFiles(Path path, String fileName, Result result) {
		ConcurrentLinkedQueue<Path> queue = getQueue(path);

		Thread[] threads = new Thread[numThreads];
		ParallelGroupFileTask[] tasks = new ParallelGroupFileTask[numThreads];

		for (int i = 0; i < numThreads; i++) {
			var task = new ParallelGroupFileTask(fileName, queue, result);
			var thread = threadFactory.newThread(task);
			tasks[i] = task;
			threads[i] = thread;
		}

        for (Thread thread : threads) {
            thread.start();
        }


		boolean isFinished = false;
		int numFinished = 0;
		while (!isFinished) {
			numFinished = 0;
			for (int i = 0; i < numThreads; i++) {
				if (threads[i].getState() == Thread.State.TERMINATED) {
					numFinished++;
					if (tasks[i].isFound()) {
						isFinished = true;
						System.out.println("%s 检测到 %s 线程完成".formatted(
								Thread.currentThread().getName(),
								threads[i].getName()));
					}
				}
			}

			if (numFinished == threads.length) {
				isFinished = true;
			}
		}

		// 中断其他线程
		if (numFinished != threads.length) {
			for (Thread thread : threads) {
				System.out.println("%s 准备中断 %s".formatted(
						Thread.currentThread().getName(),
						thread.getName()));
				thread.interrupt();
			}
		}

        
        waitForThreads(threads);

	}

	private ConcurrentLinkedQueue<Path> getQueue(Path root) {
		ConcurrentLinkedQueue<Path> directories = new ConcurrentLinkedQueue<>();
		try (Stream<Path> stream = Files.list(root)) {
			List<Path> dirs = stream
				.filter(Files::isDirectory)
				.collect(Collectors.toList());
			directories.addAll(dirs);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return directories;
	}


    private void waitForThreads(Thread[] threads){
        // 等待结束
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
    }
}
