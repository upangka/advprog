//JAVA 25+

import java.util.List;

/**
 * Methods are provided in the suggested order
 * that they should be completed.
 * Edited by Marcus Koh on 1/30/2026 to include getFirst and getLast
 */
public interface Deque61B<T> {

	/**
	 * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
	 *
	 * @param x item to add
	 */
	default void addFirst(T x) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
	 *
	 * @param x item to add
	 */
	default void addLast(T x) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns a List copy of the deque. Does not alter the deque.
	 *
	 * @return a new list copy of the deque.
	 */
	default List<T> toList() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns if the deque is empty. Does not alter the deque.
	 *
	 * @return {@code true} if the deque has no elements, {@code false} otherwise.
	 */
	default boolean isEmpty() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the size of the deque. Does not alter the deque.
	 *
	 * @return the number of items in the deque.
	 */
	default int size() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Return the element at the front of the deque, if it exists.
	 *
	 * @return element, otherwise {@code null}.
	 */
	default T getFirst() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Return the element at the back of the deque, if it exists.
	 *
	 * @return element, otherwise {@code null}.
	 */
	default T getLast() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Remove and return the element at the front of the deque, if it exists.
	 *
	 * @return removed element, otherwise {@code null}.
	 */
	default T removeFirst() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Remove and return the element at the back of the deque, if it exists.
	 *
	 * @return removed element, otherwise {@code null}.
	 */
	default T removeLast() {
		throw new UnsupportedOperationException();
	}

	/**
	 * The Deque61B abstract data type does not typically have a get method,
	 * but we've included this extra operation to provide you with some
	 * extra programming practice. Gets the element, iteratively. Returns
	 * null if index is out of bounds. Does not alter the deque.
	 *
	 * @param index index to get
	 * @return element at {@code index} in the deque
	 */
	default T get(int index) {
		throw new UnsupportedOperationException();
	}

	/**
	 * This method technically shouldn't be in the interface, but it's here
	 * to make testing nice. Gets an element, recursively. Returns null if
	 * index is out of bounds. Does not alter the deque.
	 *
	 * @param index index to get
	 * @return element at {@code index} in the deque
	 */
	default T getRecursive(int index) {
		throw new UnsupportedOperationException();
	}
}