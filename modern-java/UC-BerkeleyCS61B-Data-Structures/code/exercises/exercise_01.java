///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS edu.princeton.cs:algs4:1.0.4
//REPOS aliyun=https://maven.aliyun.com/repository/public

import edu.princeton.cs.algs4.StdIn;

/**
 * 测试algs4算法书的jar包，maven正常工作
 * @param args
 */
void main(String... args) {
	int a = StdIn.readInt();
	System.out.println(a);
}
