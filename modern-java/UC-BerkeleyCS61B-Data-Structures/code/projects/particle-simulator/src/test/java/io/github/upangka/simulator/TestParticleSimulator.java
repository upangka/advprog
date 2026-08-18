package io.github.upangka.simulator;

import com.google.common.truth.Truth;
import io.github.upangka.simulator.factory.ParticleSimulatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public void testCreateParticleSimulatorFromStringBoard() {
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
    public void testFallVisual() {
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
    public void testTickAndFlow() {
        // Arrange:
        // Col 0: Stacked Sand (s) on Barrier -> Should be Stable
        // Col 2: Water (w) on Barrier -> Should Flow
        // Col 4: Sand (s) in Air -> Should Fall
        String initialBoard = """
                s...s
                s.w..
                bbbbb
                """.trim();


        // Possibility 1: Water stays put (or moves Right then Left)
        // Sand falls.
        String expectStay = """
                s....
                s.w.s
                bbbbb
                """.trim();

        // Possibility 2: Water flows Left.
        // Sand falls.
        String expectLeft = """
                s....
                sw..s
                bbbbb
                """.trim();

        // Possibility 3: Water flows Right ONCE (Right then Stay).
        // Sand falls.
        String expectRightSingle = """
                s....
                s..ws
                bbbbb
                """.trim();

        // Possibility 4: Water flows Right TWICE (Right then Right).
        // Water ends up under the Sand (at 4,1), blocking the Sand at (4,2).
        // 因为粒子系统是从下到上，从左到右遍历的
        String expectRightDouble = """
                s...s
                s...w
                bbbbb
                """.trim();

        int moveLeftCount = 0, stayedCount = 0, moveRightCount = 0, moveRightDoubleCount = 0;
        for (int i = 0; i < 1000; i++) {
            ParticleSimulator sim = ParticleSimulatorFactory.create(initialBoard);
            sim.tick();

            var ret = sim.toString().trim();

            if (ret.equals(expectStay)) {
                stayedCount += 1;
            } else if (ret.equals(expectLeft)) {
                moveLeftCount += 1;
            } else if (ret.equals(expectRightSingle)) {
                moveRightCount += 1;
            } else if (ret.equals(expectRightDouble)) {
                moveRightDoubleCount += 1;
            }
        }

        // Assert:
        // 1. Left (~33%): > 240 is safe.
        Truth.assertThat(moveLeftCount).isGreaterThan(240);

        // 2. Stay (~44%): 1/3 (Stay) + 1/9 (Right-then-Left) = 4/9. > 240 is safe.
        Truth.assertThat(stayedCount).isGreaterThan(240);

        // 3. Right Single (~11%): 1/3 (Right) * 1/3 (Stay) = 1/9.
        // Expected ~111. Threshold 50 is safe.
        Truth.assertThat(moveRightCount).isGreaterThan(50);

        // 4. Right Double (~11%): 1/3 (Right) * 1/3 (Right) = 1/9.
        // Expected ~111. Threshold 50 is safe.
        Truth.assertThat(moveRightDoubleCount).isGreaterThan(50);

        log.info("Particle system move around special water particle: Good Test");
    }


    @Test
    @DisplayName("Task 8: Making Plants (and flowers) Grow")
    public void testGrow() {
        String initialState = """
                ...
                .p.
                bbb
                """.trim();


        // The list of REQUIRED growth outcomes
        List<String> expectedGrowthStates = new ArrayList<>();

        expectedGrowthStates.add("""
                ...
                .p.
                bbb
                """.trim()); // no growth

        expectedGrowthStates.add("""
                ...
                pp.
                bbb
                """.trim()); // Left

        expectedGrowthStates.add("""
                .p.
                .p.
                bbb
                """.trim()); // Up

        expectedGrowthStates.add("""
                pp.
                .p.
                bbb
                """.trim()); // Up + Left

        expectedGrowthStates.add("""
                ...
                .pp
                bbb
                """.trim()); // Right

        expectedGrowthStates.add("""
                ..p
                .pp
                bbb
                """.trim()); // Right + Up

        expectedGrowthStates.add("""
                .p.
                .pp
                bbb
                """.trim()); // Up, Right (fall)

        expectedGrowthStates.add("""
                .pp
                .pp
                bbb
                """.trim()); // Right, Up, Left


        // --- ACT ---
        Set<String> observedStates = new HashSet<>();

        for (int i = 0; i < 10000; i++) {
            ParticleSimulator sim = ParticleSimulatorFactory.create(initialState);
            sim.tick();
            observedStates.add(sim.toString().trim());
        }

        // --- ASSERT 1: CHECK FOR MISSING STATES ---
        for (String expected : expectedGrowthStates) {
            Truth.assertWithMessage("""
                            Test Failed: A required growth state was never observed.
                            Missing State:
                            %s
                            """, expected)
                    .that(observedStates)
                    .contains(expected);
        }

        // --- ASSERT 2: CHECK FOR UNEXPECTED (INVALID) STATES ---

        // Create a "White List" of all valid outcomes (Growth + No Change)
        Set<String> validStates = new HashSet<>(expectedGrowthStates);

        for (String observed : observedStates) {
            Truth.assertWithMessage("""
                            Test Failed: An invalid/impossible state was generated.
                            Unexpected State:
                            %s
                            """, observed)
                    .that(validStates)
                    .contains(observed);
        }

        log.info("Plants (and flowers) Grow: Good Test");
    }
}
