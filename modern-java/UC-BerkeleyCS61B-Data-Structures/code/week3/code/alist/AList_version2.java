///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

class AList<T> {
	private T[] items;
	private int size;

	@SuppressWarnings("unchecked")
	public AList() {
		this.items = (T[]) new Object[2];
		this.size = 0;
	}

	@SuppressWarnings("unchecked")
	private void resize(int capacity) {
		T[] resized = (T[]) new Object[capacity];
		// copy over the array items
		System.arraycopy(this.items, 0, resized, 0, this.size);
		this.items = resized;
	}

	public void addLast(T val) {
		// When the array is too full - resize
		if (size == this.items.length) {
			System.out.printf("Need resize... when add %s%n", val);
			// 简单起见，每次扩容一倍
			resize(size * 2);
		}

		this.items[size] = val;
		this.size += 1;
	}

	public T removeLast() {
		var ret = this.items[size - 1];
		// 方便Java GC
		this.items[size - 1] = null;
		size -= 1;

		// R < 0.25 意味着 size / items.length < 0.25
		// 即数组使用率过低，需要缩容
		if ((double) size / items.length < 0.25) {
			resize(this.items.length / 2); // 缩容：R 从 0.24 变成 0.48
		}
		return ret;
	}

	public T get(int idx) {
		if (idx > -1 && idx < size) {
			return this.items[idx];
		}
		throw new IndexOutOfBoundsException();
	}

	public int size() {
		return this.size;
	}
}

void main(String... args) {
	var lst = new AList<String>();
	lst.addLast("Douyin");
	lst.addLast("Bilibibi");
	lst.addLast("Youtube");
	lst.addLast("Google");

	lst.removeLast();
	lst.removeLast();
	lst.removeLast();
}
