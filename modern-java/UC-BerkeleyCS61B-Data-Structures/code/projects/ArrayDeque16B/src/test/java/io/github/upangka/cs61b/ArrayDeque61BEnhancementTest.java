package io.github.upangka.cs61b;

import com.google.common.truth.Truth;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/21
 */
@Slf4j
public class ArrayDeque61BEnhancementTest {
    @Test
    @DisplayName("Task 12: iterator")
    public void addLastTestBasicWithoutToList() {
        Deque61B<String> ad = new ArrayDeque61B<>();

        ad.addLast("front"); // after this call we expect: ["front"]
        ad.addLast("middle"); // after this call we expect: ["front", "middle"]
        ad.addLast("back"); // after this call we expect: ["front", "middle", "back"]

        /**  The Truth library works by iterating over our object  */
        Truth.assertThat(ad).containsExactly("front", "middle", "back");
    }

}
