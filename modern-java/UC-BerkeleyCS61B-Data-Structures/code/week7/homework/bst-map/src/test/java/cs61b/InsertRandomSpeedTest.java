package cs61b;

import edu.princeton.cs.algs4.Stopwatch;
import util.StringUtils;

import java.util.Scanner;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/27
 */
public class InsertRandomSpeedTest {
    static void main() {
        try (Scanner sc = new Scanner(System.in)) {
            IO.println("""
                    This program inserts random \
                    Strings of length L \
                    into different types of maps \
                    as <String, Integer> pairs.""");
            System.out.print("Please enter desired length of each string: ");

            int maxLengthOfStr = waitForPositiveInt(sc);

            System.out.print("\nEnter # strings to insert into the maps: ");
            int numStrings = waitForPositiveInt(sc);
            timeRandomMap61B(new ULLMap<String, Integer>(), numStrings, maxLengthOfStr);

        }


    }

    public static void timeRandomMap61B(Map16B<String, Integer> map, int numStrings, int maxLengthOfStr) {
        double elapsedSeconds = getElapsedTimeOfTask(() -> {
            for (int i = 0; i < numStrings; i++) {
                String key = StringUtils.randomString(maxLengthOfStr);
                map.put(key, i);
            }
        });
        System.out.println("%s: %.2f sec".formatted(map.getClass(), elapsedSeconds));
    }


    public static double getElapsedTimeOfTask(Runnable task) {
        Stopwatch sw = new Stopwatch();
        task.run();
        return sw.elapsedTime(); // seconds
    }


    public static int waitForPositiveInt(Scanner sc) {
        while (!sc.hasNextInt()) {
            errorBadIntegerInput();
            var ret = sc.next();
        }
        var ret = sc.nextInt();
        // 消耗剩余的输入
        sc.nextLine();
        return ret;
    }

    private static void errorBadIntegerInput() {
        System.out.println("没有检查到整数");
    }
}
