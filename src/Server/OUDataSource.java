package Server;

import Common.OU;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;



public class OUDataSource {


    private static final String INSERT_OU = "INSERT INTO ou (ouName, credit) VALUES (?, ?);";

    private static final String UPDATE_CREDIT = "UPDATE ou SET credit=? WHERE ouName=?";

    private static final String LIST_OU = "SELECT * FROM ou";

    private static final String GET_OU = "SELECT * FROM ou WHERE ouName=?";

    private static final String DELETE_OU = "DELETE FROM ou WHERE ouName=?";

    private static final String COUNT_ROWS = "SELECT COUNT(*) FROM ou";

    private Connection connection;

    private PreparedStatement addOU;

    private PreparedStatement editCredit;

    private PreparedStatement getOuList;

    private PreparedStatement getOU;

    private PreparedStatement deleteOU;

    private PreparedStatement rowCount;

    /**
     * Constructor for the configuration of the database connection.
     */
    public OUDataSource() {
        connection = DBConnection.getInstance();
        try {
            /* BEGIN MISSING CODE */
            addOU = connection.prepareStatement(INSERT_OU);
            editCredit = connection.prepareStatement(UPDATE_CREDIT);
            getOuList = connection.prepareStatement(LIST_OU);
            getOU = connection.prepareStatement(GET_OU);
            deleteOU = connection.prepareStatement(DELETE_OU);
            rowCount = connection.prepareStatement(COUNT_ROWS);
            /* END MISSING CODE */
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Add OU(oraganizational unit) to the ou table.
     * @param OU The OU object
     */
    public boolean addOU(OU OU) {
        try {
            /* BEGIN MISSING CODE */
            addOU.setString(1, OU.getOuName());
            addOU.setInt(2, OU.getCredits());
            if (addOU.execute()){
                return true;
            }
            else {
                return false;
            }
            /* END MISSING CODE */
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Edit the number of the credit of the specific row in the asset table.
     * @param OU The OU object
     */
    public void editCredit(OU OU) {
        try {
            editCredit.setInt(1, OU.getCredits());
            editCredit.setString(2, OU.getOuName());
            editCredit.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Return the hashmap of ou by retrieving information from database.
     */
    public HashMap<String, Integer> ouList() {
        HashMap<String, Integer> OUlist = new HashMap<>();
        ResultSet rs = null;

        /* BEGIN MISSING CODE */
        try {
            rs = getOuList.executeQuery();
            while (rs.next()) {
                OUlist.put(rs.getString("ouName"), rs.getInt("credit"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */

        return OUlist;
    }

    /**
     * Return the specific ou by retrieving information from database.
     * @param name The name of the ou.
     */
    public OU getOU(String name) {
        OU OU = new OU();
        ResultSet rs = null;
        /* BEGIN MISSING CODE */
        try {
            getOU.setString(1, name);
            rs = getOU.executeQuery();
            if (rs.next()){
                OU.setOuName(rs.getString("ouName"));
                OU.setCredits(rs.getInt("credit"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */
        return OU;
    }

    /**
     * Return the size of the ou table.
     */
    public int getSize() {
        ResultSet rs = null;
        int rowNumber = 0;

        /* BEGIN MISSING CODE */
        try {
            rs = rowCount.executeQuery();
            if (rs.next()){
                rowNumber = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */

        return rowNumber;
    }

    /**
     * Delete the OU from the ou table.
     */
    public boolean deleteOU(String name) {
        /* BEGIN MISSING CODE */
        try {
            deleteOU.setString(1, name);
            deleteOU.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
        /* END MISSING CODE */
    }

    /**
     * Closing the database.
     */
    public void close() {
        /* BEGIN MISSING CODE */
        try {
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */
    }
}