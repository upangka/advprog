///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS com.google.truth:truth:1.4.5
//SOURCES ./WdiLoader.java

package loader;

import com.google.common.truth.Truth;

/**
 * WdiLoaderTest
 */
public class WdiLoaderTest {

	public static void testParseLine() {
		String sample = "Afghanistan,AFG,\"2005 PPP conversion factor, private consumption (LCU per international $)\",PA.NUS.PRVT.PP.05,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,1.67096700000000E+01,,,,,,,,,";
		String[] expected = { "Afghanistan", "AFG",
				"2005 PPP conversion factor, private consumption (LCU per international $)", "PA.NUS.PRVT.PP.05", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "1.67096700000000E+01", "", "",
				"", "", "", "", "", "", "" };
		String[] actual = WdiLoader.parseLine(sample);
		Truth.assertThat(actual.length).isEqualTo(WdiLoader.TOTAL_COLUMNS);
		Truth.assertThat(actual).isEqualTo(expected);
		System.out.println("Good Example");

	}

	public static void main(String[] args) {
		testParseLine();
	}
}