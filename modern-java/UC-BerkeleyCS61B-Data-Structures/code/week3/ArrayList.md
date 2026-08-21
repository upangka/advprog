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

# Generics: can store anything
