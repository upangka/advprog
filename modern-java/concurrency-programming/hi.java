///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS org.projectlombok:lombok:1.18.46
//DEPS tools.jackson.core:jackson-databind:3.2.1
//REPOS aliyun=https://maven.aliyun.com/repository/central
//JAVAC_OPTIONS -proc:full

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class hi {
    private String name;
    public static void main(String... args) {
        var hi = new hi("pkmer");
        IO.println("Hello World");
        IO.println("Hello World");
        IO.println(hi);

    }
}
