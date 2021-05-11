package Server;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/** Will literally just be W7/8 prac material**/
public class DBConnector {
    /**
     * The singleton instance of the database connection.
     */
    private static final String url = "";
    private static final String username = "";
    private static final String password = "";
    private static Connection instance = null;

    /**
     * Constructor intializes the connection.
     */
    private DBConnector() {
        try {
            /** Format taken from W7 Prac, change to own config file just to store
             * url, username and password info for database
             */
            instance = DriverManager.getConnection(url, username,
                    password);
        } catch (SQLException sqle) {
            System.err.println(sqle);
        }
    }

    /**
     * Provides global access to the singleton instance of the UrlSet.
     *
     * @return a handle to the singleton instance of the UrlSet.
     */
    public static Connection getInstance() {
        if (instance == null) {
            new DBConnector();
        }
        return instance;
    }

}


