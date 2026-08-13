///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package model;

import static constants.Constants.*;

/**
 * WdiRecordFactory
 */
public class WdiRecordFactory {
	public static final Short FIRST_YEAR = 1960;

	public static WdiRecord create(String[] columns) {
		var countryName = columns[0];
		var countryCode = columns[1];
		var indicatorName = columns[2];
		var indicatorCode = columns[3];

		Double[] values = new Double[TOTAL_COLUMNS - PREFIX_STRS];
		for (int i = PREFIX_STRS, idx = 0; i < columns.length; i++, idx++) {
			values[idx] = getValue(columns[i]);
		}

		return new WdiRecord(countryName, countryCode, indicatorName, indicatorCode, values);
	}

	public static Double getValue(String value) {
		if (value.trim().length() == 0) {
			return 0.0d;
		}
		return Double.parseDouble(value);
	}

}