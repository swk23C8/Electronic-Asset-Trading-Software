package UnitTests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerManagementTest {


    @Test
    void start() {
    }


    @Test
    void reconcile(){
        testing the password verifying function
        UserDataSource userDatabase = new UserDataSource();
        String password = "abc123";
        System.out.println(userDatabase.getUser("n10559540").getPassword().
                equals(userDatabase.passwordCheck(password, userDatabase.getUser("n10559540"))));
        testing reconcile method
        reconcile();
        }
    }
}