///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

//  运行会报错 error: compact source file should not have package declaration package mypkg;
// package mypkg;

void main(String... args) {
	IO.println("Hello World");
}
