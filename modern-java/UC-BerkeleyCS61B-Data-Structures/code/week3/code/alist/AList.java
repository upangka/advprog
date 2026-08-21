///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

public class AList {
	private int[] items;
	private int size;

	public AList() {
		this.items = new int[2];
		this.size = 0;
	}

	private void resize(int capacity) {
		int[] resized = new int[capacity];
		// copy over the array items
		System.arraycopy(this.items, 0, resized, 0, this.size);
		this.items = resized;
	}

	public void addLast(int val) {
		// When the array is too full - resize
		if (size == this.items.length) {
			System.out.printf("Need resize... when add %d%n", val);
			// 简单起见，每次扩容一倍
			resize(size * 2);
		}

		this.items[size] = val;
		this.size += 1;
	}

	public int removeLast() {
		var ret = this.items[size - 1];
		size -= 1;

		// R < 0.25 意味着 size / items.length < 0.25
		// 即数组使用率过低，需要缩容
		if ((double) size / items.length < 0.25) {
			resize(this.items.length / 2); // 缩容：R 从 0.24 变成 0.48
		}
		return ret;
	}

	public int get(int idx) {
		if (idx > -1 && idx < size) {
			return this.items[idx];
		}
		throw new IndexOutOfBoundsException();
	}

	public int size() {
		return this.size;
	}

	public static void main(String[] args) {
		var lst = new AList();
		for (int i = 0; i < 5; i++) {
			lst.addLast(i);
		}
		System.out.println(lst.size());
	}
}
