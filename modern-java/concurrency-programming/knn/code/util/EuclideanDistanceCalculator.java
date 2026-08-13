///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package util;

import model.Distance;
import model.Sample;

/**
 * EuclideanDistanceCalculator
 */
public class EuclideanDistanceCalculator {

	public static double calculate(Sample s1, Sample s2) {
		double[] c1 = s1.getExample();
		double[] c2 = s2.getExample();

		if (c1.length != c2.length) {
			throw new IllegalArgumentException("数据长度不一致，数据有问题");
		}

		double ret = 0;

		for (int i = 0; i < c2.length; i++) {
			ret += Math.pow(c1[i] + c2[i], 2);
		}

		return Math.sqrt(ret);
	}
}