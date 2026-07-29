///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS org.projectlombok:lombok:1.18.46
//REPOS aliyun=https://maven.aliyun.com/repository/central
//JAVAC_OPTIONS -proc:full
//SOURCES ./**/*.java

import model.Result;

public class Main {
	public static void main(String... args) {
		Result ret = new Result(false, "/");
		IO.println(ret);
	}
}
