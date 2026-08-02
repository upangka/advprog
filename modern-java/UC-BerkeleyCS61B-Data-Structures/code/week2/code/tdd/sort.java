///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS com.google.truth:truth:1.4.5

import com.google.common.truth.Truth;

class Sort{
    public static void sort(String[] inputs){

    }
}


void main(String... args) {
    String[] inputs = {"helloworld","bonus","googletruth","java","python"};
    String[] expected = {"bonus", "googletruth", "helloworld", "java", "python"};

    Sort.sort(inputs);
    // Arrays.sort(inputs);
    Truth.assertThat(inputs).isEqualTo(expected);
}
