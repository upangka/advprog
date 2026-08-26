package cs61b;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/26
 */
public class BSTMap<K extends Comparable<? super K>, V> implements Map16B<K, V> {

    private static class BSTNode<K, V> {
        private K key;
        private V value;
        private BSTNode<K, V> left;
        private BSTNode<K, V> right;

        public BSTNode(K key, V value) {
            this(key, value, null, null);
        }

        public BSTNode(K key, V value, BSTNode<K, V> left, BSTNode<K, V> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        /**
         * 查找节点
         *
         * @param bstNode 当前要处理的节点
         * @param sk      Search Key
         * @param <K>
         * @param <V>
         * @return 找到的节点 | null
         */
        private static <K extends Comparable<? super K>, V> BSTNode<K, V> find(BSTNode<K, V> bstNode, K sk) {
            if (bstNode == null) {
                return null;
            }
            var ret = bstNode.key.compareTo(sk);
            // 二分查找
            if (ret == 0) {
                return bstNode;
            } else if (ret > 0) {
                return find(bstNode.left, sk);
            } else {
                return find(bstNode.right, sk);
            }
        }


        /**
         * 插入节点
         *
         * @param bstNode 当前要查找的节点
         * @param sk      Search Key
         * @param val     Value
         * @param <K>
         * @param <V>
         */
        private static <K extends Comparable<? super K>, V> BSTNode<K, V> insert(BSTNode<K, V> bstNode, K sk, V val) {
            // Always insert at leaf node
            if (bstNode == null) {
                return new BSTNode<K, V>(sk, val);
            }

            var ret = bstNode.key.compareTo(sk);

            if (ret < 0) {
                bstNode.right = insert(bstNode.right, sk, val);
            } else if (ret > 0) {
                bstNode.left = insert(bstNode.left, sk, val);
            } else {
                // 相等，直接更新value
                bstNode.value = val;
            }

            return bstNode;
        }

        private record DeleteResult<K, V>(
                /** 要链接的新节点 */
                BSTNode<K, V> newNode,
                /** 已经删除的节点 */
                BSTNode<K, V> deletedNode) {
        }

        /**
         * 删除操作
         *
         * @param bstNode 当前节点
         * @param sk      search key
         * @return 删除的节点
         */
        private static <K extends Comparable<? super K>, V> DeleteResult<K, V> delete(BSTNode<K, V> bstNode, K sk) {
            if (bstNode == null) {
                // 没有找到要删除的
                return new DeleteResult<K, V>(bstNode, null);
            }

            var ret = bstNode.key.compareTo(sk);
            DeleteResult<K, V> delRet = null;

            if (ret < 0) {
                delRet = delete(bstNode.right, sk);
                bstNode.right = delRet.newNode;
                // 重新包装，保持维持原先元素的链接
                return new DeleteResult<>(bstNode, delRet.deletedNode);
            } else if (ret > 0) {
                delRet = delete(bstNode.left, sk);
                bstNode.left = delRet.newNode;
                // 重新包装，保持维持原先元素的链接
                return new DeleteResult<>(bstNode, delRet.deletedNode);
            } else {
                // 没有子节点或者只有一个子节点
                if (bstNode.left == null) {
                    return new DeleteResult<K, V>(bstNode.right, bstNode);
                }

                if (bstNode.right == null) {
                    return new DeleteResult<K, V>(bstNode.left, bstNode); // 只有一个left节点
                }

                // 处理两个节点，这里采用Hibbard deletion的前驱节点
                var predecessor = bstNode.left;
                while (predecessor.right != null) {
                    predecessor = predecessor.right;
                }

                // 构建删除节点的信息
                var delNode = new BSTNode<K, V>(bstNode.key, bstNode.value);

                // trick: 直接更改 key和value,然后删除前驱节点，因为前驱节点只要一个或者0个子节点，
                // 不会形成无限递归
                bstNode.key = predecessor.key;
                bstNode.value = predecessor.value;
                // 以当前节点的左子树去找到前驱节点删除
                delete(bstNode.left, predecessor.key);

                return new DeleteResult<K, V>(bstNode, delNode);
            }
        }
    }

    private BSTNode<K, V> root;
    private int size;

    public BSTMap() {
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
        var ret = BSTNode.find(root, key);
        if (ret == null) {
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
        root = BSTNode.insert(root, key, value);
        if (!hasKey) {
            this.size += 1;
        }
    }

    /**
     * 删除
     *
     * @param key
     * @return
     */
    public V remove(K key) {
        var ret = BSTNode.delete(root, key);
        root = ret.newNode;
        if (ret.deletedNode == null) {
            return null;
        }
        this.size -= 1;
        return ret.deletedNode.value;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     * @return
     */
    @Override
    public boolean containsKey(K key) {
        var ret = BSTNode.find(root, key);
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
        // 交给GC
        this.root = null;
        this.size = 0;
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


    public static void main(String[] args) {
        var b = new BSTMap<String, Integer>();
        b.put("d", 4);
        b.put("b", 2);
        b.put("f", 6);
        b.put("a", 1);
        b.put("c", 3);
        b.put("e", 5);
        b.put("g", 7);
        var ret = new ArrayList<String>();
        b.inorder(b.root, ret);
        System.out.println(ret);
        // 树的结构（完全平衡）：
        //       d
        //      / \
        //     b   f
        //    / \ / \
        //   a  c e  g
        b.remove("d");
        ret = new ArrayList<String>();
        b.inorder(b.root, ret);
        System.out.println(ret);
    }

    private void inorder(BSTNode<K, V> node, List<K> keys) {
        if (node == null) return;
        inorder(node.left, keys);
        keys.add(node.key);
        inorder(node.right, keys);
    }

}
