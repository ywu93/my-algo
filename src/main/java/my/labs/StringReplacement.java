package my.labs;

import java.util.Map;

public class StringReplacement {

    // 直接用string替换的方式
    private static String replace(String str, Map<String, String> replaceMap) {
        if (replaceMap == null || replaceMap.isEmpty()) {
            return str;
        }
        for (Map.Entry<String, String> entry : replaceMap.entrySet()) {
            String placeHolder = "{" + entry.getKey() + "}";
            str = str.replace(placeHolder, entry.getValue());
        }
        return str;
    }

    private static String scanAndReplace(String str, Map<String, String> replaceMap) {
        if (replaceMap == null || replaceMap.isEmpty()) {
            return str;
        }
        StringBuilder result = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            char ch = str.charAt(i);
            if (ch == '{') {
                int j = str.indexOf('}', i);
                if (j == -1) {
                    // not found '}'
                    result.append(ch);
                    i++;
                    continue;
                }
                // 左闭右开
                String key = str.substring(i + 1, j);
                String replace = replaceMap.getOrDefault(key,"{"+key+"}");
                result.append(replace);
                i = j + 1;
            }else {
                result.append(ch);
                i++;
            }
        }
        return  result.toString();
    }

    public static void main(String[] args) {
        String str = "this is a {foo} {bar}";
        Map<String, String> repalceMap = Map.of("foo", "template", "bar", "string");
        System.out.println(scanAndReplace(str, repalceMap));
    }
}
