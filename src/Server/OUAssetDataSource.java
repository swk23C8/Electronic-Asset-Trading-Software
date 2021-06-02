package Server;


import Common.AssetPossession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class OUAssetDataSource {

    private static final String INSERT_OU_ASSET = "INSERT INTO ouAsset (ouName, assetName, assetQty) VALUES (?, ?, ?);";

    private static final String LIST_OU_ASSET = "SELECT * FROM ouAsset";

    private static final String GET_OU_ASSET = "SELECT * FROM ouAsset WHERE ouName=? AND assetName=?";

    private static final String DELETE_OU_ASSET = "DELETE FROM ouAsset WHERE ouName=? AND assetName=?";

    private static final String Update_Qty = "UPDATE ouAsset SET assetQty=? WHERE ouName=? AND assetName=?";

    private static final String COUNT_ROWS = "SELECT COUNT(*) FROM ouAsset";

    private Connection connection;

    private PreparedStatement addOuAsset;

    private PreparedStatement getOuAssetList;

    private PreparedStatement getOuAsset;

    private PreparedStatement deleteOuAsset;

    private PreparedStatement editQty;

    private PreparedStatement rowCount;

    /**
     * Constructor for the configuration of the database connection.
     */
    public OUAssetDataSource() {
        connection = DBConnection.getInstance();
        try {

            addOuAsset = connection.prepareStatement(INSERT_OU_ASSET);
            getOuAssetList = connection.prepareStatement(LIST_OU_ASSET);
            getOuAsset = connection.prepareStatement(GET_OU_ASSET);
            deleteOuAsset = connection.prepareStatement(DELETE_OU_ASSET);
            editQty = connection.prepareStatement(Update_Qty);
            rowCount = connection.prepareStatement(COUNT_ROWS);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Add Offer to the CurrentTrade table.
     * @param a The asset OU possess
     */
    public void addOuAsset(AssetPossession a) {
        try {

            addOuAsset.setString(1, a.getOu());
            addOuAsset.setString(2, a.getAsset());
            addOuAsset.setInt(3, a.getQuantity());

            // .... more setting for other columns.
            addOuAsset.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Edit the quantity of the specific row in the ouAsset table.

     */
    public void editQty(AssetPossession a) {
        try {
            editQty.setInt(1, a.getQuantity());
            editQty.setString(2, a.getOu());
            editQty.setString(3, a.getAsset());
            editQty.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }


    /**
     * Return the list of asset ou possess by retrieving information from database.
     */
    public List<AssetPossession> offerSet() {
        List<AssetPossession> ouAssetList = new LinkedList<>();
        ResultSet rs = null;

        /* BEGIN MISSING CODE */
        try {
            rs = getOuAssetList.executeQuery();
            while (rs.next()) {
                ouAssetList.add(new AssetPossession(rs.getString(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */

        return ouAssetList;
    }

    /**
     * Return the specific Asset OU possess by retrieving information from database.
     */
    public AssetPossession getOuAsset(String ou, String asset) {
        AssetPossession a = new AssetPossession();
        ResultSet rs = null;

        try {
            getOuAsset.setString(1, ou);
            getOuAsset.setString(2, asset);
            rs = getOuAsset.executeQuery();
            rs.next();
            a.setOu(rs.getString(1));
            a.setAsset(rs.getString(2));
            a.setQuantity((rs.getInt(3)));

        } catch (SQLException ex) {
            return null;
        }
        /* END MISSING CODE */
        return a;
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
     * Delete the offer from the currentTrade table.
     */
    public boolean deleteOffer(String ou, String asset) {

        try {
            deleteOuAsset.setString(1, ou);
            deleteOuAsset.setString(2, asset);
            deleteOuAsset.executeUpdate();
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
