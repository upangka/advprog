///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

public record IndividualMultiplierTask(
		double[][] matrix1,
		double[][] matrix2,
		double[][] ret,
		int i, int j) implements Runnable {
	@Override
	public void run() {
		ret[i][j] = 0;
		for (int k = 0; k < matrix1[0].length; k++) {
			ret[i][j] += matrix1[i][k] * matrix2[k][j];
		}
	}
}
