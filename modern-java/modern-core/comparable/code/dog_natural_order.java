///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

static record Dog(String name, int size) implements Comparable<Dog> {

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

	dogs.sort(null);
	System.out.println(Collections.max(dogs));
	System.out.println(dogs);
}
/**
 Dog[name=Clifford, size=9000]
[Dog[name=Pelusa, size=5], Dog[name=Grigometh, size=200], Dog[name=Clifford, size=9000]]
 */