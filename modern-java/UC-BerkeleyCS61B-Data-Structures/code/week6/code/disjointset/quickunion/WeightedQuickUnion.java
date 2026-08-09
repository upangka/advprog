///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//SOURCES ../interfaces/DisjointSet.java
//DEPS com.google.truth:truth:1.4.5

package quickunion;

import java.util.Arrays;

import com.google.common.truth.Truth;

import interfaces.DisjointSet;

public class WeightedQuickUnion implements DisjointSet {
	private final int parent[];

	public WeightedQuickUnion(int n) {
		this.parent = new int[n];
		Arrays.fill(parent, -1);
	}

	@Override
	public void connnect(int p, int q) {
		int pr = findRoot(p);
		int qr = findRoot(q);

		if (pr != qr) {
			// 通过size进行判断
			if (Math.abs(parent[pr]) <= Math.abs(parent[qr])) {
				int size = parent[pr];
				parent[pr] = qr;
				parent[qr] = parent[qr] + size;
			} else {
				int size = parent[qr];
				parent[qr] = pr;
				parent[pr] = parent[pr] + size;
			}
		}
	}

	@Override
	public boolean isConnection(int p, int q) {
		return findRoot(p) == findRoot(q);
	}

	private int findRoot(int p) {
		int r = p;
		while (parent[r] > -1) {
			r = parent[r];
		}

		return r;
	}

	public static void main(String[] args) {
		var ds = new WeightedQuickUnion(9);
		// [-1, -1, -1, -1, -1, -1, -1, -1, -1]
		IO.println(Arrays.toString(ds.parent));

		/**
		 0
		 / \
		 1   2
		 */
		ds.connnect(1, 0);
		ds.connnect(2, 0);
		// [-3, 0, 0, -1, -1, -1, -1, -1, -1]
		IO.println(Arrays.toString(ds.parent));
		Truth.assertThat(ds.isConnection(1, 2)).isEqualTo(true);

		/**
		  3
		 / \
		5   4
		 */
		ds.connnect(5, 3);
		ds.connnect(4, 3);
		// [-3, 0, 0, -3, 3, 3, -1, -1, -1]
		IO.println(Arrays.toString(ds.parent));
		Truth.assertThat(ds.isConnection(4, 5)).isEqualTo(true);
		/**
		      0                     
		    / | \                   
		   1  2  3 
		        / \
		       5   4                 
		 */

		ds.connnect(5, 2);
		// [-6, 0, 0, 0, 3, 3, -1, -1, -1]
		IO.println(Arrays.toString(ds.parent));
		Truth.assertThat(ds.isConnection(1, 4)).isEqualTo(true);

		/**
		 6
		/ \
		7   8
		*/
		ds.connnect(7, 6);
		ds.connnect(8, 6);
		// [-6, 0, 0, 0, 3, 3, -3, 6, 6]
		IO.println(Arrays.toString(ds.parent));

		/**
		       0                     
		    / | \  \                  
		   1  2  3    6
		        / \  / \
		       5   4 7  8
		 */
		ds.connnect(1, 8);
		// [-9, 0, 0, 0, 3, 3, 0, 6, 6]
		IO.println(Arrays.toString(ds.parent));
		Truth.assertThat(ds.isConnection(7, 5)).isEqualTo(true);
		Truth.assertThat(Math.abs(ds.parent[0])).isEqualTo(ds.parent.length);
		IO.println("Good Examples");
	}

}