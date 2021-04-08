/**
 * This class represents the process of buy and sell offer.
 */
public class Offer {
    String assetName;
    int quantity;
    /**
     * Creates name and quantity of the asset that is going to be purchased or sold.
     * @param assetName Name of the asset.
     * @param quantity Quantity of the asset
     */
    public Offer(String assetName, int quantity) {
        this.assetName = assetName;
        this.quantity = quantity;
    }

    /**
     * This method illustrates all the process of buying asset.
     * ex) Adding the offer to the offer list, constraint, changes to OU's credit, ... etc
     */
    public void buyOffers() {

    }

    /**
     * This method illustrates all the process of selling asset.
     * ex) Same as above.
     * Maybe buy and sell could happen in one method? Not sure..
     */
    public void sellOffers() {

    }
}
