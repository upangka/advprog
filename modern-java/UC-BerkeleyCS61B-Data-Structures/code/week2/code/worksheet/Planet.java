import java.util.List;

///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

public class Planet {
	double x;
	double y;
	double mass;

	public Planet(double x, double y, double mass) {
		this.x = x;
		this.y = y;
		this.mass = mass;
	}

	public double distanceTo(Planet other) {
		double dx = other.x - this.x;
        double dy = other.y - this.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

	public static double totalMass(List<Planet> planets) {
		return planets.stream()
			.mapToDouble(p -> p.mass)
			.sum();

	}
}

void main(String... args) {
    var p1 = new Planet(5, 10, 100);
    var p2 = new Planet(1, 2, 200);
    IO.println(p1.distanceTo(p2));
    IO.println(Planet.totalMass(List.of(p1, p2)));
}