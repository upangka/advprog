[Description of Project Percolation](https://sp26.datastructur.es/projects/proj3/)

# 二维转一维

![](./images/xyTo1D.png)


# Virtual Sites

One approach to making things fast is to create:
1. Virtual top site connected to all open items in top row.
2. Similar virtual site at bottom.

To check `isFull`:
- Check for a connection to top site.

To check percolates:
- Check for connection between top and bottom sites.

![](./images/virtual_sites.png)


# Backwash问题

当你同时使用顶部和底部虚拟节点时，会出现一种现象：系统渗透后，所有与底部虚拟节点连通的开放格子，都会通过**底部 → 虚拟底部 → 虚拟顶部 → 顶部的路径**，被错误地认为是“满的”。

也就是说，原本只是“与底部连通但不与顶部连通”的格子，在系统渗透后会被错误地标记为“满”。这就是 `backwash`（回流）。它会让你在执行 `isFull` 时得到错误的结果——那些只在底部连通而不在顶部的格子也会被标记为“满”。

![](./images/backwash.png)