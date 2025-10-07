package my.labs.ds.array;

public class ArrayUtil {
    // 快慢指针法,非目标元素都往前移动
    // 遇到目标元素慢指针不动，快指针继续走，遇到不同的之后再把慢指针位置上的目标元素覆盖了
    public static int removeElement(int[] nums, int target){
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++){
            if (nums[fast] != target) {
                nums[slow] = nums[fast];// 如果位置相同是重复赋值，位置不同才是覆盖
                slow++;
            }
        }
        return slow; //慢指针所在的位置就是实际剩下的不重复的元素个数
    }

    public static void main(String[] args){
        int[] nums = new int[]{2,3,3,3};
        System.out.println(ArrayUtil.removeElement(nums,3));
    }
}
