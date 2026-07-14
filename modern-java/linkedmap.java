///usr/bin/env jbang "$0" "$@" ; exit $?
//DEPS tools.jackson.core:jackson-databind:3.2.1 
//REPOS aliyun=https://maven.aliyun.com/repository/central
//JAVA 25
/*
For example, if the input list is:

["I", "love", "java", "but", "I", "love", "python", "more"]

then the output should be:

{
    "I": ["love", "love"],
    "love": ["java", "python"],
    "java": ["but"],
    "but": ["I"],
    "python": ["more"]
}
*/

import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

void main() {
    var words = List.of("I", "love", "java", "but", "I", "love", "python", "more");
    Map<String, List<String>> results = new LinkedHashMap<>();

    for (int i = 0; i < words.size() - 1; i++) {
        var follows = results.getOrDefault(words.get(i), new ArrayList<>());
        follows.add(words.get(i + 1));
        results.put(words.get(i), follows);
    }

    JsonMapper mapper = JsonMapper.builder()
            .enable(SerializationFeature.INDENT_OUTPUT)
            .build();

    try {
        String prettyJson = mapper.writeValueAsString(results);
        IO.println(prettyJson);
    } catch (Exception e) {
    }
}
