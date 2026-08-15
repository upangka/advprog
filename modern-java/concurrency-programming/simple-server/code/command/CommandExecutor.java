///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package command;

import command.cmd.Command;

/**
 * CommandExecutor
 */
public class CommandExecutor {
	private final CommandFactory cmdFactory;

	public CommandExecutor(CommandFactory cmdFactory) {
		this.cmdFactory = cmdFactory;
	}

	public void execute(CommandContext context) {
		Command cmd = cmdFactory.createCmd(context.cmdDatas());
		var out = context.out();
		try {
			String ret = cmd.execute();
			out.println(ret);
		} catch (Exception e) {
			out.println(e.getMessage());
		}
	}
}
