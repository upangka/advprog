package io.github.upangka.simulator;

import com.google.common.truth.Truth;
import io.github.upangka.simulator.factory.ParticleSimulatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/17
 */
@Slf4j
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
        log.info("ParticleSimulator particles state to str: Good Tests");

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

        log.info("ParticleSimulator Factory create: Good Test");
    }


    @Test
    public void testFallVisual(){
        // Arrange: A 3x5 grid with sand(s) suspend over empty space(.)
        // and a barrier(b) at the bottom

        String initialBoard = """
            s.s
            s.s
            ...
            ...
            bbb
            """.trim();

        var sim = ParticleSimulatorFactory.create(initialBoard);

        // Act: Run 1 tick
        sim.tick();

        String expectedAfter1Tick = """
            ...
            s.s
            s.s
            ...
            bbb
            """.trim();

        Truth.assertThat(sim.toString().trim()).isEqualTo(expectedAfter1Tick);
        log.info("visual tick 1: Good Test");

        // Act: Run 2 tick
        sim.tick();

        String expectedAfter2Ticks = """
            ...
            ...
            s.s
            s.s
            bbb
            """.trim();

        Truth.assertThat(sim.toString().trim()).isEqualTo(expectedAfter2Ticks);
        log.info("visual tick 2: Good Test");

        // Act: Run 3 tick
        sim.tick();
        Truth.assertThat(sim.toString().trim()).isEqualTo(expectedAfter2Ticks);
        log.info("visual tick 3: Good Test");

        log.info("visual tick Good Test");
    }

    /**
     * Task 7: Making Water Flow
     */
    @Test
    public void testTickAndFlow(){
        // Arrange:
        // Col 0: Stacked Sand (s) on Barrier -> Should be Stable
        // Col 2: Water (w) on Barrier -> Should Flow
        // Col 4: Sand (s) in Air -> Should Fall
        String initialBoard = """
            s...s
            s.w..
            bbbbb
            """.trim();

        ParticleSimulator sim = ParticleSimulatorFactory.create(initialBoard);
        sim.tick();

        String expectedBoard = """
            s...s
            s.w..
            bbbbb
            """.trim();

    }

}
