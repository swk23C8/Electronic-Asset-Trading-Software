package Common;

import java.io.Serializable;

/** Offer class pertaining to offer information, serializable as to send across stream**/
public class Offer implements Serializable {
    private Integer id;
    private String OUName;
    private String offerType;
    private String assetName;
    private Integer quantity;
    private Integer creditsEach;
    private String date;


    /**
     * Constructor for an Offer with all properties
     * @param OUName
     * @param type
     * @param assetName
     * @param quantity
     * @param creditsEach
     */
    public Offer(Integer id, String type, String OUName, String assetName, Integer quantity, Integer creditsEach) {
        this.id = id;
        this.OUName = OUName;
        this.offerType = type;
        this.assetName = assetName;
        this.quantity = quantity;
        this.creditsEach = creditsEach;
    }

    /** Constructor for full offer information - Used for table
     * @param id
     * @param type
     * @param OUName
     * @param assetName
     * @param quantity
     * @param creditsEach
     * @param date
     */
    public Offer(Integer id, String type, String OUName, String assetName, Integer quantity, Integer creditsEach, String date) {
        this.id = id;
        this.OUName = OUName;
        this.offerType = type;
        this.assetName = assetName;
        this.quantity = quantity;
        this.creditsEach = creditsEach;
        this.date = date;
    }

    /** Constructor to get ID
     * @param id
     */
    public Offer(Integer id){
        this.id = id;
    }

    /** Constructor to get ID and quantity
     * @param id
     * @param quantity
     */
    public Offer(Integer id, Integer quantity)
    {
        this.id = id;
        this.quantity = quantity;
    }



    public Offer(String type, String OUName, String assetName, Integer quantity, Integer creditsEach) {
        this.OUName = OUName;
        this.offerType = type;
        this.assetName = assetName;
        this.quantity = quantity;
        this.creditsEach = creditsEach;
    }


    public Offer()
    {

    }

    /**
     * Getter and setter methods for the Offer class
     */
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOUName() {
        return OUName;
    }

    public void setOUName(String OUName) {
        this.OUName = OUName;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getCreditsEach() {
        return creditsEach;
    }

    public void setCreditsEach(Integer creditsEach) {
        this.creditsEach = creditsEach;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /** possibly send buy object through to server? otherwise server will likely do most of this**/




}
