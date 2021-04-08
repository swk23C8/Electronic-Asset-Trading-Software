import java.util.*;
/**
 * This class represents organizational units.
 */
public class OU {
    String ouName;
    double credits;
    /** Creating a list of users, unsure whether to use hashset, etc.
     * Depends on the size of the company, need to query. Hashset
     * as neither order or index of element is needed
     */
    HashSet<User> unitMembers = new HashSet<User>();
    HashMap<Asset, Integer> numberOfAssets = new HashMap<Asset, Integer>();
    /**
     * Creates name of the OU and number of the credit OU has.
     * @param ouName Name of the OU.
     * @param credits Number of the credit.
     */
    public OU(String ouName, double credits) {
        this.ouName = ouName;
        this.credits = credits;
    }

    /**
     * Multiple constructors dependent on known values by admin
     * @param ouName
     */
    public OU(String ouName) {
        this.ouName = ouName;
    }

    /**
     * Adds a member to the organisational unit, returns true if successful
     * @param newUser User object being added
     */
     public boolean addMember(User newUser) {
         newUser.organisationalUnit = this;
         return unitMembers.add(newUser);
     }

    /**
     * Alternative of addMember for easily adding multiple users
     * @param newUsers
     */
    public void addMember(HashSet<User> newUsers) {
         unitMembers.addAll(newUsers);
     }
}
