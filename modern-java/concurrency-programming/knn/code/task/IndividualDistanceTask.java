///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package task;

import java.util.concurrent.CountDownLatch;

import model.Distance;
import model.Sample;
import util.EuclideanDistanceCalculator;

/**
 * 一个距离一个任务
 */
public class IndividualDistanceTask implements Runnable {
	private final Sample trainSample;
	private final Sample testSample;
	private final int index;
	private final CountDownLatch endController;
	private final Distance[] distances;

	public IndividualDistanceTask(Distance[] distances, int index, Sample trainSample, Sample testSample,
			CountDownLatch endController) {
		this.trainSample = trainSample;
		this.testSample = testSample;
		this.index = index;
		this.distances = distances;
		this.endController = endController;
	}

	@Override
	public void run() {
		double distance = EuclideanDistanceCalculator.calculate(trainSample, testSample);
		distances[index] = new Distance(index, distance);
		endController.countDown();
		// System.out.println("[%s] 完成 ——> %d".formatted(Thread.currentThread().getName(),this.index));
	}

}