package io.github.upangka.simulator;

import com.google.common.truth.Truth;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Map;

import static io.github.upangka.simulator.ParticleFlavor.*;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/17
 */
@Slf4j
@DisplayName("粒子行为测试")
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
	public void testMoveInto() {
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
	public void testFall() {
		// Arrange: Initialize a small 2x2 simulator
		var sim = new ParticleSimulator(2, 2);

		// --- Scenario 1: Fall into Empty Space ---
		// Setup: Place SAND at (0,1) and ensure (0,0) is EMPTY
		// Note that 0,0 is the bottom left, and 0,1 is the top left

		// 坐标轴以左下角为起点，水平为x轴，纵轴为y轴
		Particle[][] particles = sim.getParticles();
		particles[0][1] = new Particle(SAND);
		particles[0][0] = new Particle(EMPTY);

		// Get real neighbors for the particle at (0,1)
		var neighbors = sim.getNeighbors(0, 1);
		particles[0][1].fall(neighbors);

		// Assert:
		Truth.assertThat(particles[0][1].getFlavor()).isEqualTo(EMPTY);
		Truth.assertThat(particles[0][0].getFlavor()).isEqualTo(SAND);

		log.info("Particle `Fall` method: Good test");
	}

	@Test
	public void testFlow() {
		int moveLeftCount = 0, moveRightCount = 0, stayedCount = 0;

		for (int i = 0; i < 1000; i++) {
			var center = new Particle(WATER);
			var left = new Particle(EMPTY);
			var right = new Particle(EMPTY);

			var neighbors = Map.of(
					Direction.UP, new Particle(EMPTY),
					Direction.DOWN, new Particle(BARRIER),
					Direction.LEFT, left,
					Direction.RIGHT, right);

			center.flow(neighbors);

			// check where the water flow
			if (left.getFlavor() == WATER) {
				moveLeftCount += 1;
			} else if (right.getFlavor() == WATER) {
				moveRightCount += 1;
			} else if (center.getFlavor() == WATER) {
				stayedCount += 1;
			}
		}

		// 理想情况下，每个分支出现约 1000 / 3 ≈ 333 次。
		// 但随机数有波动，不可能每次刚好都是 333。240 是一个宽松的下限
		Truth.assertThat(moveLeftCount).isGreaterThan(240);
		Truth.assertThat(moveRightCount).isGreaterThan(240);
		Truth.assertThat(stayedCount).isGreaterThan(240);
		log.info("1/3 flow left/center/right: Good test");

		// --- Part 2: Verify Safety (Do not overwrite blocks) ---
		// We surround water with barriers and run flow() many times.
		// It should NEVER overwrite a barrier.
		for (int i = 0; i < 1000; i++) {
			var center = new Particle(WATER);
			var left = new Particle(BARRIER);
			var right = new Particle(BARRIER);

			var neighbors = Map.of(
					Direction.UP, new Particle(EMPTY),
					Direction.DOWN, new Particle(BARRIER),
					Direction.LEFT, left,
					Direction.RIGHT, right);

			center.flow(neighbors);

			Truth.assertThat(left.getFlavor()).isEqualTo(BARRIER);
			Truth.assertThat(right.getFlavor()).isEqualTo(BARRIER);
		}

		log.info("Particle `Flow` method: Good test");
	}

}
