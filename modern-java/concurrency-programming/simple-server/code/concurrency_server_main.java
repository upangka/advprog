///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//SOURCES ./**/*.java

import constants.Constants;
import server.Server;
import server.parallel.ConcurrencyServer;;

/**
 * 并行服务器
 */
void main(String... args) {
	try {
		Server server = new ConcurrencyServer(Constants.CONCURRENCY_PORT);
		Thread sThread = new Thread(server, "concurrency-server-t");
		sThread.start();

		Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
		System.out.println("main over");

	} catch (IOException e) {
		System.err.println("Failed to start server: " + e.getMessage());
	}
}
