///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

package core.parallel;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import core.KnnI;
import model.Distance;
import model.Sample;
import task.IndividualDistanceTask;

/**
 * KnnClassifierParallelIndividual
 */
public class KnnClassifierParallelIndividual implements KnnI {
	private final ThreadPoolExecutor executor;
	private final List<? extends Sample> datasets;
	private final boolean parallelSort;
	private final int k;

	public KnnClassifierParallelIndividual(List<? extends Sample> datasets, int k) {
		this(datasets, k, 0, true);
	}

	public KnnClassifierParallelIndividual(List<? extends Sample> datasets, int k, double factor,
			boolean parallelSort) {
		this.datasets = datasets;
		int threadPoolSize = (int) (Runtime.getRuntime().availableProcessors() * (1 + factor));
		this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadPoolSize);
		this.parallelSort = parallelSort;
		this.k = k;
	}

	@Override
	public String classifyPredict(Sample sample) throws InterruptedException {
		Distance[] distances = new Distance[datasets.size()];
		var endController = new CountDownLatch(this.datasets.size());
		for (int i = 0; i < distances.length; i++) {
			Sample trainSample = this.datasets.get(i);
			var task = new IndividualDistanceTask(distances, i, trainSample, sample, endController);
			executor.submit(task);
		}

		endController.await();
		// 此刻distances数据全部准备好
		if (parallelSort) {
			Arrays.parallelSort(distances, comparator);
		} else {
			Arrays.sort(distances, comparator);
		}

		return getMinTagFromKSortedDistances(this.datasets, distances, this.k);
	}

	@Override
	public void close() {
		this.executor.shutdown();
	}

}
