///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS com.google.truth:truth:1.4.5
//SOURCES ../interfaces/DisjointSet.java

package quickfind;

import interfaces.DisjointSet;

import com.google.common.truth.Truth;

public class QuickFind implements DisjointSet {
	public final int[] id;

	public QuickFind(int n) {
		this.id = new int[n];
		for (int i = 0; i < id.length; i++) {
			id[i] = i;
		}
	}

	@Override
	public void connnect(int p, int q) {
		// id[p] = id[q]
		int pid = id[p];
		int qid = id[q];

		for (int i = 0; i < id.length; i++) {
			if (pid == id[i]) {
				id[i] = qid;
			}
		}
	}

	@Override
	public boolean isConnection(int p, int q) {
		return id[p] == id[q];
	}

	public static void main(String[] args) {
		final int SIZE = 7;
		var df = new QuickFind(SIZE);
		doLab(df);
		Truth.assertThat(df.id).isEqualTo(new int[] { 5, 5, 5, 5, 5, 5, 6 });
		IO.println("Good Example");
	}

	public static void doLab(DisjointSet ds) {
		ds.connnect(0, 1);
		ds.connnect(1, 2);
		ds.connnect(0, 4);
		ds.connnect(3, 5);
		Truth.assertThat(ds.isConnection(2, 4)).isEqualTo(true);
		Truth.assertThat(ds.isConnection(3, 0)).isEqualTo(false);
		ds.connnect(2, 3);
		Truth.assertThat(ds.isConnection(3, 0)).isEqualTo(true);
	}
}
