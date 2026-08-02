///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//JAVA_OPTIONS -ea

public static int maxMinDiff(List<Integer> L) {
	int minNum = Integer.MAX_VALUE;
	int maxNum = Integer.MIN_VALUE;

	for (Integer num : L) {
		if (num > maxNum) {
			maxNum = num;
		}

		if (num < minNum) {
			minNum = num;
		}

	}
	return maxNum - minNum;
}

void main(String... args) {
	List<Integer> nums = List.of(-5, 10, 3, -8, 20, 0);
	assert maxMinDiff(nums) == 28;
}
