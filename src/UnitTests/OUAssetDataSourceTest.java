package UnitTests;

import Common.AssetPossession;
import Common.OU;
import Server.OUAssetDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OUAssetDataSourceTest {

    private OUAssetDataSource source;
    private AssetPossession assetOU;
    @BeforeEach
    void setUp() {
        source = new OUAssetDataSource();
        assetOU = new AssetPossession("abc", "abc", 10);
    }

    @Test
    void addOuAsset() {
        source.addOuAsset(assetOU);
        assertEquals(assetOU.getOu(), source.getOuAsset("abc", "abc").getOu());
        source.deleteOUAsset("abc","abc");
    }

    @Test
    void editQty() {
        source.addOuAsset(assetOU);
        source.editQty(new AssetPossession("abc", "abc", 100));
        assertEquals(100, source.getOuAsset("abc","abc").getQuantity());
        source.deleteOUAsset("abc","abc");
    }

    @Test
    void offerSet() {
        List<AssetPossession> list = source.offerSet();
        source.addOuAsset(new AssetPossession("abc", "abc", 0));
        assertNotEquals(list.size(), source.offerSet().size());
        source.deleteOUAsset("abc","abc");
    }

    @Test
    void getOuAsset() {
        source.addOuAsset(new AssetPossession("abc", "abc", 0));
        assertEquals(assetOU.getOu(), source.getOuAsset("abc","abc").getOu());
    }

    @Test
    void getSize() {
        int size = source.getSize();
        source.addOuAsset(new AssetPossession("test","test", 10));
        assertEquals(size, source.getSize());
    }

    @Test
    void deleteOffer() {
        source.deleteOUAsset("abc","abc");
    }

    @Test
    void close() {
    }

}