package my.labs.code.signal.kvstore;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryDBImplV2 implements InMemoryDB {
    private final Map<String, Map<String, FieldValue>> store = new HashMap<>();

    private final Map<String, Map<String, TreeMap<Long, FieldValue>>> storeWithTime = new HashMap<>();

    /**
     * Should insert a field-value pair to the record associated with key.
     * If the field in the record already exists, replace the existing value
     * with the specified value. If the record does not exist, create a new one.
     **/
    public void set(String key, String field, String value) {
        Map<String, FieldValue> fieldMap = store.computeIfAbsent(key, k -> new HashMap<>());
        fieldMap.compute(field, (f, fieldValue) -> {
            if (fieldValue == null) {
                return new FieldValue(field, value);
            }
            fieldValue.value = value;
            return fieldValue;
        });
    }

    /**
     * Should return the value contained within field of the reocrd asscociated with key.
     * If the record or the field does not exist, should return null.
     **/
    public String get(String key, String field) {
        Map<String, FieldValue> fieldMap = store.get(key);
        if (fieldMap == null) {
            return null;
        }
        FieldValue fieldValue = fieldMap.get(field);
        return fieldValue != null ? fieldValue.value : null;
    }

    /**
     * Should remove the field from the record associated with key. Returns True if the
     * field was successfully deleted, and False if the key or the field do not exist in
     * DB.
     **/
    public boolean delete(String key, String field) {
        Map<String, FieldValue> fieldMap = store.get(key);
        if (fieldMap == null) {
            return false;
        }
        return fieldMap.remove(field) != null;
    }

    @Override
    public List<String> scan(String key) {
        Map<String, FieldValue> fieldMap = store.get(key);
        if (fieldMap == null || fieldMap.isEmpty()) {
            return Collections.emptyList();
        }
        return fieldMap.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getValue().toString())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> scanByPrefix(String key, String prefix) {
        Map<String, FieldValue> fieldMap = store.get(key);
        if (fieldMap == null || fieldMap.isEmpty()) {
            return Collections.emptyList();
        }
        return fieldMap.entrySet().stream()
                .filter(entry -> entry.getValue() != null && entry.getKey().startsWith(prefix))
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> entry.getValue().toString())
                .collect(Collectors.toList());
    }

    @Override
    public void setAt(String key, String field, String value, long timestamp) {
        Map<String, TreeMap<Long, FieldValue>> fields = storeWithTime.computeIfAbsent(key, k -> new HashMap<>());
        TreeMap<Long, FieldValue> fieldValues = fields.computeIfAbsent(field, f -> new TreeMap<>());
        fieldValues.put(timestamp, new FieldValue(field, value, timestamp, timestamp));
    }

    @Override
    public void setAtWithTtl(String key, String field, String value, long timestamp, int ttl) {
        Map<String, TreeMap<Long, FieldValue>> fields = storeWithTime.computeIfAbsent(key, k -> new HashMap<>());
        TreeMap<Long, FieldValue> fieldValues = fields.computeIfAbsent(field, f -> new TreeMap<>());
        fieldValues.put(timestamp, new FieldValue(field, value, timestamp, timestamp, ttl));
    }

    @Override
    public boolean deleteAt(String key, String field, long timestamp) {
        Map<String, TreeMap<Long, FieldValue>> fields = storeWithTime.get(key);
        if (fields == null || fields.isEmpty()) {
            return false;
        }
        TreeMap<Long, FieldValue> fieldValues = fields.get(field);
        if (fieldValues == null || fieldValues.isEmpty()) {
            return false;
        }
        Map.Entry<Long, FieldValue> entry = fieldValues.floorEntry(timestamp);
        if (entry == null) {
            return false;
        }
        FieldValue latest = entry.getValue();
        if(latest == null) {
            return false;
        }
        FieldValue deletedFieldValue = new FieldValue(latest.name, latest.value, latest.createdAt, timestamp, true);
        fieldValues.put(timestamp, deletedFieldValue);
        return true;
    }

    @Override
    public String getAt(String key, String field, long timestamp) {
        Map<String, TreeMap<Long, FieldValue>> fields = storeWithTime.get(key);
        if (fields == null || fields.isEmpty()) {
            return null;
        }
        TreeMap<Long, FieldValue> fieldValues = fields.get(field);
        if (fieldValues == null || fieldValues.isEmpty()) {
            return null;
        }
        Map.Entry<Long, FieldValue> entry = fieldValues.floorEntry(timestamp);
        if (entry == null) {
            return null;
        }
        FieldValue latest = entry.getValue();
        if(latest == null) {
            return null;
        }
        if(latest.deleted || (latest.expireAt != -1 && latest.expireAt <= timestamp) ){
            return null; // Field is deleted or expired
        }
        return latest.value;
    }

    @Override
    public List<String> scanAt(String key, long timestamp) {
        Map<String, TreeMap<Long, FieldValue>> fields = storeWithTime.get(key);
        if (fields == null || fields.isEmpty()) {
            return Collections.emptyList();
        }
        List<FieldValue> result = new ArrayList<>();
        for(TreeMap<Long,FieldValue> fieldValue: fields.values()){
            if (fieldValue == null || fieldValue.isEmpty()){
                continue;
            }
            Map.Entry<Long,FieldValue> entry = fieldValue.floorEntry(timestamp);
            if(entry == null || entry.getValue() == null) {
                continue;
            }
            FieldValue field = entry.getValue();
            if (field.deleted || (field.expireAt != -1 && field.expireAt <= timestamp)){
                continue;
            }
            result.add(field);
        }
        return result.stream()
                .sorted(Comparator.comparing(f -> f.name))
                .map(FieldValue::toString)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> scanByPrefixAt(String key, String prefix, long timestamp) {
        Map<String, TreeMap<Long, FieldValue>> fields = storeWithTime.get(key);
        if (fields == null || fields.isEmpty()) {
            return Collections.emptyList();
        }

        List<FieldValue> result = new ArrayList<>();
        for (Map.Entry<String, TreeMap<Long, FieldValue>> entry : fields.entrySet()) {
            if (!entry.getKey().startsWith(prefix)) {
                continue; // Skip fields that do not match the prefix
            }
            TreeMap<Long, FieldValue> fieldValue = entry.getValue();
            if (fieldValue == null || fieldValue.isEmpty()) {
                continue;
            }
            Map.Entry<Long, FieldValue> entry1 = fieldValue.floorEntry(timestamp);
            if (entry1 == null || entry1.getValue() == null) {
                continue;
            }
            FieldValue field = entry1.getValue();
            if (field.deleted || (field.expireAt != -1 && field.expireAt <= timestamp)) {
                continue;
            }
            result.add(field);
        }
        return result.stream()
                .sorted(Comparator.comparing(f -> f.name))
                .map(FieldValue::toString)
                .collect(Collectors.toList());
    }

    @Override
    public int backup(long timestamp) {
        return 0;
    }

    @Override
    public void restore(long timestamp, long timestampToRestore) {}
}
