package my.labs.leetcode.recursive;

import my.labs.ds.binarytree.TreeNode;

public class Lc111_minDepth {
    public int minDepth(TreeNode root) {
        return traverseTreeDFS(root);
    }
    // 这道题的坑在于如果左右子树有一个深度是0，最小深度不是0，深度应该返回那个不为0的子树+1
    // 这跟比较最大深度还不一样。
    // 先用纯递归的思路解题
    private int traverseTreeDFS(TreeNode node) {
        if (node == null) return 0;

        int leftDepth = traverseTreeDFS(node.left);
        int rightDepth = traverseTreeDFS(node.right);
        if (leftDepth == 0) return rightDepth + 1;
        else if (rightDepth == 0) return leftDepth + 1;
        else {
            return Math.min(leftDepth,rightDepth) + 1;
        }
    }
}
