package io.github.upangka.simulator.factory;

import io.github.upangka.simulator.Particle;
import io.github.upangka.simulator.ParticleSimulator;

import static io.github.upangka.simulator.config.AppConfig.LETTER_TO_PARTICLE;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/18
 */
public class ParticleSimulatorFactory {

    /**
     *       bbb
     *       pfz
     *       fb.
     */
    public static ParticleSimulator create(String board){
        var lines = board.trim().split("\\n");
        var height = lines.length;
        var width = lines[0].trim().length();

        var sim = new  ParticleSimulator(width, height);
        Particle[][] particles = sim.getParticles();
        for (int y = height - 1; y >= 0 ; y--) {
            String line = lines[y].trim();
            for (int x = 0; x < width; x++) {
                var flavor = LETTER_TO_PARTICLE.get(line.charAt(x));
                particles[x][height - y - 1] = new Particle(flavor);
            }
        }
        return sim;
    }

}
