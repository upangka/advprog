///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

/**
 * Prints each character of a given string followed by the reverse of its index.
 * Example: printIndexed("hello") -> h4e3l2l1o0
 */
void printIndexed(String s) {
	int length = s.length();
	StringBuilder ret = new StringBuilder(length * 2);
	int lastIndex = s.length() - 1;

	for (int i = 0; i < length; i++) {
		ret.append(s.charAt(i)).append(lastIndex - i);
	}
	System.out.println(ret);
}

void main(String... args) {
	printIndexed("hello");
	printIndexed("cat"); // should print c2a1t0
}
