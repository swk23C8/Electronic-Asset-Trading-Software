package Common;

import java.io.Serializable;

/**
 * This class represents Common.User.
 */
public class User implements Serializable {

    /**
     * Private properties pertaining to a user
     */
    private String username;
    private String password;
    private String ou;
    private String type;

    /**
     * Empty constructor for different usage (manually setting username and password
     * with getters and setters, etc.)
     */
    public User() {}
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

    /**
     * Constructor for a user with full information
     * @param username
     * @param password
     * @param ou
     * @param type
     */
    public User(String username, String password, String ou, String type) {
        this.username = username;
        this.password = password;
        this.ou = ou;
        this.type = type;

    }

    /**
     * Get and Setters for User properties
     */
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


}
