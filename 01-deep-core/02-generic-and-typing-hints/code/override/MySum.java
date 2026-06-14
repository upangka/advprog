import java.util.function.Function;

/**
 * JDK 26
 * java MySum.java
 */
public class MySum {
    private static final Object MISSING = new Object();
    private static final String MSG = "max() arg is en empty sequence";

    /*
     * 等价
     * 
     * @overload
     * def mymax(_arg1: LT,/, *args: LT, key: None = ...) -> LT: ...
     * 
     * @overload
     * def mymax(first: LT, *args: LT, key: None = ...) -> LT: ...
     * 
     * @overload
     * def mymax(_arg1: LT,_arg2:LT,/, *args: LT, key: None = ...) -> LT:
     * ...
     */
    public <T extends Comparable<? super T>> T mymax(T first, T[] rest) {
        return this.mymax(first, rest, t -> t);
    }

    public <T, R extends Comparable<? super R>> T mymax(
            T first, T[] rest,
            Function<? super T, ? extends R> key) {

        T condicate = first;
        R condicateKey = key.apply(condicate);
        for (T current : rest) {
            R currentKey = key.apply(current);
            if (currentKey.compareTo(condicateKey) > 0) {
                condicate = current;
                condicateKey = currentKey;
            }
        }
        return condicate;
    }

    /*
     * 等价
     * 
     * @overload
     * def mymax(_iterable: Iterable[LT], /, *, key: None = ...) -> LT: ...
     */
    public <T extends Comparable<? super T>> T mymax(Iterable<T> iterable) {
        return this.mymax(iterable, t -> t);
    }

    /*
     * 等价
     * 
     * @overload
     * def mymax(_iterable: Iterable[T], /, *, key: Callable[[T], LT]) -> T: ...
     */
    public <T, R extends Comparable<? super R>> T mymax(Iterable<T> iterable, Function<? super T, ? extends R> key) {
        var iter = iterable.iterator();
        if (!iter.hasNext()) {
            throw new IllegalArgumentException(MSG);
        }

        T candidate = iter.next();
        R candidate_key = key.apply(candidate);
        while (iter.hasNext()) {
            var current = iter.next();
            var current_key = key.apply(current);
            if (current_key.compareTo(candidate_key) > 0) {
                candidate_key = current_key;
                candidate = current;
            }
        }
        return candidate;
    }

}
