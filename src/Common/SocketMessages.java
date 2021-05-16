package Common;

/**
 * Class set to define different commands, could be done as an enum
 * but a class construction works sufficiently well
 */
public class SocketMessages {
    /** String commands sent over network stored here**/
    public static final String LOGIN = "Login";
    public static final String ADD_OFFER = "Add Offer";
    public static final String REMOVE_OFFER = "Remove Offer";
    public static final String GET_OFFERS = "Get Offers";
    public static final String SUCCESS_LOGIN = "Success Login";
    public static final String FAILED_LOGIN = "Failed Login";
    public static final String Change_Password = "Change Password";
    public static final String EXAMPLE_COMMAND = "Example Command";

}
