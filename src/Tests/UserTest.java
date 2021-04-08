package Tests;
import Common.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;


public class UserTest {
    private Integer testNum = 20;

    @Test
    /* This test passes */
    public void constructAdmin() {
        User aUser = new User("username", "password");
        assertEquals("username", aUser.returnUsername());
        assertEquals("password", aUser.returnPassword());
    }

    @Test
    public void changeUserPassword() {
        User aUser = new User("username", "password");
        aUser.changeOwnPassword("newPassword");
        assertEquals("newPassword", aUser.returnPassword());
    }

    @Test
    public void testBuy() {
        User aUser = new User("username", "password");
        Asset anAsset = new Asset("software");
        OU anOU = new OU("team", testNum);
        anOU.addMember(aUser);
        assertNotEquals(null, aUser.makeBuy(anAsset, 1, testNum));
    }

    @Test
    public void testSell() {
        User aUser = new User("username", "password");
        Asset anAsset = new Asset("software");
        OU anOU = new OU("team");
        anOU.addMember(aUser);
        anOU.assignAssetNumber(anAsset, 1);
        assertNotEquals(null, aUser.makeSell(anAsset, 1, testNum));
    }

    @Test
    public void cancelOffer() {
        User aUser = new User("username", "password");
        Asset anAsset = new Asset("software");
        OU anOU = new OU("team", testNum);
        anOU.addMember(aUser);
        Buy buy = new Buy(aUser, anAsset, 1, testNum);
        assertTrue(aUser.cancelOffer(buy));
    }


}
