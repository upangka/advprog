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
    /**
     * 植物粒子存活个tick
     */
    public static final int PLANT_LIFESPAN = 150;
    /**
     * 花粒子存活个tick
     */
    public static final int FLOWER_LIFESPAN = 75;
    /**
     * 火粒子存活个tick
     */
    public static final int FIRE_LIFESPAN = 10;
    /**
     * BARRIER（障碍物）和 SAND（沙粒）都被设为 IMMORTAL，
     * 意思是它们不会像 PLANT（植物）、FLOWER（花）、FIRE（火）那样“活”一段时间后自然消失。
     */
    public static final int IMMORTAL = -1;
    /**
     * 花，植物，火 对应的存活时间
     */
    public static final Map<ParticleFlavor, Integer> LIFESPANS =
            Map.of(ParticleFlavor.FLOWER, FLOWER_LIFESPAN,
                    ParticleFlavor.PLANT, PLANT_LIFESPAN,
                    ParticleFlavor.FIRE, FIRE_LIFESPAN);

    static {
        PARTICLE_TO_LETTER = new HashMap<>();
        LETTER_TO_PARTICLE.forEach((k, v) -> PARTICLE_TO_LETTER.put(v, k));
    }

}
