# 核心原则

    1. 实例代码块是在构造方法体执行之前运行的
    2. 隐式调用永远调的是无参构造`super()`

# 标准的执行顺序

1. 静态代码块：类加载时执行，父类先于子类
2. 实例代码块：对象实例化时执行，在构造方法体之前执行
3. 构造方法

[fish.java](./code/fish.java)

```java
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
```

输出:

```txt
静态方法
实例方法1
实例方法2
无参构造方法
```

## 构造方法中调用构造方法

在最后执行的构造方法中（也就是真正实例化之前）会去执行实例化方法。

[apple.java](./code/apple.java)

```java
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

```

```txt
静态方法
无参构造方法1
实例方法1
实例方法2
有参构造方法
无参构造方法2
```

# 继承案例执行循序

1. 静态代码块：类加载时执行，父类先于子类
2. 实例代码块：对象实例化时执行，在构造方法体之前执行
   - 如果构造方法内有 `this()` 调用，实例代码块在 `this()` 调用之后，构造方法体剩余代码之前执行
3. 构造方法：
   - 子类构造方法隐式或显式调用父类构造方法
   - `this()` 调用会跳转到另一个构造方法，实例代码块在目标构造方法体之前执行

[Ocean.java](./code/Ocean.java)

```java

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

```

输出：

```java
1. 父类BodyOfWater静态代码块
2. 子类Ocean静态代码块
3.1 父类BodyOfWater无参构造方法进入
4. 父类BodyOfWater实例代码块1
5. 父类BodyOfWater实例代码块2
6. 父类BodyOfWater有参构造方法
3.2 父类BodyOfWater无参构造方法退出
7. 子类Ocean实例代码块1
8. 子类Ocean实例代码块2
9.1 子类Ocean构造方法进入
9.2 子类Ocean构造方法
```
