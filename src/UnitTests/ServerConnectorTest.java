package UnitTests;

import Client.AssetData;
import Client.ServerConnector;
import Common.*;
import Server.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ServerConnectorTest {
    private OfferDataSource OfferSource;
    private ServerConnector server;
    private OUAssetDataSource OUAssetSource;
    private AssetPossession assetOU;
    private OUDataSource OUsource;
    private UserDataSource userdata;
    private User user;
    private Offer offer;
    private OU TestOU;
    private AssetDataSource AssetSource;
    private Asset asset;

    @BeforeEach
    void setUp() {

        server = new ServerConnector();
        // Offer
        OfferSource = new OfferDataSource();
        offer = new Offer(011, "sell", "abc", "test2", 1, 1 );
        OfferSource.addOffer(offer);

        // OUAsset setup
        OUAssetSource = new OUAssetDataSource();
        assetOU = new AssetPossession("abc", "abc", 10);
        OUAssetSource.addOuAsset(assetOU);

        // OU
        OUsource = new OUDataSource();
        TestOU = new OU("test", 0);
        OUsource.addOU(TestOU);

        // User
        userdata = new UserDataSource();
        user = new User("Chris", "Chris", "abc", "admin");
        userdata.addUser(user);

        // Asset
        AssetSource = new AssetDataSource();
        asset = new Asset("Danie");
        AssetSource.addAsset(asset);

    }

    @AfterEach
    void reset(){
        OfferSource.deleteOffer(offer.getId());
        OUAssetSource.deleteOUAsset(assetOU.getOu(), assetOU.getAsset());
        OUsource.deleteOU(TestOU.getOuName());
        userdata.deleteUser(user.getUsername());
        AssetSource.deleteAsset(asset.getAsset());
    }

    @Test
    void getOffers() throws Exception{
        assertEquals(OfferSource.offerSet().size(), server.GetOffers().size());
    }

    @Test
    void removeOffer() throws Exception{
        boolean d1 = OfferSource.deleteOffer(offer.getId());
        OfferSource.addOffer(offer);
        boolean d2 = server.removeOffer(new Offer(offer.getId(), 0));
        assertEquals(d1, d2);
    }

    @Test
    void editOffer() {
        int qty = offer.getQuantity();
        server.editOffer(new Offer(offer.getId(), 100));
        assertEquals(100, OfferSource.getOffer(offer.getId()).getQuantity());
    }

    @Test
    void addAsset() {
        assertTrue(server.getAsset().contains(asset.getAsset()));
    }

    @Test
    void removeAsset() {
        boolean d1 = AssetSource.deleteAsset(asset.getAsset());
        server.addAsset(asset);
        boolean d2 = server.removeAsset(new Asset(asset.getAsset()));
        assertEquals(d1, d2);
    }

    @Test
    void getAsset() {
        assertEquals(AssetSource.assetSet(), server.getAsset());
    }

    @Test
    void getAssetSize() {
        assertEquals(AssetSource.getSize(), server.getAssetSize());
    }

    @Test
    void addOUAsset() {
        assertEquals(assetOU.getAsset(), server.getSingleOUAsset(assetOU).getAsset());
    }

    @Test
    void removeOUAsset() {
        boolean d1 = OfferSource.deleteOffer(offer.getId());
        OfferSource.addOffer(offer);
        boolean d2 = server.removeOffer(new Offer(offer.getId(), 0));
        assertEquals(d1, d2);
    }

    @Test
    void getOUAsset() {
        List<AssetPossession> list = OUAssetSource.ouAssetList();
        assertEquals(list.size(), server.getOUAsset().size());
    }

    @Test
    void getOUAssetList() {
        List<AssetPossession> list = OUAssetSource.ouAssetList();
        assertEquals(list.size(), server.getOUAsset().size());
    }

    @Test
    void getSingleOUAsset() {
        AssetPossession get = server.getSingleOUAsset(assetOU);
        assertEquals(get.getOu(), assetOU.getOu());
    }

    @Test
    void editOUAsset() {
        server.editOUAsset(new AssetPossession(assetOU.getOu(), assetOU.getAsset(), 100));
        assertEquals(100, assetOU.getQuantity());
    }

    @Test
    void addOU() {
        assertEquals(TestOU.getOuName(), server.getSingleOU(TestOU).getOuName());
    }

    @Test
    void getOU() {
        HashMap<String, Integer> map = OUsource.ouList();
        assertEquals(map , server.getOU());
    }

    @Test
    void getSingleOU() {
        assertEquals(TestOU.getOuName(), server.getSingleOU(TestOU).getOuName());
    }

    @Test
    void editOUCredit() {
        server.editOUCredit(new OU(TestOU.getOuName(),100));
        assertEquals(100, TestOU.getCredits());
    }

    @Test
    void removeOU() {
        assertTrue(server.removeOU(TestOU));
        server.addOU(TestOU);
    }

    @Test
    void addUser() {
        assertTrue(server.getUser().contains(user.getUsername()));
    }

    @Test
    void getUser() {
        Set<String> list = userdata.userSet();
        assertEquals(list , server.getUser());
    }

    @Test
    void removeUser() {
        assertTrue(server.removeUser(user));
        server.addUser(user);
    }

    void getSingleUser() {
        assertEquals(user.getUsername(), server.getSingleUser(user.getUsername()).getUsername());
    }

    @Test
    void login() {
        assertEquals(user.getUsername(), server.login(user).getUsername());
    }

    @Test
    void checkPassword() {

    }

    @Test
    void changePassword() {
        String newPass = "danie";
        String oldPass = user.getPassword();
        server.changePassword(new User(user.getUsername(), newPass));
        assertNotEquals(user.getPassword(), oldPass);
    }
}