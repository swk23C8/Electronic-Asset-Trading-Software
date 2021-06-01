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


    private static final String LIST_OFFERS = "SELECT * FROM currentTrade";

    private static final String GET_OFFER = "SELECT * FROM currentTrade WHERE offerID=?";

    private static final String DELETE_OFFER = "DELETE FROM currentTrade WHERE offerID=?";

    private static final String COUNT_ROWS = "SELECT COUNT(*) FROM currentTrade";

    private Connection connection;

    private PreparedStatement addOffer;

    private PreparedStatement getOfferList;

    private PreparedStatement getOffer;

    private PreparedStatement deleteOffer;

    private PreparedStatement rowCount;

    /**
     * Constructor for the configuration of the database connection.
     */
    public OfferDataSource() {
        connection = DBConnection.getInstance();
        try {

            addOffer = connection.prepareStatement(INSERT_OFFER);
            getOfferList = connection.prepareStatement(LIST_OFFERS);
            getOffer = connection.prepareStatement(GET_OFFER);
            deleteOffer = connection.prepareStatement(DELETE_OFFER);
            rowCount = connection.prepareStatement(COUNT_ROWS);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Add Offer to the CurrentTrade table.
     * @param o The offer object
     */
    public void addOffer(Offer o) {
        try {

            addOffer.setInt(1, o.getId());
            // .... more setting for other columns.
            addOffer.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Return the list of Offer by retrieving information from database.
     */
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

    /**
     * Return the specific offer by retrieving information from database.
     * @param id The id of the offer.
     */
    public Offer getOffer(Integer id) {
        Offer o = new Offer();
        ResultSet rs = null;
        /* BEGIN MISSING CODE */
        try {
            getOffer.setInt(1, id);
            rs = getOffer.executeQuery();
            rs.next();
            o.setId(rs.getInt(1));

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */
        return o;
    }

    /**
     * Return the size of the currentTable table.
     */
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

    /**
     * Delete the offer from the curentTrade table.
     */
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

