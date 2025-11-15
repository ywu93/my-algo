package my.labs.leetcode.recursive;

import my.labs.ds.binarytree.TreeNode;

public class Lc100_sameTree {
    // 注意这里不能认为p.val == q.val就一样了，还是去继续执行子树的比较，一直到p == null && p==null的时候才能认为一样
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        if (p.value != q.value) return false;

        return isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
    }
}
