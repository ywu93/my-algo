package my.labs.leetcode.recursive;

import my.labs.ds.binarytree.TreeNode;

public class Lc112_pathSum {
    // 记住，一定不能只往左边走或只往右边走，要全路径覆盖
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        targetSum -= root.value;
        if (root.left == null && root.right == null){
            return targetSum == 0;
        }
        return hasPathSum(root.left,targetSum) ||  hasPathSum(root.right,targetSum);
    }
}
