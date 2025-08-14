package my.labs.code.signal.kvstore;

public class FieldValue {
    String value;
    long updatedAt;

    int ttl = -1;

    long expireAt = -1;

    public FieldValue(String value, long timestamp, int ttl) {
        this.value = value;
        this.updatedAt = timestamp;
        this.ttl = ttl;
        if (ttl >= 0){
            this.expireAt = timestamp + ttl;
        }
    }

}
