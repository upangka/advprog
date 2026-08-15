///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package command.cmd;

/**
 * 格式:
 * q;CodCountry;codIndicator;year
 * 其中year是可选参数
 */
public class QueryCommand extends Command implements Initializable {

	@Override
	public void init(String[] data) {
		super.commands = data;
	}

	@Override
	public String execute() throws Exception {
		System.out.println("执行命令: " + String.join(";", commands));
		if (commands.length == 3) {
			return dao.query(commands[1], commands[2]);
		}
		return null;
	}

}
