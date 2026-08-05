# InnerClass的作用

1. 代码组织
2. An inner class provides a kind of window into the outer class.内部类是外部类的一扇"内部视角之窗"，能看见外部类的一切。就像是三体里面，从四维空间中伸向三维空间的手，毫无毫无痕迹的取出大脑

[fourth_handle.java](./code/fourth_handle.java)

```java
class Human {

	private class Heart {
		String stream;

		Heart(String s) {
			stream = s;
		}
	}

	public class FourthHand {
		private String name;

		FourthHand(String name) {
			this.name = name;
		}

		public void takeYourHeart() {
			System.out.println("偷偷偷走你的心");
			heart = new Heart("在你的心里(LIU)下一滴泪 By %s".formatted(name));
		}
	}

	private Heart heart = new Heart("寻找白晶晶");

	@Override
	public String toString() {
		return heart.stream;
	}
}

void main(String... args) {
	Human zhiZunBao = new Human();
	System.out.println(zhiZunBao);
	Human.FourthHand fourthHand = zhiZunBao.new FourthHand("紫霞");
	fourthHand.takeYourHeart();
	System.out.println(zhiZunBao);
}
/**
寻找白晶晶
偷偷偷走你的心
在你的心里(LIU)下一滴泪 By 紫
*/
```

# non-static innerclass

内部类对象创建时，会"悄悄"持有外部类对象的引用（就是那个 `OuterClass.this`），通过这个引用，内部类可以访问外部类的所有成员，`private` 也不例外。(**注意：此时内部类不被static修饰**)

> An inner class has a link to its enclosing class.（内部类持有指向其外部类的链接。）
>
> 简单的理解就是内部类持有两个this,一个`this`代表自己，一个`OuterClass.this`代表外部类

下面的代码实验证明了：

1. 非static的内部类持有外部类的this,能够访问所有属性，无视private，因为想想成this的使用方式即可
2. 当变量：方法参数变量，内部类的属性名，外部类的属性名一样的时候访问方式,案例中: `message`,`this.message`,`OuterClass.this.message`
3. 非static的内部类就相当于是外部类实例中的方法或者属性一样，在外部类的非static方法中如`accessInnerClass`,内部类的声明和创建内部类实例，可以直接使用`new 内部类`
4. 但是在OuterClass中的static方法，或者OuterClass类之外的其他代码中访问内部类(此时内部类能够访问-(非private)),则需要`OutClassName.InnerClassName`的访问类型，并且需要用外部类的实例`outerInstance.new InnerClass()`的方式创建内部类的实例
5. 在`accessInnerClass`能够访问内部类的私有属性`message`，这是因为内部类本身就是它的一部分，所以能够直接访问。
   - Java 的访问控制（private / public / protected）是基于类的，而不是基于对象的。也就是说，**同一个类中的代码，可以访问该类的所有对象的私有成员**。

   ```java
   public class Person {
   private int money = 100;

       public void cheat(Person other) {
           // 同一个类 Person，可以访问 other 对象的 private 字段
           other.money = 0;  // ✅ 完全合法
       }
   }
   ```

[OuterClass.java](./code/OuterClass.java)

```java
class OuterClass {
	private int size = 6;
	private String message = "I'm OuterClass";

	class InnerClass {
		private String message = "I'm InnerClass";

		public void show(String message) {
			// 默认持有OutClass.this
			System.out.printf("Access outer From inner class: %d\n", size);
			// 访问相同的属性
			System.out.println(message);
			System.out.println(this.message);
			System.out.println(OuterClass.this.message);
		}
	}

	public void accessInnerClass() {
		// 实例能够直接访问
		InnerClass innerClass = new InnerClass();
		// 无视private
		System.out.println(innerClass.message);
	}

	public static void staticMethodAccessInnerClass() {
		// 不是实例方法需要像外部访问一样实例化
		OuterClass outerClass = new OuterClass();
		InnerClass innerClass = outerClass.new InnerClass();
		System.out.println(innerClass.message);
	}
}
```

[exercise_01.java](./code/exercise_01.java)

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//SOURCES ./OuterClass.java

public static void main(String[] args) {
	OuterClass outClass = new OuterClass();
	// 在OuterClass外访问内部类 `OuterClassName.InnerClassName`
	// 内部类非static class需要通过外部类实例化
	OuterClass.InnerClass inner = outClass.new InnerClass();
	inner.show("I'm main method");

	System.out.println("--------------------------------------");
	outClass.accessInnerClass();
	System.out.println("--------------------------------------");
	OuterClass.staticMethodAccessInnerClass();
	System.out.println("--------------------------------------");
}
```

输出:

```java
Access outer From inner class: 6
I'm main method
I'm InnerClass
I'm OuterClass
--------------------------------------
I'm InnerClass
--------------------------------------
I'm InnerClass
--------------------------------------
```

## 小结

Construction of non-static the inner-class object **requires the reference to the object of the enclosing class**,
and the compiler will complain if it cannot access that reference.

构造内部类对象时，需要持有外部类对象的引用，如果编译器无法获取该引用，就会报错。

---

# static innerclass

内部类为`static`修饰，此时不能访问外部类实例的属性，也就是没有持有`Outer.this`的实例，可以理解为这个类只是代码结构上放在哪里，就像静态方法，静态属性一样。

# private innerclass

## 通过接口只提供功能而不暴露内部类

private 内部类的 private 只是"类型名"不可见，但通过 public 接口暴露出去的对象，其方法仍然可以被外部调用。

private 内部类隐藏的是"类型身份"，而不是"行为"。通过接口暴露行为，是 Java 内部类设计中最经典的安全封装模式——你只给外面一把钥匙（接口），但从不告诉他们这把锁是哪家工厂造的。

[exercise_02.java](./code/sequence/exercise_02.java)

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//SOURCES ./Selector.java ./Selector.java

final static int SIZE = 10;
Random random = new Random();

void main(String... args) {

	Sequence sequence = new Sequence(SIZE);
	random.ints(SIZE, 0, 10)
		.forEach(sequence::add);

	System.out.println("生成的结果 => " + Arrays.toString(sequence.getItems()));

	// 通过接口抽象，底层是内部类实现的
	Selector selector = sequence.getSelector();

	while (!selector.end()) {
		System.out.println(selector.current());
		selector.next();
	}

}
/**output:
生成的结果 => [3, 5, 5, 7, 8, 0, 2, 5, 4, 5]
3
5
5
7
8
0
2
5
4
5
*/
```

接口类[Selector.java](./code/sequence/Selector.java)抽象行为

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

public interface Selector {
	boolean end();

	Integer current();

	void next();
}
```

[Sequence.java](./code/sequence/Sequence.java)内部类实现了接口，而内部类又能访问外部类

```java
import java.util.Arrays;

///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

public class Sequence {
	// 内部类实现了接口，而内部类又能访问外部类
	private class SequenceSelector implements Selector {
		private int idx = 0;

		@Override
		public boolean end() {
			return idx >= items.length;
		}

		@Override
		public Integer current() {
			// 一般的写法
			// return items[idx];
			// 原本的样子
			return Sequence.this.items[this.idx];
		}

		@Override
		public void next() {
			idx++;
		}

	}

	private Integer[] items;
	private int next = 0;

	public Sequence(int size) {
		this.items = new Integer[size];
	}

	public void add(Integer item) {
		if (next < items.length) {
			items[next++] = item;
		}
	}

	// 暴露出去了
	public Selector getSelector() {
		return new SequenceSelector();
	}

	public Integer[] getItems() {
		return Arrays.copyOf(items, items.length);
	}

}
```

# Local Inner Class

InnerClass in method

实现一个接口或者继承一个类，但是不想将实现类公布出来，旨在解决当前的问题。就像上面[通过接口只提供功能而不暴露内部类](#通过接口只提供功能而不暴露内部类)的效果一样。

继承一个类[exercise_03.java](./code/exercise_03.java)

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

void main(String... args) {
	List<Integer> mylist = new ArrayList<Integer>(List.of(53)) {
		private String author = "鲨鱼のJavthon";

		//  Instance initialization for each object:
		{
			System.out.printf("Initialization at %s%n", LocalDate.now());
		}

		@Override
		public boolean add(Integer e) {
			System.out.printf("Now %s add => %d\n", author, e);
			return super.add(e);
		}
	};

	(new Random())
		.ints(5, 0, 20)
		.forEach(mylist::add);

	System.out.println(mylist);
}
/**
Initialization at 2026-08-05
Now 鲨鱼のJavthon add => 19
Now 鲨鱼のJavthon add => 11
Now 鲨鱼のJavthon add => 10
Now 鲨鱼のJavthon add => 13
Now 鲨鱼のJavthon add => 2
[53, 19, 11, 10, 13, 2]
*/
```

# 参考

1. [modern java](https://javabook.mccue.dev/inner_classes)
2. [Dev.java](https://dev.java/learn/classes-objects/nested-classes/)
3. [Oracle's official document](https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html)
4. 《On Java 8》
