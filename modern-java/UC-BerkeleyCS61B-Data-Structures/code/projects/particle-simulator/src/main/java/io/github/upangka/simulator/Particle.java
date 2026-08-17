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

    public void changeFlavor(ParticleFlavor flavor){
        this.flavor = flavor;
    }
}
