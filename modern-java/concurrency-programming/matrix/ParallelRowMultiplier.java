
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

import java.util.ArrayList;
import java.util.List;

/**
 * 每行开一个线程进行计算
 */
public class ParallelRowMultiplier implements Multiplier {
	// My Computer is 12
	private final static int SIZE = Runtime.getRuntime().availableProcessors();

	@Override
	public double[][] multiply(double[][] matrix1, double[][] matrix2) {
		int rows = matrix1.length, columns = matrix2[0].length;
		double[][] ret = new double[rows][columns];
		List<Thread> threads = new ArrayList<>();
		for (int i = 0; i < rows; i++) {
			RowMultiplierTask task = new RowMultiplierTask(
					matrix1, matrix2, ret, i);
			Thread t = new Thread(task);
			t.start();
			threads.add(t);

			if (threads.size() % SIZE == 0) {
				waitForThreads(threads);
			}
		}

		return ret;
	}
}
