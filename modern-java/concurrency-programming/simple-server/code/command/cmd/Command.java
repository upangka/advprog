///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
package command.cmd;

import dao.WdiDao;

public abstract class Command {
	protected String[] commands;
	protected final WdiDao dao;

	public Command() {
		dao = new WdiDao();
	}

	public abstract String execute() throws Exception;
}