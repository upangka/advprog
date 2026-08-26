package cs61b;

import com.google.common.truth.Truth;
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


}
