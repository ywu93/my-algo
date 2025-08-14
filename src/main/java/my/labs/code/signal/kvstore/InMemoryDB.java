package my.labs.code.signal.kvstore;

import java.util.List;

public interface InMemoryDB {
    /**
     * Should insert a field-value pair to the record associated with key.
     * If the field in the record already exists, replace the existing value
     * with the specified value. If the record does not exist, create a new one.
     **/
    void set(String key, String field, String value);

    /**
     * Should return the value contained within field of the reocrd asscociated with key.
     * If the record or the field does not exist, should return null.
     **/
    String get(String key, String field);

    /**
     * Should remove the field from the record associated with key. Returns True if the
     * field was successfully deleted, and False if the key or the field do not exist in
     * DB.
     **/
    boolean delete(String key, String field);

    /**
     * Should return a list of strings representing the fields of a record associated with
     * key. The returned list should be in the following format [field1(value1),field2(value2),...]
     * , where fields are sorted lexicographically. If the specified record does not exist,
     * returns an empty list.
     **/
    List<String> scan(String key);

    /**
     * Should return a list of strings representing the fields of a record associated with
     * key. Specifically, only fields that start with prefix should be included.
     * The returned list should be in the same format as in the scan operation with fields
     * sorted in lexicographical order.
     **/
    List<String> scanByPrefix(String key, String prefix);

// Time should always flow forward, so timestamps are guranteed to strictly increase
// as operations are executed.
// Each test cannot contain both versions of operations (with and without timestamp).
// However, you should maintain backward compatibility, so all previously defined methods
// should work in the same way as before.

    /**
     * Should insert a field-value pair or updates the value of the field in the record
     * associated with key.
     **/
    void setAt(String key, String field, String value, long timestamp);

    /**
     * Should insert a field-value pair or updates the value of the field in the record
     * associated with key. Also sets its Time-To-Live starting at timestamp to be ttl.
     * The ttl is the amount of time that this field-value pair should exist in the database,
     * meaning it will be available during this interval: (timestamp, timestamp + ttl).
     **/
    void setAtWithTtl(String key, String field, String value, long timestamp, int ttl);

    /**
     * The same as delete, but with timestamp of the operation specified. Should return True if the field
     * existed and was successfully deleted and False it the key didn't exist.
     **/
    boolean deleteAt(String key, String field, long timestamp);

    /**
     * The same as get, but with timestamp of the operation specified.
     **/
    String getAt(String key, String field, long timestamp);

    /**
     * The same as scan, but with timestamp of the operation specifed.
     **/
    List<String> scanAt(String key, long timestamp);

    /**
     * The same as scanByPrefix, but with timestamp of the operation specifed.
     **/
    List<String> scanByPrefixAt(String key, String prefix, long timestamp);

// The database should be backed up from time to time. Introduce operations to
// support backing up and restoring the database state based on timestamps. When
// restoring, ttl expiration times should be recalculated accordingly.

    /**
     * Should save the database state at the specified timestamp, including the remaining
     * ttl for all records and fields. Remaining ttl is the difference between the initial
     * ttl and their current lifespan (the duration between the timestamp of this operation
     * and their initial timestamp). Returns the number of non-empty non-expired records
     * in the database)
     **/
    int backup(long timestamp);

    /**
     * Should restore the database from the latest backup before or at timestampToRestore.
     * It's guranteed that a backup before or at timestampToRestore will exist. Expiration times
     * for restored records and fields should be recalculated according to the timestamp of
     * this operation - since the database timeline always flows forward, restored records and fields
     * should expire after the timestamp of this operation, depending on their remaing ttls at backup.
     **/
    void restore(long timestamp, long timestampToRestore);
}
