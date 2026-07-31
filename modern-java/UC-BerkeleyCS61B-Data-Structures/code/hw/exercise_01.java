///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

String name = "Compact Source File";
String version = "JDK25";
static String desc = "US16B";

void showMe() {
	Class<?> clazz = this.getClass();
	System.out.println("Class Name: " + clazz.getName());

	// ---- 分析属性 (Fields) ----
	Field[] fields = clazz.getDeclaredFields();
	for (Field field : fields) {
		// 2. 获取修饰符的整数位掩码，并判断是否包含 static
		boolean isStatic = Modifier.isStatic(field.getModifiers());
		// 3. 在输出时做标记
		System.out.println("  Field: " + field.getName() +
				(isStatic ? " (STATIC)" : " (instance)"));
	}

	// ---- 分析方法 (Methods) ----
	Method[] methods = clazz.getDeclaredMethods();
	for (Method method : methods) {
		// 4. 同理判断方法
		boolean isStatic = Modifier.isStatic(method.getModifiers());
		System.out.println("  Method: " + method.getName() +
				"() " + (isStatic ? " (STATIC)" : " (instance)"));
	}
}

void main(String... args) {
	// 证明这是一个实例方法
	System.out.println(this);
	showMe();
}
