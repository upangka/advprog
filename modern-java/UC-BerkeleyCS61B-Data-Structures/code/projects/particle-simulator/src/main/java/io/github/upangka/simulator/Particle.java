package io.github.upangka.simulator;

import io.github.upangka.simulator.util.RandomUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.Map;

/**
 * 粒子
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

    public Particle(ParticleFlavor flavor){
        this.flavor = flavor;
        this.lifespan = -1;
    }

    public Color color(){
       return switch (flavor) {
            case EMPTY-> Color.BLACK;
            case SAND -> Color.YELLOW;
            case BARRIER -> Color.GRAY;
            case WATER -> Color.BLUE;
            case FOUNTAIN -> Color.CYAN;
            case PLANT -> new Color(0, 255, 0);
            case FIRE -> new Color(255, 0, 0);
            case FLOWER -> new Color(255, 141, 161);
       };
    }

    public void moveInto(Particle other){
        other.lifespan = lifespan;
        other.flavor = flavor;

        this.flavor = ParticleFlavor.EMPTY;
        this.lifespan = -1;
    }

    public void changeFlavor(ParticleFlavor flavor){
        this.setFlavor(flavor);
    }

    public void fall(Map<Direction,Particle> neighbors){
        Particle other = neighbors.get(Direction.DOWN);
        if(other.getFlavor() == ParticleFlavor.EMPTY){
            this.moveInto(other);
        }
    }

    public void action(Map<Direction,Particle> neighbors){

        // If the flavor of the current particle is EMPTY, return immediately.
        if(this.flavor == ParticleFlavor.EMPTY){
            return;
        }

        // If the flavor of the current particle is not BARRIER, call fall.
        if(this.flavor !=  ParticleFlavor.BARRIER){
            this.fall(neighbors);
        }

        // If the flavor of the current particle is WATER, call flow.
        if(this.flavor == ParticleFlavor.WATER){
            flow(neighbors);
        }

    }

    /**
     * With 1/3 chance, don’t do anything.
     * With 1/3 chance, if the left neighbor is empty, moveInto it.
     * With 1/3 chance, if the right neighbor is empty, moveInto it.
     */
    public void flow(Map<Direction, Particle> neighbors) {

        int chance = RandomUtil.nextInt(0, 2);
        if(chance == 0){
            // do anything
            return;
        }else if(chance == 1){
            Particle other = neighbors.get(Direction.LEFT);
            if(other.getFlavor() == ParticleFlavor.EMPTY){
                moveInto(other);
            }
        }else if(chance == 2){
            Particle other = neighbors.get(Direction.RIGHT);
            if(other.getFlavor() == ParticleFlavor.EMPTY){
                moveInto(other);
            }
        }
    }
}
