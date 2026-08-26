
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