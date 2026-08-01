///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

/**
 * 研究一下compact soure file
 * @param args
 */

int a = 0;
static int b = 1;

static void f1(){
    // System.out.println(a);
    System.out.println(b);
}

void f2(){
    System.out.println(a);
    System.out.println(b);
}

void main(String... args) {
    f1();
    f2();
}
