///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package command.cmd;

/**
 * Initializable
 */
public interface Initializable {

	void init(String[] data);
}