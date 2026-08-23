package io.github.upangka.c61b.visualizer;

import edu.princeton.cs.algs4.StdDraw;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

import static io.github.upangka.c61b.config.AppConfig.BIAS_BASE;

@Slf4j
public class InteractivePercolationVisualizer {
    void main(String[] args) {

//        int n = SIZE;
        int n = 5;
        PercolationPicture pp = new PercolationPicture(n);

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-BIAS_BASE * n, (1.0d + BIAS_BASE) * n);
        StdDraw.setYscale(-BIAS_BASE * n, (1.0d + BIAS_BASE) * n);
        StdDraw.clear(Color.LIGHT_GRAY);
        pp.draw();
        // 记录上次
        int lastClickX = -1,lastClickY = -1;

        while (true) {
            if(StdDraw.isMousePressed()){
                int x = (int)StdDraw.mouseX();
                int y = (int)StdDraw.mouseY();
                if(x >= 0 && y >= 0 && x < n && y < n){
                   if(x != lastClickX && y != lastClickY){
                       pp.open(x,y);
                       lastClickX = x;
                       lastClickY = y;
                       pp.draw();
                   }
                }
            }else{
                lastClickX = lastClickY = -1;
            }
            StdDraw.show();
            // 1s 60FPS
            StdDraw.pause(16);
        }
    }
}