package my.labs;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

public class Braze_DataPointsCollector {

    /**
     * @param user 当前 Map
     * @param dataPoints 已累加的点数
     * @param path 当前字段路径，用于打印调试
     * @return 累加后的 dataPoints
     */
    public static int collectDataPoints(Map<String, Object> user, int dataPoints, String path) {
        // 显式 base case：Map 为 null 或空，直接返回
        if (user == null || user.isEmpty()) {
            return dataPoints;
        }

        for (Map.Entry<String, Object> entry : user.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String currentPath = path.isEmpty() ? key : path + "." + key;

            // 打印当前字段类型
            System.out.println("Field: " + currentPath + ", Type: " + (value == null ? "null" : value.getClass().getSimpleName()));

            // 基础类型 + List 计分
            if (value instanceof String || value instanceof Boolean || value instanceof Number || value instanceof List) {
                dataPoints++;
                System.out.println("  +1 point for " + currentPath);
            }

            // 如果是 Map，递归
            if (value instanceof Map) {
                dataPoints = collectDataPoints((Map<String, Object>) value, dataPoints, currentPath);
            }
        }
        return dataPoints;
    }
    public static void main(String[] args){
        String json = "{\n" +
                "  \"id\": \"u123\",\n" +
                "  \"info\": {\n" +
                "    \"location\": {\n" +
                "      \"city\": \"Burnaby\",\n" +
                "      \"code\": 123\n" +
                "    },\n" +
                "    \"settings\": {\n" +
                "      \"languages\": [\"en\", \"zh\"],\n" +
                "      \"premium\": true\n" +
                "    }\n" +
                "  },\n" +
                "  \"meta\": {\n" +
                "    \"active\": true\n" +
                "  }\n" +
                "}";

        Gson gson = new Gson();

        // JSON -> Map<String, Object>
        Map<String, Object> map = gson.fromJson(
                json,
                new TypeToken<Map<String, Object>>() {}.getType()
        );
        System.out.println(collectDataPoints(map,0,""));
    }
}
