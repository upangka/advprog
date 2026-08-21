package io.github.upangka.cs61b;

import com.google.common.truth.Truth;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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
    public void testGetFirstAndGetLast() {
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
    public void testToString() {
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

    @Test
    @DisplayName("Task 5: get")
    public void testGet() {
        Deque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("61B");
        deque.addLast("SP26");
        deque.addFirst("CS");
        deque.addFirst("UCBerkeley");
        deque.addLast("Data");
        deque.addLast("Structure");
        // [Structure, _ , _ ,UCBerkeley,CS,61B,SP26,Data]

        Truth.assertWithMessage("Negative index is invalid")
                .that(deque.get(-1)).isNull();

        Truth.assertWithMessage("Too Large index is invalid")
                .that(deque.get(6)).isNull();

        Truth.assertThat(deque.get(0)).isEqualTo("UCBerkeley");
        Truth.assertThat(deque.get(1)).isEqualTo("CS");
        Truth.assertThat(deque.get(2)).isEqualTo("61B");
        Truth.assertThat(deque.get(3)).isEqualTo("SP26");
        Truth.assertThat(deque.get(4)).isEqualTo("Data");
        Truth.assertThat(deque.get(5)).isEqualTo("Structure");

        log.info("get: Good Test");
    }


    @Test
    @DisplayName("Task 6: isEmpty and size")
    public void testIsEmptyAndSize() {
        Deque61B<String> deque = new ArrayDeque61B<>();
        Truth.assertThat(deque.isEmpty()).isTrue();
        deque.addLast("61B");
        deque.addLast("SP26");
        deque.addFirst("CS");
        Truth.assertThat(deque.isEmpty()).isFalse();

        Truth.assertThat(deque.size()).isEqualTo(3);

        log.info("isEmpty and size: Good Test");
    }


    @Test
    @DisplayName("Task 7: toList")
    public void testToList() {
        var expected_v1 = List.of("UCBerkeley", "CS", "61B", "SP26");
        var expected_v2 = List.of("UCBerkeley", "CS", "61B", "SP26", "Data", "Structure");

        Deque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("61B");
        deque.addLast("SP26");
        deque.addFirst("CS");
        deque.addFirst("UCBerkeley");
        Truth.assertThat(deque.toList()).isEqualTo(expected_v1);

        deque.addLast("Data");
        deque.addLast("Structure");
        Truth.assertThat(deque.toList()).isEqualTo(expected_v2);
        log.info("toList: Good Test");
    }

    @Test
    @DisplayName("Task 8: removeFirst")
    public void testRemoveFirst() {
        Deque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("61B");
        deque.addLast("SP26");
        deque.addFirst("CS");
        deque.addFirst("UCBerkeley");
        deque.addLast("Data");
        deque.addLast("Structure");

        Truth.assertThat(deque.removeFirst()).isEqualTo("UCBerkeley");
        Truth.assertThat(deque.getFirst()).isEqualTo("CS");
        log.info("removeFirst: Good Test");
    }

    @Test
    @DisplayName("Task 8: removeLast")
    public void removeLast() {
        Deque61B<String> deque = new ArrayDeque61B<>();
        deque.addLast("61B");
        deque.addLast("SP26");
        deque.addFirst("CS");
        deque.addFirst("UCBerkeley");
        deque.addLast("Data");
        deque.addLast("Structure");

        Truth.assertThat(deque.removeLast()).isEqualTo("Structure");
        Truth.assertThat(deque.getLast()).isEqualTo("Data");
        log.info("removeLast: Good Test");
    }
}
