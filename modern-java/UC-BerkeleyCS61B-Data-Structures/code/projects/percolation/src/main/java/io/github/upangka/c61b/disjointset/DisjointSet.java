package io.github.upangka.c61b.disjointset;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/23
 */
public interface DisjointSet {

    /**
     * 判断两个元素是否联通
     * @param p 元素1
     * @param q 元素2
     * @return true 联通 | false 没有联通
     */
    boolean isConnection(int p, int q);

    /**
     * 连接两个元素
     * @param p 元素1
     * @param q 元素2
     */
    void connnect(int p, int q);
}
