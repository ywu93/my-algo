package my.labs.leetcode.array;

public class FirstLastPositionSearch_lc34 {
    // 同时用左侧边界二分法和右侧边界二分法的搜索，返回索引位置或者-1（未找到情况）
    public int[] searchRange(int[] nums, int target) {
        int leftBound = leftBoundSearch(nums,target);
        int rightBound = rightBoundSearch(nums,target);
        return new int[]{leftBound,rightBound};

    }


}
