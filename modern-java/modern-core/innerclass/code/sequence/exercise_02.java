///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//SOURCES ./Selector.java ./Selector.java

final static int SIZE = 10;
Random random = new Random();

void main(String... args) {

	Sequence sequence = new Sequence(SIZE);
	random.ints(SIZE, 0, 10)
		.forEach(sequence::add);

	System.out.println("生成的结果 => " + Arrays.toString(sequence.getItems()));

	// 通过接口抽象，底层是内部类实现的
	Selector selector = sequence.getSelector();

	while (!selector.end()) {
		System.out.println(selector.current());
		selector.next();
	}

}
/**output:
生成的结果 => [3, 5, 5, 7, 8, 0, 2, 5, 4, 5]
3
5
5
7
8
0
2
5
4
5
*/