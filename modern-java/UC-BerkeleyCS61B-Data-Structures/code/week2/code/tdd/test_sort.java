///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS org.junit.jupiter:junit-jupiter:6.1.2
//DEPS com.google.truth:truth:1.4.5
import org.junit.jupiter.api.Test;
import com.google.common.truth.Truth;

public class test_sort{
    
    @Test
    void testFindSmallestElement(){
        String[] inputs = {"helloworld","bonus","googletruth","java","python"};
        String[] expected = {"bonus", "googletruth", "helloworld", "java", "python"};

        // Sort.sort(inputs);
        // Arrays.sort(inputs);
        Truth.assertThat(inputs).isEqualTo(expected);
    }
}