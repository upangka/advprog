
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