///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package core.serial;

import module java.base;
import java.util.Map.Entry;

import core.KnnI;
import model.Distance;
import model.Sample;

public class KnnClassifier implements KnnI {
	static final Comparator<Distance> comparator = new Comparator<Distance>() {
		public int compare(Distance o1, Distance o2) {
			return Double.compare(o2.distance(), o1.distance());
		};
	};
	final int k;
	final List<? extends Sample> dataset;

	public KnnClassifier(List<? extends Sample> dataset, int k) {
		this.dataset = dataset;
		this.k = k;
	}

	@Override
	public String classify(Sample sample) {

		// TODO
		List<Distance> distances = new ArrayList<>(dataset.size());

		distances.sort(comparator);

		var ret = new HashMap<String, Integer>();

		for (int i = 0; i < this.k; i++) {
			int idx = distances.get(i).idx();
			Sample locaSample = dataset.get(idx);
			ret.merge(locaSample.getTag(), 1, (a, b) -> a + b);
		}

		Entry<String, Integer> maxEntry = Collections.max(ret.entrySet(), Map.Entry.comparingByValue());
		return maxEntry.getKey();
	}
}