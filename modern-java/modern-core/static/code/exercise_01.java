///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

class Human {
	static void ponder(int x) {
		IO.println(x);
	}
}

void main(String... args) {
	Human h = null;
	Human.ponder(2); // 输出：2
	h.ponder(3); // 输出：3（注意：h 是 null，但不会抛出异常！）
}
