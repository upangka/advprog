///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package command;

import java.io.PrintWriter;

/**
 * CommandContext
 */
public record CommandContext(
		PrintWriter out,
		String[] cmdDatas) {
}