///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

public class BST<T extends Comparable<? super T>> {

	private Node<T> root;

	private static class Node<T> {
		T key;
		Node<T> left;
		Node<T> right;

		public Node(T k) {
			this.key = k;
		}
	}

	private static <T extends Comparable<? super T>> Node<T> find(Node<T> node, T key) {
		if (node == null)
			return null;

		int ret = key.compareTo(node.key);
		if (ret == 0) {
			return node;
		} else if (ret < 0) {
			return find(node.left, key);
		} else {
			return find(node.right, key);
		}

	}

	public boolean contains(T key) {
		Node<T> node = find(root, key);
		return node != null;
	}

	private static <T extends Comparable<? super T>> Node<T> insert(Node<T> node, T key) {
		if (node == null) {
			return new Node<T>(key);
		}

		int ret = key.compareTo(node.key);

		if (ret < 0) {
			node.left = insert(node.left, key);
		} else if (ret > 0) {
			node.right = insert(node.right, key);
		}

		return node;

	}

	public void insert(T key) {
		Node<T> node = find(root, key);
		if (node != null) {
			System.out.println("Find %s. Do nothing.".formatted(node.key));
			return;
		}

		root = insert(root, key);
	}

	public static void main(String... args) {
		BST<String> bst = new BST<String>();
		bst.insert("dog");
		bst.insert("bag");
		bst.insert("flat");
		bst.insert("glut");
		bst.insert("cat");
		bst.insert("alf");
		bst.insert("elf");
		bst.insert("eyes");

		bst.insert("cat");

		System.out.println("");
	}

}
