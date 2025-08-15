package my.labs.code.signal.kvstore;

public class FieldValue {
    String name;
    String value;
    long createdAt = -1;
    long updatedAt = -1;

    int remainingTtl = -1;

    int ttl = -1;

    long expireAt = -1;

    boolean deleted = false;

    public FieldValue(String name,String value){
        this.name = name;
        this.value = value;
    }

    public FieldValue(String name, String value, long createdAt, long updatedAt, int ttl) {
        this.name = name;
        this.value = value;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.ttl = ttl;
        if (ttl >= 0){
            this.expireAt = createdAt + ttl;
            this.remainingTtl = ttl;
        }
    }



    public FieldValue(String name, String value, long createdAt,long updatedAt){
        this.name = name;
        this.value = value;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
    }

    public FieldValue(String name, String value, long createdAt,long updatedAt, boolean delete){
        this.name = name;
        this.value = value;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.deleted = delete;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return this.name + "(" + this.value + ")";
    }

}
