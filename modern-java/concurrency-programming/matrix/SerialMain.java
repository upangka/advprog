///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
import java.time.Duration;
import java.time.Instant;

/**
 * 耗时: 92309ms(92.31s)
 */
final static int SIZE = 2;

void main(String... args) {
    double[][] matrix1 = MatrixGenerator.generate(SIZE, SIZE);
    double[][] matrix2 = MatrixGenerator.generate(SIZE, SIZE);
    Instant start = Instant.now();
    double[][] _ret = SerialMultiplier.multiply(matrix1, matrix2);
    long duration = Duration.between(start, Instant.now()).toMillis();
    IO.println("耗时: %sms(%.2fs)".formatted(duration,duration / 1_000.0));

    System.out.println(Arrays.deepToString(matrix1));
    System.out.println(Arrays.deepToString(matrix2));
    System.out.println(Arrays.deepToString(_ret));
}
