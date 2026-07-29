///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
package model;

import java.nio.file.Path;

public interface FileSearch {
	void searchFiles(Path path, String fileName, Result result);
}