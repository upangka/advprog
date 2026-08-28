package cs61b;

/**
 * Left Leaning Red Black Tree(左倾红黑树)
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/28
 */
public class LLRedBlackTree<T extends Comparable<? super T>>  {

        private static class RBTreeNode<T>{
            private final T item;
            private boolean isBlack;
            private RBTreeNode<T> left;
            private RBTreeNode<T> right;

            public RBTreeNode(boolean isBlack,T item){
                this(isBlack,item,null,null);
            }

            public RBTreeNode(boolean isBlack,T item,RBTreeNode<T> left,RBTreeNode<T> right){
                this.item = item;
                this.isBlack = isBlack;
                this.left = left;
                this.right = right;
            }

        }

    /**
     * Flips the color of node and its children.
     * Assume that {@code node} has both left and right children
     * @param node
     */
    private void flipColors(RBTreeNode<T> node){
        node.isBlack = !node.isBlack;
        node.left.isBlack = !node.left.isBlack;
        node.right.isBlack = !node.right.isBlack;
    }
}
