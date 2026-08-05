import java.util.Arrays;

///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

public class Sequence {
	// 内部类实现了接口，而内部类又能访问外部类
	private class SequenceSelector implements Selector {
		private int idx = 0;

		@Override
		public boolean end() {
			return idx >= items.length;
		}

		@Override
		public Integer current() {
			// 一般的写法
			// return items[idx];
			// 原本的样子
			return Sequence.this.items[this.idx];
		}

		@Override
		public void next() {
			idx++;
		}

	}

	private Integer[] items;
	private int next = 0;

	public Sequence(int size) {
		this.items = new Integer[size];
	}

	public void add(Integer item) {
		if (next < items.length) {
			items[next++] = item;
		}
	}

	// 暴露出去了
	public Selector getSelector() {
		return new SequenceSelector();
	}

	public Integer[] getItems() {
		return Arrays.copyOf(items, items.length);
	}

}