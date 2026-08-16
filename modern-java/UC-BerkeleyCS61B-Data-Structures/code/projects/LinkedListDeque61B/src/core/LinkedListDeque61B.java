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
		if(item == null) return;

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
		if(item == null) return;

		Node<T> node = new Node<T>(item);

		var tempNode = this.sentinel.prev;
		this.sentinel.prev = node;
		node.next = this.sentinel;

		node.prev = tempNode;
		tempNode.next = node;

		size++;
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
		return size;
	}

	@Override
	public T getFirst() {
		if(isEmpty()) return null;
		return this.sentinel.next.item;
	}

	@Override
	public T getLast() {
		if(isEmpty()) return null;
		return this.sentinel.prev.item;
	}

	@Override
	public T removeFirst() {
		if(isEmpty()) return null;

		var first = this.sentinel.next;
		var candidateFirst = first.next;

		this.sentinel.next = candidateFirst;
		candidateFirst.prev = this.sentinel;
		
		size--;
		return first.item;
	}

	@Override
	public T removeLast() {
		if(isEmpty()) return null;

		var last = this.sentinel.prev;
		var candidateLast = last.prev;
		
		candidateLast.next = this.sentinel;
		this.sentinel.prev = candidateLast;
		
		size--;
		return last.item;
	}

	@Override
	public T get(int index) {
		if(index < 0 || index > size - 1){
			return null;
		}

		var currentNode = this.sentinel.next;

		for (int i = 0; i < index; i++) {
			currentNode = currentNode.next;
		}

		return currentNode.item;
	}

	@Override
	public T getRecursive(int index) {
		if(index < 0 || index > size - 1){
			return null;
		}
		return getRecursive(this.sentinel.next, index);
	}

	private static <T> T getRecursive(Node<T> node,int index){
		if(index == 0){
			return node.item;
		}
		return getRecursive(node.next, index - 1);
	}

}