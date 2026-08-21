///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

static final record Dog(String name, int size) implements Comparable<Dog> {

	@Override
	public int compareTo(Dog o) {
		return size - o.size; // 按 size 升序 → 这是 Dog 的自然顺序
	}
}

void main(String... args) {
	var dogs = new ArrayList<Dog>() {
		{
			add(new Dog("Grigometh", 200));
			add(new Dog("Pelusa", 5));
			add(new Dog("Clifford", 9000));
		}
	};

	// 方式2：lambda语法糖

	System.out.println(Collections.max(dogs, (dog, otherDog) -> dog.name().compareTo(otherDog.name())));
	dogs.sort((dog, otherDog) -> dog.name().compareTo(otherDog.name()));
	System.out.println(dogs);
}
/**
Dog[name=Pelusa, size=5]
[Dog[name=Clifford, size=9000], Dog[name=Grigometh, size=200], Dog[name=Pelusa, size=5]]
 */