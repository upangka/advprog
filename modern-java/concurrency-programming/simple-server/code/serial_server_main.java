///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//SOURCES ./**/*.java

import constants.Constants;
import server.Server;
import server.serial.SerialServer;

/**
 * 串行服务器
 */
void main(String... args) {
	try {
		Server server = new SerialServer(Constants.SERIAL_PORT);
		Thread sThread = new Thread(server, "serial-server-t");
		sThread.start();

		Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
		System.out.println("main over");

	} catch (IOException e) {
		System.err.println("Failed to start server: " + e.getMessage());
	}
}
