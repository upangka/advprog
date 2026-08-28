# Left Leaning Red-Black Trees(LLRB)

[B-Tree（2-3 树、2-3-4 树）](./B-Trees.md)，它解决了 [BST](./README.md) 不平衡的问题，但实现起来太麻烦。而 **左倾红黑树**（**LLRBs**）让 BST 保持平衡，同时实现起来和普通 BST 一样简单。

# 旋转Rotation

把一棵“歪脖子”树变成一棵“平衡”树。

[BST Rotation Game](https://joshh.ug/61b/bst_rotation/)

# BST与LLRB之间有一个glue link

A BST with left glue links that represents a 2-3 tree is often called a "Left Leaning Red Black Binary Search Tree" or LLRB

**3-节点** 是包含 `2 个键` + `3 个子指针` 的**整体容器**。

对应关系案例：
https://docs.google.com/presentation/d/1jgOgvx8tyu_LQ5Y21k4wYLffwp84putW8iD7_EerQmI/edit
