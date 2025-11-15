package my.labs.leetcode.recursive;

import my.labs.ds.binarytree.TreeNode;

public class Lc104_maxDepth {

    public int maxDepth(TreeNode root) {
        return retrieveTreeDFS_2(root);
    }

    // 这是我的第一思路，就是传depth，进去之后就加1
    // 最后返回的是比较左右子树，哪个深度大取哪个值
    // 还有一种更优化的思路，可以不传depth作为入参，见下面
    private int retrieveTreeDFS_1(TreeNode node, int depth) {
        if (node == null) {
            return depth;
        }
        depth++;
        // 遍历左子树
        int leftMaxDepth = retrieveTreeDFS_1(node.left, depth);
        int rightMaxDepth = retrieveTreeDFS_1(node.right, depth);
        return Math.max(leftMaxDepth, rightMaxDepth);
    }

    // 优化思路就是当前层的深度，应该是左右子树比较后的最大深度+1
    private int retrieveTreeDFS_2(TreeNode node) {
        if (node == null) return 0;
        int leftDepth = retrieveTreeDFS_2(node.left);
        int rightDepth = retrieveTreeDFS_2(node.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }
}
