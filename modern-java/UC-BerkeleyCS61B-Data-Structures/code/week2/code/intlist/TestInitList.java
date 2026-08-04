///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS org.junit.jupiter:junit-jupiter:6.1.2
//DEPS com.google.truth:truth:1.4.5
//SOURCES ./**/IntList.java

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import com.google.common.truth.Truth;

import core.IntList;

public class TestInitList {
	private IntList L = null;

	@BeforeEach
	public void setup() {
		L = new IntList(67, null);
		L = new IntList(38, L);
		L = new IntList(1, L);
	}

	@Test
	public void testIntList() {
		int expected = 3;
		int actual = L.size();

		Truth.assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testGetIterative() {
		Truth.assertThat(L.getIterative(0)).isEqualTo(1);
		Truth.assertThat(L.getIterative(1)).isEqualTo(38);
		Truth.assertThat(L.getIterative(2)).isEqualTo(67);
	}

	@Test
	public void testIncrementRecursiveNoDestructive() {
		IntList newL = L.incrementRecursiveNoDestructive();

		Truth.assertThat(newL.size()).isEqualTo(L.size());
		Truth.assertThat(newL.get(0)).isEqualTo(2);
		Truth.assertThat(newL.get(1)).isEqualTo(39);
		Truth.assertThat(newL.get(2)).isEqualTo(68);

	}

}
