package my.labs.leetcode;

public class FirstMissingPositive {
    public static int firstMissingPositive(int[] nums) {
        int n = nums.length;

        // 把每个数字放到对应下标，如果在 1~n 范围内
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            // 循环交换直到当前数字放到正确位置或不需要交换
            while (num >= 1 && num <= n && nums[num - 1] != num) {
                // 交换 nums[i] 和 nums[num-1]
                int temp = nums[num - 1];
                nums[num - 1] = num;
                nums[i] = temp;

                // 更新 num，继续检查当前位置的新数字
                num = nums[i];
            }
        }

        // 扫描数组找第一个不匹配的下标
        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }

        // 全都匹配，缺失数字是 n+1
        return n + 1;
    }

    public static void main(String[] args){
       FirstMissingPositive.firstMissingPositive(new int[]{3,5,7,-1,0});
    }

    int calcIndex (int[] nums, int i) {
        if (nums[i] >= 1 && nums[i] <= nums.length) {
            return i - 1;
        }
        return -1;
    }

    boolean inRange (int [] nums, int i){
        return (nums[i] >= 1 && nums[i] <= nums.length);
    }


}
