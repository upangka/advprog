import edu.princeton.cs.algs4.StdDraw;
import io.github.upangka.simulator.ParticleSimulator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static final int WIDTH = 150;
    public static final int HEIGHT = 150;

    public static void main() {
        var simulator = new ParticleSimulator();
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.clear(StdDraw.BLACK);


        while (true) {
            // 检查键盘输入
            if (StdDraw.hasNextKeyTyped()) {
                char c = StdDraw.nextKeyTyped();
                log.info("检测到{}按键被按下", c);
            }
            // 1s 200FPS
            StdDraw.show();
            StdDraw.pause(5);
        }
    }
}