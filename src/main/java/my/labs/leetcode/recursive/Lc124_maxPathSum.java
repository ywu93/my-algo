package my.labs.leetcode.recursive;

import my.labs.ds.binarytree.TreeNode;


public class Lc124_maxPathSum {
    private int maxSumPath = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        calcMaxPath(root);
        return maxSumPath;
    }
    /**
     * LC124 - 二叉树中的最大路径和<br>
     * <br>
     * 【定义】<br>
     * 路径 = 任意连续节点序列，每对相邻节点有边，节点最多出现一次。<br>
     * 路径可以不经过根。<br>
     * 最大路径和 = 所有路径和里最大的。<br>
     * <br>
     * 【递归思路（后序遍历）】<br>
     * 递归函数 calc(node) 返回：<br>
     * &nbsp;&nbsp;节点 node 向上延伸的最大单边路径和<br>
     * <br>
     * 左子树最大单边路径 = Math.max(0, calc(node.left))<br>
     * 右子树最大单边路径 = Math.max(0, calc(node.right))<br>
     * 更新全局最大路径和 = node.val + left + right<br>
     * 返回单边路径 = node.val + max(left, right)<br>
     * <br>
     * 【关键点记忆口诀】<br>
     * 负数舍弃 → 用 0<br>
     * 全局最大 → 左 + 右 + 根<br>
     * 返回给父节点 → 根 + max(左,右)<br>
     * <br>
     * 【注意点】<br>
     * 单节点树或全负数树，maxSumPath 初始化为 Integer.MIN_VALUE<br>
     * 返回值不是最大路径和，而是父节点延伸的单边路径和<br>
     */
    private int calcMaxPath(TreeNode node) {
        if (node == null) {
            return 0;
        }
        // calculate left tree
        int leftSumPath = Math.max(0,calcMaxPath (node.left));
        // calculate right tree
        int rightSumPath = Math.max(0,calcMaxPath(node.right));
        int currentMaxPath = node.value + leftSumPath + rightSumPath;
        maxSumPath = Math.max(maxSumPath,currentMaxPath);
        return node.value + Math.max(leftSumPath,rightSumPath);
    }

}
