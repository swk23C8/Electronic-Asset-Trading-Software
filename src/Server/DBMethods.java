package Server;
import Common.Offer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/** Prac 7 Implementation of SQL methods in the server and interactions with database**/
public class DBMethods {

    /** Use ? to set changed variable with setString**/
    public static final String CREATE_TABLE = "";

    private static final String INSERT_OFFER = "?";

    private Connection connection;

    private PreparedStatement addOffer;

    /** Constructor for DBMethods preparing SQL Statements**/
    public DBMethods() {
        connection = DBConnector.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
            /* BEGIN MISSING CODE */
            addOffer = connection.prepareStatement(INSERT_OFFER);
            /* END MISSING CODE */
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /** Example SQL Command for future commands, add offer, needs development**/
    public void addOffer(Offer offer) {
        try {
            addOffer.setString(1, offer.returnOU());
            /** Add remaining setStrings**/
            addOffer.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /** Add other SQL command methods for server usage here**/

    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
