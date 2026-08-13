///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package core.serial;

import module java.base;
import java.util.Map.Entry;

import core.KnnI;
import model.Distance;
import model.Sample;
import util.EuclideanDistanceCalculator;

public class KnnClassifier implements KnnI {
	
	final int k;
	final List<? extends Sample> dataset;

	public KnnClassifier(List<? extends Sample> dataset, int k) {
		this.dataset = dataset;
		this.k = k;
	}

	@Override
	public String classifyPredict(Sample sample) throws Exception{

		Distance[] distances = new Distance[dataset.size()];

		for (int i = 0; i < this.dataset.size(); i++) {
			double distance = EuclideanDistanceCalculator.calculate(this.dataset.get(i), sample);
			distances[i] = new Distance(i, distance);
		}

		Arrays.sort(distances,comparator);

		return getMinTagFromKSortedDistances(this.dataset,distances,this.k);
	}
}