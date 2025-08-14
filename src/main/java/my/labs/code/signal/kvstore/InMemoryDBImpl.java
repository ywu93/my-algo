package my.labs.code.signal.kvstore;

import java.util.*;

public class InMemoryDBImpl implements InMemoryDB {

    private final Map<String, Map<String, TreeMap<Long, FieldValue>>> store = new HashMap<>();

    public InMemoryDBImpl() {
        // Constructor can be used for any initialization if needed
    }

    @Override
    public void set(String key, String field, String value) {
        setAt(key, field, value, System.currentTimeMillis());
    }

    @Override
    public String get(String key, String field) {
        return getAt(key, field, System.currentTimeMillis());
    }

    @Override
    public boolean delete(String key, String field) {
        return deleteAt(key, field, System.currentTimeMillis());
    }

    @Override
    public List<String> scan(String key) {
      return scanAt(key,System.currentTimeMillis());
    }

    @Override
    public List<String> scanByPrefix(String key, String prefix) {
        return scanByPrefixAt(key,prefix,System.currentTimeMillis());
    }

    @Override
    public void setAt(String key, String field, String value, long timestamp) {
       setAtWithTtl(key, field, value, timestamp, -1);
    }

    @Override
    public void setAtWithTtl(String key, String field, String value, long timestamp, int ttl) {
        if (!store.containsKey(key)) {
            Map<String, TreeMap<Long,FieldValue>> fields = new HashMap<>();
            TreeMap<Long, FieldValue> fieldValues = new TreeMap<>();
            fieldValues.put(timestamp, new FieldValue(field,value, timestamp, ttl));
            fields.put(field, fieldValues);
            store.put(key, fields);
            return;
        }
        Map<String, TreeMap<Long, FieldValue>> fields = store.get(key);
        if (fields.containsKey(field)) {
            // Update existing field
            TreeMap<Long, FieldValue> fieldValues = fields.get(field);
            FieldValue fieldValue = fieldValues.floorEntry(timestamp).getValue();
            fieldValue.updatedAt = timestamp;
            fieldValue.value = value;
            fieldValues.put(timestamp, fieldValue);
        } else {
            // Add new field
            TreeMap<Long, FieldValue> fieldValues = new TreeMap<>();
            fieldValues.put(timestamp,  new FieldValue(field,value, timestamp, ttl));
            fields.put(field, fieldValues);
        }
    }

    @Override
    public boolean deleteAt(String key, String field, long timestamp) {
        if (!store.containsKey(key)) {
            return false;
        }
        Map<String, TreeMap<Long, FieldValue>> fields = store.get(key);
        if (fields == null || !fields.containsKey(field)) {
            return false;
        }
        TreeMap<Long, FieldValue> fieldValues = fields.get(field);
        if (fieldValues == null
                || fieldValues.isEmpty()
                || fieldValues.floorEntry(timestamp).getValue() == null) {
            return false;
        }
        fieldValues.put(timestamp, null); // Mark as deleted by adding a null value
        return true;
    }

    @Override
    public String getAt(String key, String field, long timestamp) {
        if (!store.containsKey(key)) {
            return null;
        }
        Map<String, TreeMap<Long, FieldValue>> fields = store.get(key);
        if (fields.containsKey(field)) {
            TreeMap<Long, FieldValue> fieldValues = fields.get(field);
            if (fieldValues != null
                    && !fieldValues.isEmpty()
                    && fieldValues.floorEntry(timestamp) != null
                    && fieldValues.floorEntry(timestamp).getValue() != null) {
                return fieldValues.floorEntry(timestamp).getValue().value;
            }
        }
        return null;
    }

    @Override
    public List<String> scanAt(String key, long timestamp) {
        if (!store.containsKey(key) || store.get(key) == null || store.get(key).isEmpty()) {
            return Collections.emptyList();
        }
        Map<String, TreeMap<Long,FieldValue>> fields = store.get(key);
        if (fields == null || fields.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, TreeMap<Long, FieldValue>> entry : fields.entrySet()) {
            if (entry.getValue().floorEntry(timestamp) != null
                    && entry.getValue().floorEntry(timestamp).getValue() != null) {
                result.add(entry.getKey() + "(" + entry.getValue().floorEntry(timestamp).getValue().value + ")");
            }
        }
        return result;
    }

    @Override
    public List<String> scanByPrefixAt(String key, String prefix, long timestamp) {
        if (!store.containsKey(key) || store.get(key) == null || store.get(key).isEmpty()) {
            return Collections.emptyList();
        }
        Map<String, TreeMap<Long, FieldValue>> fields = store.get(key);
        if (fields == null || fields.isEmpty()) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, TreeMap<Long, FieldValue>> entry : fields.entrySet()) {
            TreeMap<Long, FieldValue> historyValue = entry.getValue();
            if (entry.getKey().startsWith(prefix)
                    && historyValue.floorEntry(timestamp) != null
                    && historyValue.floorEntry(timestamp).getValue() != null) {
                result.add(entry.getKey() + "(" + entry.getValue().floorEntry(timestamp).getValue().value + ")");
            }
        }
        result.sort(String::compareTo);
        return result;
    }

    @Override
    public int backup(long timestamp) {
        return 0;
    }

    @Override
    public void restore(long timestamp, long timestampToRestore) {}
}
