# AList

模仿`java.util.ArrayList`，理解动态数组的工作原理

在 AList 中，`size` 是边界，它既是指示器，又是哨兵。**有效元素总是从 `0` 到 `size - 1`，而 `size` 就是下一个空位的索引**

1. The position of the next item to be inserted is always `size`.
   下一个要插入的元素的位置始终是 `size`。
2. `size` is always the number of items in the AList.
   `size` 始终是 `AList` 中元素的数量。
3. The last item in the list is always in position `size - 1`.
   列表中的最后一个元素始终在位置 `size - 1`。

基本实现：

[AList.java](./code/alist/AList.java)

```java
public class AList<T> {
	private int[] items;
	private int size;

	public AList(){
		this.items = new int[2];
		this.size = 0;
	}

	public void addLast(int val){
		this.items[size] = val;
		this.size++;
	}

	public int get(int idx){
		if(idx > -1 && idx < size){
			return this.items[idx];
		}
		throw new IndexOutOfBoundsException();
	}

	public int size(){
		return this.size;
	}
}
```

# Resizing (Expanding array to unlimited elements)

[AList.java](./code/alist/AList.java)

```java
private void resize(int capacity){
    int[] resized = new int[capacity];
    // copy over the array items
    System.arraycopy(this.items, 0, resized, 0, this.size);
    this.items = resized;
}

public void addLast(int val){
    // When the array is too full - resize
    if(size == this.items.length){
        System.out.printf("Need resize... when add %d%n",val);
        resize(size * 2);
    }

    this.items[size] = val;
    this.size += 1;
}
```

## Tradeoffs between time and space efficiency

使用率 `R = size / items.length`,避免浪费内存

[AList.java](./code/alist/AList.java)

```java
public int removeLast(){
    var ret = this.items[size - 1];
    size = -1;

    // R < 0.25 意味着 size / items.length < 0.25
    // 即数组使用率过低，需要缩容
    if((double)size / items.length < 0.25){
        resize(size / 2); // 缩容：R 从 0.24 变成 0.48
    }
    return ret;
}
```

# Generics: can store anything

当使用泛型的时候`new T[2]`Java不允许这种语法，为了支持我们的泛型，这里使用强制类型转化`(T[])new Object[2]`，尽管有警告`warning`，但是我们可以选择忽略

[AList_version2.java](./code/alist/AList_version2.java)

```java
	@SuppressWarnings("unchecked")
    public AList(){
		this.items = (T[])new Object[2];
		this.size = 0;
	}
```

使用泛型存储的是对象不再是原始的数据类型，当删除元素的时候，需要设置元素所在数组的位置为null,去掉这个引用方便Java GC

```java
	public T removeLast(){
		var ret = this.items[size - 1];
        // 方便Java GC
        this.items[size - 1] = null;
		size = -1;

		// R < 0.25 意味着 size / items.length < 0.25
        // 即数组使用率过低，需要缩容
		if((double)size / items.length < 0.25){
			resize(size / 2); // 缩容：R 从 0.24 变成 0.48
		}
		return ret;
	}
```
