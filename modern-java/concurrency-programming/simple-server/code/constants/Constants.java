///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package constants;

/**
 * Constants
 */
public class Constants {
	/** 每行数据的列数 */
	public static final int TOTAL_COLUMNS = 59;
	/** 前4列都为字符串，剩下的55列要么是缺失值，要么是数值 */
	public static final int PREFIX_STRS = 4;

	public static final int SERIAL_PORT = 10_001;

}