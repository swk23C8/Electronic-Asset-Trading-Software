/**
 * This class represents User.
 */
public class User {
    String username;
    String password;
    /** Assume can only be in one OU, not sure if this should be kept*/
    OU organisationalUnit;

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
     * This method makes buy offer.
     */
    public void makeBuy() {

    }

    /**
     * This method makes sell offer.
     */
    public void makeSell() {

    }
}
