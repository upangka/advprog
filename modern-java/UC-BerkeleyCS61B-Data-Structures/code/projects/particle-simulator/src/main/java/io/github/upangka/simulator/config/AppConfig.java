package io.github.upangka.simulator.config;

import io.github.upangka.simulator.ParticleFlavor;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/18
 */
public class AppConfig {

    /**
     * 键盘的key对应的粒子的类型
     */
    public static final Map<Character, ParticleFlavor> LETTER_TO_PARTICLE = Map.of(
            's', ParticleFlavor.SAND,
            'b', ParticleFlavor.BARRIER,
            'w', ParticleFlavor.WATER,
            'p', ParticleFlavor.PLANT,
            'f', ParticleFlavor.FIRE,
            '.', ParticleFlavor.EMPTY,
            'n', ParticleFlavor.FOUNTAIN,
            'z', ParticleFlavor.FLOWER
    );

    /**
     * 粒子类型对应键盘的key
     */
    public static final Map<ParticleFlavor, Character> PARTICLE_TO_LETTER;

    static {
        PARTICLE_TO_LETTER = new HashMap<>();
        LETTER_TO_PARTICLE.forEach((k, v) -> PARTICLE_TO_LETTER.put(v, k));
    }

    /**
     * 植物粒子存活个tick
     */
    private static final int PLANT_LIFESPAN = 150;
    /**
     * 花粒子存活个tick
     */
    private static final int FLOWER_LIFESPAN = 75;
    /**
     * 火粒子存活个tick
     */
    private static final int FIRE_LIFESPAN = 10;

    public static final Map<ParticleFlavor, Integer> LIFESPANS =
            Map.of(ParticleFlavor.FLOWER, FLOWER_LIFESPAN,
                    ParticleFlavor.PLANT, PLANT_LIFESPAN,
                    ParticleFlavor.FIRE, FIRE_LIFESPAN);

}
