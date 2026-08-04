///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

/** 
 * Returns an array containing only the positive
 * integers from the given list
 */
int[] filterPositive(List<Integer> L) {
	return L.stream()
		.filter(x -> x > 0)
		.mapToInt(Integer::intValue)
		.toArray();
}

void main(String... args) {
	int[] ret = filterPositive(List.of(3, -1, 0, 2, -5, 7));
	System.out.println(Arrays.toString(ret));
}
