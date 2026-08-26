package io.github.upangka.c61b.disjointset;

import com.google.common.truth.Truth;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/23
 */
@Slf4j
public class WeightedQuickUnionFindC61BTest {

    @Test
    @DisplayName("测试并查集基本功能")
    public void testUnionFind() {
        DisjointSet ds = new WeightedQuickUnionFindC61B(9);

        String initState = "[-1, -1, -1, -1, -1, -1, -1, -1, -1]";
        Truth.assertThat(ds.toString()).isEqualTo(initState);

        /**
         *     0
         *    / \
         *   1   2
         */
        ds.connnect(0, 1);
        ds.connnect(0, 2);

        var expected = "[-3, 0, 0, -1, -1, -1, -1, -1, -1]";
        Truth.assertThat(ds.toString()).isEqualTo(expected);

        /**
         *  3
         *  / \
         * 5   4
         */
        ds.connnect(3, 5);
        ds.connnect(3, 4);
        expected = "[-3, 0, 0, -3, 3, 3, -1, -1, -1]";
        Truth.assertThat(ds.toString()).isEqualTo(expected);

        // 联通之后不变
        ds.connnect(4, 5);
        Truth.assertThat(ds.toString()).isEqualTo(expected);

        /**
         *       0
         *     / | \
         *    1  2  3
         *        / \
         *        5   4
         */
        ds.connnect(0, 3);
        expected = "[-6, 0, 0, 0, 3, 3, -1, -1, -1]";
        Truth.assertThat(ds.toString()).isEqualTo(expected);


        /**
         *     6
         *    / \
         *   7   8
         */
        ds.connnect(6, 7);
        ds.connnect(6, 8);
        expected = "[-6, 0, 0, 0, 3, 3, -3, 6, 6]";
        Truth.assertThat(ds.toString()).isEqualTo(expected);


        /**
         *        0
         *    / | \  \
         *  1  2  3    6
         *       / \  / \
         *      5   4 7  8
         */
        ds.connnect(0, 6);
        expected = "[-9, 0, 0, 0, 3, 3, 0, 6, 6]";
        Truth.assertThat(ds.toString()).isEqualTo(expected);
        log.info("union find: Good Test");
    }


    @Test
    @DisplayName("路径压缩")
    public void testPathCompress(){
        DisjointSet ds = new WeightedQuickUnionFindC61B(16);

        ds.connnect(8, 14);
        ds.connnect(2, 9);
        ds.connnect(2, 8);

        ds.connnect(3, 10);
        ds.connnect(0, 4);
        ds.connnect(0, 3);


        ds.connnect(11, 15);
        ds.connnect(5, 12);
        ds.connnect(5, 11);

        ds.connnect(6, 13);
        ds.connnect(1, 7);
        ds.connnect(1, 6);

        ds.connnect(0, 2);
        ds.connnect(1, 5);
        ds.connnect(0, 1);

        // 未调用isConnection的时候
        var expected = "[-16, 0, 0, 0, 0, 1, 1, 1, 2, 2, 3, 5, 5, 6, 8, 11]";
        Truth.assertThat(ds.toString()).isEqualTo(expected);
        // 会产生路径压缩
        Truth.assertThat(ds.isConnection(10,15)).isTrue();
        expected = "[-16, 0, 0, 0, 0, 0, 1, 1, 2, 2, 0, 0, 5, 6, 8, 0]";
        Truth.assertThat(ds.toString()).isEqualTo(expected);
    }

}
