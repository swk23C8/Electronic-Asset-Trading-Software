package Common;

/**
 * This class represents Common.User.
 */
public class User {
    private String username;
    private String password;
    private String ou;
    private String type;

    public User() {

    }
    /** Assume can only be in one OU, not sure if this should be kept*/

    /**
     * Creates username and password for users.
     *
     * @param username
     * @param password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String ou, String type) {
        this.username = username;
        this.password = password;
        this.ou = ou;
        this.type = type;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOu() {
        return ou;
    }

    public void setOu(String ou) {
        this.ou = ou;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


//    /**
//     * Change the user's own password
//     * @param newPassword
//     */
//    public void changeOwnPassword (String newPassword) {
//        this.password = newPassword;
//    }
//
//    /**
//     * This method makes buy offer.
//     */
//    public void makeBuy() {
//
//    }
//
//    /**
//     * This method makes sell offer.
//     */
//    public void makeSell() {
//
//    }
}
