//JAVA 25
import java.util.Arrays;
import java.util.Random;

public class MatrixGenerator {

	private final static Random random = new Random(System.currentTimeMillis());

	public static double[][] generate(final int rows, final int columns) {
		double[][] ret = new double[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < ret.length; j++) {
				ret[i][j] = random.nextDouble() * 10;
			}
		}
		return ret;
	}

	public static void main(String[] args) {
		double[][] m = generate(2, 2);
		System.out.println(Arrays.deepToString(m));
	}
}