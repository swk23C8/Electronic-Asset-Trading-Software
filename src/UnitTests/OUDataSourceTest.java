package UnitTests;

import Common.OU;
import Server.OUDataSource;
import org.junit.jupiter.api.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class OUDataSourceTest {

    private OUDataSource source;
    private OU TestOU;
    @BeforeEach
    void setUp() {
        source = new OUDataSource();
        TestOU = new OU("test", 0);
    }


    @Test
    void addOU() {
        source.addOU(TestOU);
        assertEquals(source.getOU("test").getOuName(), TestOU.getOuName());
    }

    @Test
    void editCredit() {
        source.addOU(TestOU);
        source.editCredit(new OU("test", 100));
        assertEquals(100, source.getOU("test").getCredits());
        source.deleteOU("test");
    }

    @Test
    void ouList() {
        HashMap<String, Integer> list = source.ouList();
        source.addOU(new OU("new", 0));
        assertNotEquals(list.size(), source.ouList().size());
        source.deleteOU("new");
    }

    @Test
    void getOU() {
        assertEquals(TestOU.getOuName(), source.getOU("test").getOuName());
    }

    @Test
    void getSize() {
        int size = source.getSize();
        source.addOU(new OU("new", 0));
        assertNotEquals(size, source.getSize());
        source.deleteOU("new");
    }

    @Test
    void deleteOU() {
        assertTrue(source.deleteOU("test"));
    }

    @Test
    void close() {
    }

}