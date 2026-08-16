///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package core;

import java.util.ArrayList;
import java.util.List;

/**
 * LinkedListDeque61B
 */
public class LinkedListDeque61B<T> implements Deque61B<T> {
	private final Node<T> sentinel;
	private int size;

	private static class Node<T> {
		private Node<T> prev;
		private T item;
		private Node<T> next;

		Node(T item) {
			this.item = item;
		}
	}

	public LinkedListDeque61B() {
		var node = new Node<T>(null);
		this.sentinel = node;
		this.sentinel.prev = node;
		this.sentinel.next = node;

		size = 0;
	}

	@Override
	public void addFirst(T item) {
		Node<T> node = new Node<T>(item);

		var tempNode = this.sentinel.next;
		this.sentinel.next = node;
		node.prev = this.sentinel;

		node.next = tempNode;
		tempNode.prev = node;

		size++;
	}

	@Override
	public void addLast(T item) {
		Node<T> node = new Node<T>(item);

		var tempNode = this.sentinel.prev;
		this.sentinel.prev = node;
		node.next = this.sentinel;

		node.prev = tempNode;
		tempNode.next = node;
	}

	@Override
	public List<T> toList() {
		var ret = new ArrayList<T>();

		var currentNode = this.sentinel.next;

		while (currentNode != this.sentinel) {
			ret.add(currentNode.item);
			currentNode = currentNode.next;
		}

		return ret;
	}

	@Override
	public boolean isEmpty() {
		return size == 0 && this.sentinel == this.sentinel.prev;
	}

	@Override
	public int size() {

		throw new UnsupportedOperationException("Unimplemented method 'size'");
	}

	@Override
	public T getFirst() {

		return this.sentinel.next.item;
	}

	@Override
	public T getLast() {
		return this.sentinel.prev.item;
	}

	@Override
	public T removeFirst() {

		throw new UnsupportedOperationException("Unimplemented method 'removeFirst'");
	}

	@Override
	public T removeLast() {

		throw new UnsupportedOperationException("Unimplemented method 'removeLast'");
	}

	@Override
	public T get(int index) {

		throw new UnsupportedOperationException("Unimplemented method 'get'");
	}

	@Override
	public T getRecursive(int index) {

		throw new UnsupportedOperationException("Unimplemented method 'getRecursive'");
	}

}