package cs61b;

import com.google.common.truth.Truth;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Black Nodes are represented with () and red nodes are represented with () *
 * Left children are listed before right children
 *
 * @author 鲨鱼不喝Jvaa 抖音号:77283340926
 * @version 1.0
 * @since 2026/8/28
 */
@Slf4j
public class LLRedBlackTreeTest {

    /*
    Tests for a very basic case of rotating right. This does not check for color flips, but only if the nodes are in the proper
    place after rotating right. Note that we have not provided any basic tests for rotate left, but implementation details for
    rotate right and rotate left should be symmetrical.
     */

    /**
     * Tests for a very basic case of rotating right. This does not check for color flips,
     * but only if the nodes are in the proper place after rotating right.
     */
    @Test
    public void sanityTestRotateRight() {
        LLRedBlackTree<Integer> rbTree = new LLRedBlackTree<>();
        var node1 = new LLRedBlackTree.RBTreeNode<Integer>(true, 7, null, null);
        var node2 = new LLRedBlackTree.RBTreeNode<Integer>(false, 6, null, null);
        var node3 = new LLRedBlackTree.RBTreeNode<Integer>(false, 5, null, null);
        /**
         * LLRBs representation:
         *  (7)
         *  └── (6)*
         *      └── (5)*
         */
        node1.left = node2;
        node2.left = node3;

        Truth.assertThat(rbTree.hasTwoConsecutiveRedLeftLinks(node1)).isTrue();
        var node = rbTree.rotateRight(node1);
        /**
         * LLRBs representation:
         *  (6)
         *  ├── (5)*
         *  └── (7)*
         */
        Truth.assertThat(node.isBlack).isTrue();
        Truth.assertThat(node.item).isEqualTo(6);

        Truth.assertThat(node.left.isBlack).isFalse();
        Truth.assertThat(node.left.item).isEqualTo(5);

        Truth.assertThat(node.right.isBlack).isFalse();
        Truth.assertThat(node.right.item).isEqualTo(7);

        log.info("rotateRight: Good Test");
    }

    @Test
    @DisplayName("insert(rotateRight+flip")
    public void sanityTestInsertSimple() {
        LLRedBlackTree<Integer> rbTree = new LLRedBlackTree<>();
        rbTree.insert(7);
        rbTree.insert(6);
        rbTree.insert(5);

        /**
         * LLRBs representation:
         *  (6)
         *  ├── (5)
         *  └── (7)
         */
        Truth.assertThat(rbTree.root.isBlack).isTrue();
        Truth.assertThat(rbTree.root.left.isBlack).isTrue();
        Truth.assertThat(rbTree.root.right.isBlack).isTrue();

        Truth.assertThat(rbTree.root.item).isEqualTo(6);
        Truth.assertThat(rbTree.root.left.item).isEqualTo(5);
        Truth.assertThat(rbTree.root.right.item).isEqualTo(7);
    }


    @Test
    @DisplayName("insert(upward propagation)")
    public void sanityTestInsertComplex() {
        LLRedBlackTree<Integer> rbTree = new LLRedBlackTree<>();
        rbTree.insert(7);
        rbTree.insert(6);
        rbTree.insert(5);
        rbTree.insert(4);
        rbTree.insert(3);
        rbTree.insert(2);
        rbTree.insert(1);

        /**
         *            (4)
         *             ├── (2)
         *             │   ├── (1)
         *             │   └── (3)
         *             └── (6)
         *                 ├── (5)
         *                 └── (7)
         */

        // 中序遍历会是1,2,3,4,5,6,7 并且此时树节点都是黑色
        Deque<LLRedBlackTree.RBTreeNode<Integer>> stack = new ArrayDeque<>();
        var expected = List.of(1, 2, 3, 4, 5, 6, 7);
        var actual = new ArrayList<Integer>();
        var currentNode = rbTree.root;
        while (currentNode != null || !stack.isEmpty()) {

            while (currentNode != null) {
                stack.push(currentNode);
                currentNode = currentNode.left;
            }

            currentNode = stack.pop();
            actual.add(currentNode.item);
            Truth.assertThat(currentNode.isBlack).isTrue();
            currentNode = currentNode.right;
        }

        Truth.assertThat(actual).isEqualTo(expected);
        log.info("insert 7 to 1: Good Test");
    }


}
