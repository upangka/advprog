1. 创建类 `LinkedListDeque61B<T>` 实现接口 `Deque61B<T>`，自动生成所有方法签名，并写好空的构造器
2. 构造器中创建哨兵节点，让它的 `next` 和 `prev` 都指向自身，`size` 初始化为 `0`
3. 实现 `addFirst` 和 `addLast`：不循环，`O(1)`
4. 实现 `toList()`：遍历链表，把元素逐个加入 `ArrayList`
5. 测试 `addFirst`、`addLast`、`toList`：跑通提供的测试
6. 实现 `isEmpty` 和 `size`：`O(1)`，`size` 直接从缓存返回
7. 实现 `getFirst` 和 `getLast`：`O(1)`，空列表返回 `null`
8. 实现 `get`（迭代版本）：遍历到指定索引，越界返回 `null`
9. 实现 `getRecursive`：递归版本，越界返回 `null`
10. 实现 `removeFirst` 和 `removeLast`：`O(1)`，空列表返回 `null`

# 构造函数

![](./images/sentinel_1.png)

```java
public LinkedListDeque61B(){
    var node = new Node<T>();
    this.sentinel = node;
    this.sentinel.prev = node;
    this.sentinel.item = null;
    this.sentinel.next = node;

    size = 0;
}
```

![](./images/sentinel_2.png)
