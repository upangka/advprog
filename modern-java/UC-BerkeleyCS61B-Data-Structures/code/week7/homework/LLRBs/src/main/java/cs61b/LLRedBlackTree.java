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
}
