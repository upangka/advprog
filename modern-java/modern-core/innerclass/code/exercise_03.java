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