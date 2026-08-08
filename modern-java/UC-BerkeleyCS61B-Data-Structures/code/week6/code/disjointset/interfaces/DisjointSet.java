///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package interfaces;
public interface DisjointSet {

    void connnect(int p,int q);
    boolean isConnection(int p,int q);
}