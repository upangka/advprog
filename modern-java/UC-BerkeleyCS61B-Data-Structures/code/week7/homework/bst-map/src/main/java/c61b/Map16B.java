package c61b;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/26
 */
public interface Map16B<K,V> extends Iterable<K> {

    /**
     * Associates the specified value with specified key in this map.
     * If the map already contains the specified key,replace the key's mapping
     * with the value specified.
     * @param key
     * @param value
     */
    void put(K key,V value);


    /**
     * Returns the value to which the specified key is mapped,or null if this
     * map contains no mapping for the key
     * @param key
     * @return
     */
    V get(K key);

    /**
     * Returns whether this map contains a mapping for the specified key.
     * @param key
     * @return
     */
    boolean containsKey(K key);

    /**
     * Returns the number of key-value mappings in this map
     * @return
     */
    int size();

    /**
     * Removes every mapping from this map
     */
    void clear();
}
