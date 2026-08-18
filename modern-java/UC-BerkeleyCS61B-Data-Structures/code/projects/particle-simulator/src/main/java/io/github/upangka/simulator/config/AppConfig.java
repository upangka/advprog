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

    public static final Map<ParticleFlavor,Character> PARTICLE_TO_LETTER;

    static{
        PARTICLE_TO_LETTER = new HashMap<>();
        LETTER_TO_PARTICLE.forEach((k,v) -> PARTICLE_TO_LETTER.put(v,k));
    }

}
