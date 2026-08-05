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