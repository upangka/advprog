
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

final static String PARENT_NAME = BodyOfWater.class.getSimpleName();
final static String CHILD_NAME = Ocean.class.getSimpleName();

class BodyOfWater {
	final long depth;

	{
		System.out.println("4. 父类%s实例代码块1".formatted(PARENT_NAME));
	}

	static {
		System.out.println("1. 父类%s静态代码块".formatted(PARENT_NAME));
	}

	BodyOfWater() {
		System.out.println("3.1 父类%s无参构造方法进入".formatted(PARENT_NAME));
		this(0L);
		System.out.println("3.2 父类%s无参构造方法退出".formatted(PARENT_NAME));
	}

	BodyOfWater(long depth) {
		System.out.println("6. 父类%s有参构造方法".formatted(PARENT_NAME));
		this.depth = depth;
	}

	{
		System.out.println("5. 父类%s实例代码块2".formatted(PARENT_NAME));
	}
}

class Ocean extends BodyOfWater {
	private final String name;

	{
		System.out.println("7. 子类%s实例代码块1".formatted(CHILD_NAME));
	}

	static {
		System.out.println("2. 子类%s静态代码块".formatted(CHILD_NAME));
	}

	public Ocean(String name) {
		// super() 这里隐式调用父类的无参构造方法
		System.out.println("9.1 子类%s构造方法进入".formatted(CHILD_NAME));
		this.name = name;
		System.out.println("9.2 子类%s构造方法".formatted(CHILD_NAME));
	}

	{
		System.out.println("8. 子类%s实例代码块2".formatted(CHILD_NAME));
	}
}

void main(String... args) {
	new Ocean("Pacific");
}
