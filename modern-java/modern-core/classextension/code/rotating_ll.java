///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS com.google.truth:truth:1.4.5

import com.google.common.truth.Truth;

class RotatingLL<T> extends LinkedList<T> {

	public void rotateLeft() {
		var first = poll();
		if (first != null) {
			offer(first);
		}

	}
}

void main(String... args) {

	var rotatingLL = new RotatingLL<Integer>();

	rotatingLL.addLast(10);
	rotatingLL.addLast(11);
	rotatingLL.addLast(12);
	rotatingLL.addLast(13);
	Truth.assertThat(rotatingLL).isEqualTo(List.of(10, 11, 12, 13));

	rotatingLL.rotateLeft();
	Truth.assertThat(rotatingLL).isEqualTo(List.of(11, 12, 13, 10));
	System.out.println("Good Example");
	System.out.println(rotatingLL);

}
