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

	// 方式1：匿名类
	var nameComparator = new Comparator<Dog>() {

		@Override
		public int compare(Dog o1, Dog o2) {
			return o1.name().compareTo(o2.name());
		}
	};

	System.out.println(Collections.max(dogs, nameComparator));
	dogs.sort(nameComparator);
	System.out.println(dogs);
}
/**
Dog[name=Pelusa, size=5]
[Dog[name=Clifford, size=9000], Dog[name=Grigometh, size=200], Dog[name=Pelusa, size=5]]
 */