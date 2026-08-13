///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS com.google.truth:truth:1.4.5
//SOURCES ./WdiLoader.java ../loader/WdiLoader.java ../model/WdiRecord.java ../model/WdiRecordFactory.java ../constants/Constants.java

package loader;

import java.util.List;

import com.google.common.truth.Truth;

import model.WdiRecord;
import static constants.Constants.*;

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
		Truth.assertThat(actual.length).isEqualTo(TOTAL_COLUMNS);
		Truth.assertThat(actual).isEqualTo(expected);
		System.out.println("Good test parse line");
	}

	public static void testLoad() {
		List<WdiRecord> list = WdiLoader.load();
		// WDI_Data.csv一共336289行，出去标题一共336288条数据
		Truth.assertThat(list.size()).isEqualTo(336_288);
		System.out.println("Good test load");
	}

	public static void main(String[] args) {
		testParseLine();
		testLoad();
	}
}