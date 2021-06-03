package Common;

import java.io.Serializable;

public class AssetPossession implements Serializable {

    private String ou;
    private String asset;
    private Integer quantity;

    public AssetPossession () {}

    public AssetPossession(String ou, String asset, Integer quantity) {
        this.ou = ou;
        this.asset = asset;
        this.quantity = quantity;
    }


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
