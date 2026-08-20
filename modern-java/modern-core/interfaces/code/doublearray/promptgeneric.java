///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

interface Parser<T> {
	T parse(String input);
}

class IntParser implements Parser<Integer> {

	@Override
	public Integer parse(String input) {
		return Integer.parseInt(input);
	}

}

class DoubleParser implements Parser<Double> {
	@Override
	public Double parse(String input) {
		return Double.parseDouble(input);
	}
}

<T> T promptGeneric(String message, Parser<T> parser) {

	while (true) {
		try {
			String input = IO.readln(message);
			T ret = parser.parse(input);
			return ret;
		} catch (NumberFormatException e) {
			IO.println(e.getMessage());
		}
	}

}

void main(String... args) {
	int x = promptGeneric(
			"Give me an x: ", new IntParser());
	double y = promptGeneric(
			"Give me a floating point y: ", new DoubleParser());
}
