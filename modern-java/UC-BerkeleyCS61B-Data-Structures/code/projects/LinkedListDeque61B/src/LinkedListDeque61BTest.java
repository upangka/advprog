///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//SOURCES ./core/Deque61B.java ./core/LinkedListDeque61B.java 

//DEPS org.junit.jupiter:junit-jupiter:6.1.2
//DEPS com.google.truth:truth:1.4.5

import java.util.List;

import org.junit.jupiter.api.Test;
import com.google.common.truth.Truth;

import core.Deque61B;
import core.LinkedListDeque61B;

/**
 * LinkedListDeque61BTest
 */
public class LinkedListDeque61BTest {
	private final static LinkedListDeque61BTest instance = new LinkedListDeque61BTest();

	@Test
	public void testOneElement() {
		Deque61B<Integer> lld = new LinkedListDeque61B<>();
		lld.addFirst(3);
		Truth.assertThat(lld.getLast()).isEqualTo(lld.getFirst());

		lld = new LinkedListDeque61B<>();
		lld.addLast(5);
		Truth.assertThat(lld.getLast()).isEqualTo(lld.getFirst());

		System.out.println("Good testOneElement");
	}

	@Test
	public void testToList() {

		Deque61B<Integer> lld = new LinkedListDeque61B<>();
		List<Integer> expected = List.of(3, 5, 9, 10);

		lld.addLast(5);
		lld.addLast(9);
		lld.addLast(10);
		lld.addFirst(3);
		List<Integer> actual = lld.toList();
		Truth.assertThat(actual).isEqualTo(expected);
		System.out.println("Good testToList: " + actual);

	}

	@Test
	public void testIsEmpty() {
		Deque61B<Integer> lld = new LinkedListDeque61B<>();
		Truth.assertThat(lld.isEmpty()).isTrue();
		System.out.println("Good testIsEmpty");
	}

	public static void main(String[] args) {
		instance.testOneElement();
		instance.testToList();
		instance.testIsEmpty();
	}
}