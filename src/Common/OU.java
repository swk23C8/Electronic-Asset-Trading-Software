package Common;

import java.util.*;
/**
 * This class represents organizational units.
 */
public class OU {
    private String ouName;
    private Integer credits;
    /** Creating a list of users, unsure whether to use hashset, etc.
     * Depends on the size of the company, need to query. Hashset
     * as neither order or index of element is needed, maybe not needed?
     */
    HashSet<User> unitMembers = new HashSet<User>();
    HashMap<Asset, Integer> assetHashMap = new HashMap<Asset, Integer>();
    /**
     * Creates name of the Common.OU and number of the credit Common.OU has.
     * @param ouName Name of the Common.OU.
     * @param credits Number of the credit.
     */
    public OU(String ouName, Integer credits) {
        this.ouName = ouName;
        this.credits = credits;
    }

    /**
     * Multiple constructors dependent on known values by admin
     * @param ouName
     */
    public OU(String ouName) {
        this(ouName, 0);
    }

    /**
     * Return OU name
     * @return
     */
    public String returnOUName() {
        return this.ouName;
    }

    /**
     * Return the number of credits held by the OU
     * @return
     */
    public Integer returnUnitCredits() {
        return this.credits;
    }

    /**
     * Returns the number of assets depending on the quantity held
     * @param assetUsed
     * @return
     */
    public Integer returnAssetQuantity(Asset assetUsed) {
        return this.assetHashMap.get(assetUsed);
    }

    /**
     * Change this OU's credits, returns true if correct
     * @param newCredits
     * @return
     */
    public boolean modifyCredits(Integer newCredits) {
        if (newCredits >= 0) {
            this.credits = newCredits;
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Replace/ Create asset and quantity this has for the asset
     * @param asset
     * @param quantity
     */
    public void assignAssetNumber(Asset asset, Integer quantity) {
        assetHashMap.put(asset, quantity);
    }

    /**
     * Create asset with default number of 0 of that asset
     * @param asset
     */
    public void assignAssetNumber(Asset asset) {
        this.assignAssetNumber(asset, 0);
    }

    /**
     * Adds a member to the organisational unit, returns true if successful
     * @param newUser Common.User object being added
     */
     public boolean addMember(User newUser) {
         newUser.organisationalUnit = this;
         return unitMembers.add(newUser);
     }

    /**
     * Alternative of addMember for easily adding multiple users
     * @param newUsers
     */
    public void addMember(HashSet<User> newUsers) {
        unitMembers.addAll(newUsers);
    }

    /**
     * Returns the private HashMap
     * @return
     */
    public HashMap<Asset, Integer> returnAssetHashMap() {
        return this.assetHashMap;
    }

    /**
     * Return the users in the organisational unit for external usage
     * @return
     */
    public HashSet<User> returnUsers() {
        return this.unitMembers;
    }

    /**
     * Modify Credits internally
     * @param NewCredit
     */
    private void changeCredits(Integer NewCredit) {
        this.credits = NewCredit;
    }


}