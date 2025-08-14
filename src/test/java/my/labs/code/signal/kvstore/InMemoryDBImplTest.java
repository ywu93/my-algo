package my.labs.code.signal.kvstore;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class InMemoryDBImplTest {
    private InMemoryDBImpl db = new InMemoryDBImpl();


    @Before
    public void setUp() {
        db = new InMemoryDBImpl();
    }
    @Test
    public void testSetAndGet() {
        db.set("user1", "name", "Alice");
        assertEquals("Alice", db.get("user1", "name"));

        db.set("user1", "name", "Bob");
        assertEquals("Bob", db.get("user1", "name"));
    }

    @Test
    public void testGetNonExisting() {
        assertNull(db.get("noKey", "field"));
        db.set("user1", "name", "Alice");
        assertNull(db.get("user1", "age"));
    }

    @Test
    public void testDelete() {
        db.set("user1", "name", "Alice");
        assertTrue(db.delete("user1", "name"));
        assertFalse(db.delete("user1", "name")); // 已经被删了
        assertNull(db.get("user1", "name"));
    }

    @Test
    public void testScan() {
        db.set("user1", "name", "Alice");
        db.set("user1", "age", "25");
        List<String> result = db.scan("user1");

        assertEquals(2, result.size());
        assertTrue(result.contains("name(Alice)"));
        assertTrue(result.contains("age(25)"));
    }

    @Test
    public void testScanByPrefix() {
        db.set("user1", "name", "Alice");
        db.set("user1", "nickname", "Ally");
        db.set("user1", "age", "25");

        List<String> result = db.scanByPrefix("user1", "n");

        assertEquals(2, result.size());
        assertEquals(List.of("name(Alice)", "nickname(Ally)"), result);
    }
}
