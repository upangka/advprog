///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package dao;

import java.util.List;

import loader.WdiLoader;
import model.WdiRecord;

/**
 * WdiDao
 */
public class WdiDao {

	private List<WdiRecord> data;

	public WdiDao() {
		this.data = WdiLoader.load();
	}

	/**
	 * 查询所有年份了
	 */
	public String query(String codCountry, String codIndicator) {

		WdiRecord target = doFindWdiRecord(codCountry, codIndicator);

		if (target == null) {
			return "Not find any data";
		}

		StringBuilder ret = new StringBuilder();

		ret.append(target.countryName())
			.append(";")
			.append(target.indicatorName());

		for (var v : target.values()) {
			ret.append(";").append(v);
		}

		return ret.toString();
	}

	private WdiRecord doFindWdiRecord(String codCountry, String codIndicator) {
		for (WdiRecord wdiRecord : data) {
			if (wdiRecord.countryCode().equals(codCountry) && wdiRecord.indicatorCode().equals(codIndicator)) {
				return wdiRecord;
			}
		}
		return null;
	}
}