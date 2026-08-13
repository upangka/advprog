///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS org.junit.jupiter:junit-jupiter:6.1.2
//DEPS com.google.truth:truth:1.4.5
//SOURCES ./WdiLoader.java

package loader;

import java.util.Arrays;

/**
 * WdiLoaderTest
 */
public class WdiLoaderTest {

	public static void testParseLine() {
		String sample = "hello,\"world\",,,";
		String[] words = WdiLoader.parseLine(sample);
		System.out.println(words.length);
		System.out.println(Arrays.toString(words));
	}

	public static void main(String[] args) {
		testParseLine();
	}
}