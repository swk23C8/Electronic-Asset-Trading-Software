package Server;

import Common.Asset;
import Common.Offer;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class AssetDataSource {
    private static final String INSERT_ASSET = "INSERT INTO asset (assetName) VALUES (?);";

    private static final String COUNT_ROWS = "SELECT COUNT(*) FROM asset";

    private static final String LIST_ASSETS = "SELECT * FROM asset";

    private static final String GET_ASSET = "SELECT * FROM asset WHERE assetName=?";

    private static final String DELETE_ASSET = "DELETE FROM asset WHERE assetName=?";


    private Connection connection;

    private PreparedStatement addAsset;

    private PreparedStatement getAssetList;

    private PreparedStatement getAsset;

    private PreparedStatement deleteAsset;

    private PreparedStatement rowCount;


    /**
     * Constructor for the configuration of the database connection.
     */
    public AssetDataSource() {
        connection = DBConnection.getInstance();
        try {

            /* BEGIN MISSING CODE */
            addAsset = connection.prepareStatement(INSERT_ASSET);
            getAssetList = connection.prepareStatement(LIST_ASSETS);
            getAsset = connection.prepareStatement(GET_ASSET);
            deleteAsset = connection.prepareStatement(DELETE_ASSET);
            rowCount = connection.prepareStatement(COUNT_ROWS);
            /* END MISSING CODE */
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Add Asset to the asset table.
     * @param a The Asset object
     */
    public void addAsset(Asset a) {
        try {
            /* BEGIN MISSING CODE */
            addAsset.setString(1, a.getAsset());
            addAsset.execute();
            /* END MISSING CODE */
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Return the set of asset by retrieving information from database.
     */
    public Set<String> nameSet() {
        Set<String> assetNames = new TreeSet<String>();
        ResultSet rs = null;

        /* BEGIN MISSING CODE */
        try {
            rs = getAssetList.executeQuery();
            while (rs.next()) {
                assetNames.add(rs.getString("assetName"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */

        return assetNames;
    }

    /**
     * Return a list of Asset from the information from the database
     */
    public List<Asset> AssetSet() {
        List<Asset> assetList = new LinkedList<>();
        ResultSet rs = null;

        /* BEGIN MISSING CODE */
        try {
            rs = getAssetList.executeQuery();
            while (rs.next()) {
                assetList.add(new Asset(rs.getString(1)));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */

        return assetList;
    }

    /**
     * Return the specific asset by retrieving information from database.
     * @param name The name of the offer.
     */
    public Asset getAsset(String name) {
        Asset a = new Asset();
        ResultSet rs = null;
        /* BEGIN MISSING CODE */
        try {
            getAsset.setString(1, name);
            rs = getAsset.executeQuery();
            rs.next();
            a.setAsset(rs.getString("assetName"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */
        return a;
    }

    /**
     * Return the size of the asset table.
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
     * Delete the Asset from the asset table.
     */
    public boolean deleteAsset(String name) {
        /* BEGIN MISSING CODE */
        try {
            deleteAsset.setString(1, name);
            deleteAsset.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
        /* END MISSING CODE */
    }

    /**
     * Closing the database..
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


