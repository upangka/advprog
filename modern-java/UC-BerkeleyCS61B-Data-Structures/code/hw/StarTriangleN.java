///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

void starTriangle(final int maxStars) {
	for (int i = 1; i <= maxStars; i++) {
		int blankNum = maxStars - i;
		System.out.printf("%s%s%n",
				" ".repeat(blankNum),
				"*".repeat(i));
	}
}

void main(String... args) {
	for (int i = 6; i < 10; i++) {
		starTriangle(i);
		System.out.printf("%s%n%n", "__".repeat(30));
	}
}
