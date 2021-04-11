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
    public void addAsset(String assetName) {
        // I'm not sure this is the right way to add new asset.
        Asset newAsset = new Asset(assetName);
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
        /** Should make overloads with varying arguments using "this"
         */
    }

    /**
     * This method edits the number of the Organizational unit's credit.
     * @param ouName Name of the OU admin user wants to edit.
     * @param credits Number of the credits admin user wants to set.
     */
    public void editCredits(String ouName, double credits) {
        //..
    }

    /**
     * Establish a new asset to an organisational unit
     * @param organisationalUnit
     * @param asset
     * @param assetNum
     */
    public void changeOUAsset(OU organisationalUnit, Asset asset, Integer assetNum){
        organisationalUnit.numberOfAssets.put(asset, assetNum);
    }

    /**
     * Overload of addOUasse in case the value is not specified
     * @param organisationalUnit
     * @param asset
     */
    public void changeOUAsset(OU organisationalUnit, Asset asset){
        this.changeOUAsset(organisationalUnit, asset, 0);
    }

    /**
     * Method assigns a user to a unit, returns true if successful
     * @param user
     * @param theirUnit
     */
    public boolean assignUser(User user, OU theirUnit) {
        return theirUnit.addMember(user);
    }

    /**
     * Admin changing User's Password
     * @param user
     * @param newPassword
     */
    public void changePassword(User user, String newPassword) {
        user.changeOwnPassword(newPassword);
    }

}
