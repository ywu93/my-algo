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
     * <br> I use an integer depth to keep track of how many open parentheses '(' haven’t been closed yet.
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
     * We iterate through each character in the string.
     *
     * <br>If it’s an opening bracket (like '(', '[', or '{'), we push it onto a stack.
     *
     * <br>If it’s a closing bracket (like ')', ']', or '}'), we check whether the stack is not empty and whether the top element matches the current closing bracket.
     *
     * <br>If it matches, we pop the top element from the stack.
     *
     * <br>If it doesn’t match or the stack is empty, the string is invalid, so we return false.
     * <br>After we finish scanning all characters, the string is valid only if the stack is empty — meaning all opening brackets have been properly closed.
     *
     * <br>
     * Time Complexity:
     * O(n), where n is the length of the string — because we visit each character once.
     * <br>
     * Space Complexity:
     * O(n) in the worst case — when all characters are opening brackets and we push all of them onto the stack.
     *
     * @param s
     * @return
     */
    public static boolean isValid_stackVersion(String s) {
       Stack<Character> leftStack = new Stack<>();
       for (char c : s.toCharArray()) {
          if (isOpenBracket(c)) {
              leftStack.push(c);
          } else if (isCloseBracket(c)) {
              if (leftStack.isEmpty()) {
                  return false;
              }
              if (leftStack.peek() != matchOpenBracket(c)) {
                  return false;
              }
              leftStack.pop();
          }
       }
       return leftStack.isEmpty();
    }

    private static boolean isOpenBracket (char c) {
        return c == '(' || c == '[' || c == '{';
    }
    private static boolean isCloseBracket (char c) {
        return c == ')' || c == ']' || c == '}';
    }

    private static Character matchOpenBracket(char closeBracket) {
        if (closeBracket == ')') return '(';
        else if (closeBracket == ']') return '[';
        else if (closeBracket == '}') return '{';
        else return null;
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
     *  I'm going to use a stack to simulate the nested structure.
     *  <br> The stack store the node counts for each depth level
     *  <br> I start by pushing a 0 as a virtual root, representing depth 0
     *  <br> When I see an open round bracket, it means we're entering a new level:
     *          <br>we pop the parent level count, increment it by 1, and push it back - meaning the parent just got
     *          a new child.
     *          <br> then, we push 0 for the new level's child count
     *          <br> during this step, we also maintain maxBreadth with the parent's updated count.
     *  <br> When I see a close bracket, it means the current level ends, maybe a temporary end - so I pop it off the stack
     *  <br> but it's no necessarily the final end - we might later open anther sub-level at the same parent level.
     *  <br> At the end, maxBreadth contains the maximum number of breadth at any level
     *
     * @param s
     * @return
     */
    public static int calcMaxBreadth(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0); // 虚拟根节点
        int maxBreadth = 0;

        System.out.println("=== Start calculating max breadth ===");
        System.out.println("Input: " + s);
        System.out.println("------------------------------------");

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                int parentCount = stack.pop() + 1; // 父层节点数 +1
                stack.push(parentCount);
                maxBreadth = Math.max(maxBreadth, parentCount);
                stack.push(0); // 新层初始化为0

                System.out.printf("[%d] '%c' → Enter new level | parentCount=%d, maxBreadth=%d, stack=%s%n",
                        i, c, parentCount, maxBreadth, stack);
            } else if (c == ')') {
                if (!stack.isEmpty()) {
                    int finishedLevel = stack.pop();
                    System.out.printf("[%d] '%c' → Exit level | finishedLevelCount=%d, stackAfterPop=%s%n",
                            i, c, finishedLevel, stack);
                } else {
                    System.out.printf("[%d] Warning: Unmatched ')' ignored.%n", i);
                }
            } else {
                System.out.printf("[%d] Ignored char: '%c'%n", i, c);
            }
        }

        System.out.println("------------------------------------");
        System.out.println("Final Result:");
        System.out.println("Max Breadth = " + maxBreadth);
        System.out.println("Final Stack = " + stack);
        System.out.println("====================================");

        return maxBreadth;
    }

    public static boolean isValid_DFS(String s) {
        return dfs(s, 0, new ArrayDeque<>());
    }

    private static boolean dfs(String s, int index, Deque<Character> stack) {
        // 1. 走到末尾：只有栈空才算成功
        if (index == s.length()) {
            return stack.isEmpty();
        }

        char c = s.charAt(index);

        // 2. 遇到开括号：选择入栈，然后继续 DFS
        if (isOpen(c)) {
            stack.push(c);
            if (dfs(s, index + 1, stack)) return true;
            stack.pop();                     // backtrack
            return false;                    // 没有别的分支
        }

        // 3. 遇到闭括号：必须匹配栈顶
        if (isClose(c)) {
            if (!stack.isEmpty() && stack.peek() == match(c)) {
                stack.pop();
                if (dfs(s, index + 1, stack)) return true;
                stack.push(match(c));        // backtrack
            }
            return false; // 不匹配 → 死路
        }

        // 4. 其他字符：直接跳过
        return dfs(s, index + 1, stack);
    }

    private static boolean isOpen(char c) {
        return c == '(' || c == '{' || c == '[';
    }

    private static boolean isClose(char c) {
        return c == ')' || c == '}' || c == ']';
    }

    private static char match(char c) {
        if (c == ')') return '(';
        if (c == ']') return '[';
        if (c == '}') return '{';
        return '#';
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
        System.out.println(isValid_stackVersion("()[]{}"));
        System.out.println(isValid_stackVersion("((){[]}"));
        System.out.println((calcMaxBreadth("(()())(())")));

        System.out.println(isValid_DFS("()[]{}"));
    }

}
