///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//SOURCES ./core/Deque61B.java ./core/LinkedListDeque61B.java

import core.Deque61B;
import core.LinkedListDeque61B;

void main(String... args) {
	Deque61B<Integer> lld = new LinkedListDeque61B<>();
	lld.addFirst(3);
	System.out.println(lld.getFirst());
	System.out.println(lld.getLast());

	// lld.addLast(0);   // [0]
	// lld.addLast(1);   // [0, 1]
	// lld.addFirst(-1); // [-1, 0, 1]
}
