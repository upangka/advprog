package io.github.upangka.c61b.core;

import io.github.upangka.c61b.disjointset.DisjointSet;
import io.github.upangka.c61b.disjointset.WeightedQuickUnionFindC61B;

import javax.swing.text.Position;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Percolation Model渗透模型
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/23
 */
public class Percolation {
    /**
     * 格子
     * 0 0 为底部
     */
    private final boolean[][] sites;
    private final int _n;
    /**
     * 虚拟顶部节点
     */
    private final int virtualTop;
    /**
     * 虚拟顶部节点
     */
    private final int virtualBottom;
    private final DisjointSet unionFinder;

    private static record Point(int x, int y) {
    }

    public Percolation(int n) {
        this._n = n;
        sites = new boolean[n][n];
        int lastIdx = xyTo1D(n - 1, n - 1);
        virtualTop = lastIdx + 1;
        virtualBottom = lastIdx + 2;
        unionFinder = new WeightedQuickUnionFindC61B(n * n + 2);
    }

    /**
     * 将二维数组坐标改为一维数组坐标
     *
     * @param x
     * @param y
     * @param rowBase 每行是多少个
     * @return
     */
    public int xyTo1D(int x, int y) {
        return x * _n + y;
    }


    public boolean isOpen(int x, int y) {
        return sites[x][y];
    }

    public boolean isFull(int x, int y) {
        int p = xyTo1D(x, y);
        return unionFinder.isConnection(virtualTop, p);
    }

    public void open(int x, int y) {
        sites[x][y] = true;

        int p = xyTo1D(x, y);

        getNeighbors(x, y).forEach(point -> {
            if (sites[point.x][point.y]) {
                int q = xyTo1D(point.x, point.y);
                unionFinder.connnect(p, q);
            }
        });

        // 处理顶部
        if (x == 0) {
            unionFinder.connnect(virtualTop, p);
        }
        // 处理底部
        if(x == _n - 1) {
            unionFinder.connnect(virtualBottom, p);
        }
    }



    private List<Point> getNeighbors(int x, int y) {
        List<Position> neighbors = new ArrayList<>();

        var directions = List.of(
                // UP
                new Point(x, y - 1),
                // DOWN
                new Point(x, y + 1),
                // LEFT
                new Point(x - 1, y),
                // RIGHT
                new Point(x + 1, y)
        );

        return directions.stream().filter(this::validate).toList();
    }


    private boolean validate(Point p) {
        return p.x >= 0 && p.x < _n && p.y >= 0 && p.y < _n;
    }

    public int numberOfOpenSites() {
        int ret = 0;
        for (int row = 0; row < this.sites.length; row++) {
            for (int col = 0; col < this.sites[row].length; col++) {
                if (sites[row][col]) {
                    ret += 1;
                }
            }
        }
        return ret;
    }

    public boolean percolates() {
        return isFull(virtualTop, virtualBottom);
    }

}
