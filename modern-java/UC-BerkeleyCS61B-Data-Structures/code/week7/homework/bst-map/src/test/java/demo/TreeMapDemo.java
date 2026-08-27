package demo;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/27
 */
import java.util.TreeMap;

public class TreeMapDemo {
     static void main(String[] args) {
        // 创建 TreeMap，键为分数，值为学生姓名
        TreeMap<Integer, String> scoreMap = new TreeMap<>();

        scoreMap.put(85, "张三");
        scoreMap.put(92, "李四");
        scoreMap.put(78, "王五");
        scoreMap.put(95, "赵六");
        scoreMap.put(88, "孙七");

        System.out.println("========== 成绩排名系统 ==========");

        // 1. 第一名和最后一名
        System.out.println("最高分：" + scoreMap.lastKey() + " 分，学生：" + scoreMap.get(scoreMap.lastKey()));
        System.out.println("最低分：" + scoreMap.firstKey() + " 分，学生：" + scoreMap.get(scoreMap.firstKey()));

        // 2. 90分以上的学生（tailMap）
        System.out.println("\n90分以上的学生：");
        TreeMap<Integer, String> highScores = new TreeMap<>(scoreMap.tailMap(90));
        highScores.forEach((score, name) ->
                System.out.println("  " + name + "：" + score + " 分"));

        // 3. 80分以下的学生（headMap）
        System.out.println("\n80分以下的学生：");
        scoreMap.headMap(80).forEach((score, name) ->
                System.out.println("  " + name + "：" + score + " 分"));

        // 4. 80-90分之间的学生（subMap）
        System.out.println("\n80-90分之间的学生：");
        scoreMap.subMap(80, true, 90, false).forEach((score, name) ->
                System.out.println("  " + name + "：" + score + " 分"));

        // 5. 查找某个分数附近的学生
        int targetScore = 86;
        Integer ceilingScore = scoreMap.ceilingKey(targetScore);
        Integer floorScore = scoreMap.floorKey(targetScore);

        System.out.println("\n查找 " + targetScore + " 分附近的学生：");
        System.out.println("  大于等于 " + targetScore + " 的最低分：" + ceilingScore + " 分，学生：" + scoreMap.get(ceilingScore));
        System.out.println("  小于等于 " + targetScore + " 的最高分：" + floorScore + " 分，学生：" + scoreMap.get(floorScore));

        // 6. 严格大于和严格小于
        System.out.println("\n严格大于 85 分的最低分：" + scoreMap.higherKey(85) + " 分");
        System.out.println("严格小于 85 分的最高分：" + scoreMap.lowerKey(85) + " 分");
    }
}