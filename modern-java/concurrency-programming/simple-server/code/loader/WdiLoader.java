///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package loader;

/**
 * WdiLoader
 */
public class WdiLoader {
	public static final int TOTAL_COLUMNS = 59;

	public static String[] parseLine(String line) {
		String[] ret = new String[TOTAL_COLUMNS];

		var unitContainer = new StringBuilder();
		int cIdx = 0;
		boolean beginQuote = false;
		//  Afghanistan,AFG,"2005 PPP conversion factor, private consumption (LCU per international $)",PA.NUS.PRVT.PP.05,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,1.67096700000000E+01,,,,,,,,,

		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);

			if (c == '"') {
				beginQuote = !beginQuote;
			} else if (c == ',' && !beginQuote) {
				ret[cIdx] = unitContainer.toString();
				unitContainer.setLength(0);
				cIdx++;
			} else {
				unitContainer.append(c);
			}
		}

		ret[cIdx] = unitContainer.toString();
		return ret;

	}
}
