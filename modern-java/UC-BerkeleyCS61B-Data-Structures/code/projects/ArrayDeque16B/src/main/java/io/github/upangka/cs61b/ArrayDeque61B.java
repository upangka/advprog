package io.github.upangka.cs61b;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/21
 */
@Slf4j
public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private static final int FACTOR = 2;
    /**
     * 使用率
     */
    private static final double USAGE_FACTOR = 0.25d;
    private static final int INITIAL_SIZE = 8;

    public ArrayDeque61B() {
        this(INITIAL_SIZE);
    }

    @SuppressWarnings("unchecked")
    public ArrayDeque61B(int capacity) {
        items = (T[]) new Object[capacity];
        nextFirst = initNextFirst(capacity);
        nextLast = nextFirst + 1;
        size = 0;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        doAdd(() -> {
            this.items[nextFirst] = x;
            this.nextFirst = Math.floorMod(nextFirst - 1, items.length);
        });
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        doAdd(() -> {
            this.items[nextLast] = x;
            this.nextLast = Math.floorMod(nextLast + 1, items.length);
        });
    }


    /**
     * 抽离添加元素的公共逻辑
     * size +1
     * 是否需要扩容
     */
    private void doAdd(Runnable runnable) {
        if (size == items.length) {
            resizeUp();
        }
        size += 1;
        runnable.run();
    }


    @SuppressWarnings("unchecked")
    private void resizeUp() {
        final int capacity = items.length * FACTOR;
        log.info("Resizing up from {} to {}", items.length, capacity);
        doResize(capacity);
    }

    private int initNextFirst(int capacity) {
        return capacity / 2;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        var ret = new ArrayList<T>();
//        for (int i = 0; i < size; i++) {
//            int idx = Math.floorMod(nextFirst + 1 + i, items.length);
//            ret.add(items[idx]);
//        }

        // 优化在实现了迭代器之后
        for (var item : this) {
            ret.add(item);
        }

        return ret;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Return the element at the front of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getFirst() {
        if (isEmpty()) {
            return null;
        }
        int firstIdx = Math.floorMod(this.nextFirst + 1, this.items.length);
        return items[firstIdx];
    }

    /**
     * Return the element at the back of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getLast() {
        if (isEmpty()) {
            return null;
        }
        int lastIdx = Math.floorMod(this.nextLast - 1, this.items.length);
        return items[lastIdx];
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        return doRemove(() -> {
            nextFirst = Math.floorMod(this.nextFirst + 1, items.length);
            var ret = items[nextFirst];
            items[nextFirst] = null;
            return ret;
        });
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        return doRemove(() -> {
            nextLast = Math.floorMod(this.nextLast - 1, items.length);
            var ret = items[nextLast];
            items[nextLast] = null;
            return ret;
        });
    }

    /**
     * 抽离公共逻辑
     * size -= 1;
     * 是否缩容
     */
    private T doRemove(Supplier<T> supplier) {
        if (size == 0) {
            return null;
        }

        if (shouldResizeDown()) {
            resizeDown();
        }
        var ret = supplier.get();
        this.size -= 1;
        return ret;
    }

    private boolean shouldResizeDown() {
        return items.length >= 16
                && (double) size / items.length <= USAGE_FACTOR;
    }

    @SuppressWarnings("unchecked")
    private void resizeDown() {
        final int capacity = items.length / 2;
        log.info("Resize down from {} to {}", items.length, capacity);
        doResize(capacity);
    }

    private void doResize(int capacity) {
        T[] resized = (T[]) new Object[capacity];
        int newNextFirst = initNextFirst(capacity);
        int newNextLast = newNextFirst + 1;

        for (int i = 0; i < size; i++) {
            T item = get(i);
            resized[newNextLast] = item;
            newNextLast = Math.floorMod(newNextLast + 1, capacity);
        }

        items = resized;
        nextFirst = newNextFirst;
        nextLast = newNextLast;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (index >= 0 && index < size) {
            int targetIndex = Math.floorMod(this.nextFirst + 1 + index, this.items.length);
            return items[targetIndex];
        }
        return null;
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for ArrayDeque61B.");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < this.items.length; i++) {
            if (items[i] != null) {
                sb.append(items[i].toString());
            } else {
                sb.append(" _ ");
            }
            if (i != this.items.length - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * 获取底层数组的最大容量
     */
    public int capacity() {
        return items.length;
    }


    private class ArrayDeque61BIterator implements Iterator<T> {
        private int idx;

        ArrayDeque61BIterator() {
            this.idx = 0;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return idx < size;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            return get(idx++);
        }
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<T> iterator() {
        return new ArrayDeque61BIterator();
    }
}
