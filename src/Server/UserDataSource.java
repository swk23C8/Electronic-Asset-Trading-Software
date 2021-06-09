package Server;


import Common.User;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.*;

public class UserDataSource {
    private static final String INSERT_USER = "INSERT INTO user (username, password, saltValue, ouName, accountType) VALUES (?, ?, ?, ?, ?);";

    private static final String UPDATE_PASSWORD = "UPDATE user SET password=? WHERE username=?";

    private static final String UPDATE_SALT_VALUE = "UPDATE user SET saltValue=? WHERE username=?";

    private static final String COUNT_ROWS = "SELECT COUNT(*) FROM user";

    private static final String LIST_USERS = "SELECT * FROM user";

    private static final String GET_USER = "SELECT * FROM user WHERE username=?";

    private static final String DELETE_USER = "DELETE FROM user WHERE username=?";

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS user ("
                    + "username VARCHAR(45) PRIMARY KEY NOT NULL UNIQUE,"
                    + "password VARCHAR(100),"
                    + "saltValue VARCHAR(100),"
                    + "ouName VARCHAR(45),"
                    + "accountType VARCHAR(45));";

    private Connection connection;

    private PreparedStatement addUser;

    private PreparedStatement changePassword;

    private PreparedStatement changeSaltValue;

    private PreparedStatement getUserList;

    private PreparedStatement getUser;

    private PreparedStatement deleteUser;

    private PreparedStatement rowCount;

    /**
     * Constructor for the configuration of the database connection.
     */
    public UserDataSource() {
        connection = DBConnection.getInstance();
        try {
            /* BEGIN MISSING CODE */
            addUser = connection.prepareStatement(INSERT_USER);
            changePassword = connection.prepareStatement(UPDATE_PASSWORD);
            changeSaltValue = connection.prepareStatement(UPDATE_SALT_VALUE);
            getUserList = connection.prepareStatement(LIST_USERS);
            getUser = connection.prepareStatement(GET_USER);
            deleteUser = connection.prepareStatement(DELETE_USER);
            rowCount = connection.prepareStatement(COUNT_ROWS);
            /* END MISSING CODE */
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Hashing the User password with the salt string.
     * @param User The User object
     */
    public String saltPassword(User User) {
        String raw = User.getPassword();
        SecureRandom random = null;
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        String salt = new String(Base64.getEncoder().encode(bytes));
        User.setSaltValue(salt);
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(salt.getBytes());
        md.update(raw.getBytes());
        String hex = String.format("%064x", new BigInteger(1, md.digest()));
        return hex;
    }

    /**
     * Verifying the user input password
     * @param  uncheckedPassword The user input password
     * @param User The User object
     */
    public String passwordCheck(String uncheckedPassword, User User) {
        String salt = User.getSaltValue();
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(salt.getBytes());
        md.update(uncheckedPassword.getBytes());
        String hex = String.format("%064x", new BigInteger(1, md.digest()));
        return hex;
    }

    /**
     * Add User to the user table.
     * @param User The User object
     */
    public void addUser(User User) {
        try {
            /* BEGIN MISSING CODE */
            addUser.setString(1, User.getUsername());
            addUser.setString(2, saltPassword(User));
            addUser.setString(3, User.getSaltValue());
            addUser.setString(4, User.getOu());
            addUser.setString(5, User.getType());
            addUser.execute();
            /* END MISSING CODE */
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void changePassword(User user) {

        try {
            changePassword.setString(1, saltPassword(user));
            changePassword.setString(2, user.getUsername());
            changeSaltValue.setString(1, user.getSaltValue());
            changeSaltValue.setString(2, user.getUsername());
            changePassword.executeUpdate();
            changeSaltValue.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Return the set of user by retrieving information from database.
     */
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

    /**
     * Return the specific user by retrieving information from database.
     * @param name The name of the user.
     */
    public User getUser(String name) {
        User User = new User();
        ResultSet rs = null;
        /* BEGIN MISSING CODE */
        try {
            getUser.setString(1, name);
            rs = getUser.executeQuery();
            if (rs.next()){
                User.setUsername(rs.getString("username"));
                User.setPassword(rs.getString("password"));
                User.setSaltValue(rs.getString("saltValue"));
                User.setOu(rs.getString("ouName"));
                User.setType(rs.getString("accountType"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */
        return User;
    }

    /**
     * Return the size of the user table.
     */
    public int getSize() {
        ResultSet rs = null;
        int rowNumber = 0;

        /* BEGIN MISSING CODE */
        try {
            rs = rowCount.executeQuery();
            if (rs.next())
            {
                rowNumber = rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        /* END MISSING CODE */

        return rowNumber;
    }

    /**
     * Delete the User from the user table.
     */
    public boolean deleteUser(String name) {
        /* BEGIN MISSING CODE */
        try {
            deleteUser.setString(1, name);
            deleteUser.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
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
