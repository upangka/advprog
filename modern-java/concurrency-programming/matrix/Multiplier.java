
//JAVA 25+

import java.util.List;

public interface Multiplier {

	final int SIZE = Runtime.getRuntime().availableProcessors();

	double[][] multiply(double[][] matrix1, double[][] matrix2);

	default void waitForThreads(List<Thread> threads) {
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
