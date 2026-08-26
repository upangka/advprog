package cs61b;

import java.util.Iterator;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/26
 */
public class BSTMap<K extends Comparable<? super K>, V> implements Map16B<K, V> {

    private static class BSTNode<K,V>{
        private K key;
        private V value;
        private BSTNode<K,V> left;
        private BSTNode<K,V> right;

        /**
         *  查找节点
         * @param bstNode 当前要处理的节点
         * @param sk    Search Key
         * @return 找到的节点 | null
         * @param <K>
         * @param <V>
         */
        private static <K extends Comparable<? super K>, V> BSTNode<K,V> find(BSTNode<K,V> bstNode,K sk){

            return null;
        }
    }

    private BSTNode<K,V> root;
    private int size;

    public BSTMap(){}


    /**
     * Returns the value to which the specified key is mapped,or null if this
     * map contains no mapping for the key
     *
     * @param key
     * @return
     */
    @Override
    public V get(K key) {
        var ret =  BSTNode.find(root, key);
        if(ret == null){
            return null;
        }
        return ret.value;
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

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        return null;
    }

}
