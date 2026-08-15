///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package command;

import java.util.Map;
import java.util.function.Supplier;
import java.util.HashMap;

import command.cmd.BadCommand;
import command.cmd.Command;
import command.cmd.Initializable;
import command.cmd.QueryCommand;

/**
 * CommandFactory
 */
public class CommandFactory {
	private static final String DEFAULT_NAME = "__default__";

	private final Map<String, Supplier<Command>> cmdRegistry = new HashMap<>();

	public CommandFactory() {

		registerDefaultCommands();
	}

	private void registerDefaultCommands() {
		register("q", QueryCommand::new);
		register(DEFAULT_NAME, BadCommand::new);
	}

	public void register(String cmdName, Supplier<Command> factory) {
		cmdRegistry.put(cmdName, factory);
	}

	public Command createCmd(String[] cmdDatas) {
		if (cmdDatas == null || cmdDatas.length == 0) {
			return cmdRegistry.get(DEFAULT_NAME).get();
		}
		Supplier<Command> cmdSupplier = cmdRegistry.get(cmdDatas[0]);
		if (cmdSupplier == null) {
			return cmdRegistry.get(DEFAULT_NAME).get();
		} else {
			Command command = cmdSupplier.get();
			if (command instanceof Initializable initialCmd) {
				initialCmd.init(cmdDatas);
			}
			return command;
		}
	}

}