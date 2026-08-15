///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package command.cmd;

/**
 * BadCommand
 */
public class BadCommand extends Command {
	private static final String msg = "ERROR;Bad Command";

	@Override
	public String execute() {
		return msg;
	}

}