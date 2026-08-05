///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//SOURCES  ./OldLady.java

/** Successfully make an instance of Fly */
void main(String... args) {
	var f = ((((((((new OldLady()).new Horse()).new Cow()).new Goat()).new Dog()).new Cat()).new Bird()).new Spider()).new Fly();

	IO.println(f);
	IO.println(f.getClass());
}
/**
Hi,You find me. Called me deeeep f
OldLady$Horse$Cow$Goat$Dog$Cat$Bird$Spider$Fly@74a14482
class OldLady$Horse$Cow$Goat$Dog$Cat$Bird$Spider$Fly
*/
