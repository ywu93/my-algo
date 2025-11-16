package my.labs.leetcode.recursive;

import my.labs.ds.binarytree.TreeNode;

public class Lc110_balancedBinaryTree {
    public boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;

    }

    // 返回树的高度，如果不平衡返回 -1
    private int checkHeight (TreeNode node) {
        if (node == null) {
            return 0;
        }
        // 检查左子树
        int leftHeight = checkHeight(node.left);
        if (leftHeight == -1) {
            return -1;
        }
        // 检查右子树
        int rightHeight = checkHeight (node.right);
        if (rightHeight == -1) {
            return -1;
        }

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }
        return Math.max(leftHeight,rightHeight) + 1;
    }

}
