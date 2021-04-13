package Common;

import java.sql.*;

public class DBConnection {
    private Connection con;
    private Statement st;
    private PreparedStatement pst;
    private ResultSet rs;

    public DBConnection() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://database-1.c4eceq88oul1.us-east-2.rds.amazonaws.com/CAB302","admin", "12345678");
            st = con.createStatement();

        }
        catch (Exception e){
            System.out.println("Database connection error" + e.getMessage());
        }
    }

    public void insertAsset(String asset) {
        String sql = "INSERT INTO ASSET VALUE (?)";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, asset);
            pst.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAsset(String asset) {
        String sql = "DELETE FROM ASSET WHERE assetName = ?";
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, asset);
            pst.execute();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }


}
