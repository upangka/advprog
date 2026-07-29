///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

/**
 * 将行平均分配给指定的数量线程
 */
public record GroupMultiplierTask(
		double[][] matrix1,
		double[][] matrix2,
		double[][] ret,
		int startRow, int endRow) implements Runnable {
	@Override
	public void run() {
		for (int i = startRow; i < endRow; i++) {
			for (int j = 0; j < matrix2[0].length; j++) {
				ret[i][j] = 0;
				for (int k = 0; k < matrix2.length; k++) {
					ret[i][j] += ret[i][k] * ret[k][j];
				}
			}
		}
	}
}