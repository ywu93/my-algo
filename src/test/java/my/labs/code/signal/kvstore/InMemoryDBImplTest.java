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
        // t = 1000: 插入一个没有 TTL 的字段
        db2.setAt("user1", "name", "Alice", 1000);

        // t = 2000: 插入一个 TTL = 5000 的字段 (过期时间 7000)
        db2.setAtWithTtl("user1", "age", "30", 2000, 5000);

        // t = 2500: 再插入一个 TTL = 3000 的字段 (过期时间 5500)
        db2.setAtWithTtl("user2", "city", "Vancouver", 2500, 3000);

        // t = 3000: 删除 user1.age
        db2.deleteAt("user1", "age", 3000);

        // t = 4000: 调用 backup()
        int count = db2.backup(4000);

        // 验证返回值
        assertEquals(2, count); // 4000 时刻应该有 2 条有效记录 user1, user2

        // 验证备份存储
        Map<String, List<FieldValue>> backup = db2.getBackupStore().get(4000L);
        assertNotNull(backup);//4000 时刻的备份不应为空

        // user1.name
        List<FieldValue> user1Fields = backup.get("user1");
        assertNotNull(user1Fields);
        assertEquals(1, user1Fields.size());
        FieldValue nameField = user1Fields.get(0);
        assertEquals("Alice", nameField.value);
        assertEquals(-1, nameField.remainingTtl); // 无 TTL 记录的 remainingTtl 应为 -1

        // user2.city
        List<FieldValue> user2Fields = backup.get("user2");
        assertNotNull(user2Fields);
        assertEquals(1, user2Fields.size());
        FieldValue cityField = user2Fields.get(0);
        assertEquals("Vancouver", cityField.value);
        assertEquals(1500, cityField.remainingTtl); //city 剩余 TTL 应该是 expireAt(5500) - timestamp(4000) = 1500

        // t = 5000: 从 4000 的备份恢复
        db2.restore(5000, 4000);

        // 恢复后检查数据
        assertEquals("Alice", db2.getAt("user1", "name", 5000)); // 永不过期
        assertEquals("Vancouver", db2.getAt("user2", "city", 5000)); // TTL 还剩 1500

        // 再模拟时间走到过期点 (6500)
        assertNull(db2.getAt("user2", "city", 6500)); // 5000 + 1500 = 6500 过期
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
