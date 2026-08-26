package io.github.upangka.c61b.core;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/23
 */
public interface IPercolation {


    void open(int row, int col);

    boolean isOpen(int row, int col);

    boolean isFull(int row, int col);

    int numberOfOpenSites();

    boolean percolates();

    int xyTo1D(int x, int y);
}
