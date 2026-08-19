package io.github.upangka.util;


import io.github.upangka.simulator.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * RandomUtils 单元测试
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/18
 */
@Slf4j
public class RandomUtilsTest {

    @Test
    @DisplayName("正常范围：返回值应该在 [min, max] 之间")
    void testNextInt() {
        int min = 0;
        int max = 2;

        // 执行多次确保可靠性
        for (int i = 0; i < 100; i++) {
            var ret = RandomUtil.nextInt(min, max);
            assertThat(ret).isAtLeast(min);
            assertThat(ret).isAtMost(max);
        }
        log.info("Range nextInt Good Test");
    }
}
