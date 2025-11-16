package my.labs.leetcode.recursive;

import my.labs.ds.binarytree.TreeNode;

import java.util.ArrayList;
import java.util.List;

public class Lc257_binaryTreePaths {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        collectTreePaths(root, paths, "");
        return paths;
    }

    private void collectTreePaths(TreeNode node, List<String> paths, String path) {
        System.out.println("Path:" + path);
        if (node == null) return;
        // 叶子节点
        if (node.left == null && node.right == null) {
            String addedPath = addToPath(path, node.value);
            System.out.println("addToPath:" + addedPath);
            paths.add(addedPath);
            return;
        }
        String addedPath = addToPath(path, node.value);
        collectTreePaths(node.left, paths, addedPath);
        collectTreePaths(node.right, paths, addedPath);
    }

    private static String addToPath(String path, int val) {
        if ("".equals(path)) {
            path = String.valueOf(val);
        } else {
            path += "->" + val;
        }
        return path;
    }
}
