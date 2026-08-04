# IntList

> "为什么需要 IntList？数组长度固定，无法动态增长。"

Java中**数组长度固定，无法动态增长**。所以采用链表的方式`IntList`是能够无线递增的数据结构。

IntList 虽然解决了数组‘长度固定’的痛点，但也带来了‘访问和计算长度慢’的新问题

1. 好处 (The Good)
   - **长度不固定**：只要内存够，你想加多少个元素都行，不用像数组那样一开始就得定死长度。

   - **实现简单**：`IntList` 就两个成员变量（`first` 和 `rest`），代码很直观。

2. 坏处 (The Bad)
   - **访问特定位置的元素很慢**：比如你想拿第 100 个元素，没办法直接跳到那里，必须从第 0 个开始，沿着 `rest` 指针一步步"爬"过去，这叫 **O(n)** 的时间复杂度。

   - **计算长度（`size`）也很慢**：不管是递归还是迭代的 `size()` 方法，都得把整个列表从头到尾数一遍。如果列表很长，就很耗时（也是 **O(n)**）。相比之下，数组的 `length` 属性是直接记录好的，看一眼就知道长度，瞬间完成（**O(1)**）。

[IntList.java](./code/intlist/core/IntList.java)的**递归实现**: `把一个大问题，拆解成一个更小、一模一样的子问题` 不能只看到递归，而是看到大问题拆解为小问题的背后思想

1. `size`就像课堂上老师让坐在后面的学生报出自己现在是第几排
2. `size`和`get`和`incrementRecursiveNoDestructive`都是利用`nextIntList`，并且在本方法栈中已经处理了结果，比如`get`，通过`i-1`进行变化

```java
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
```

测试[TestInitList.java](./code/intlist/TestInitList.java)

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS org.junit.jupiter:junit-jupiter:6.1.2
//DEPS com.google.truth:truth:1.4.5
//SOURCES ./**/IntList.java

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import com.google.common.truth.Truth;

import core.IntList;

public class TestInitList {
	private IntList L = null;

	@BeforeEach
	public void setup() {
		L = new IntList(67, null);
		L = new IntList(38, L);
		L = new IntList(1, L);
	}

	@Test
	public void testIntList() {
		int expected = 3;
		int actual = L.size();

		Truth.assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testGetIterative() {
		Truth.assertThat(L.getIterative(0)).isEqualTo(1);
		Truth.assertThat(L.getIterative(1)).isEqualTo(38);
		Truth.assertThat(L.getIterative(2)).isEqualTo(67);
	}

	@Test
	public void testIncrementRecursiveNoDestructive() {
		IntList newL = L.incrementRecursiveNoDestructive();

		Truth.assertThat(newL.size()).isEqualTo(L.size());
		Truth.assertThat(newL.get(0)).isEqualTo(2);
		Truth.assertThat(newL.get(1)).isEqualTo(39);
		Truth.assertThat(newL.get(2)).isEqualTo(68);

	}

}
```

# 思想

1. 把一个大问题，拆解成一个更小、一模一样的子问题
2. NoDestructive: 非破坏性，不改变原来的数据，重新返回一个新的数据
3. Destructive: 破坏性修改，会直接在原来的数据上进行修改
