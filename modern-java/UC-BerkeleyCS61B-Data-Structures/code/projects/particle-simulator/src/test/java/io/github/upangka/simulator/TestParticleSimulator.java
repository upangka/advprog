package io.github.upangka.simulator;

import com.google.common.truth.Truth;
import io.github.upangka.simulator.factory.ParticleSimulatorFactory;
import org.junit.jupiter.api.Test;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/17
 */
public class TestParticleSimulator {

    /**
     * 坐标轴
     * y
     * ^
     * |
     * |   (0,3)  (1,3)  (2,3)  (3,3)
     * |   (0,2)  (1,2)  (2,2)  (3,2)
     * |   (0,1)  (1,1)  (2,1)  (3,1)
     * |   (0,0)  (1,0)  (2,0)  (3,0)
     * |
     * +----------------------------------> x
     */
    @Test
    public void testVisual() {
        var expectBoard = """
                bbb
                pfz
                fb.
                """.trim();

        var sim = new ParticleSimulator(3, 3);

        Particle[][] particles = sim.getParticles();
        particles[0][0] = new Particle(ParticleFlavor.FIRE);
        particles[1][0] = new Particle(ParticleFlavor.BARRIER);
        particles[2][0] = new Particle(ParticleFlavor.EMPTY);

        particles[0][1] = new Particle(ParticleFlavor.PLANT);
        particles[1][1] = new Particle(ParticleFlavor.FIRE);
        particles[2][1] = new Particle(ParticleFlavor.FLOWER);

        particles[0][2] = new Particle(ParticleFlavor.BARRIER);
        particles[1][2] = new Particle(ParticleFlavor.BARRIER);
        particles[2][2] = new Particle(ParticleFlavor.BARRIER);


        var actualState = sim.toString().trim();
        Truth.assertThat(actualState).isEqualTo(expectBoard);
        System.out.println("ParticleSimulator particles state to str: Good Tests");

    }

    @Test
    public void testCreateParticleSimulatorFromStringBoard(){
        var expertBoard = """
                ...
                .w.
                ...
                bbb
                """.trim();

        ParticleSimulator sim = ParticleSimulatorFactory.create(expertBoard);
        Truth.assertThat(sim.toString().trim()).isEqualTo(expertBoard);

        System.out.println("ParticleSimulator Factory create: Good Test");
    }

}
