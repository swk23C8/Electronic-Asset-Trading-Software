package Server;

import Common.OU;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;



public class OUDataSource {


    private static final String INSERT_OU = "INSERT INTO ou (ouName, credit) VALUES (?, ?);";

    private static final String UPDATE_CREDIT = "UPDATE ou SET credit=? where ouName=?";

    private static final String LIST_OU = "SELECT * FROM ou";

    private static final String GET_OU = "SELECT * FROM ou WHERE ouName=?";

    private static final String DELETE_OU = "DELETE FROM ou WHERE ouName=?";

    private static final String COUNT_ROWS = "SELECT COUNT(*) FROM ou"
            ;
    private Connection connection;

    private PreparedStatement addOU;

    private PreparedStatement editCredit;

    private PreparedStatement getOuList;

    private PreparedStatement getOU;

    private PreparedStatement deleteOU;

    private PreparedStatement rowCount;

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


    public void addOU(OU o) {
        try {
            /* BEGIN MISSING CODE */
            addOU.setString(1, o.getOuName());
            addOU.setInt(2, o.getCredits());
            addOU.execute();
            /* END MISSING CODE */
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void editCredit(OU o) {
        try {
            editCredit.setInt(1, o.getCredits());
            editCredit.setString(2, o.getOuName());
            editCredit.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }


    public HashMap<String, Integer> ouList() {
        HashMap<String, Integer> list = new HashMap<>();
        ResultSet rs = null;

        /* BEGIN MISSING CODE */
        try {
            rs = getOuList.executeQuery();
            while (rs.next()) {
                list.put(rs.getString("ouName"), rs.getInt("credit"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */

        return list;
    }


    public OU getOU(String name) {
        OU o = new OU();
        ResultSet rs = null;
        /* BEGIN MISSING CODE */
        try {
            getOU.setString(1, name);
            rs = getOU.executeQuery();
            rs.next();
            o.setOuName(rs.getString("ouName"));
            o.setCredits(rs.getInt("credit"));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */
        return o;
    }


    public int getSize() {
        ResultSet rs = null;
        int rows = 0;

        /* BEGIN MISSING CODE */
        try {
            rs = rowCount.executeQuery();
            rs.next();
            rows = rs.getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */

        return rows;
    }


    public void deleteOU(String name) {
        /* BEGIN MISSING CODE */
        try {
            deleteOU.setString(1, name);
            deleteOU.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */
    }


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