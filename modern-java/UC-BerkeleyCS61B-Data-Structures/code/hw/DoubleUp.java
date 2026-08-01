///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

/**
 * Returns a new string where each character of the given string is repeated twice.
 * Example: doubleUp("hello") -> "hheelllloo"
 * 
 */
static String doubleUp(String s) {
	// 这里尝试用函数方式风格实现
	return s.chars()
		.mapToObj(c -> String.valueOf((char) c))
		.map(x -> x.repeat(2))
		.collect(Collectors.joining());
}

void main(String... args) {
	System.out.println(doubleUp("hello"));
}

/**笔记
map = 保持原样（IntStream → IntStream）
mapToObj = 转换为对象（IntStream → Stream<对象>）

mapToObj 返回的是具体类型的 Stream，由你指定的类型决定。
// ✅ 是具体类型 Stream<String>
.mapToObj(c -> String.valueOf(c))  // 返回 Stream<String>

// ✅ 也可以是自定义类型
.mapToObj(c -> new Person(c))  // 返回 Stream<Person>
*/