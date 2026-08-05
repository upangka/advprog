///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

class SList {
	private IntNode first;

	public SList(int item) {
		first = new IntNode(item, first);
	}

	public void addFirst(int item) {
		first = new IntNode(item, first);
	}

	public int getFirst() {
		return first.item;
	}

	private static int get(IntNode node, int index) {
		if (index == 0) {
			return node.item;
		}

		return get(node.next, index - 1);
	}

	public int get(int index) {
		return get(this.first, index);
	}

	public void print() {
		var current = first;
		int i = 0;
		while (current != null) {
			if (i > 0) {
				System.out.print(" -> ");
			}
			i += 1;
			System.out.print(current.item);
			current = current.next;
		}

		System.out.printf("%n一共%d个元素%n", i);
	}
}

class IntNode {
	private int item;
	private IntNode next;

	public IntNode(int item, IntNode next) {
		this.item = item;
		this.next = next;
	}
}

void main(String... args) {
	SList L = new SList(15);
	L.addFirst(10);
	L.addFirst(5);

	System.out.println(L.getFirst());
	System.out.println(L.get(1));
	System.out.println(L.get(2));
    L.print();
}
