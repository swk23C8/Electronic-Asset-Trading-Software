package Common;

/**
 * This class represents Common.User.
 */
public class User {
    private String username;
    private String password;
    /** Assume can only be in one Common.OU, not sure if this should be kept*/
    OU ou;

    /**
     * Creates username and password for users.
     * @param username
     * @param password
     */
    public User (String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Change the user's own password
     * @param newPassword
     */
    public void changeOwnPassword (String newPassword) {
        this.password = newPassword;
    }

    /**
     * Create a buy offer, return null if not able
     */
    public Buy makeBuy(Asset asset, Integer quantity, Integer creditsEach) {
        Integer newTotal = this.ou.returnUnitCredits() - (creditsEach * quantity);
        if (this.ou.modifyCredits(newTotal)) {
            Buy newBuy = new Buy(this, asset, quantity, creditsEach);
            return newBuy;
        }
        else {
            return null;
        }
    }

    /**
     * Create a sell offer, return Sell object if successful
     * @param asset
     * @param quantity
     * @param creditsEach
     */
    public Sell makeSell(Asset asset, Integer quantity, Integer creditsEach) {
        if (this.ou.returnAssetQuantity(asset) >= quantity) {
            Sell newSell = new Sell(this, asset, quantity, creditsEach);
            return newSell;
        }
        else {
            return null;
        }
    }

    /**
     * Cancel the offer (buy or sell), true if successful
     * @return
     */
    public boolean cancelOffer(Offer offer){
        return true;
    }
    /**
     * Internal method to allow access to username externally
     * @return
     */
    public String returnUsername() {
        return this.username;
    }

    /**
     * Return password to external classes, etc.
     * @return
     */
    public String returnPassword() {
        return this.password;
    }

    /**
     * Return organisational unit of the user to external methods
     * @return
     */
    public OU returnOU() {
        return this.ou;
    }
}
