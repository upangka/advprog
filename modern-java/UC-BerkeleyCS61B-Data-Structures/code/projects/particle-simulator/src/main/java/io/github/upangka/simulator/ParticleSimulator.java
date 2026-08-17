package io.github.upangka.simulator;

import edu.princeton.cs.algs4.StdDraw;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/17
 */
@Getter
public class ParticleSimulator {
    private final int width;
    private final int height;
    private final Particle[][] particles;


    public ParticleSimulator(int w, int h) {
        this.width = w;
        this.height = h;
        this.particles = new Particle[w][h];

        traverseParticles((x, y) -> particles[x][y] = new Particle(ParticleFlavor.EMPTY));
    }


    public void drawParticles() {

        traverseParticles((x, y) -> {
            var particle = particles[x][y];
            StdDraw.setPenColor(particle.color());
            // 以半边长为0.5,正方形的中心画一个正方形
            StdDraw.filledSquare(x + 0.5d, y + 0.5d, 0.5d);
        });
    }


    public void traverseParticles(BiConsumer<Integer, Integer> consumer) {
        for (int x = 0; x < particles.length; x++) {
            for (int y = 0; y < particles[0].length; y++) {
                consumer.accept(x, y);
            }
        }

    }

    public void changeParticleFlavor(int x, int y, ParticleFlavor flavor) {
        if (validPosition(x, y)) {
            var particle = particles[x][y];
            if (particle != null) {
                particle.changeFlavor(flavor);
            }
        }
    }

    public void trick(){

    }
    /** 类似`毒丸` */
    public static final Particle SENTINEL = new Particle(ParticleFlavor.BARRIER);
    public Map<Direction, Particle> getNeighbors(int x, int y) {

        var ret = new HashMap<Direction, Particle>();

        Map<Direction, List<Integer>> directions = Map.of(
                Direction.UP, List.of(x, y + 1),
                Direction.DOWN, List.of(x, y - 1),
                Direction.LEFT, List.of(x - 1, y),
                Direction.RIGHT, List.of(x + 1, y));

        directions.forEach((direction, position) -> {
            int neighborX = position.get(0);
            int neighborY = position.get(1);
            if (validPosition(neighborX, neighborY)) {
                ret.put(direction, particles[neighborX][neighborY]);
            } else {
                ret.put(direction, SENTINEL);
            }
        });

        return ret;
    }


    private boolean validPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }


    /**
     * 在模拟器、游戏和动画程序里，{@code tick} 指的是模拟世界的一次“心跳”或“步进”。每发生一次 {@code tick}，
     * 就代表整个模拟世界前进了一个时间单位。
     *
     * 你可以把它想象成一个时钟的滴答声——每滴答一下，所有粒子就更新一次状态
     */
    public void tick(){
        traverseParticles((x, y) -> {
            var particle = particles[x][y];
            var neighbors = getNeighbors(x, y);
            particle.action(neighbors);
        });
    }

    static void main() {
        var ret = new ParticleSimulator(150, 150);
        System.out.println(Arrays.toString(ret.particles));
    }


}
