///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

void main() {
	var pressTimes = Integer.parseInt(IO.readln());
	int aNums = 1, bNums = 0;

	for (int i = 0; i < pressTimes; i++) {
		var temp = aNums;
		aNums = bNums;
		bNums = temp + bNums;
	}
	IO.println("%d %d".formatted(aNums, bNums));
}
