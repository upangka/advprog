///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

class Dog {
	String name;
	int size;

	Dog(String name, int size) {
		this.name = name;
		this.size = size;
	}

	void grow() {
		this.size += 1;
	}

	@Override
	public String toString() {
		return "%s the size %d dog".formatted(
				this.name,
				this.size);
	}
}

void main(String... args) {
	List<Dog> dogs = List.of(
			new Dog("maya", 1000),
			new Dog("yipster", 5),
			new Dog("scott", 25));

	System.out.println(dogs.get(0));
}
