///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

void capitalize(List<String> L) {
	for (int i = 0; i < L.size(); i++) {
		L.set(i, L.get(i).toUpperCase());
	}
}

void main(String... args) {
	var L = new ArrayList<>(List.of("HeLLo", "WoRLd"));
	capitalize(L);
	System.out.println(L);
}
