package UnitTests;

import Common.User;
import Server.UserDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        userdata.changePassword(user);
    }

    @Test
    void userSet() {
    }

    @Test
    void getUser() {
    }

    @Test
    void getSize() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void close() {
    }
}