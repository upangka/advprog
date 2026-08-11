///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

class Apple {

	static {
		System.out.println("静态方法");
	}

	{
		System.out.println("实例方法1");
	}

	Apple() {
		IO.println("无参构造方法1");
		this("Iphone");
		IO.println("无参构造方法2");
	}

	Apple(String name) {
		// ⚠️会在这里去执行实例化方法
		IO.println("有参构造方法");
	}

	{
		System.out.println("实例方法2");
	}
}

void main(String... args) {
	new Apple();
}
