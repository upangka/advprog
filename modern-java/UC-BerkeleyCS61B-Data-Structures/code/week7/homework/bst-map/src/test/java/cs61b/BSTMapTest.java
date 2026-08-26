package cs61b;

import com.google.common.truth.Truth;
import edu.princeton.cs.algs4.BST;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/26
 */
public class BSTMapTest {

    @Test
    @DisplayName("put")
    public void sanityPutTest() {
        var b = new BSTMap<String, String>();
        b.put("北京", "北京");
        b.put("广东", "广州");
        b.put("广东", "深圳"); // 只是更新
        b.put("上海", "上海");
        Truth.assertThat(b.size()).isEqualTo(3);
        Truth.assertThat(b.get("广东")).isEqualTo("深圳");

    }

    //  Assumes `put`/`containsKey` is implemented properly.
    @Test
    @DisplayName("containsKey")
    public void sanityContainsKeyTest() {
        var b = new BSTMap<String, Integer>();
        Truth.assertThat(b.containsKey("waterYouDoingHere")).isFalse();
        b.put("waterYouDoingHere", 10);
        Truth.assertThat(b.containsKey("waterYouDoingHere")).isTrue();
    }

    //  Assumes `put` is implemented properly.
    @Test
    @DisplayName("size")
    public void sanitySizeTest() {
        var b = new BSTMap<String, Integer>();
        Truth.assertThat(b.size()).isEqualTo(0);
        b.put("Java", 1);
        Truth.assertThat(b.size()).isEqualTo(1);
        for (int i = 0; i < 455; i++) {
            b.put("item-" + i, i);
        }
        Truth.assertThat(b.size()).isEqualTo(456);
    }


    @Test
    @DisplayName("clear")
    public void sanityClearTest() {
        var b = new BSTMap<String, Integer>();

        for (int i = 0; i < 455; i++) {
            var key = "item-" + i;
            var val = 6 + i;
            b.put(key,val);
            //make sure put is working via containsKey and get
            Truth.assertThat(b.get(key)).isEqualTo(val);
            Truth.assertThat(b.containsKey(key)).isTrue();
        }

        Truth.assertThat(b.size()).isEqualTo(455);
        b.clear();
        Truth.assertThat(b.size()).isEqualTo(0);
        for (int i = 0; i < 455; i++) {
            var key = "item-" + i;
            Truth.assertThat(b.containsKey(key)).isFalse();
        }
    }


    @Test
    @DisplayName("remove")
    public void sanityRemoveTest() {
        var b = new BSTMap<String, Integer>();

        // 测试1：删除不存在的键，返回 null，size 不变
        Truth.assertThat(b.remove("nonexistent")).isNull();
        Truth.assertThat(b.size()).isEqualTo(0);

        // 准备测试数据
        b.put("c", 3);
        b.put("a", 1);
        b.put("e", 5);
        b.put("b", 2);
        b.put("d", 4);
        // 树的结构：
        //     c
        //    / \
        //   a   e
        //    \  /
        //     b d
        Truth.assertThat(b.size()).isEqualTo(5);

        // 测试2：删除叶子节点（b 和 d）
        Truth.assertThat(b.remove("b")).isEqualTo(2);
        Truth.assertThat(b.containsKey("b")).isFalse();
        Truth.assertThat(b.size()).isEqualTo(4);

        Truth.assertThat(b.remove("d")).isEqualTo(4);
        Truth.assertThat(b.containsKey("d")).isFalse();
        Truth.assertThat(b.size()).isEqualTo(3);

        // 测试3：删除只有一个子节点的节点（a）
        // 删除前：a 只有右子节点 b（已被删除），现在 a 是叶子节点，所以是情况1
        // 但为了测试单子节点，我们可以重新构造
        b.clear();
        b.put("c", 3);
        b.put("a", 1);
        b.put("b", 2);
        // 树的结构：
        //     c
        //    /
        //   a
        //    \
        //     b
        Truth.assertThat(b.size()).isEqualTo(3);
        Truth.assertThat(b.remove("a")).isEqualTo(1);  // a 只有一个右子节点 b
        Truth.assertThat(b.containsKey("a")).isFalse();
        Truth.assertThat(b.containsKey("b")).isTrue();  // b 应该还在
        Truth.assertThat(b.size()).isEqualTo(2);

        // 测试4：删除有两个子节点的节点（c）
        // 重新构造一个更大的树
        b.clear();
        b.put("d", 4);
        b.put("b", 2);
        b.put("f", 6);
        b.put("a", 1);
        b.put("c", 3);
        b.put("e", 5);
        b.put("g", 7);
        // 树的结构（完全平衡）：
        //       d
        //      / \
        //     b   f
        //    / \ / \
        //   a  c e  g
        Truth.assertThat(b.size()).isEqualTo(7);
        Truth.assertThat(b.remove("d")).isEqualTo(4);  // d 有两个子节点
        Truth.assertThat(b.containsKey("d")).isFalse();
        Truth.assertThat(b.size()).isEqualTo(6);

        // 验证删除 d 后，树的键仍然有序，且所有节点仍可访问
        // 前驱（c）或后继（e）应该替代了 d 的位置
        // 检查前驱或后继是否在正确位置（取决于你的实现用前驱还是后继）
        // 这里我们只验证整棵树的结构完整性：所有剩下的键都应该还在
        Truth.assertThat(b.containsKey("b")).isTrue();
        Truth.assertThat(b.containsKey("f")).isTrue();
        Truth.assertThat(b.containsKey("a")).isTrue();
        Truth.assertThat(b.containsKey("c")).isTrue();
        Truth.assertThat(b.containsKey("e")).isTrue();
        Truth.assertThat(b.containsKey("g")).isTrue();

        // 验证 get 仍然正确
        Truth.assertThat(b.get("b")).isEqualTo(2);
        Truth.assertThat(b.get("f")).isEqualTo(6);
        Truth.assertThat(b.get("a")).isEqualTo(1);
        Truth.assertThat(b.get("c")).isEqualTo(3);
        Truth.assertThat(b.get("e")).isEqualTo(5);
        Truth.assertThat(b.get("g")).isEqualTo(7);

        // 测试5：删除根节点（只剩一个节点时）
        b.clear();
        b.put("only", 1);
        Truth.assertThat(b.size()).isEqualTo(1);
        Truth.assertThat(b.remove("only")).isEqualTo(1);
        Truth.assertThat(b.containsKey("only")).isFalse();
        Truth.assertThat(b.size()).isEqualTo(0);
        Truth.assertThat(b.remove("only")).isNull();  // 再次删除返回 null
    }




}
