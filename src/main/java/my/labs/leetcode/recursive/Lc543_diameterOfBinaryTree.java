package my.labs.leetcode.recursive;

import my.labs.ds.binarytree.TreeNode;

/**
 * 这道题的要点是如何理解树的直径，
 * <br>所谓直径就是左右子树高度之和，
 * <br> 如果要计算最大直径，就是把每个节点的左右子树计算下高度之后，找到最大的那个数字
 */
public class Lc543_diameterOfBinaryTree {
    private int maxDiameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        traverseDfs(root);
        return maxDiameter;
    }

    int traverseDfs(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftDepth = traverseDfs (node.left);
        int rightDepth = traverseDfs(node.right);
        int diameter = leftDepth + rightDepth;
        maxDiameter = Math.max(diameter,maxDiameter);
        return Math.max(leftDepth,rightDepth) + 1;
    }
}
