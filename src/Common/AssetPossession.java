package Common;

import java.io.Serializable;

/**
 * Serialized class pertaining to an asset's possession for an OU,
 * specifically object will represent a row information in the corresponding database
 * table
 */
public class AssetPossession implements Serializable {

    private String ou;
    private String asset;
    private Integer quantity;

    /**
     * Empty constructor for the object and
     * can add information later
     */
    public AssetPossession () {}

    /**
     * @param ou
     * @param asset
     * Constructor for AssetPossession
     */
    public AssetPossession (String ou, String asset) {
        this.ou = ou;
        this.asset = asset;
    }

    /**
     * @param ou
     * @param asset
     * @param quantity
     * Constructor for AssetPossession
     */
    public AssetPossession(String ou, String asset, Integer quantity) {
        this.ou = ou;
        this.asset = asset;
        this.quantity = quantity;
    }


    /**
     * Get and setters for the AssetPossession class
     */
    public String getOu() {
        return ou;
    }

    public void setOu(String ou) {
        this.ou = ou;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
