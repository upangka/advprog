///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//SOURCES ./**/*.java

import loader.BankMarketingLoader;
import model.BankMarketing;

static final String TRAIN_DATAS_PATH = "/home/pkmer/projects/advprog/modern-java/concurrency-programming/knn/resources/bank.data";

void main(String... args) {
    var loader = new BankMarketingLoader(TRAIN_DATAS_PATH);
    List<BankMarketing> samples = loader.load();
    System.out.println(samples.size());
    System.out.println(samples.get(79).getTag());
    System.out.println(Arrays.toString(samples.get(79).getExample()));

}
