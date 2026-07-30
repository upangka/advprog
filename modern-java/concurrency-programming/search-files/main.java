///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS org.projectlombok:lombok:1.18.46
//REPOS aliyun=https://maven.aliyun.com/repository/central
//JAVAC_OPTIONS -proc:full
//SOURCES ./**/*.java

import model.Result;
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
	String target = "/home/pkmer/projects/advprog/modern-java/concurrency-programming/search-files";
	Path targetPath = Paths.get(target);

	Result ret = new Result(false, null);
	SerialFileSearch serial = new SerialFileSearch();
	serial.searchFiles(targetPath, "Result.java", ret);

}