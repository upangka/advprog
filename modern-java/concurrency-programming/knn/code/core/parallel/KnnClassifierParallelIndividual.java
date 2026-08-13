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

/**
 * KnnClassifierParallelIndividual
 */
public class KnnClassifierParallelIndividual implements KnnI{
    private final ThreadPoolExecutor executor;
    private final List<? extends Sample> datasets;
    private final CountDownLatch endController;
    private final boolean parallelSort;


    public KnnClassifierParallelIndividual(List<? extends Sample> datasets){
        this(datasets,0.5,true);
    }

    public KnnClassifierParallelIndividual(List<? extends Sample> datasets ,double factor,boolean parallelSort){
        this.datasets = datasets;
        this.endController = new CountDownLatch(0);
        int threadPoolSize = (int) (Runtime.getRuntime().availableProcessors() * (1 + factor));
        this.executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(threadPoolSize);
        this.parallelSort = parallelSort;
    }

    @Override
    public String classifyPredict(Sample sample) throws InterruptedException{
       Distance[] distances = new Distance[datasets.size()];

        endController.await();
        // 此刻distances数据全部准备好
        if(parallelSort){
            Arrays.parallelSort(distances, comparator);
        }else{
            Arrays.sort(distances,comparator);
        }
        return null;
    }
    
}
