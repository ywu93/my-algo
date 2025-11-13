package my.labs.leetcode.array;

public class Lc35_SearchInsertPosition {
    // 左侧边界法二分搜索，搜索范围使用闭区间
    // Left-bound binary search using a closed interval [left, right]
    public static int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2; // avoid int overflow
            System.out.println("left=" + left + ",right=" + right + ",mid=" + mid);
            if (nums[mid] == target) {
                right = mid -1; // 收缩左侧边界 shrink the left boundary to narrow the search range
            } else if (nums[mid] > target) {
                right = mid - 1; // 收缩左侧边界 shrink the left boundary to narrow the search range
                // 可以合并条件变成 nums[mid] >= target;
            } else {
                left = mid + 1;
            }
        }
        System.out.println("When the loop ends, the left value is:" + left);
        return left; // The final return value is either the insertion position or the index of the leftmost target element."
    }

    public static void main(String[] args){
        int [] nums = {1,2,4,5,6,8,9,10};
        int index = searchInsert(nums,3);
        System.out.println("search index:" + index);
    }

}
