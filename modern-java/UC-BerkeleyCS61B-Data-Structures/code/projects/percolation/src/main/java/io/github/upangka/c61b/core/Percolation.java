package io.github.upangka.c61b.core;

import io.github.upangka.c61b.disjointset.DisjointSet;
import io.github.upangka.c61b.disjointset.WeightedQuickUnionFindC61B;

/**
 * Percolation Model渗透模型
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/23
 */
public class Percolation {
    // 格子
    private final int[][] sites;
    /** 虚拟顶部节点 */
    private final int virtualTop;
    /** 虚拟顶部节点 */
    private final int virtualBottom;
    private final DisjointSet unionFinder;

    public Percolation(int n) {
        sites = new int[n][n];
        int lastIdx = xyTo1D(n-1,n-1,n);
        virtualTop = lastIdx + 1;
        virtualBottom = lastIdx + 2;
        unionFinder = new WeightedQuickUnionFindC61B(n+2);
    }

    /**
     * 将二维数组坐标改为一维数组坐标
     * @param x
     * @param y
     * @param rowBase 每行是多少个
     * @return
     */
    private int xyTo1D(int x,int y,int rowBase){
        return x * rowBase + y;
    }

}
