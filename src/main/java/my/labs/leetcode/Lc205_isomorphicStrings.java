package my.labs.leetcode;

import java.util.HashMap;
import java.util.Map;

public class Lc205_isomorphicStrings {
    // 检查两个字符串是否同构的，可以互相映射
    // abc -> def true
    // bar -> foo false
    // 从时间复杂度角度来看只能是O(n),因为至少要扫描一次数组
    // 要从绝对性能来提升，可以用数组代替map

    public static  boolean isIsomorphic(String s, String t) {
        if(s.length() != t.length()) {
            return false;
        }
        Map<Character, Character> s2t = new HashMap<>();
        Map<Character, Character> t2s = new HashMap<>();
        for (int i = 0; i < s.length(); i++){
            // check t maps to s and s maps to t
            char tChar = t.charAt(i);
            char sChar = s.charAt(i);
            if (t2s.containsKey(tChar) && t2s.get(tChar) != sChar) return false;
            if(s2t.containsKey(sChar) && s2t.get(sChar) != tChar) return false;
            s2t.put(sChar,tChar);
            t2s.put(tChar,sChar);
        }
        return true;
    }

    public static  boolean isIsomorphic_array(String s, String t) {
        if(s.length() != t.length()) {
            return false;
        }

        // ASCII extended size
        int[] s2t = new int[256];
        int[] t2s = new int[256];
        for (int i = 0; i < s.length(); i++){
            // check t maps to s and s maps to t
            char sChar = s.charAt(i);
            char tChar = t.charAt(i);
            if(s2t[sChar] == 0 && t2s[tChar] == 0){
                s2t[sChar] = tChar;
                t2s[tChar] = sChar;
            }else if (s2t[sChar] != tChar || t2s[tChar] != sChar){
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args){
        System.out.println(isIsomorphic_array("paper","title"));
    }
}
