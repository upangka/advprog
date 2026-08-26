![](./images/demo.png)
# BSTMap

A BST-based implementation of Map(a basic tree-based)


# 关于size的处理

在增加新元素的时候，`size++`,我最初的想法是在Java中内部类能够访问外部类的属性，原本打算在内部类处理size++，但是这样方法就不能是静态方法了，内部类也不能处理成静态内部类。

```java
private <K extends Comparable<? super K>, V> BSTNode<K,V> insert(BSTNode<K,V> bstNode,K sk,V val) {
    // Always insert at leaf node
    if (bstNode == null) {
        // 在这里添加size++
        BSTMap.this.size += 1;
        return new BSTNode<K, V>(sk, val);
    }
    // ...
}
```

下面是标记处理size时机：

| 方法                      | 可读性   | 性能    | 推荐程度                       |
|-------------------------|----------|---------|--------------------------------|
| 先 `containsKey` 再 `insert` | ⭐⭐⭐⭐⭐   | ⭐⭐⭐⭐⭐  | ✅ 强烈推荐                     |
| 用 `boolean[]` 作为输出参数      | ⭐⭐⭐     | ⭐⭐⭐⭐⭐  | ❌ 不推荐（过于 trick）         |
| 用自定义 `InsertResult` 类     | ⭐⭐⭐     | ⭐⭐⭐⭐⭐  | ⚠️ 可行，但在此场景下过设计     |
|                         |       |       |                            |

# 迭代器的实现

版本一: 在构建迭代器的时候，已经收集了所有元素，缺点也很明显，就是如果有大量元素，会造成不必要的内存浪费。
```java
private class BSTMapIterator implements Iterator<K> {
        private K[] keys;
        private int index;

        private BSTMapIterator() {
            this.keys = (K[]) new Comparable[size()];
            this.index = 0;
            inorderInit();
        }

        private void inorderInit() {
            Deque<BSTNode<K, V>> stack = new ArrayDeque<>();
            var current = root;
            int idx = 0;
            while (current != null || !stack.isEmpty()) {
                // 一路向左遍历
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }
                // 访问
                current = stack.pop();
                keys[idx++] = current.key;
                current = current.right;
            }

        }
        
        @Override
        public boolean hasNext() {
            return index < keys.length;
        }
        
        @Override
        public K next() {
            return keys[index++];
        }
    }
```

版本二: 使用栈进行优化，每当取走一个元素的时候才进行处理

```java
private class BSTMapIteratorV2 implements Iterator<K> {
    private Deque<BSTNode<K, V>> stacks = new ArrayDeque<>();

    private BSTMapIteratorV2() {
        pushLeft(root);
    }

    private void pushLeft(BSTNode<K, V> node) {
        while(node != null){
            stacks.push(node);
            node = node.left;
        }
    }

    @Override
    public boolean hasNext() {
        return !stacks.isEmpty();
    }


    @Override
    public K next() {
        if(!hasNext()){
            throw new NoSuchElementException();
        }
        var current = stacks.pop();
        pushLeft(current.right);
        return current.key;
    }
}
```


# ULLMap的递归

```java
Entry get(K sk){
    if(sk != null && sk.equals(key)){
        return this;
    }
    if(next == null){
        return null;
    }
    return next.get(sk);
}
```

`put`方法进行更新和新增

```java
 public void put(K key, V value) {

    var entry = sentinel.get(key);
    if(entry == null){
        sentinel.next = new Entry(key,value,sentinel.next);
        size += 1;
    }else{
        entry.value = value;
    }

}
```