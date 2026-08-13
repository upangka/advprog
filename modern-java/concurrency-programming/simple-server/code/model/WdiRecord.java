///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package model;

/**
 * World Development Indicators Record
 * WDI 是世界银行数据库的官方缩写
 */
public record WdiRecord(
		/* 国家名称（如 Afghanistan） */
		String countryName,
		/* 国家代码（如 AFG） */
		String countryCode,
		/* 指标名称（如 "2005 PPP conversion factor, GDP"） */
		String indicatorName,
		/* 指标代码（如 PA.NUS.PPP.05） */
		String indicatorCode,
		/* 时间范围：1960-2014年 数组长度55 */
		Double[] values) {
}