package my.labs.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * <li> 考察DFS,递归就是隐式的栈</li>
 * <li>考察栈的引用，显示的栈</li>
 */
public class Lc394_decodeString {

    public static String decodeString(String encodedString){
        return solveByTwoStacks(encodedString);
    }



    /**
     * using explicit stack to solve
     * <br>核心口诀：“看到左边界压，看到右边界弹”。
     * <li> 在括号匹配、表达式求值、字符串解码（如 LC 394）等场景里，[、(、{ 就是“左边界”，它们的出现意味着新的作用域开始，必须先把当前状态“存档”——压栈；
     * <li>而对应的右边界出现时，说明当前作用域结束，需要“读档”——弹栈恢复上一层状态
     * @param encodedString
     * @return
     *
     * <li>个人的理解，当遇到一个新的情况（新的状态），需要处理新的现场，那么压栈的目的就是保护当前的现场，
     * 把当前需要保存的现场存起来，等处理完新的状态之后用来恢复现场
     */

   private static String solveByTwoStacks(String encodedString) {
        // 3[a]2[bc]
        Deque<Integer> countStack = new ArrayDeque<>();
        Deque<StringBuilder> strStack = new ArrayDeque<>();
        StringBuilder cur = new StringBuilder(); // 开始的时候解码的字符串为空串
        int k = 0;
        for (char ch : encodedString.toCharArray()){
            if (Character.isDigit(ch)){
                // get the number
                k = k * 10 + (ch - '0');
            } else if (ch == '[') {
                // 新的作用域开始，把当前状态保存，无论是数字 还是字符串
                // 开始保存现在得到的数字或字符串，然后将临时变量重置以便重复使用
                countStack.push(k);
                strStack.push(cur);
                k = 0;
                cur = new StringBuilder();

            }else if ( ch == ']'){
                int repeatTime = countStack.pop();
                StringBuilder prev =  strStack.pop();
                for (int i = 0; i < repeatTime; i++){
                    prev.append(cur);
                }
                cur = prev;

            }else {
                cur.append(ch);
            }
        }
        return cur.toString();
    }

    public static void main(String[] args) {
        System.out.println(decodeString("3[a5[ef]2[bc]]"));

    }
}
