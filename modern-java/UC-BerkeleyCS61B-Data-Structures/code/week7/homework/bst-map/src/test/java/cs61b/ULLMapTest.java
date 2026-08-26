package cs61b;

import com.google.common.truth.Truth;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/26
 */
@Slf4j
public class ULLMapTest {

    @Test
    @DisplayName("测试ULLMap的基本功能")
    public void sanityULLMapTest(){
        var b = new ULLMap<String,String>();
        b.put("北京", "北京");
        b.put("广东", "广州");
        b.put("广东", "深圳"); // 只是更新
        b.put("上海", "上海");
        Truth.assertThat(b.size()).isEqualTo(3);
        Truth.assertThat(b.get("广东")).isEqualTo("深圳");
        System.out.println(b);
    }
}
