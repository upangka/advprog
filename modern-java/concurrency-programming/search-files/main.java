///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS org.projectlombok:lombok:1.18.46
//REPOS aliyun=https://maven.aliyun.com/repository/central
//JAVAC_OPTIONS -proc:full
//SOURCES ./**/*.java

import model.FileSearch;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import model.Result;
import parallel.ParallelFileSearch;
import serial.SerialFileSearch;

/**output
    Handling with => README.md
    Handling with => serial
    Handling with => SerialFileSearch.java
    Handling with => model
    Handling with => FileSearch.java
    Handling with => Result.java
    Serial Search: Path: /home/pkmer/projects/advprog/modern-java/concurrency-programming/search-files/model/Result.java
*/
void main(String... args) {
	final String root = "/";
	final String targetFileName = "functional-programming-in-python.md";
	Path rootPath = Paths.get(root);

	List<FileSearch> demos = List.of(new SerialFileSearch(), new ParallelFileSearch());
	// List<FileSearch> demos = List.of(new ParallelFileSearch());
	for (FileSearch demo : demos) {
		Result ret = new Result(false, null);
		Instant start = Instant.now();
		demo.searchFiles(rootPath, targetFileName, ret);
		long durationMills = Duration.between(start, Instant.now()).toMillis();
		if (ret.isFound()) {
			System.out.println("找到: %s文件,位于: %s".formatted(
					targetFileName,
					ret.getPath()));
		} else {
			System.out.println("没有找到 %s".formatted(targetFileName));
		}
		System.out.println();
		System.out.printf("%s 耗时 %sms (%.2fs)\n",
				demo.getClass().getName(), durationMills, durationMills / 1000.0);
		System.out.println("=".repeat(50));
	}
}