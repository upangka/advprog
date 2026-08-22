package io.github.upangka.c61b.disjointset;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 自定实现的并查集
 * 1. 带有weight(size)
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/22
 */
public class WeightedQuickUnionFindC61B implements DisjointSet {

    /**
     * 下标： 代表元素的值（元素本身为0,1,2,3..）
     * 值：
     * 有parent为parent的id
     * 本身是root的为 -size
     *
     */
    private int[] parent;

    /**
     * @param N 元素数量
     */
    public WeightedQuickUnionFindC61B(int N) {
        parent = new int[N];
        Arrays.fill(parent, -1);
    }

    /**
     * 判断两个元素是否联通
     *
     * @param p 元素1
     * @param q 元素2
     * @return true 联通 | false 没有联通
     */
    @Override
    public boolean isConnection(int p, int q) {
        return findRoot(p) == findRoot(q);
    }

    /**
     * 连接两个元素
     *
     * @param p 元素1
     * @param q 元素2
     */
    @Override
    public void connnect(int p, int q) {
        int pAtRoot = findRoot(p);
        int qAtRoot = findRoot(q);

        if (pAtRoot == qAtRoot) {
            return;
        }

        int pAtRootSize = getSize(pAtRoot);
        int qAtRootSize = getSize(qAtRoot);

        // Always link root of smaller tree to larger tree
        int smallRoot = pAtRoot, bigRoot = qAtRoot;

        if (pAtRootSize >= qAtRootSize) {
            smallRoot = qAtRoot;
            bigRoot = pAtRoot;
        }

        parent[smallRoot] = bigRoot;
        // set size
        parent[bigRoot] = -(pAtRootSize + qAtRootSize);

    }


    /**
     * 找到元素p所在的root元素
     *
     * @return root元素
     */
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


    private int getSize(int idx) {
        return Math.abs(parent[idx]);
    }

    /**
     * 判断元素是否为root
     *
     * @param idx 下标
     */
    private boolean isRoot(int idx) {
        return parent[idx] < 0;
    }

    private void validate(int p) {
        int n = parent.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("index " + p + " is not between 0 and " + (n - 1));
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(parent);
    }
}
