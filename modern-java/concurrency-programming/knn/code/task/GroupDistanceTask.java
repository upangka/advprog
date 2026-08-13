///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package task;

import java.util.concurrent.CountDownLatch;
import java.util.List;
import model.Distance;
import model.Sample;
import util.EuclideanDistanceCalculator;

/**
 * GroupDistanceTask
 */
public class GroupDistanceTask implements Runnable {
	private final List<? extends Sample> datasets;
	private final Sample testSample;
	private final int startIndex;
	private final int endIndex;
	private final CountDownLatch endController;
	private final Distance[] distances;

	public GroupDistanceTask(Distance[] distances, int startIndex, int endIndex, List<? extends Sample> datasets,
			Sample testSample,
			CountDownLatch endController) {
		this.datasets = datasets;
		this.testSample = testSample;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.endController = endController;
		this.distances = distances;
	}

	@Override
	public void run() {
		for (int i = startIndex; i < endIndex; i++) {
			Sample trainSample = this.datasets.get(i);
			double distance = EuclideanDistanceCalculator.calculate(trainSample, this.testSample);
			distances[i] = new Distance(i, distance);
		}
		endController.countDown();
	}
}