///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package command;

/**
 * BadCommand
 */
public class BadCommand extends Command {
	private static final String msg = "ERROR;Bad Command";

	public BadCommand() {
		super(null);
	}

	@Override
	public String execute() {
		return msg;
	}

}