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
