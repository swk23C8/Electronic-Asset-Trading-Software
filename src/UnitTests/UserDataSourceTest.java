package UnitTests;

import Common.User;
import Server.UserDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserDataSourceTest {

    private UserDataSource userdata;
    private User user;
    @BeforeEach
    void newDatabase() throws Exception{
        userdata = new UserDataSource();
        user = new User("Chris", "Danie", "abc", "admin");
    }


    @Test
    void addUser() {
        userdata.addUser(user);
        assertEquals(userdata.getUser("Chris").getUsername(), user.getUsername());
        assertEquals(userdata.getUser("Chris").getOu(), user.getOu());
        assertEquals(userdata.getUser("Chris").getType(), user.getType());
        //assertEquals(userdata.getUser("Danie").getPassword(), user.getPassword());
    }

    @Test
    void changePassword() {
        User newPass = new User("Chris", "Chris");
        userdata.changePassword(newPass);
        assertNotEquals(user, newPass);
    }

    @Test
    /* Sucess as set will not be equal as you add in a new user */
    void userSet() {
        Set<String> setofuser = userdata.userSet();
        userdata.addUser(new User("Jay", "Jay", "abc", "admin"));
        // Change the name above as a new user and change AssertEqual to AssertNotEqual to test
        assertEquals(setofuser.size(), userdata.userSet().size());
        userdata.deleteUser("Jay");
    }

    @Test
    void getUser() {
        User getuser = userdata.getUser("Chris");
        assertEquals(getuser.getUsername(), user.getUsername());
    }

    @Test
    void getSize() {
        assertEquals(userdata.getSize(), userdata.userSet().size());
    }

    @Test
    void deleteUser() {
        assertTrue(userdata.deleteUser("Chris"));
    }

    @Test
    void close() {
    }
}