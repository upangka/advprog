///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

void main(String... args) {
	var lst = new ArrayList<String>();
	lst.add("zero");
	lst.add("one");

	lst.set(0, "zed");
	System.out.println(lst.size());

	if (lst.contains("one")) {
		System.out.println("one in lst");
	}

	lst.stream().forEach(IO::println);

}
