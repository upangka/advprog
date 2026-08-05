///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

class SList {

	private static class IntNode {
		private int item;
		private IntNode next;

		public IntNode(int item, IntNode next) {
			this.item = item;
			this.next = next;
		}
	}

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



void main(String... args) {
	SList L1 = new SList(15);
	L1.addFirst(10);
	L1.addFirst(5);

	System.out.println(L1.getFirst());
    L1.print();
}
