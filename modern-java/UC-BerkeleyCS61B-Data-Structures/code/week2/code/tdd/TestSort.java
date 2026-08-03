///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS org.junit.jupiter:junit-jupiter:6.1.2
//DEPS com.google.truth:truth:1.4.5

import org.junit.jupiter.api.Test;
import com.google.common.truth.Truth;

public class TestSort {

	@Test
	public void testFindSmallest() {
		String[] inputs = { "helloworld", "bonus", "googletruth", "java", "python", "cplusplus" };
		var expected = 5;

		int actual = Sort.findSmallest(inputs, 2);
		Truth.assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testSwap() {
		String[] inputs = { "helloworld", "bonus", "googletruth", "java", "python", "cplusplus" };
		String[] expected = { "helloworld", "java", "googletruth", "bonus", "python", "cplusplus" };

		Sort.swap(inputs, 1, 3);
		Truth.assertThat(inputs).isEqualTo(expected);
	}

	@Test
	public void testSort() {
		String[] inputs = { "helloworld", "bonus", "googletruth", "java", "python", "cplusplus" };
		String[] expected = { "bonus", "cplusplus", "googletruth", "helloworld", "java", "python" };

		Sort.sort(inputs);
		Truth.assertThat(inputs).isEqualTo(expected);
	}
}