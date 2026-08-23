[Description of Project Percolation](https://sp26.datastructur.es/projects/proj3/)


# simple board

> StdDraw的基本研究：
> 
> 1. 左下角为坐标轴，不一定为(0,0)开始，这里设置为(-0.05*n,-0.05*n)
> 2. StdDraw的窗口大小不能改变，所以通过setXscale，setYscale等来映射 (0,5),代表从左到右坐标为0 -> 5

[SimpleBoardVisualizer.java](src/main/java/io/github/upangka/c61b/visualizer/SimpleBoardVisualizer.java)

```java
public class SimpleBoardVisualizer {
    private static final int DEFAULT_DISPLAY = 5;

    static void main() {
        int n = DEFAULT_DISPLAY;
        // 使用缓冲
        StdDraw.enableDoubleBuffering();
        // 等比例间距用乘法
        StdDraw.setXscale(-0.05d * n, 1.05 * n);
        StdDraw.setYscale(-0.05d * n, 1.05 * n);
        StdDraw.clear(Color.LIGHT_GRAY);
        var colors = List.of(Color.BLACK, Color.RED);
        while (true) {

            for (int x = 0; x < n; x++) {
                for (int y = 0; y < n; y++) {
                    // 方便颜色错开
                    int pos = (x + y);
                    StdDraw.setPenColor(colors.get(pos % 2));
                    // 从下往上画
                    StdDraw.filledSquare(0.5d + y, 0.5 + x, 0.49);
                    StdDraw.setPenColor(Color.WHITE);

                    // 显示文字
                    Character c = (char) ('A' + (x * n + y));
                    String content = c + "(%d,%d)".formatted(x, y);
                    StdDraw.text(0.5d + y, 0.5 + x, content);
                }
            }

            StdDraw.show();
            // 1s 60FPS
            StdDraw.pause(16);
        }
    }
}
```

![](./images/simple_board.png)

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

[backwash video hint 2](https://www.youtube.com/watch?v=gTqM4WvM9D8)

![](./images/backwash.png)

Hint:

- When checking percolates, we call `isConnected(virtualTop,virtualBottom)` on some DisjointSets object.
  - In the disjoint sets object, we want M,N,O and P to be connected to the virtual bottom.
- When checking `isFull`, we call `isConnected(virtualTop,X)` on some DisjointSets object:
  - In the disjoint sets object, we do NOT want M,N,O and P to be connected to the virtual bottom.


# 自己实现并查集

[WeightedQuickUnionFindC61B](./src/main/java/io/github/upangka/c61b/disjointset/WeightedQuickUnionFindC61B.java)底层是通过调用`findRoot`做**全路径压缩**

```java
private int findRoot(int p) {
    validate(p);
    List<Integer> accessEls = new ArrayList<>();
    int root = p;
    while (!isRoot(root)) {
        accessEls.add(root);
        root = parent[root];
    }

    // 完整路径压缩
    for (Integer access : accessEls) {
        parent[access] = root;
    }

    return root;
}
```

压缩测试[WeightedQuickUnionFindC61BTest.java](./src/test/java/io/github/upangka/c61b/disjointset/WeightedQuickUnionFindC61BTest.java)

```java
// 未调用isConnection的时候
var expected = "[-16, 0, 0, 0, 0, 1, 1, 1, 2, 2, 3, 5, 5, 6, 8, 11]";
Truth.assertThat(ds.toString()).isEqualTo(expected);
// 会产生路径压缩
Truth.assertThat(ds.isConnection(10,15)).isTrue();
expected = "[-16, 0, 0, 0, 0, 0, 1, 1, 2, 2, 0, 0, 5, 6, 8, 0]";
Truth.assertThat(ds.toString()).isEqualTo(expected);
```

![](./images/compress_path_1.png)
![](./images/compress_path_2.png)
![](./images/compress_path_3.png)

