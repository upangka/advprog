
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

import java.util.Map;
public class Particle {
    public String flavor; 
    public int lifespan;

    public Particle(String f, int l) {
        flavor = f; 
        lifespan = l;
    }

    public static void boil(Particle p) {
        p.flavor = "steam";
    }

    public static void decrement(int x) {
        x = x - 1;
    }

    public static void action(Map<Integer, Particle> m) {
        m.get(2).flavor = "lava";
        m.get(2).lifespan = 5;
    }

    public static void main(String... args) {
        Particle p1 = new Particle("water", -1);
        Particle p2 = new Particle("sand", -1);
        Map<Integer, Particle> m = Map.of(1, p1, 2, p2);
        boil(p1);
        IO.println(p1.flavor);
        decrement(p1.lifespan);
        IO.println(p1.lifespan);
        action(m);
        IO.println(p2.lifespan);
        IO.println(p2.flavor);
    }
}