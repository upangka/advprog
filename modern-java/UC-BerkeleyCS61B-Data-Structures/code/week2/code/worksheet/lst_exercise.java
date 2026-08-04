///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

List<Integer> common(List<Integer> L1, List<Integer> L2) {
	var ret = new ArrayList<Integer>();
	for (Integer item : L1) {
		if (L2.contains(item) && !ret.contains(item)) {
			ret.add(item);
		}
	}
	return ret;
}

void main(String... args) {
	List<Integer> ret = common(
			List.of(1, 2, 3, 4),
			List.of(3, 4, 5, 6));
	System.out.println(ret);
}
