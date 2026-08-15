///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package server;

/**
 * Server
 */
public interface Server extends Runnable {

	public void stop();
}
