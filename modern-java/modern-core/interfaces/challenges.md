# Challenge 1.

Declare an interface named `DoubleArray` which requires
two methods of classes that implementing classes: `length` and `get`.

These methods should be specified to work the same as how `[]` and `.length`
work on a `double[]`.

[DoubleArray.java](./code/doublearray/DoubleArray.java)

```java
public interface DoubleArray {
    int length();
    double get(int i);
}
```

# Challenge 2.

Make a class that implements your `DoubleArray` interface
using a `double[]` in a field to perform all the operations.

[challenge2.java](./code/doublearray/challenge2.java)

```java
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
```

# Challenge 3.

Make a second class that implements `DoubleArray` but have this one
be backed by an `int[]` and perform `widening conversions` when returning values.

拓宽转换（widening conversion）

1. `int → long → float → double`
2. 在 Java 中，int 可以自动转换为 double，不需要显式强制转换（(double)）

[challenge3.java](./code/doublearray/challenge3.java)

```java
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

```

# Challenge 4.

Make an implementation of the following `Tarot` interface for [each tarot card
featured in JoJo's Bizzare Adventure](https://jojowiki.com/Tarot_Cards).

[Github: tlp-clean](https://github.com/xx11xx22/tlp-clean)

# Challenge 5.

Make a method named `promptGeneric` which can prompt the user for information
but, based on if what they typed is properly interpretable, can reprompt them.

As part of this make a `Parser` interface and at least two implementations: `IntParser`
and `DoubleParser`.

[promptgeneric.java](./code/doublearray/promptgeneric.java)

```java
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
```
