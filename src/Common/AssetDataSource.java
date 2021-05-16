package Common;

import java.sql.*;
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


    public void deletePerson(String name) {
        /* BEGIN MISSING CODE */
        try {
            deleteAsset.setString(1, name);
            deleteAsset.executeUpdate();
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


