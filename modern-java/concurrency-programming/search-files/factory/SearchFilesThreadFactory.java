///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package factory;

import java.util.concurrent.ThreadFactory;

public class SearchFilesThreadFactory implements ThreadFactory {
	private int counter = 0;
	private String namePrefix = "sf";

	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r, namePrefix + "-" + (++counter));
	}

}