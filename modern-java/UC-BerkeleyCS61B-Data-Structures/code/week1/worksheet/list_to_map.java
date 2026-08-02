///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS tools.jackson.core:jackson-databind:3.1.0

import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

public static Map<String, List<String>> listOfFollowers(List<String> x) {
	// Map<String, List<String>> ret = new TreeMap<>();
	// 保证插入顺序
	Map<String, List<String>> ret = new LinkedHashMap<>();
	for (int i = 0; i < x.size() - 1; i++) {
		String key = x.get(i);
		String value = x.get(i + 1);
		ret.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
	}
	return ret;
}

void main(String... args) {
	JsonMapper jsonMapper = JsonMapper.builder()
		.enable(SerializationFeature.INDENT_OUTPUT)
		.build();
	String msg = "I love java but I love python more";
	Map<String, List<String>> ret = listOfFollowers(List.of(msg.split(" ")));
	System.out.println(jsonMapper.writeValueAsString(ret));
}
/**output
 {
  "I" : [ "love", "love" ],
  "love" : [ "java", "python" ],
  "java" : [ "but" ],
  "but" : [ "I" ],
  "python" : [ "more" ]
}
 */