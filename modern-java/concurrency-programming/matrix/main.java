///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//JAVA_OPTIONS -ea

import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * SerialMultiplier 耗时: 89434ms(89.43s)
 * ParallelIndividualMultiplier 耗时: 660412ms(660.41s)
 * ParallelRowMultiplier 耗时: 25962ms(25.96s)
 * ParallelGroupMultiplier 耗时: 26244ms(26.24s)
 */
final static int SIZE = 2000;

void main(String... args) {
	double[][] matrix1 = MatrixGenerator.generate(SIZE, SIZE);
	double[][] matrix2 = MatrixGenerator.generate(SIZE, SIZE);
	List<double[][]> rets = new ArrayList<>();

	List<Multiplier> multipliers = List.of(
			new SerialMultiplier(),
			new ParallelIndividualMultiplier(),
			new ParallelRowMultiplier(),
			new ParallelGroupMultiplier());

	for (Multiplier m : multipliers) {
		Instant start = Instant.now();
		double[][] _ret = m.multiply(matrix1, matrix2);
		long duration = Duration.between(start, Instant.now()).toMillis();
		IO.println("%s 耗时: %sms(%.2fs)".formatted(
				m.getClass().getCanonicalName(),
				duration, duration / 1_000.0));
		rets.add(_ret);

	}

	// System.out.println(Arrays.deepToString(matrix1));
	// System.out.println(Arrays.deepToString(matrix2));
	// System.out.println(Arrays.deepToString(_ret));

	for (int i = 0; i < rets.size() - 1; i++) {
		double[][] prev = rets.get(i);
		double[][] next = rets.get(i + 1);
		assert Arrays.deepEquals(prev, next) : "计算结果不正确";
	}
}
