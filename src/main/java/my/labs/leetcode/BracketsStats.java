package my.labs.leetcode;

import java.util.*;

public class BracketsStats {

    static class Node {
        List<Node> children = new ArrayList<>();
    }

    public static int maxWidth(String s) {
        Node root = new Node();      // 虚拟根节点
        Stack<Node> stack = new Stack<>();
        stack.push(root);

        // 构建树
        for (char c : s.toCharArray()) {
            if (c == '(') {
                Node node = new Node();
                stack.peek().children.add(node);
                stack.push(node);
            } else if (c == ')') {
                if (stack.isEmpty()) return -1; // 不匹配
                stack.pop();
            }
        }

        if (stack.size() != 1) return -1; // 不匹配

        // BFS计算最大宽度
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        int maxWidth = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            maxWidth = Math.max(maxWidth, size - 1); // 减去虚拟根
            for (int i = 0; i < size; i++) {
                Node node = queue.poll();
                queue.addAll(node.children);
            }
        }

        return maxWidth;
    }

    public static void main(String[] args) {
        String s = "(()()(()))((()))";
        System.out.println(maxWidth(s)); // 输出 3

        String p = "(()())(())";
        System.out.println(maxWidth(p)); // 输出 2
    }
}
