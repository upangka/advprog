///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

void starTriangle(int n) {
	System.out.println(this);
}

void main(String... args) {
	for (int i = 3; i < 6; i++) {
		starTriangle(i);
		System.out.println("__".repeat(30));
	}
}
