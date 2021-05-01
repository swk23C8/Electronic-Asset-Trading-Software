package Tests;
import Common.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.HashSet;


public class AdminTest {
    private Integer testNum = 20;
    private Integer testNum2 = 1;

    @Test
    public void constructAdmin() {
        Admin anAdmin = new Admin("username", "password");
        assertEquals("username", anAdmin.returnUsername());
        assertEquals("password", anAdmin.returnPassword());
    }

    @Test
    public void addNewAsset() {
        Admin anAdmin = new Admin("username", "password");
        Asset newAsset = anAdmin.addAsset("software license");
        assertEquals("software license", newAsset.getAsset());
    }

    @Test
    public void deleteOldAsset() {
        Admin anAdmin = new Admin("username", "password");
        assertTrue(anAdmin.deleteAsset(("software license")));
    }

    @Test
    public void addNewOU() {
        Admin anAdmin = new Admin("username", "password");
        OU newOU = anAdmin.addOU("newTeam", testNum);
        assertEquals("newTeam", newOU.getOuName());
        assertEquals(testNum, newOU.getUnitCredits());
    }



    @Test
    public void editAssetOU() {
        Admin anAdmin = new Admin("username", "password");
        OU newOU = anAdmin.addOU("newTeam", testNum);
        Asset anAsset = new Asset("software");
        anAdmin.changeOUAsset(newOU,anAsset, testNum);
        HashMap<Asset, Integer> testMap = new HashMap<Asset, Integer>();
        testMap.put(anAsset, testNum);
        assertEquals(testMap, newOU.returnAssetHashMap());
    }

    @Test
    public void editCreditsOU() {
        Admin anAdmin = new Admin("username", "password");
        OU newOU = anAdmin.addOU("newTeam", testNum);
        assertTrue(anAdmin.changeOUCredits(newOU, testNum2));
        assertEquals(testNum2, newOU.getUnitCredits());
    }

    @Test
    public void assignUserToOU() {
        Admin anAdmin = new Admin("username", "password");
        OU newOU = anAdmin.addOU("newTeam");
        assertTrue(anAdmin.assignUserToOU(anAdmin, newOU));
        HashSet<User> testUsers = new HashSet<>();
        testUsers.add(anAdmin);
        assertEquals(testUsers, newOU.returnUsers());
        assertEquals(newOU, anAdmin.returnOU());
    }

    @Test
    public void changeUserPassword() {
        Admin anAdmin = new Admin("username", "password");
        User newUser = new User("username2", "password");
        anAdmin.changePassword(newUser, "newPassword");
        assertEquals("newPassword", newUser.returnPassword());
    }

    @Test
    public void addNewAdmin() {
        Admin anAdmin = new Admin("username", "password");
        assertNotNull(anAdmin.addAdmin("username2", "password"));
    }

    @Test
    public void addSameAdmin() {
        Admin anAdmin = new Admin("username", "password");
        assertNull(anAdmin.addAdmin("username", "password"));
    }

    @Test
    public void addNewUser() {
        Admin anAdmin = new Admin("username", "password");
        assertNotNull(anAdmin.addUser("username2", "password"));
    }

}
