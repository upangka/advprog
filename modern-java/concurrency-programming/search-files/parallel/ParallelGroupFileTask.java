///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
package parallel;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentLinkedQueue;

import lombok.Data;
import model.Result;

@Data
public class ParallelGroupFileTask implements Runnable {

	private final String targetFileName;
	private final ConcurrentLinkedQueue<Path> directories;
	private final Result result;
	private boolean found = false;

	public ParallelGroupFileTask(
			String targetFileName,
			ConcurrentLinkedQueue<Path> directories,
			Result resut) {
		this.targetFileName = targetFileName;
		this.directories = directories;
		this.result = resut;
	}

	@Override
	public void run() {
		Path dir;
		while ((dir = directories.poll()) != null) {
			try {
				processDirectory(dir);
				if (this.found) {
					System.out.println("%s has found the file".formatted(
							Thread.currentThread().getName()));
					System.out.println("%s Parallel Search: Path: %s".formatted(
							Thread.currentThread().getName(),
							result.getPath()));
					return;
				}
			} catch (InterruptedException e) {
				System.out.println("%s has been interrupted".formatted(
						Thread.currentThread().getName()));
				break;
			}
		}
	}

	private void processDirectory(Path dir) throws InterruptedException {
		try (DirectoryStream<Path> paths = Files.newDirectoryStream(dir);) {
			for (Path p : paths) {
				if (Files.isDirectory(p)) {
					directories.offer(p);
				} else {
					String currentFileName = p.getFileName().toString();
					if (currentFileName.equals(targetFileName)) {
						this.found = true;
						result.setFound(true);
						// 有线程安全问题，但是问题不大
						result.setPath(p.toAbsolutePath().toString());
						return;
					}
				}
				// 其他人已经找到
				if (Thread.currentThread().isInterrupted()) {
					throw new InterruptedException();
				}
			}

		} catch (IOException e) {
		}
	}
}
