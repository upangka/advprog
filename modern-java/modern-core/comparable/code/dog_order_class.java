import java.util.Comparator;

///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

static final record Dog(String name, int size) implements Comparable<Dog> {

	@Override
	public int compareTo(Dog o) {
		return size - o.size; // 按 size 升序 → 这是 Dog 的自然顺序
	}
}

static final class NameComparator implements Comparator<Dog> {
	@Override
	public int compare(Dog o1, Dog o2) {
		return o1.name().compareTo(o2.name());
	}
}

// 定义一个全局实例
public static final NameComparator NAME_COMPARATOR = new NameComparator();

void main(String... args) {
	var dogs = new ArrayList<Dog>() {
		{
			add(new Dog("Grigometh", 200));
			add(new Dog("Pelusa", 5));
			add(new Dog("Clifford", 9000));
		}
	};

	// 方式3：定义一个实现类，提供一个实例

	System.out.println(Collections.max(dogs, NAME_COMPARATOR));
	dogs.sort(NAME_COMPARATOR);
	System.out.println(dogs);
}
/**
Dog[name=Pelusa, size=5]
[Dog[name=Clifford, size=9000], Dog[name=Grigometh, size=200], Dog[name=Pelusa, size=5]]
 */