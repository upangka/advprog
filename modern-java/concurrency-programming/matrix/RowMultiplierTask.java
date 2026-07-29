///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

public record RowMultiplierTask(
		double[][] matrix1,
		double[][] matrix2,
		double[][] ret,
		int row) implements Runnable {

	@Override
	public void run() {
		for (int i = 0; i < matrix1[0].length; i++) {
			ret[row][i] = 0;
			for (int j = 0; j < matrix1[0].length; j++) {
				ret[row][i] += matrix1[row][j] * matrix2[j][i];
			}
		}
	}
}
