///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
package command;

public abstract class Command{
    protected String[] commands;

    public Command(String[] commands){
        this.commands = commands;
    }

    public abstract String execute() throws Exception;
}