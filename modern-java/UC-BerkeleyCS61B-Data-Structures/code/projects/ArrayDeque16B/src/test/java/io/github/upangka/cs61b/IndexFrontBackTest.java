package io.github.upangka.cs61b;

import com.google.common.truth.Truth;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/21
 */
@Slf4j
public class IndexFrontBackTest {
    @Test
    public void testIndexFrontBack() {
        int size = 8;
        int nextFirst = 4, nextLast = 5;
        int[] actual = new int[size];
        int[] expected = new int[size];

        for (int i = 0; i < size; i++) {
            actual[i] = Math.floorMod(nextFirst - i, size);
            expected[i] = (nextFirst - i + size) % size;
        }

        Truth.assertThat(actual).isEqualTo(expected);
        log.info("Good Test nextFirst {}", Arrays.toString(actual));


        actual = new int[size];
        expected = new int[size];

        for (int i = 0; i < size; i++) {
            actual[i] = Math.floorMod(nextLast + i, size);
            expected[i] = (nextLast + i + size) % size;
        }

        Truth.assertThat(actual).isEqualTo(expected);
        log.info("Good Test nextLast {}", Arrays.toString(actual));
    }
}
