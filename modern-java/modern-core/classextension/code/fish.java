///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

class Fish {

	static {
		System.out.println("静态方法");
	}

	{
		System.out.println("实例方法1");
	}

	public Fish() {
		IO.println("无参构造方法");
	}

	{
		System.out.println("实例方法2");
	}
}

void main(String... args) {
	new Fish();
}
