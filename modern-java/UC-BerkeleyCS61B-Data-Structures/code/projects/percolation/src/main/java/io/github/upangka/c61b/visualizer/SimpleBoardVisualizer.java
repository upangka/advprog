package io.github.upangka.c61b.visualizer;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.util.List;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/23
 */
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
