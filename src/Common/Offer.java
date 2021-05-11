package Common;

import java.io.Serializable;

/** Offer class pertaining to offer information, serializable as to send across stream**/
public class Offer implements Serializable {
    private String OUName;
    private String offerType;
    private String assetName;
    private Integer quantity;
    private Integer creditsEach;
    /**
     * Construction for a Buy Offer
     * @param OUName
     * @param type
     * @param assetName
     * @param quantity
     * @param creditsEach
     */
    public Offer(String OUName, String type, String assetName, Integer quantity, Integer creditsEach) {

    }

    /** possibly send buy object through to server? otherwise server will likely do most of this**/

    public String returnOU() {
        return this.OUName;
    }


}
