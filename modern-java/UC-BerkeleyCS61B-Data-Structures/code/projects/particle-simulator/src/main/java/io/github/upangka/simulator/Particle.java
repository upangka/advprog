package io.github.upangka.simulator;

import java.awt.*;

/**
 * 粒子
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/17
 */
public class Particle {
    private ParticleFlavor flavor;

    public Particle(ParticleFlavor flavor){
        this.flavor = flavor;
    }

    public Color color(){
        if(flavor==ParticleFlavor.EMPTY){
            return Color.BLACK;
        }

        return Color.GRAY;
    }

    public void changeFlavor(ParticleFlavor flavor){
        this.flavor = flavor;
    }
}
