///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//SOURCES ./DoubleArray.java

class RealDoubleArray implements DoubleArray {
	private double[] data;

	RealDoubleArray(double[] data) {
		this.data = data;
	}

	@Override
	public int length() {
		return this.data.length;
	}

	@Override
	public double get(int i) {
		return this.data[i];
	}

}

void main(String... args) {
	DoubleArray arr = new RealDoubleArray(new double[] {
			1.0, 1.5, 2.0, 2.5, 3.0
	});

	for (int i = 0; i < arr.length(); i++) {
		IO.println("Got double value: " + arr.get(i));
	}
}
