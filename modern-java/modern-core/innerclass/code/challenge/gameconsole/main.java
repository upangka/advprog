///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//SOURCES ./GameConsole.java

void main(String... args) {
	GameConsole gameConsole = new GameConsole();
	var controller = gameConsole.new Controller();
	controller.showStatus();

	gameConsole.isPoweredOn = true;
	controller.showStatus();

	controller.isPoweredOn = true;
	controller.showStatus();

}
/**
Controller[OFF] - GameConsole[OFF]
Controller[OFF] - GameConsole[ON]
Controller[ON] - GameConsole[ON]
*/