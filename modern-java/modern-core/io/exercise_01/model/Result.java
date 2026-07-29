///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
package model;

import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class Result {
	private volatile boolean isFound;
	private String path;
}
