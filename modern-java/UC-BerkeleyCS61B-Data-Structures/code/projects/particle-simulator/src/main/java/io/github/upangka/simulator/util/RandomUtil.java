package io.github.upangka.simulator.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机数工具
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/18
 */
public class RandomUtil {
    /**
     * 生成随机整数 [min, max]（包含两端）
     *
     * @param min 最小值（包含）
     * @param max 最大值（包含）
     * @return 随机整数
     * @throws IllegalArgumentException 当 min > max 时抛出
     */
    public static int nextInt(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException("min must be <= max");
        }
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * 生成随机整数 [0, bound)（不包含 bound）
     *
     * @param bound 上界（不包含）
     * @return 随机整数
     */
    public static int nextInt(int bound) {
        return ThreadLocalRandom.current().nextInt(bound);
    }


    /**
     * 从集合中随机选择一个元素
     *
     * @param collection 集合
     * @param <T>        元素类型
     * @return 随机选择的元素，如果集合为空则返回 null
     */
    public static <T> T randomElement(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }

        int index = nextInt(collection.size());

        Iterator<T> iterator = collection.iterator();
        T element = null;
        for (int i = 0; i <= index && iterator.hasNext(); i++) {
            element = iterator.next();
        }
        return element;
    }
}
