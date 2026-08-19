package io.github.upangka.simulator;

import io.github.upangka.simulator.util.RandomUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.Map;

import static io.github.upangka.simulator.ParticleFlavor.*;
import static io.github.upangka.simulator.config.AppConfig.IMMORTAL;
import static io.github.upangka.simulator.config.AppConfig.LIFESPANS;

/**
 * 粒子
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/17
 */
@Slf4j
@Getter
@Setter
public class Particle {

    private ParticleFlavor flavor;
    private int lifespan;

    public Particle(ParticleFlavor flavor) {
        changeFlavor(flavor);
    }

    public Color color() {
        return switch (flavor) {
            case EMPTY -> Color.BLACK;
            case SAND -> Color.YELLOW;
            case BARRIER -> Color.GRAY;
            case WATER -> Color.BLUE;
            case FOUNTAIN -> Color.CYAN;
            case PLANT -> new Color(0, 255, 0);
            case FIRE -> new Color(255, 0, 0);
            case FLOWER -> new Color(255, 141, 161);
        };
    }

    public void moveInto(Particle other) {
        other.lifespan = lifespan;
        other.flavor = flavor;

        this.flavor = EMPTY;
        this.lifespan = -1;
    }

    public void changeFlavor(ParticleFlavor flavor) {
        this.setFlavor(flavor);
        this.setLifespan(LIFESPANS.getOrDefault(flavor, IMMORTAL));
    }


    public void fall(Map<Direction, Particle> neighbors) {
        Particle other = neighbors.get(Direction.DOWN);
        if (other.getFlavor() == EMPTY) {
            this.moveInto(other);
        }
    }

    /**
     * tick 会调用所有粒子的action方法，从而决定粒子在本次tick中行为
     *
     * @param neighbors
     */
    public void action(Map<Direction, Particle> neighbors) {

        // If the flavor of the current particle is EMPTY, return immediately.
        if (this.flavor == EMPTY) {
            return;
        }

        // If the flavor of the current particle is not BARRIER, call fall.
        if (this.flavor != BARRIER) {
            this.fall(neighbors);
        }

        // If the flavor of the current particle is WATER, call flow.
        if (this.flavor == WATER) {
            flow(neighbors);
        }

        if (this.flavor == FLOWER || this.flavor == PLANT) {
            grow(neighbors);
        }

    }

    /**
     * With 1/3 chance, don’t do anything.
     * With 1/3 chance, if the left neighbor is empty, moveInto it.
     * With 1/3 chance, if the right neighbor is empty, moveInto it.
     */
    public void flow(Map<Direction, Particle> neighbors) {

        int chance = RandomUtil.nextInt(0, 2);
        if (chance == 0) {
            // do anything
            return;
        } else if (chance == 1) {
            Particle other = neighbors.get(Direction.LEFT);
            if (other.getFlavor() == EMPTY) {
                moveInto(other);
            }
        } else if (chance == 2) {
            Particle other = neighbors.get(Direction.RIGHT);
            if (other.getFlavor() == EMPTY) {
                moveInto(other);
            }
        }
    }

    /**
     * Making Plants (and flowers) Grow
     */
    public void grow(Map<Direction, Particle> neighbors) {
        int num = RandomUtil.nextInt(1, 10);

        if (num == 1) {
            // With 10% chance, if the UP neighbor has flavor EMPTY, set the flavor of the up neighbor to the same flavor as the current particle.
            var upParticle = neighbors.get(Direction.UP);
            if (upParticle.getFlavor() == EMPTY) {
                upParticle.setFlavor(this.flavor);
                upParticle.setLifespan(LIFESPANS.get(this.flavor));
            }
        } else if (num == 2) {
            // With 10% chance, if the LEFT neighbor has flavor EMPTY, set the flavor of the LEFT neighbor to the same flavor as the current particle.
            var leftParticle = neighbors.get(Direction.LEFT);
            if (leftParticle.getFlavor() == EMPTY) {
                leftParticle.setFlavor(this.flavor);
                leftParticle.setLifespan(LIFESPANS.get(this.flavor));
            }

        } else if (num == 3) {
            // With 10% chance, if the RIGHT neighbor has flavor EMPTY, set the flavor of the RIGHT neighbor to the same flavor as the current particle.
            var rightParticle = neighbors.get(Direction.RIGHT);
            if (rightParticle.getFlavor() == EMPTY) {
                rightParticle.setFlavor(this.flavor);
                rightParticle.setLifespan(LIFESPANS.get(this.flavor));
            }
        } else {
            // With 70% chance do none of the above.

        }


    }

    public void decrementLifespan() {
        if (this.lifespan > 0) {
            // If the lifespan of the current particle is greater than 0, subtract 1 from the lifespan.
            setLifespan(lifespan - 1);
        }

        if (this.lifespan == 0) {
            //  If the lifespan of the current particle is zero, set its flavor to EMPTY and its lifespan to -1.
            setLifespan(IMMORTAL);
            setFlavor(EMPTY);
        }
    }
}
