package Common;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class DBConnection {
    private Connection con;
    private Statement st;
    private PreparedStatement pst;
    private ResultSet rs;

    public DBConnection() {
        try{
            Scanner configfile = new Scanner(new File("dbconfig.txt"));
            ArrayList<String> configlist = new ArrayList<String>();
            while (configfile.hasNextLine())
            {
                configlist.add(configfile.nextLine());
            }
            configfile.close();
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(configlist.get(0), configlist.get(1), configlist.get(2));
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
