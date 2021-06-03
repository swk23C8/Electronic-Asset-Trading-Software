package UnitTests;

import Server.ServerManagement;
import org.junit.jupiter.api.BeforeEach;

import java.io.Serializable;

public class ServerManagementTest {

    private ServerManagement serverManager;

    @BeforeEach
    /**
     * Establish new server per test case
     */
    public void newServer() throws Exception {
        serverManager = new ServerManagement();
    }

    //Test different methods, different cases
}
