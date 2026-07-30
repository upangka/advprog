///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package serial;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import model.FileSearch;
import model.Result;

public class SerialFileSearch implements FileSearch {
	@Override
	public void searchFiles(Path path, String fileName, Result result) {
		try (DirectoryStream<Path> paths = Files.newDirectoryStream(path);) {
			for (Path p : paths) {
				var currentFileName = p.getFileName().toString();
				// System.out.println("Handling with => " + currentFileName);
				if (Files.isDirectory(p)) {
					searchFiles(p, fileName, result);
				} else {
					if (currentFileName.equals(fileName)) {
						result.setFound(true);
						result.setPath(p.toAbsolutePath().toString());
						System.out.printf("Serial Search: Path: %s%n", result.getPath());
					}
				}
				if (result.isFound()) {
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}