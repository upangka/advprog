///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * SerialMultiplier 耗时: 6028ms(6.03s)
 * ParallelIndividualMultiplier 耗时: 163171ms(163.17s)
 */
final static int SIZE = 1000;

void main(String... args) {
	double[][] matrix1 = MatrixGenerator.generate(SIZE, SIZE);
	double[][] matrix2 = MatrixGenerator.generate(SIZE, SIZE);
	// List<double[][]> rets = new ArrayList<>();

	List<Multiplier> multipliers = List.of(
			new SerialMultiplier(),
			new ParallelIndividualMultiplier());
	for (Multiplier m : multipliers) {
		Instant start = Instant.now();
		double[][] _ret = m.multiply(matrix1, matrix2);
		long duration = Duration.between(start, Instant.now()).toMillis();
		IO.println("%s 耗时: %sms(%.2fs)".formatted(
				m.getClass().getCanonicalName(),
				duration, duration / 1_000.0));
		// rets.add(_ret);

	}

	// System.out.println(Arrays.deepToString(matrix1));
	// System.out.println(Arrays.deepToString(matrix2));
	// System.out.println(Arrays.deepToString(_ret));
}
