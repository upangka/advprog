//JAVA 25+

package core;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.List;

import model.Distance;
import model.Sample;

public interface KnnI {

	/**
	 * 升序，取最小的前K条数据
	 */
	Comparator<Distance> comparator = new Comparator<Distance>() {
		public int compare(Distance o1, Distance o2) {
			return Double.compare(o1.distance(), o2.distance());
		};
	};

	default String getMinTagFromKSortedDistances(List<? extends Sample> dataset, Distance[] distances, int k) {
		var ret = new HashMap<String, Integer>();

		for (int i = 0; i < k; i++) {
			int idx = distances[i].idx();
			Sample locaSample = dataset.get(idx);
			ret.merge(locaSample.getTag(), 1, (a, b) -> a + b);
		}

		Entry<String, Integer> maxEntry = Collections.max(ret.entrySet(), Map.Entry.comparingByValue());
		return maxEntry.getKey();
	}

	String classifyPredict(Sample sample) throws Exception;

}
