///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS io.vavr:vavr:1.0.0
import io.vavr.Tuple;

void main(String... args) {
    IO.println("Exchange data");
    var langOne = "Java";
    var langTwo = "Python";
    IO.println("langOne=%s langTwo=%s".formatted(langOne,langTwo));
    
    // Exchange data 
    var tuple = Tuple.of(langOne,langTwo);
    langOne = tuple._2;
    langTwo = tuple._1;
    IO.println("langOne=%s langTwo=%s".formatted(langOne,langTwo));
    
}
