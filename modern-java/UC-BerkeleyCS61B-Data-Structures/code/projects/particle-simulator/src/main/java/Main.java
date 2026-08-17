import edu.princeton.cs.algs4.StdDraw;
import io.github.upangka.simulator.ParticleFlavor;
import io.github.upangka.simulator.ParticleSimulator;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class Main {
    public static final int WIDTH = 150;
    public static final int HEIGHT = 150;
    /** 不同的按键对应的粒子类型  */
    public static final Map<Character, ParticleFlavor> LETTER_TO_PARTICLE = Map.of(
            's', ParticleFlavor.SAND,
            'b', ParticleFlavor.BARRIER,
            'w', ParticleFlavor.WATER,
            'p', ParticleFlavor.PLANT,
            'f', ParticleFlavor.FIRE,
            '.', ParticleFlavor.EMPTY,
            'n', ParticleFlavor.FOUNTAIN,
            'z', ParticleFlavor.FLOWER
    );

    public static void main() {
        var simulator = new ParticleSimulator(WIDTH,HEIGHT);
        StdDraw.setXscale(0, simulator.getWidth());
        StdDraw.setYscale(0, simulator.getHeight());
        StdDraw.enableDoubleBuffering();
        StdDraw.clear(StdDraw.BLACK);

        var nextParticleFlavor = ParticleFlavor.SAND;

        while (true) {
            // 检查键盘输入
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                log.info("检测到{}按键被按下", c);
            }

            // 检测鼠标
            if(StdDraw.isMousePressed()){
                var x = (int)StdDraw.mouseX();
                var y = (int)StdDraw.mouseY();
                simulator.changeParticleFlavor(x,y,nextParticleFlavor);
                log.info("点击鼠标[{},{}]",x,y);
            }

            simulator.drawParticles();

            // 1s 200FPS
            StdDraw.show();
            StdDraw.pause(5);
        }


    }
}