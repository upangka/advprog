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
import task.GroupDistanceTask;

/**
 * KnnClassifierParallelIndividual
 */
public class KnnClassifierParallelGroup implements KnnI {
	private final ThreadPoolExecutor executor;
	private final List<? extends Sample> datasets;
	private final boolean parallelSort;
	private final int k;

	public KnnClassifierParallelGroup(List<? extends Sample> datasets, int k) {
		this(datasets, k, 0, true);
	}

	public KnnClassifierParallelGroup(List<? extends Sample> datasets, int k, double factor,
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
		int numThreads = this.executor.getCorePoolSize();
		int length = datasets.size() / numThreads;
		int startIndex = 0, endIndex = length;

		// 设置为线程的数量，因为是以每个线程为一组进行分配任务的
		var endController = new CountDownLatch(numThreads);
		for (int i = 0; i < length; i++) {
			if (i == length - 1) {
				endIndex += datasets.size();
			}
			var task = new GroupDistanceTask(distances, startIndex, endIndex, this.datasets, sample, endController);
			executor.submit(task);
			startIndex = endIndex;
			endIndex += length;
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
