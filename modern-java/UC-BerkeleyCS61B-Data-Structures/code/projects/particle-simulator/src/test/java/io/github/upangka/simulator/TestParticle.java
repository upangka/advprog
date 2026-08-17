package io.github.upangka.simulator;

import com.google.common.truth.Truth;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static io.github.upangka.simulator.ParticleFlavor.*;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/17
 */
@Slf4j
public class TestParticle {

    @Test
    public void testColor() {
        var particle = new Particle(EMPTY);
        Truth.assertThat(particle.color()).isEqualTo(Color.BLACK);

        particle.changeFlavor(SAND);
        Truth.assertThat(particle.color()).isEqualTo(Color.YELLOW);

        particle.changeFlavor(BARRIER);
        Truth.assertThat(particle.color()).isEqualTo(Color.GRAY);

        particle.changeFlavor(WATER);
        Truth.assertThat(particle.color()).isEqualTo(Color.BLUE);

        particle.changeFlavor(FOUNTAIN);
        Truth.assertThat(particle.color()).isEqualTo(Color.CYAN);

        particle.changeFlavor(PLANT);
        Truth.assertThat(particle.color()).isEqualTo(new Color(0, 255, 0));

        particle.changeFlavor(FIRE);
        Truth.assertThat(particle.color()).isEqualTo(new Color(255, 0, 0));

        particle.changeFlavor(FLOWER);
        Truth.assertThat(particle.color()).isEqualTo(new Color(255, 141, 161));

        log.info("Color: Good test");
    }

    @Test
    public void testMoveInto(){
        var particleA = new Particle(FIRE);
        particleA.setLifespan(10);

        var particleB = new Particle(EMPTY);
        particleB.setLifespan(-1);

        particleA.moveInto(particleB);

        Truth.assertThat(particleA.getFlavor()).isEqualTo(EMPTY);
        Truth.assertThat(particleA.getLifespan()).isEqualTo(-1);

        Truth.assertThat(particleB.getFlavor()).isEqualTo(FIRE);
        Truth.assertThat(particleB.getLifespan()).isEqualTo(10);

        log.info("Particle `MoveInto` method: Good test");
    }


    @Test
    public void testFall(){
        // Arrange: Initialize a small 2x2 simulator
        var sim = new ParticleSimulator(2,2);

        // --- Scenario 1: Fall into Empty Space ---
        // Setup: Place SAND at (0,1) and ensure (0,0) is EMPTY
        // Note that 0,0 is the bottom left, and 0,1 is the top left

        // 坐标轴以左下角为起点，水平为x轴，纵轴为y轴
        Particle[][] particles = sim.getParticles();
        particles[0][1] = new Particle(SAND);
        particles[0][0] = new Particle(EMPTY);

        // Get real neighbors for the particle at (0,1)
        var neighbors = sim.getNeighbors(0,1);
        particles[0][1].fall(neighbors);


        // Assert:
        Truth.assertThat(particles[0][1].getFlavor()).isEqualTo(EMPTY);
        Truth.assertThat(particles[0][0].getFlavor()).isEqualTo(SAND);

        log.info("Particle `Fall` method: Good test");
    }
}
