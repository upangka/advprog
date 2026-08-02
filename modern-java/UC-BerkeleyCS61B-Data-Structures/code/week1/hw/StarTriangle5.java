///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

/**
  * Prints a right-aligned triangle of stars ('*') with 5 lines.
  * The first row contains 1 star, the second 2 stars, and so on. 
  * output:
---------------------------------------
*
**
***
****
*****
---------------------------------------
  */
void main(String... args) {
	final int STARS = 5;
	for (int i = 1; i <= STARS; i++) {
		int blankNums = STARS - i;
		System.err.printf("%s%s%n",
				" ".repeat(blankNums),
				"*".repeat(i));
	}
}
