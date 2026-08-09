///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package quickunion;

import java.util.Arrays;
import interfaces.DisjointSet;

public class QuickUnion implements DisjointSet {
	public final int[] parent;

	public QuickUnion(int n) {
		this.parent = new int[n];
		Arrays.fill(this.parent, -1);
	}

	private int findRoot(int p) {

		int r = p;
		while (parent[r] != -1) {
			r = parent[r];
		}

		return r;

	}

	@Override
	public void connnect(int p, int q) {
		var rp = findRoot(p);
		var rq = findRoot(q);
		if (rp != rq) {
			parent[rp] = rq;
		}
	}

	@Override
	public boolean isConnection(int p, int q) {
		return findRoot(p) == findRoot(q);
	}

}