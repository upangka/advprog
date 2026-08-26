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

    //  Assumes `put`/`containsKey` is implemented properly.
    @Test
    @DisplayName("containsKey")
    public void sanityContainsKeyTest(){
        var b = new BSTMap<String,Integer>();
        Truth.assertThat(b.containsKey("waterYouDoingHere")).isFalse();
        b.put("waterYouDoingHere", 10);
        Truth.assertThat(b.containsKey("waterYouDoingHere")).isTrue();
    }
}
