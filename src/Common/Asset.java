package Common;

/**
 * This class represents assets.
 */
public class Asset {
    private String asset;

    public Asset() {
    }

    /**
     * Creates an asset with specified name.
     * @param assetName The name of the asset.
     */
    public Asset(String assetName) {
        asset = assetName;
    }

    /**
     * Returns the name of the asset
     * @return
     */
    public String getAsset() {
        return asset;
    }

    /**
     * @param assetName the name to set
     */
    public void setAsset(String assetName) {
        asset = assetName;
    }

}
