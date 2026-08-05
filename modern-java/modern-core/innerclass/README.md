内部类对象创建时，会"悄悄"持有外部类对象的引用（就是那个 `OuterClass.this`），通过这个引用，内部类可以访问外部类的所有成员，`private` 也不例外。(**注意：此时内部类不被static修饰**)

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

---

内部类为`static`修饰，此时不能访问外部类实例的属性，也就是没有持有`Outer.this`的实例，可以理解为这个类只是代码结构上放在哪里，就像静态方法，静态属性一样。

# 参考

1. [modern java](https://javabook.mccue.dev/inner_classes)
2. [Dev.java](https://dev.java/learn/classes-objects/nested-classes/)
3. [Oracle's official document](https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html)
