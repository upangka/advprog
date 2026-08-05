///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

public class GameConsole {
	public boolean isPoweredOn;

	public class Controller {
		public boolean isPoweredOn;

		public void showStatus() {

			var status = "Controller["
					+ (this.isPoweredOn ? "ON" : "OFF") + "] - GameConsole["
					+ (GameConsole.this.isPoweredOn ? "ON" : "OFF") + "]";

			System.out.println(status);
		}
	}

}