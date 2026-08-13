///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package loader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import model.WdiRecord;
import model.WdiRecordFactory;
import static constants.Constants.*;

/**
 * WdiLoader
 */
public class WdiLoader {
	private static final String SPLIT_SYMBOL = ",";
	private static final String DATA_PATH = "/home/pkmer/projects/advprog/modern-java/concurrency-programming/simple-server/resources/WDI_Data.csv";

	public static List<WdiRecord> load() {

		var ret = new ArrayList<WdiRecord>();

		try (var reader = Files.newBufferedReader(Path.of(DATA_PATH))) {
			String line = reader.readLine();
			assert line.split(SPLIT_SYMBOL).length == TOTAL_COLUMNS : "数据格式错误";

			while ((line = reader.readLine()) != null) {
				String[] columns = parseLine(line);
				WdiRecord record = WdiRecordFactory.create(columns);
				ret.add(record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

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
