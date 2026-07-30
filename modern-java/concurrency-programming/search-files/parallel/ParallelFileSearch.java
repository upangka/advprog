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
	private final int numThreads = Runtime.getRuntime().availableProcessors();

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
			thread.start();
		}

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

}
