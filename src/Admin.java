/**
 * This class represents an admin and inherits from Users.
 */
public class Admin extends Users {

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
    public void addAsset(String assetName) {
        // I'm not sure this is the right way to add new asset.
        Assets newAsset = new Assets(assetName);
    }

    /**
     * This method deletes certain asset from the system(database?)
     * @param assetName Name of the asset.
     */
    public void deleteAsset(String assetName) {
        // ...
    }

    /**
     * This method adds new OU to the system.
     * @param ouName Name of new OU.
     * @param credits Initial number of credit.
     */
    public void addOU(String ouName, double credits) {

    }

    /**
     * This method edits the number of the Organizational unit's credit.
     * @param ouName Name of the OU admin user wants to edit.
     * @param credits Number of the credits admin user wants to set.
     */
    public void editCredits(String ouName, double credits) {
        //..
    }


}
