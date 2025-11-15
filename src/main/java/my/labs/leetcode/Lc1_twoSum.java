package my.labs.leetcode;

import java.util.HashMap;
import java.util.Map;

public class Lc1_twoSum {
    // 第一题数组是无序的，数组内元素是可以重复的。
    public int[] twoSum(int[] nums, int target) {
        // if using a brutal force to solve, just use two loops
        // brutal force approach
        // for (int i = 0; i < nums.length; i++) {
        //     for (int j = i+1; j < nums.length;j++){
        //         if (target == nums[i] + nums[j]) {
        //             return new int[]{i,j};
        //         }
        //     }
        // }
        // return null;

        // a better solution to this problem is to scan the array once and get the answer
        // 扫描一次表，把数字和索引用map存起来，然后再扫描一次表来找 -> 这是我的第一思路，实际一次扫描就可以完成
        // 实际之需要扫描表一次-
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++){
            int need = target - nums[i];
            // 但是存map的时机很关键，先找，找不到再存，而不是进来就先存
            // 这样相当于我检查的内容都是在我当前的这个元素之前的内容
            if (map.containsKey(need)){
                return new int[]{i,map.get(need)};
            }
            map.put(nums[i],i);
        }
        return null;

    }
}
