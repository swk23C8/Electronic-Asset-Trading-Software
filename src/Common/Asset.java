package Common;

/**
 * This class represents asset, possibly being sent across so kept if needed to be made serializable
 */
public class Asset {
    private String asset;

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
    public String returnName() {
        return this.asset;
    }

}
