package my.labs.leetcode;

public class Lc36_SingleNumber {
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }

    public static void main(String[] args) {
        Lc36_SingleNumber singleNumber = new Lc36_SingleNumber();
        int[] nums = {4, 1, 2, 1, 2};
        System.out.println(singleNumber.singleNumber(nums)); // Output: 4
    }
}
