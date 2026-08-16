///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//SOURCES ./core/Deque61B.java ./core/LinkedListDeque61B.java

import core.Deque61B;
import core.LinkedListDeque61B;

void main(String... args) {
	Deque61B<String> lld = new LinkedListDeque61B<>();

	lld.addFirst("Berkely");
	lld.addLast("CS");
	lld.addLast("16B");
	lld.addFirst("UC");

	System.out.println(lld.toList() + " size: " + lld.size());

}
