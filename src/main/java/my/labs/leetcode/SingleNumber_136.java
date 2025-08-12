package my.labs.leetcode;

import java.util.Arrays;

public class SingleNumber_136 {
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }

    public static void main(String[] args) {
        SingleNumber_136 singleNumber = new SingleNumber_136();
        int[] nums = {4, 1, 2, 1, 2};
        System.out.println(singleNumber.singleNumber(nums)); // Output: 4
    }
}
