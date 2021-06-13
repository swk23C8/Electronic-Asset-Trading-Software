package Server;


import Common.AssetPossession;
import Common.OU;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class OUAssetDataSource {

    private static final String INSERT_OU_ASSET = "INSERT INTO ouAsset (ouName, assetName, assetQty) VALUES (?, ?, ?);";

    private static final String LIST_OU_ASSET = "SELECT * FROM ouAsset";

    private static final String GET_OU_ASSET = "SELECT * FROM ouAsset WHERE ouName=? AND assetName=?";

    private static final String DELETE_OU_ASSET = "DELETE FROM ouAsset WHERE ouName=? AND assetName=?";

    private static final String UPDATE_QTY = "UPDATE ouAsset SET assetQty=? WHERE ouName=? AND assetName=?";

    private static final String GET_ASSET_LIST = "SELECT assetName FROM ouAsset WHERE ouName=?";

    private static final String COUNT_ROWS = "SELECT COUNT(*) FROM ouAsset";

    private Connection connection;

    private PreparedStatement addOuAsset;

    private PreparedStatement getOuAssetList;

    private PreparedStatement getOuAsset;

    private PreparedStatement deleteOuAsset;

    private PreparedStatement getAssetList;

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
            getAssetList = connection.prepareStatement(GET_ASSET_LIST);
            editQty = connection.prepareStatement(UPDATE_QTY);
            rowCount = connection.prepareStatement(COUNT_ROWS);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Add Offer to the CurrentTrade table.
     * @param OUAsset The asset OU possess
     */
    public void addOuAsset(AssetPossession OUAsset) {
        try {

            addOuAsset.setString(1, OUAsset.getOu());
            addOuAsset.setString(2, OUAsset.getAsset());
            addOuAsset.setInt(3, OUAsset.getQuantity());

            // .... more setting for other columns.
            addOuAsset.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Edit the quantity of the specific row in the ouAsset table.

     */
    public void editQty(AssetPossession OUAsset) {
        try {
            editQty.setInt(1, OUAsset.getQuantity());
            editQty.setString(2, OUAsset.getOu());
            editQty.setString(3, OUAsset.getAsset());
            editQty.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }


    /**
     * Return the list of asset ou possess by retrieving information from database.
     */
    public List<AssetPossession> ouAssetList() {
        List<AssetPossession> ouAssetList = new LinkedList<>();
        ResultSet rs = null;
        try {
            rs = getOuAssetList.executeQuery();
            while (rs.next()) {
                ouAssetList.add(new AssetPossession(rs.getString(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        return ouAssetList;
    }

    /**
     * Return the specific Asset OU possess by retrieving information from database.
     */
    public AssetPossession getOuAsset(String ou, String asset) {
        AssetPossession OUAsset = new AssetPossession();
        ResultSet rs = null;

        try {
            getOuAsset.setString(1, ou);
            getOuAsset.setString(2, asset);
            rs = getOuAsset.executeQuery();
            if (rs.next()){
                OUAsset.setOu(rs.getString(1));
                OUAsset.setAsset(rs.getString(2));
                OUAsset.setQuantity((rs.getInt(3)));
            }

        } catch (SQLException ex) {
            return null;
        }

        return OUAsset;
    }

    /**
     * Return the list of Asset specific OU possess by retrieving information from database.
     */
    public Set<String> assetList(OU ou) {
        Set<String> assetList = new TreeSet<String>();
        ResultSet rs = null;
        try {
            getAssetList.setString(1, ou.getOuName());
            rs = getAssetList.executeQuery();
            while (rs.next()) {
                assetList.add(rs.getString("assetName"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return assetList;
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
     * Delete the offer from the currentTrade table.
     */
    public boolean deleteOUAsset(String ou, String asset) {

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
