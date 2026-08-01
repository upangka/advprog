///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

int a = 0;
static int b = 1;

static void f1() {
	// System.out.println(a);
	System.out.println(b);
}

void f2() {
	System.out.println(a);
	System.out.println(b);
	System.out.println(this.getClass());
}

void main(String... args) {
	f1();
	f2();
	System.out.println(this.getClass());

	// 默认导入了import module java.base，直接使用java se的api
	IO.println(List.of(1, 3, 5));
}
