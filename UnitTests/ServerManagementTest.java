package UnitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import Server.*;
import Common.*;

class ServerManagementTest {
    private AssetDataSource mockAssetData = new AssetDataSource();
    private OUDataSource mockOUData = new OUDataSource();
    private OfferDataSource mockOfferData = new OfferDataSource();
    private OUAssetDataSource mockOUAssetOfferData = new OUAssetDataSource();
    private ServerManagement mockServerManager;

    @BeforeEach
    void populateTestDBInfo(){

        mockAssetData.addAsset(new Asset("testerAsset"));
        mockOUData.addOU(new OU("testerOU"));
        mockOUData.addOU(new OU("testerOU2"));
        mockOUAssetOfferData.addOuAsset(new AssetPossession("testerOU", "testerAsset", 10));
        mockOfferData.addOffer(new Offer("sell", "testerOU",
                "testerAsset", 10, 1));
        mockOfferData.addOffer(new Offer("buy", "testerOU",
                "testerAsset", 10, 1));
    }

    @Test
    void reconcile(){
        //serverManager.handleCommand();
        //testing reconcile method
        try {
            mockServerManager.reconcile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertEquals(0, mockOUData.getOU("testerOU").returnAssetQuantity(new Asset("testerAsset")));
    }
}