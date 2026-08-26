package c61b;

import java.util.Iterator;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/26
 */
public class BSTMap<K extends Comparable<? super K>, V> implements Map16B<K, V> {
    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return null;
    }

    /**
     * Associates the specified value with specified key in this map.
     * If the map already contains the specified key,replace the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {

    }

    /**
     * Returns the value to which the specified key is mapped,or null if this
     * map contains no mapping for the key
     *
     * @param key
     * @return
     */
    @Override
    public V get(K key) {
        return null;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     * @return
     */
    @Override
    public boolean containsKey(K key) {
        return false;
    }

    /**
     * Returns the number of key-value mappings in this map
     *
     * @return
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * Removes every mapping from this map
     */
    @Override
    public void clear() {

    }
}
