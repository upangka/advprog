///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//SOURCES ./**/*.java

import core.serial.KnnClassifier;
import loader.BankMarketingLoader;

static final String TRAIN_DATAS_PATH = "/home/pkmer/projects/advprog/modern-java/concurrency-programming/knn/resources/bank.data";
static final String TEST_DATAS_PATH = "/home/pkmer/projects/advprog/modern-java/concurrency-programming/knn/resources/bank.test";
static int K = 10;

void main(String... args) {
	var loader = new BankMarketingLoader();
	var trainSamples = loader.load(TRAIN_DATAS_PATH);
	var testSamples = loader.load(TEST_DATAS_PATH);

	KnnClassifier knnClassifier = new KnnClassifier(trainSamples, K);

	var sample = testSamples.get(29);
	String tagActual = sample.getTag();
	String tagPredict = knnClassifier.classify(sample);
	System.out.printf("%s %s\n", tagActual, tagPredict);

}
