//JAVA 25+

package core;

import java.util.Comparator;

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

	String classifyPredict(Sample sample);
}
