
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

import java.util.ArrayList;
import java.util.List;

/**
 * 一个元素开一个线程进行计算
 */
public class ParallelIndividualMultiplier implements Multiplier {
	// My Computer is 12
	private final static int SIZE = Runtime.getRuntime().availableProcessors();

	@Override
	public double[][] multiply(double[][] matrix1, double[][] matrix2) {
		int row = matrix1.length;
		int column = matrix2[0].length;
		double[][] ret = new double[row][column];
		List<Thread> threads = new ArrayList<>(SIZE);

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < column; j++) {
				Runnable task = new IndividualMultiplierTask(
						matrix1, matrix2, ret, i, j);
				Thread t = new Thread(task);
				t.start();
				threads.add(t);

				if (threads.size() % SIZE == 0) {
					waitForThreads(threads);
				}
			}
		}

		return ret;
	}
}