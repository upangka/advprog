///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

Map<Integer, List<Integer>> build_less_than_map(List<Integer> lst) {
	var ret = new HashMap<Integer, List<Integer>>();

	for (Integer key : lst) {
		List<Integer> valLst = ret.computeIfAbsent(key, ArrayList<Integer>::new);

		for (Integer val : lst) {
			if (key > val && !valLst.contains(val)) {
				valLst.add(val);
			}
		}
	}

	return ret;
}

void main(String... args) {
	var ret = build_less_than_map(List.of(4, 1, 3, 3));
	System.out.println(ret);
}
