package my.labs.leetcode.recursive;

import my.labs.ds.binarytree.TreeNode;

public class Lc101_symmetricTree {
    // 如何去理解对称？ 叶子节点是镜像关系？根节点一样？
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;
        return isMirror(root.left,root.right);

    }

    private boolean isMirror(TreeNode n1, TreeNode n2) {
        if (n1 == null && n2 == null) return true;
        if (n1 == null || n2 == null) return false;
        // 节点的值不相同一定是不对称的，若节点值相同就需要继续后续的比较
        if (n1.value != n2.value) return false;

        return isMirror(n1.left, n2.right) && isMirror(n1.right, n2.left);
    }
}
