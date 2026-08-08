import quickfind.QuickFind;

///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//SOURCES  ./quickfind/QuickFind.java ./interfaces/DisjointSet.java

void main(String... args) {
    var quickfind = new QuickFind();
    quickfind.connnect(0, 3);
}
