package my.labs.leetcode;

import java.util.*;

/**
 * Check <a href="https://leetcode.com/problems/valid-parentheses/description/">Lc20_ValidParentheses </a>
 */
public class Lc20_ValidParentheses {
    // 这道题如果只有一种括号类型，则根本不需要stack来实现
    /**
     * 判断是否有效
     * @param s
     * @return
     * Time complexity O (n)
     * space O (1)
     *
     * This function checks if a parentheses string is valid.
     *
     * I use an integer depth to keep track of how many open parentheses '(' haven’t been closed yet.
     *
     * As I iterate through the string:
     *
     * When I see '(', I increase depth by 1.
     *
     * When I see ')', I decrease depth by 1.
     *
     * If depth ever becomes -1, that means there’s a closing parenthesis without a matching opening one, so I return false immediately.
     *
     * After finishing the loop, I check if depth == 0.
     *
     * If it’s zero, all parentheses are properly matched.
     *
     * Otherwise, it means there are unmatched '(' left, so it’s invalid.
     *
     * Time complexity: O(n), since I only scan the string once.
     * Space complexity: O(1), because I only use one integer variable.
     */
    public static boolean isValid(String s) {
       // ()
        int depth = 0;
        if (s == null || s.isEmpty()) {
            return false;
        }
        for (char c : s.toCharArray()) {
            if (c == '(') {
                depth ++;
            } else if (c == ')') {
                depth --;
            }
            if (depth == -1) {
                return false;
            }
        }
        return depth == 0;
    }

    /**
     * I maintain two counters:
     *
     * depth: the current nesting level of parentheses.
     *
     * maxDepth: the maximum depth seen so far.
     *
     * For every character:
     *
     * If it’s '(', you go one level deeper (depth++), and update maxDepth if needed.
     *
     * If it’s ')', you go one level up (depth--).
     *
     * If at any point depth becomes -1, it means there’s a ')' without a matching '(' — so return -1 (invalid string).
     *
     * After the loop:
     *
     * If depth is not 0, there are unmatched '(', so also return -1.
     *
     * Otherwise, return maxDepth, which is the maximum nesting depth.
     *
     *  Time complexity: O(n), since I only scan the string once.
     * Space complexity: O(1), because I only use two integer variables.
     * @param s
     * @return
     */
    public static int calcMaxDepth(String s) {
        int depth = 0;
        int maxDepth = 0;
        for (char c: s.toCharArray()) {
            if (c == '('){
                depth ++;
                if (depth > maxDepth) {
                    maxDepth = depth;
                }
            }else if (c == ')') {
                depth --;
                if (depth == -1) {
                    return -1;
                }
            }
        }
        return depth == 0 ? maxDepth: -1;
    }

    /**
     * 计算括号序列的最大宽度（同一层同时出现的节点数）
     */
    public static int calcMaxBreadth(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0); // 栈底是虚拟根节点，代表深度 0 层

        int maxWidth = 0; // 最大宽度
        // ()

        for (char c : s.toCharArray()) {
            if (c == '(') {
                // 遇到 '(' → 进入下一层
                // 栈顶是当前父层（上一层）的节点计数
                int parentCount = stack.pop() + 1; // 父层的节点数 +1
                stack.push(parentCount);           // 更新父层计数

                maxWidth = Math.max(maxWidth, parentCount); // 更新最大宽度

                stack.push(0); // 当前层（新开层）的子节点计数初始化为 0
            } else if (c == ')') {
                // 遇到 ')' → 当前层结束，弹出当前层计数
                // 回到上一层（父层）继续处理
                stack.pop();
            }
        }

        return maxWidth; // 返回最大宽度
    }



    public static void main(String[] args){
//        String p = "((())";
//        String p1 = "()())";
//        System.out.println(isValid(p));
//        System.out.println(isValid(p1));
//
//        String p2 = "((())) ()()";
//        System.out.println (calcMaxDepth(p2));
//        String p3 = "()(())";
//        System.out.println (calcMaxDepth(p3));
//        String p4 = "(()) (((())";
//        System.out.println (calcMaxDepth(p4));


        System.out.println(calcMaxBreadth("((()))"));
    }

}
