package Common;

public abstract class Offer {

    private User user;
    private Asset asset;
    private Integer quantity;
    private Integer creditsEach;
    private Integer totalCost;
    /**
     * Construction for a general offer
     * @param user
     * @param asset
     * @param quantity
     * @param creditsEach
     */
    public Offer(User user, Asset asset, Integer quantity, Integer creditsEach) {
        this.asset = asset;
        this.quantity = quantity;
        this.user = user;
        this.creditsEach = creditsEach;
    }

    /**
     * Internal method for calculating overall cost
     */
    private void costCalculation(){
        this.totalCost = quantity * creditsEach;
    }
}
