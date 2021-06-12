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

    private static final String INSERT_OFFER = "INSERT INTO currentTrade (offerID, offerType, ouName, assetName, assetQty, price, date) VALUES (NULL, ?, ?, ?, ?, ?, date_format(now(), '%Y%m%d'));";

    private static final String INSERT_HISTORY = "INSERT INTO tradeHistory (offerID, offerType, ouName, assetName, assetQty, price, date) VALUES (?, ?, ?, ?, ?, ?, date_format(now(), '%Y%m%d'));";

    private static final String LIST_OFFERS = "SELECT * FROM currentTrade";

    private static final String GET_OFFER = "SELECT * FROM currentTrade WHERE offerID=?";

    private static final String DELETE_OFFER = "DELETE FROM currentTrade WHERE offerID=?";

    private static final String Update_Qty = "UPDATE currentTrade SET assetQty=? WHERE offerID=?";

    private static final String COUNT_ROWS = "SELECT COUNT(*) FROM currentTrade";

    private Connection connection;

    private PreparedStatement addOffer;

    private PreparedStatement addHistory;

    private PreparedStatement getOfferList;

    private PreparedStatement getOffer;

    private PreparedStatement deleteOffer;

    private PreparedStatement editQty;

    private PreparedStatement rowCount;

    /**
     * Constructor for the configuration of the database connection.
     */
    public OfferDataSource() {
        connection = DBConnection.getInstance();
        try {

            addOffer = connection.prepareStatement(INSERT_OFFER);
            addHistory = connection.prepareStatement(INSERT_HISTORY);
            getOfferList = connection.prepareStatement(LIST_OFFERS);
            getOffer = connection.prepareStatement(GET_OFFER);
            deleteOffer = connection.prepareStatement(DELETE_OFFER);
            editQty = connection.prepareStatement(Update_Qty);
            rowCount = connection.prepareStatement(COUNT_ROWS);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Add Offer to the CurrentTrade table.
     * @param Offer The offer object
     */
    public void addOffer(Offer Offer) {
        try {

            addOffer.setString(1, Offer.getOfferType());
            addOffer.setString(2, Offer.getOUName());
            addOffer.setString(3, Offer.getAssetName());
            addOffer.setInt(4, Offer.getQuantity());
            addOffer.setInt(5, Offer.getCreditsEach());

            // .... more setting for other columns.
            addOffer.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param Offer
     * Add offer into the history table
     */
    public void addHistory(Offer Offer) {
        try {

            addHistory.setInt(1, Offer.getId());
            addHistory.setString(2, Offer.getOfferType());
            addHistory.setString(3, Offer.getOUName());
            addHistory.setString(4, Offer.getAssetName());
            addHistory.setInt(5, Offer.getQuantity());
            addHistory.setInt(6, Offer.getCreditsEach());

            // .... more setting for other columns.
            addHistory.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Edit the quantity of the specific row in the currentTrade table.
     * @param Offer edited Offer

     */
    public void editQty(Offer Offer) {
        try {
            editQty.setInt(1, Offer.getQuantity());
            editQty.setInt(2, Offer.getId());
            editQty.executeUpdate();
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
                offerList.add(new Offer(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getInt(6), rs.getString(7)));
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
        Offer Offer = new Offer();
        ResultSet rs = null;
        /* BEGIN MISSING CODE */
        try {
            getOffer.setInt(1, id);
            rs = getOffer.executeQuery();
            if (rs.next()){
                Offer.setId(rs.getInt(1));
                Offer.setOfferType(rs.getString(2));
                Offer.setOUName(rs.getString(3));
                Offer.setAssetName(rs.getString(4));
                Offer.setQuantity(rs.getInt(5));
                Offer.setCreditsEach(rs.getInt(6));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */
        return Offer;
    }

    /**
     * Return the size of the currentTable table.
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
     * Delete the offer from the curentTrade table.
     */
    public boolean deleteOffer(Integer id) {
        /* BEGIN MISSING CODE */
        try {
            deleteOffer.setInt(1, id);
            deleteOffer.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        //Need proper return true or false if id exists in database
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

