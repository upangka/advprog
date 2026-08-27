package util;

import java.util.Random;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/27
 */
public class StringUtils {
    private static final int ALPHABET_SIZE = 26;
    private static Random random = new Random();


    public static String randomString(int length){
        char[] chars = new char[length];
        for(int i = 0; i < length; i++){
            chars[i] = (char)(random.nextInt(ALPHABET_SIZE) + 'a');
        }
        return  new String(chars);
    }

}
