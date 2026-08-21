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
public class ArrayDeque61BTest {

    @Test
    @DisplayName("Task 4: 测试getFirst和getLast")
    public void testGetFirstAndGetLast(){
        Deque61B<String> deque = new ArrayDeque61B<>();

        Truth.assertThat(deque.getFirst()).isEqualTo(null);
        Truth.assertThat(deque.getLast()).isEqualTo(null);

        deque.addFirst("Structure");
        deque.addFirst("Data");
        deque.addFirst("SP26");
        deque.addFirst("61B");
        deque.addFirst("CS");
        deque.addFirst("UCBerkeley");
        // [CS,61B,SP26,Data,Structure, _ , _ ,UCBerkeley]

        Truth.assertThat(deque.getFirst()).isEqualTo("UCBerkeley");
        Truth.assertThat(deque.getLast()).isEqualTo("Structure");

    }


    @Test
    @DisplayName("测试toString")
    public void testToString(){
        String expected = "[Structure, _ , _ ,UCBerkeley,CS,61B,SP26,Data]";

        Deque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("61B");
        deque.addLast("SP26");
        deque.addFirst("CS");
        deque.addFirst("UCBerkeley");
        deque.addLast("Data");
        deque.addLast("Structure");

        Truth.assertThat(deque.toString()).isEqualTo(expected);
        log.info("toString: Good Test");
    }
}
