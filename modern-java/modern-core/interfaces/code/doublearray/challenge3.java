///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//SOURCES ./DoubleArray.java

class FauxDoubleArray implements DoubleArray {
	private final int[] data;

	FauxDoubleArray(int[] data) {
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
	DoubleArray arr = new FauxDoubleArray(new int[] {
			1, 2, 3, 4, 5
	});

	for (int i = 0; i < arr.length(); i++) {
		IO.println("Got double value: " + arr.get(i));
	}
}
