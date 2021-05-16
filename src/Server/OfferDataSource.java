package Server;

import Common.OU;
import Common.Offer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OfferDataSource {
    private static final String INSERT_OFFER = "INSERT INTO currentTrade (offerID, offerType, ouName, assetName, assetQty, price, date) VALUES (?, ?, ?, ?, ?, ?, ?);";

    private static final String UPDATE_CREDIT = "UPDATE ou SET credit=? where ouName=?";

    private static final String LIST_OFFERS = "SELECT * FROM currentTrade";

    private static final String GET_OFFER = "SELECT * FROM currentTrade WHERE offerID=?";

    private static final String DELETE_OFFER = "DELETE FROM currentTrade WHERE offerID=?";

    private static final String COUNT_ROWS = "SELECT COUNT(*) FROM ou";

    private Connection connection;

    private PreparedStatement addOU;

    private PreparedStatement editCredit;

    private PreparedStatement getOfferList;

    private PreparedStatement getOffer;

    private PreparedStatement deleteOffer;

    private PreparedStatement rowCount;

    public OfferDataSource() {
        connection = DBConnection.getInstance();
        try {
            /* BEGIN MISSING CODE */
            addOU = connection.prepareStatement(INSERT_OFFER);
            editCredit = connection.prepareStatement(UPDATE_CREDIT);
            getOfferList = connection.prepareStatement(LIST_OFFERS);
            getOffer = connection.prepareStatement(GET_OFFER);
            deleteOffer = connection.prepareStatement(DELETE_OFFER);
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


    public List<Offer> offerSet() {
        List<Offer> offerList = new LinkedList<>();
        ResultSet rs = null;

        /* BEGIN MISSING CODE */
        try {
            rs = getOfferList.executeQuery();
            while (rs.next()) {
                offerList.add(new Offer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */

        return offerList;
    }


    public OU getOU(String name) {
        OU o = new OU();
        ResultSet rs = null;
        /* BEGIN MISSING CODE */
        try {
            getOffer.setString(1, name);
            rs = getOffer.executeQuery();
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


    public void deleteOffer(Integer id) {
        /* BEGIN MISSING CODE */
        try {
            deleteOffer.setInt(1, id);
            deleteOffer.executeUpdate();
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

