///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

void main(String... args) {
	int score = 100;

	// lambda 匿名local class
	Runnable t1 = () -> {
		// local variables referenced from a lambda expression must be final or effectively final
		// score -= 10;
		System.out.println(score);
	};

	// 匿名local class
	Runnable t2 = new Runnable() {
		private String name = "Mark";

		@Override
		public void run() {
			// local variables referenced from an inner class must be final or effectively final
			// score -= 10;
			System.out.printf("%s %d\n", name, score);
		}
	};

	// Local Class
	class Task implements Runnable {
		private String name = "John";

		@Override
		public void run() {
			// local variables referenced from an inner class must be final or effectively final
			// score -= 10;
			System.out.printf("%s %d\n", name, score);
		}
	}

	var t3 = new Task();

	t1.run();
	t2.run();
	t3.run();
}
