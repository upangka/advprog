///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS org.junit.jupiter:junit-jupiter:6.1.2
//DEPS com.google.truth:truth:1.4.5

//SOURCES  ./quickfind/QuickFind.java ./interfaces/DisjointSet.java

import interfaces.DisjointSet;
import quickfind.QuickFind;

import org.junit.Test;
import com.google.common.truth.Truth;
public class TestDisJointSet {
	final static int SIZE = 7;
	public static void doLab(DisjointSet ds){
		ds.connnect(0, 1);
		ds.connnect(1, 2);
		ds.connnect(0, 4);
		ds.connnect(3, 5);
		Truth.assertThat(ds.isConnection(2, 4)).isEqualTo(true);
		Truth.assertThat(ds.isConnection(3, 0)).isEqualTo(false);
		ds.connnect(4, 2);
		ds.connnect(4, 6);
		ds.connnect(3, 6);
		Truth.assertThat(ds.isConnection(3, 0)).isEqualTo(true);
	}

	@Test
	public void testQuickFind(){
		var df = new QuickFind(SIZE);
		doLab(df);
		IO.println(df.id);
	}

}

