package Common;

import java.util.HashSet;

/** A class pertaining to the management of offers and assets in the database
 *
 */
public class Trades {
    HashSet<Asset> Assets = new HashSet<Asset>();

    //Periodic Trading, methods that do that go here

    /**
     * This method illustrates all the process of buying asset.
     * ex) Adding the offer to the offer list, constraint, changes to Common.OU's credit, ... etc
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
