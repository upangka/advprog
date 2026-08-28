
[LLRBs Demo](https://docs.google.com/presentation/d/1jgOgvx8tyu_LQ5Y21k4wYLffwp84putW8iD7_EerQmI/edit?slide=id.g4d0f5fdd87_0_127#slide=id.g4d0f5fdd87_0_127)

At its core, LLRBs are just a binary search tree, but there are a few additional invariants related to “coloring” each node red or black. This “coloring” creates a one-to-one mapping between 2-3 trees and LLRBs! 

**In particular, every 2-3 tree corresponds to exactly one LLRB, and vice-versa.**

> Rules

1. Right red link -> rotate left.
2. Two consecutive left links -> rotate right
3. Red left and red right -> color flip.

在 LLRB 的旋转（左旋或右旋）中，两个节点会**交换颜色**。新根的节点会继承原来根节点的颜色，而原来的根节点会变成红色。这个操作的目的是：在改变树的结构时，`保持这条路径上的“黑色节点数量”不变`。
![img.png](images/rotateleft1.png)
---
![img.png](images/rotateleft2.png)


color flip颜色翻转则不同于颜色交换

![img.png](images/colorflip.png)


# Rotate Left And Right

![img.png](images/rotateleftandright.png)

LL RB 树在插入后可能出现的“违规状态”。这些状态之间**有先后依赖关系**：

1. 先解决“右倾”问题：如果出现了右倾的红色链接，必须先左旋，把它转成左倾。如果不先处理右倾，后续的“连续左倾”和“双红”判断可能基于一个错误的树形结构，导致修复操作不再对应任何有效的 2-3 树状态。
2. 再解决“连续左倾”问题：左旋后可能产生两个连续的左倾红色链接，这时需要右旋来修正它。
3. 最后解决“双红”问题：前面的旋转修正了树形后，可能会出现一个节点有两个红色子节点的情况，这时用颜色翻转来模拟 2-3 树 4-节点的分裂。

```java
private RBTreeNode<T> fixUp(RBTreeNode<T> node) {
    // 情况1：右倾红色链接 → 左旋
    if (isRed(node.right) && !isRed(node.left)) {
        node = rotateLeft(node);
    }

    // 情况2：连续左倾红色链接 → 右旋
    if (hasTwoConsecutiveRedLeftLinks(node)) {
        node = rotateRight(node);
    }

    // 情况3：两个红色子节点 → 颜色翻转（模拟4-节点分裂）
    if (isRed(node.left) && isRed(node.right)) {
        flipColors(node);
    }
    return node;
}
```


# 案例

[LLRedBlackTreeTest.java](src/test/java/cs61b/LLRedBlackTreeTest.java)

## 案例1

从大到小一次插入7到1,最后得到一个非常平衡的搜索二叉树

![img.png](images/two_consecutive_red_left_links.png)


```java
    @Test
    @DisplayName("insert(upward propagation)")
    public void sanityTestInsertComplex() {
        LLRedBlackTree<Integer> rbTree = new LLRedBlackTree<>();
        rbTree.insert(7);
        rbTree.insert(6);
        rbTree.insert(5);
        rbTree.insert(4);
        rbTree.insert(3);
        rbTree.insert(2);
        rbTree.insert(1);

        /**
         *            (4)
         *             ├── (2)
         *             │   ├── (1)
         *             │   └── (3)
         *             └── (6)
         *                 ├── (5)
         *                 └── (7)
         */

        // 中序遍历会是1,2,3,4,5,6,7 并且此时树节点都是黑色
        Deque<LLRedBlackTree.RBTreeNode<Integer>> stack = new ArrayDeque<>();
        var expected = List.of(1, 2, 3, 4, 5, 6, 7);
        var actual = new ArrayList<Integer>();
        var currentNode = rbTree.root;
        while (currentNode != null || !stack.isEmpty()) {

            while (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.left;
            }

            currentNode = stack.pop();
            actual.add(currentNode.item);
            Truth.assertThat(currentNode.isBlack).isTrue();
            currentNode = currentNode.right;
        }

        Truth.assertThat(actual).isEqualTo(expected);
        log.info("insert 7 to 1: Good Test");
    }

```


## 案例2

![img.png](images/LLRBs_demo2.png)

```java
@Test
@DisplayName("最全案例包含rotateLeft,rotateRight,flipcolor (upward propagation)")
public void testInsertUpwardPropagation() {
    LLRedBlackTree<Integer> rbTree = new LLRedBlackTree<>();

    rbTree.insert(5);
    rbTree.insert(11);
    rbTree.insert(3);
    rbTree.insert(9);
    rbTree.insert(7);
    rbTree.insert(1);
    rbTree.insert(2);

    /**
     *  LLRB Tree Representation:
     *      (5)
     *      ├── (2)
     *      │   ├── (1)
     *      │   └── (3)
     *      └── (9)
     *          ├── (7)
     *          └── (11)
     */

    Deque<LLRedBlackTree.RBTreeNode<Integer>> stack = new ArrayDeque<>();
    var actual = new ArrayList<Integer>();
    var currentNode = rbTree.root;
    while (currentNode != null || !stack.isEmpty()) {

        while (currentNode != null) {
            stack.push(currentNode);
            currentNode = currentNode.left;
        }

        currentNode = stack.pop();
        actual.add(currentNode.item);
        Truth.assertThat(currentNode.isBlack).isTrue();
        currentNode = currentNode.right;
        log.info("insert 5,11,3,9,7,1,2: Good Test");
    }

}
```