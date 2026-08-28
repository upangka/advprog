package cs61b;

/**
 * Left Leaning Red Black Tree(左倾红黑树)
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/28
 */
public class LLRedBlackTree<T extends Comparable<? super T>> {

    private static class RBTreeNode<T> {
        private final T item;
        private boolean isBlack;
        private RBTreeNode<T> left;
        private RBTreeNode<T> right;

        public RBTreeNode(boolean isBlack, T item) {
            this(isBlack, item, null, null);
        }

        public RBTreeNode(boolean isBlack, T item, RBTreeNode<T> left, RBTreeNode<T> right) {
            this.item = item;
            this.isBlack = isBlack;
            this.left = left;
            this.right = right;
        }

    }

    /**
     * Flips the color of node and its children.
     * Assume that {@code node} has both left and right children
     *
     * @param node
     */
    private void flipColors(RBTreeNode<T> node) {
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }

    /**
     * Rotates the given node to the right. Returns the new root node of
     * this subtree. For this implemention, make sure to swap the colors
     * of the new root and the old root!.
     *
     * @param node
     * @return
     */
    private RBTreeNode<T> rotateRight(RBTreeNode<T> node) {
        var candidate = node.left;
        node.left = candidate.right;
        candidate.right = node;

        // swap color
        var isBlack = node.isBlack;
        node.isBlack = candidate.isBlack;
        candidate.isBlack = isBlack;

        return candidate;
    }

    /**
     * Rotates the given node to the left. Returns the new root node of
     * this subtree. For this implementation, make sure to swap the colors
     * of the new root and the old root!
     *
     * @param node
     * @return
     */
    RBTreeNode<T> rotateLeft(RBTreeNode<T> node) {
        var candidate = node.right;

        node.right = candidate.left;
        candidate.left = node;

        // swap color
        var isBlack = node.isBlack;
        node.isBlack = candidate.isBlack;
        candidate.isBlack = isBlack;

        return candidate;
    }

    private RBTreeNode<T> root;

    /**
     * Inserts the item into the Red Black Tree. Colors the root of the tree black.
     *
     * @param item
     */
    public void insert(T item) {
        root = insertHelper(root, item);
        root.isBlack = true;
    }

    private RBTreeNode<T> insertHelper(RBTreeNode<T> node, T item) {

        // 1. Insert (return) new red leaf node.
        // 2. Handle normal binary search tree insertion.
        // 3.  Rotate left operation
        // 4.  Rotate right operation
        // 5.  Color flip

        if (node == null) {
            return new RBTreeNode<>(false, item, null, null);
        }

        var ret = node.item.compareTo(item);
        if (ret < 0) {
            // 说明在右边
            node.right = insertHelper(node.right, item);
        } else if (ret > 0) {
            // 说明在左边
            node.left = insertHelper(node.left, item);
        } else {
            // 相等则ignore不处理，因为不允许重复的元素
            return node;
        }
        return fixTree(node);
    }

    private RBTreeNode<T> fixTree(RBTreeNode<T> node) {
        // 检测三种情况
        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }

        if (hasTwoConsecutiveRedLeftLinks(node)) {
            node = rotateRight(node);
        }

        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }
        return node;
    }

    private boolean isRed(RBTreeNode<T> node) {
        // null节点为黑
        if (node == null) {
            return false;
        }
        return !node.isBlack;
    }

    private boolean hasTwoConsecutiveRedLeftLinks(RBTreeNode<T> node) {
        return isRed(node.left) && isRed(node.left.left);
    }
}
