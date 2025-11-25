package my.labs.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lc1087_braceExpansion {

    /**
     * {a,b}c{d,e}
     * ["acd","ace","bcd","bce"]
     * @param braceStr
     * @return
     */
    public static List<String> expand(String braceStr) {
        List<String> result = new ArrayList<>();
        List<List<Character>> groups = splitStr(braceStr);
        for (List<Character> group : groups) {
            Collections.sort(group);
        }

        dfs(groups, 0, new StringBuilder(), result);
        return result;
    }

    // [[a, b, c], [d], [e]]
    private static void dfs(List<List<Character>> groups, int index, StringBuilder path, List<String> result) {
        if (index == groups.size()) {
            result.add(path.toString());
            System.out.printf("Path[%s]递归即将返回,结果集[%s]%n",path,result);
            return;
        }
        for (char c : groups.get(index)) {
            path.append(c);
            System.out.printf("遍历group[%d],当前字符[%c]加入path中，path[%s]%n",index,c,path);
            dfs(groups, index + 1, path, result);
            System.out.printf("Group[%d]递归已经返回，当前的path[%s]%n",index,path);
            path.deleteCharAt(path.length() - 1); // 回溯
            System.out.printf("Group[%d]递归已经返回，回朔后的path[%s]%n",index,path);
        }
    }

    // c{a,b,d}
    public static List<List<Character>> splitStr(String str) {
        List<List<Character>> characterList = new ArrayList<>();
        List<Character> separateList = new ArrayList<>();
        boolean inBrace = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '{') {
                inBrace = true;
                separateList = new ArrayList<>();
            } else if (c == '}') {
                inBrace = false;
                characterList.add(separateList);
            } else if (c != ',') {
                if (!inBrace) {
                    characterList.add(new ArrayList<>(List.of(c)));
                } else {
                    separateList.add(c);
                }
            }
        }
        return characterList;
    }

    public static void main(String[] args) {
        System.out.println(expand("{a}{d}{e,f,g}"));
    }
}
