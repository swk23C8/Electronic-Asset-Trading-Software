package Server;


import Common.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class UserDataSource {
    private static final String INSERT_USER = "INSERT INTO user (username, password, ouName, accountType) VALUES (?, ?, ?, ?);";

    private static final String COUNT_ROWS = "SELECT COUNT(*) FROM user";

    private static final String LIST_USERS = "SELECT * FROM user";

    private static final String GET_USER = "SELECT * FROM user WHERE username=?";

    private static final String DELETE_USER = "DELETE FROM user WHERE username=?";



    private Connection connection;

    private PreparedStatement addUser;

    private PreparedStatement getUserList;

    private PreparedStatement getUser;

    private PreparedStatement deleteUser;

    private PreparedStatement rowCount;

    public UserDataSource() {
        connection = DBConnection.getInstance();
        try {

            /* BEGIN MISSING CODE */
            addUser = connection.prepareStatement(INSERT_USER);
            getUserList = connection.prepareStatement(LIST_USERS);
            getUser = connection.prepareStatement(GET_USER);
            deleteUser = connection.prepareStatement(DELETE_USER);
            rowCount = connection.prepareStatement(COUNT_ROWS);
            /* END MISSING CODE */
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public void addUser(User u) {
        try {
            /* BEGIN MISSING CODE */
            addUser.setString(1, u.getUsername());
            addUser.setString(2, u.getPassword());
            addUser.setString(3, u.getOu());
            addUser.setString(4, u.getType());
            addUser.execute();
            /* END MISSING CODE */
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    public Set<String> userSet() {
        Set<String> userList = new TreeSet<String>();
        ResultSet rs = null;

        /* BEGIN MISSING CODE */
        try {
            rs = getUserList.executeQuery();
            while (rs.next()) {
                userList.add(rs.getString("username"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */

        return userList;
    }


    public User getUser(String name) {
        User u = new User();
        ResultSet rs = null;
        /* BEGIN MISSING CODE */
        try {
            getUser.setString(1, name);
            rs = getUser.executeQuery();
            rs.next();
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setOu(rs.getString("ouName"));
            u.setType(rs.getString("accountType"));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */
        return u;
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


    public void deleteUser(String name) {
        /* BEGIN MISSING CODE */
        try {
            deleteUser.setString(1, name);
            deleteUser.executeUpdate();
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
