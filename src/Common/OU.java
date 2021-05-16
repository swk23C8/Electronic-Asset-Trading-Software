package Common;

import java.io.Serializable;
import java.util.*;
/**
 * This class represents organizational units, serialized for sending over the socket
 */
public class OU implements Serializable {
    private String ouName;
    private Integer credits;

    /** Hashmap of Assets and quantity owned **/
    HashMap<Asset, Integer> assetHashMap = new HashMap<Asset, Integer>();

    /**
     * Constructor name of the OU and number of the credit OU has.
     * @param ouName Name of the Common.OU.
     * @param credits Number of the credit.
     */
    public OU(String ouName, Integer credits) {
        this.ouName = ouName;
        this.credits = credits;
    }

    /**
     * Empty constructor for OU, added to via getter and setters
     */
    public OU() { }

    /**
     * Another constructor dependent on currently known values
     * @param ouName
     */
    public OU(String ouName) {
        this(ouName, 0);
    }


    /**
     * Return OU name
     * @return
     */
    public String getOuName() {
        return this.ouName;
    }

    /**
     * Return the number of credits held by the OU
     * @return
     */
    public Integer getCredits() {
        return this.credits;
    }

    /**
     * Set the name of the OU
     * @param name the name to set
     */
    public void setOuName(String name) {
        ouName = name;
    }


    /**
     * Set the OU's credits to a new amount
     * @param credit the credit to set
     */
    public void setCredits(Integer credit) {
        credits = credit;
    }

    /**
     * Returns the number of assets depending on the quantity held
     * @param assetUsed
     * @return
    **/
    public Integer returnAssetQuantity(Asset assetUsed) { return this.assetHashMap.get(assetUsed);
    }

    /**
     * Set the quantity of an asset the OU has
     * @param assetUsed
     * @param quantity
     */
    public void setAssetQuantity(Asset assetUsed, int quantity) { assetHashMap.put(assetUsed, quantity);   }

    /**
     * Overload of setAssetQuantity for singularly setting an asset, with default value
     * @param assetUsed
     */
    public void setAssetQuantity(Asset assetUsed) { assetHashMap.put(assetUsed, 0);   }

}
