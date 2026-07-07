///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

void main(String... args) {
	var lst = new ArrayList<>() {
		{
			add(1);
			add(2);
		}
	};
	var n = Integer.parseInt(IO.readln());
	f(n);
}

void f(Integer n) {
	IO.println(n * 3);
}
