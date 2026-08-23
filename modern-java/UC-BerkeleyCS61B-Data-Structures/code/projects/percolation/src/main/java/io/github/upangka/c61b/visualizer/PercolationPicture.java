package io.github.upangka.c61b.visualizer;

import edu.princeton.cs.algs4.StdDraw;
import io.github.upangka.c61b.core.Percolation;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

import static io.github.upangka.c61b.config.AppConfig.SHOW_POS;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/23
 */
@Slf4j
public class PercolationPicture {
    private final Percolation percolation;
    private final int n;

    public PercolationPicture(int n) {
        percolation = new Percolation(n);
        this.n = n;
    }

    public void open(int dx, int dy) {
        // 转换坐标
        int x = n - dy - 1;
        int y = dx;
        log.info("点击坐标({},{}) -> 数组坐标[{}][{}]", dx, dy, x, y);
        percolation.open(x, y);
    }

    public void draw() {
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                boolean siteOpen = percolation.isOpen(x, y);
                boolean siteFull = percolation.isFull(x, y);

                if (siteOpen && siteFull) {
                    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                } else if (siteOpen) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                } else {
                    StdDraw.setPenColor(StdDraw.DARK_GRAY);
                }

                // 画图转换为StdDraw的坐标
                int dy = n - x - 1;
                int dx = y;

                // 从底部往上画
                StdDraw.filledSquare(0.5 + dx, 0.5 + dy, 0.49);
                if (SHOW_POS) {
                    int id = percolation.xyTo1D(x, y);
                    String content = "%d(%d,%d)".formatted(id, x, y);
                    StdDraw.setPenColor(Color.orange);
                    StdDraw.text(0.5 + dx, 0.5 + dy, content);
                }
            }
        }
    }
}
