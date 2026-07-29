///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

/**
 * 串行计算
 */
public class SerialMultiplier implements Multiplier {

	public double[][] multiply(double[][] matrix1, double[][] matrix2) {
		int row = matrix1.length;
		int column = matrix2[0].length;
		double[][] ret = new double[row][column];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				ret[i][j] = 0;
				for (int k = 0; k < column; k++) {
					ret[i][j] += matrix1[i][k] * matrix2[k][j];
				}
			}
		}
		return ret;
	}
}
