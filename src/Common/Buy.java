package Common;

public class Buy extends Offer {

    /**
     * Construction for a Buy Offer
     * @param user
     * @param asset
     * @param quantity
     * @param creditsEach
     */
    public Buy(User user, Asset asset, Integer quantity, Integer creditsEach) {
        super(user, asset, quantity, creditsEach);
    }
}
