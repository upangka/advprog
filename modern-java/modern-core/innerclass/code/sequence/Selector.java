///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

public interface Selector {
	boolean end();

	Integer current();

	void next();
}