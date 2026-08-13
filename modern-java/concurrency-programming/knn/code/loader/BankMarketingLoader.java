///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
package loader;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import model.BankMarketing;

public class BankMarketingLoader {

	public List<BankMarketing> load(final String path) {
		return this.load(Path.of(path));
	}

	public List<BankMarketing> load(final Path path) {
		var ret = new ArrayList<BankMarketing>();

		try (var reader = new BufferedReader(Files.newBufferedReader(path))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] columns = line.split(";");
				ret.add(new BankMarketing(columns));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
}