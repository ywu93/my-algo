package my.labs.leetcode;

public class Lc344_ReverseString {
    public static void reverse(char[] str) {
        int left = 0, right = str.length - 1;
        while (left < right) {
            char temp = str[left];
            str[left] = str[right];
            str[right] = temp;
            left++;
            right--;
        }
    }

    public static void reverse2(char[] str) {
        swap(str, 0, str.length - 1);
    }

    private static void swap(char[] str, int left, int right) {
        if (left >= right) {
            return;
        }
        char temp = str[left];
        str[left] = str[right];
        str[right] = temp;
        swap(str, left + 1, right - 1);
    }

    public static void main(String[] args) {
        char[] str = "Disconnected".toCharArray();
        reverse2(str);
        System.out.println(str);
    }
}
