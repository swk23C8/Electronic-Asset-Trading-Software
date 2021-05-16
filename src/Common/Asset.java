package Common;

import java.io.Serializable;

/**
 * This class represents asset, possibly being sent across so kept if needed to be made serializable
 */
public class Asset implements Serializable {
    private String asset;

    /**
     * Empty constructor to initialise the object and add later
     */
    public Asset() { }

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
