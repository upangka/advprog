///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//JAVA_OPTIONS -ea

public static List<Integer> evens(List<Integer> L) {
	return L.stream()
		.filter(n -> (n & 1) == 0)
		.toList();
}

public static Map<String, Integer> countWords(List<String> words) {
	Map<String, Integer> ret = new TreeMap<>();
	for (String word : words) {
		Integer count = ret.getOrDefault(word, 0);
		ret.put(word, count + 1);
	}
	return ret;

}

void main(String... args) {
	var nums = List.of(2, 5, 8, 7, 3, 10);
	var words = List.of("hello", "world", "hello", "cs61b", "world", "hello");

	var expected_evens = List.of(2, 8, 10);
	assert evens(nums).equals(expected_evens)
			: "evens 测试失败: 期望 %s, 实际得到 %s".formatted(expected_evens, evens(nums));

	var expected_words = Map.of("hello", 3, "cs61b", 1, "world", 2);
	assert countWords(words).equals(expected_words)
			: "countWords 测试失败: 期望 %s, 实际得到 %s".formatted(expected_words, countWords(words));

	System.out.println("所有测试通过 ✅");
}
