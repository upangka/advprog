
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

import java.util.ArrayList;
import java.util.List;

public class ParallelIndividualMultiplier implements Multiplier {

	private final static int SIZE = 13;

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

	private void waitForThreads(List<Thread> threads) {
		for (Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		threads.clear();
	}
}