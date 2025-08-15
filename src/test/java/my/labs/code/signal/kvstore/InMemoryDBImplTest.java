package my.labs.code.signal.kvstore;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class InMemoryDBImplTest {
    private InMemoryDBImpl db;
    private InMemoryDBImplV2 db2;


    @Before
    public void setUp() {
        db = new InMemoryDBImpl();
        db2 = new InMemoryDBImplV2();
    }

    @Test
    public void testDB (){

        db2.setAt("user1","name","Alice", 1);
        db2.setAt("user1","age","20", 20);
        db2.setAt("user2","name","bob",2);
//        System.out.println(db2.getAt("user1","name",3));
//        System.out.println(db2.getAt("user1","name",1));
//        System.out.println(db2.getAt("user1","age",1));
//        System.out.println(db2.getAt("user1","age",40));
        db2.setAtWithTtl("user1","career","student", 21,5);
        db2.backup(22);

//        System.out.println(db2.getAt("user1","career",22));
//        System.out.println(db2.getAt("user1","career",28));
//        System.out.println(db2.scanAt("user1",30));
//        System.out.println(db2.scanByPrefixAt("user1","c",22));
    }

    @Test
    public void testBackupWithTtlAndDeleted() {
        long t1 = 1000L;
        long t2 = 2000L;
        long t3 = 3000L;

        // 添加记录
        db2.setAt("user1", "name", "Alice", t1);
        db2.setAtWithTtl("user1", "age", "30", t1, 5000); // TTL 5000ms

        // 删除 name
        db2.deleteAt("user1", "name", t2);

        // 再加一条记录
        db2.setAt("user2", "score", "100", t3);

        // 调用 backup
        int backupCount = db2.backup(2500L); // timestamp 2500ms
        assertEquals(1, backupCount); // 只有 age 没过期且未删除

        // 检查 backupsStore
        Map<String, FieldValue> backupFields = db2.getBackupStore().get(2500L);
        assertNotNull(backupFields);
        assertTrue(backupFields.containsKey("age")); // 只有 age 存在
        assertFalse(backupFields.containsKey("name")); // name 被删除
        FieldValue ageBackup = backupFields.get("age");
        assertEquals(3500, ageBackup.remainingTtl); // 剩余 TTL = expireAt - timestamp
    }
    @Test
    public void testSetAndGet() {
        db2.set("user1", "name", "Alice");
        assertEquals("Alice", db2.get("user1", "name"));

        db2.set("user1", "name", "Bob");
        assertEquals("Bob", db2.get("user1", "name"));
    }

    @Test
    public void testGetNonExisting() {
        assertNull(db2.get("noKey", "field"));
        db2.set("user1", "name", "Alice");
        assertNull(db2.get("user1", "age"));
    }

    @Test
    public void testDelete() {
        db2.set("user1", "name", "Alice");
        assertTrue(db2.delete("user1", "name"));
        assertFalse(db2.delete("user1", "name")); // 已经被删了
        assertNull(db2.get("user1", "name"));
    }

    @Test
    public void testScan() {
        db2.set("user1", "name", "Alice");
        db2.set("user1", "age", "25");
        List<String> result = db2.scan("user1");

        assertEquals(2, result.size());
        assertTrue(result.contains("name(Alice)"));
        assertTrue(result.contains("age(25)"));
    }

    @Test
    public void testScanByPrefix() {
        db2.set("user1", "name", "Alice");
        db2.set("user1", "nickname", "Ally");
        db2.set("user1", "age", "25");

        List<String> result = db2.scanByPrefix("user1", "n");

        assertEquals(2, result.size());
        assertEquals(List.of("name(Alice)", "nickname(Ally)"), result);
    }
}
