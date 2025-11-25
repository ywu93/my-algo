package my.labs.twopointers;

public class CompressSpaces {
    // 使用in-place方式连续压缩空格
    // 总结一下这里快慢指针的使用方式
    // 快指针用于从头到位游走数组，慢指针始终指向需要写入的位置
    // 写入的规则则是：
    // 快指针指向的不是空格，则把快指针的字符赋值给慢指针指向的位置，快慢指针可以是重合的。慢指针被赋值之后也要往前走一格。
    // 遇到空格时，由于我们的需求是合并空格，我们之需要留一个空格，那么我们需要看看慢指针的前一次赋值是不是空格，如果不是空格，我们要把空格赋值进来往前移动，
    // 如果是空格，则慢指针不动
    // 最后慢指针停留的位置应该是最后一次赋值之后的位置+1

    /**
     * 快慢指针在数组/字符串压缩空格中的使用说明：
     *
     * <p><b>快指针 (fast)</b></p>
     * <ul>
     *   <li>用于扫描整个数组或字符串，从头到尾遍历。</li>
     * </ul>
     *
     * <p><b>慢指针 (slow)</b></p>
     * <ul>
     *   <li>总是指向下一个需要写入的位置。</li>
     * </ul>
     *
     * <p><b>写入规则：</b></p>
     * <ul>
     *   <li>如果 fast 指向非空格字符 → 直接写到 slow 所指位置，slow++。</li>
     *   <li>如果 fast 指向空格：
     *       <ul>
     *           <li>若 slow 前一个字符不是空格 → 写入空格，slow++。</li>
     *           <li>若 slow 前一个字符是空格 → 跳过，不写，slow 不动。</li>
     *       </ul>
     *   </li>
     * </ul>
     *
     * <p><b>重合情况：</b></p>
     * <ul>
     *   <li>当 fast 和 slow 重合时，直接写也没问题。</li>
     * </ul>
     *
     * <p><b>结束时 slow 的含义：</b></p>
     * <ul>
     *   <li>slow 停下的位置就是有效结果的长度。</li>
     *   <li>可通过 <code>new String(arr, 0, slow)</code> 生成最终结果。</li>
     * </ul>
     */

    public static String compressSpaces(String s) {
        char [] arr = s.toCharArray();
        int slow = 0;
        for (int fast = 0; fast < arr.length; fast ++){
            if (arr[fast] != ' '){
                arr[slow] = arr[fast];
                slow ++;
            }else {
                if (slow == 0 || arr[slow - 1] != ' ') {
                    arr[slow] = ' ';
                    slow ++;
                }
            }
        }
        return new String(arr,0,slow);
    }

    public static void main(String[] args){
        String str = "This ";
        System.out.println(compressSpaces(str));
    }
}
