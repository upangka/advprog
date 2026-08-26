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

        public BSTNode(K key, V value) {
            this(key, value, null, null);
        }

        public BSTNode(K key, V value, BSTNode<K,V> left,BSTNode<K,V> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        /**
         *  查找节点
         * @param bstNode 当前要处理的节点
         * @param sk    Search Key
         * @return 找到的节点 | null
         * @param <K>
         * @param <V>
         */
        private static <K extends Comparable<? super K>, V> BSTNode<K,V> find(BSTNode<K,V> bstNode,K sk){
            if(bstNode == null){
                return null;
            }
            var ret = bstNode.key.compareTo(sk);
            // 二分查找
            if(ret == 0){
                return bstNode;
            }else if(ret > 0){
                return find(bstNode.left,sk);
            }else{
                return find(bstNode.right,sk);
            }
        }

        /**
         * 插入节点
         * @param bstNode 当前要查找的节点
         * @param sk    Search Key
         * @param val   Value
         * @param <K>
         * @param <V>
         */
        private static  <K extends Comparable<? super K>, V> BSTNode<K,V> insert(BSTNode<K,V> bstNode,K sk,V val){
            // Always insert at leaf node
            if(bstNode == null){
                return new BSTNode<K,V>(sk,val);
            }

            var ret = bstNode.key.compareTo(sk);

            if(ret < 0){
                bstNode.right = insert(bstNode.right,sk,val);
            }else if(ret > 0){
                bstNode.left = insert(bstNode.left,sk,val);
            }else{
                // 相等，直接更新value
                bstNode.value = val;
            }

            return bstNode;
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
         var hasKey = containsKey(key);
         root = BSTNode.insert(root,key,value);
         if(!hasKey){
             this.size += 1;
         }
    }



    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     * @return
     */
    @Override
    public boolean containsKey(K key) {
        var ret =  BSTNode.find(root, key);
        return ret != null;
    }

    /**
     * Returns the number of key-value mappings in this map
     *
     * @return
     */
    @Override
    public int size() {
        return this.size;
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
