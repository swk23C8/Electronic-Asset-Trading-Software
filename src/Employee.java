/**
 * This class represent an employee in certain organization units and inherits from Users.
 */
public class Employee extends Users{

    String ouName;
    /**
     * Creates username and password(inherited from Users) for employee in certain OU.
     *
     * @param username
     * @param password
     * @param ouName Name of the OU the employee belongs to.
     */
    public Employee(String username, String password, String ouName) {
        super(username, password);
        this.ouName = ouName;
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
