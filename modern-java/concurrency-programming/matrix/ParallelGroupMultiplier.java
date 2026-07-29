///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

import java.util.List;
import java.util.ArrayList;

public class ParallelGroupMultiplier implements Multiplier {

	@Override
	public double[][] multiply(double[][] matrix1, double[][] matrix2) {
		int rows = matrix1.length, columns = matrix2[0].length;
		double[][] ret = new double[rows][columns];
		List<Thread> threads = new ArrayList<>();

		int startRow = 0;
		int step = rows / SIZE;
		int endRow = step;

		for (int i = 0; i < SIZE; i++) {
			// 最后一轮取剩余的全部
			if (i == SIZE - 1) {
				endRow = rows;
			}
			GroupMultiplierTask task = new GroupMultiplierTask(
					matrix1, matrix2, ret, startRow, endRow);
			Thread t = new Thread(task);
			t.start();
			threads.add(t);

			if (threads.size() == SIZE) {
				assert i == SIZE - 1 : "断言失败";
				waitForThreads(threads);
			}

			startRow = endRow;
			endRow += step;
		}
		return ret;
	}

}
