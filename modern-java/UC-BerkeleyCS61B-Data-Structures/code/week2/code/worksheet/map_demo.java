///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

void main(String... args) {
	var d = new HashMap<String, String>();
	d.put("hello", "hi");
	d.put("hello", "goodbye");

	System.out.println(d.get("hello"));
	System.out.println(d.size());

	if (d.containsKey("hello")) {
		System.out.println("\"hello\" in d");
	}

	for (String key : d.keySet()) {
		System.out.println(key);
	}
}
