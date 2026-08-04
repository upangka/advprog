///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
package core;

public class IntList {
	int val;
	IntList nextIntList;

	public IntList(int value, IntList nextIntList) {
		this.val = value;
		this.nextIntList = nextIntList;
	}

	public int size() {
		if (nextIntList == null) {
			return 1;
		}

		return nextIntList.size() + 1;
	}

	public int get(int i) {
		if (i == 0) {
			return this.val;
		}

		return this.nextIntList.get(i - 1);

	}

	public int getIterative(int i) {

		IntList target = this;
		while (i > 0 && target != null) {
			target = target.nextIntList;
			i -= 1;
		}
		return target.val;
	}


	/**
	 * 仔细看这个递归是从后往前建节点
	 */
	public IntList incrementRecursiveNoDestructive() {
		
		IntList ret = null;
		if (this.nextIntList != null) {
			ret = this.nextIntList.incrementRecursiveNoDestructive();
		}

		return new IntList(this.val + 1, ret);
	}

}