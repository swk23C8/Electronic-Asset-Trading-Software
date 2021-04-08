package Common;

/**
 * This class represents an admin and inherits from Users.
 */
public class Admin extends User {

    /**
     * Creates username and password for "admin" users(inherited from Users).
     * @param username
     * @param password
     */
    public Admin(String username, String password) {
        super(username, password);
    }

    /**
     * This method adds certain asset to the system(database?)
     * @param assetName Name of the asset.
     */
    public Asset addAsset(String assetName) {
        // I'm not sure this is the right way to add new asset.
        return new Asset(assetName);
    }

    /**
     * This method deletes certain asset from the system(database?), true if successful
     * @param assetName Name of the asset.
     */
    public boolean deleteAsset(String assetName) {
        return true;
    }

    /**
     * This method adds new Common.OU to the system.
     * @param ouName Name of new Common.OU.
     * @param credits Initial number of credit.
     */
    public OU addOU(String ouName, Integer credits) {
        /** Should make overloads with varying arguments using "this"
         */
        return new OU(ouName, credits);
    }

    /**
     * This method adds new Common.OU to the system.
     * @param ouName Name of new Common.OU.
     */
    public OU addOU(String ouName) {
        /** Should make overloads with varying arguments using "this"
         */
        return new OU(ouName);
    }

    /**
     * Change an organisational unit's current quantity of an asset/ add an asset to it
     * @param organisationalUnit
     * @param asset
     * @param assetNum
     */
    public void changeOUAsset(OU organisationalUnit, Asset asset, Integer assetNum){
        organisationalUnit.assetHashMap.put(asset, assetNum);
    }

    /**
     * Overload of addOUAsset in case the value is not specified
     * @param organisationalUnit
     * @param asset
     */
    public void changeOUAsset(OU organisationalUnit, Asset asset) {
        this.changeOUAsset(organisationalUnit, asset, 0);
    }

    /**
     * This method edits the number of the Organizational unit's credit, returns true on success
     * @param organisationalUnit Name of the Common.OU admin user wants to edit.
     * @param credits Number of the credits admin user wants to set.
     */
    public boolean changeOUCredits(OU organisationalUnit, Integer credits) {
        return organisationalUnit.modifyCredits(credits);
    }



    /**
     * Method assigns a user to a unit, returns true if successful
     * @param user
     * @param theirUnit
     */
    public boolean assignUserToOU(User user, OU theirUnit) {
        return theirUnit.addMember(user);
    }

    /**
     * Common.Admin changing Common.User's Password
     * @param user
     * @param newPassword
     */
    public void changePassword(User user, String newPassword) {
        user.changeOwnPassword(newPassword);
    }

    /**
     * Create a new admin
     * @param username
     * @param password
     * @return
     */
    public Admin addAdmin(String username, String password) {
        /** Check if username is unique, not implemented*/
        if (true) {
            return new Admin(username, password);
        }
        else {
            return null;
        }
    }

    /**
     * Create a new user
     * @param username
     * @param password
     * @return
     */
    public User addUser(String username, String password) {
        /** Check if username is unique, not implemented*/
        if (true) {
            return new User(username, password);
        }
        else {
            return null;
        }
    }

    /**
     * Modify user to admin, incomplete!
     * @param user
     * @return
     */
    /**
    public Admin userToAdmin(User user) {
        //Database change type to other type
        return new Admin(user.returnUsername(), user.returnPassword());
    }
    */

}
