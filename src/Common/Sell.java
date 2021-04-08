package Common;

public class Sell extends Offer {

    /**
     * Construction for a Sell Offer
     * @param user
     * @param asset
     * @param quantity
     * @param creditsEach
     */
    public Sell(User user, Asset asset, Integer quantity, Integer creditsEach) {
        super(user, asset, quantity, creditsEach);
    }


}
