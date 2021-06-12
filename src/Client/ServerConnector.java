package Client;
import Common.*;
import Server.DBConnection;

import java.io.*;
import java.net.Socket;
import java.util.*;

/** Class handling Client to Server Tasks**/
public class ServerConnector {

    /** Method that pushes info to server **/

    /** Possibly will merge ReadConfigClient here as a method/ part of the connection **/
    private String HOSTIP;
    private int PORT;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean isConnected = false;

    private List<Offer> currentOffers = new LinkedList<>();
    private List<AssetPossession> currentOUAsset = new LinkedList<>();
    private HashMap<String,Integer> currentOUs = new HashMap<>();
    private Set<String> currentUsers = new TreeSet<>();
    private Set<String> currentAssets = new TreeSet<>();
    private Set<String> OUAssetList = new TreeSet<>();

    /** Constructor for class**/
    public ServerConnector() {
        Properties props = new Properties();
        FileInputStream in = null;
        try {
            in = new FileInputStream("./clientconfig.props");
            props.load(in);
            in.close();

            // This needs to be
            try {
                this.HOSTIP = props.getProperty("jdbc.ip");
                this.PORT = Integer.parseInt(props.getProperty("jdbc.port"));
            }
            catch (NumberFormatException e)
            {
                /** Insert Catch handler**/
            }

        }  catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            // Persist a single connection through the whole lifetime of the application.
            // We will re-use this same connection/socket, rather than repeatedly opening
            // and closing connections.
            isConnected = true;
            socket = new Socket(HOSTIP, PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            // If the server connection fails, we're going to throw exceptions
            // whenever the application actually tries to query anything.
            // But it wasn't written to handle this, so make sure your
            // server is running beforehand!
            System.out.println("Failed to connect to server");
            isConnected = false;
        }
    }

    public boolean getIfConnected(){
        return isConnected;
    }

    /**
     * Add offer client command setup
     */
    private void AddOffer(Offer newOffer) {
        if (newOffer == null)
        {
            throw new IllegalArgumentException("Offer cannot be null");
        }
        try{
            outputStream.writeObject(Command.ADD_OFFER);
            /** Change this to just send a list of strings, though possibly not so multiple offers
             * can be sent across easily in GetOffers
             */
            Offer createdOffer = new Offer(0,"","","", 0,0);
            outputStream.writeObject(createdOffer);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Example setup, minus GUI and much functionality, for getting offers for display
     * This needs to be entirely changed to use the socket command handling class, i.e.
     * will be removed
     */
    public List<Offer> GetOffers() {
        //Remember we need to flush between read and write
        try {
            outputStream.writeObject(Command.GET_OFFERS);
            outputStream.flush();
            currentOffers = (LinkedList<Offer>) inputStream.readObject();
            return currentOffers;
        } catch (IOException | ClassNotFoundException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
            return null;
        }
    }

    public boolean removeOffer(Offer removedOffer)
    {
        //Return true if successful, otherwise false
        try {
            outputStream.writeObject(Command.REMOVE_OFFER);
            outputStream.writeObject(removedOffer);
            outputStream.flush();
            final Command command = (Command) inputStream.readObject();
            if (command == Command.SUCCESS)
            {
                //GUI Authentication
                return true;
            }
            else
            {
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
            return false;
        }
    }

    public void editOffer(Offer editedOffer)
    {
        try {
            outputStream.writeObject(Command.EDIT_OFFER);
            outputStream.writeObject(editedOffer);
            outputStream.flush();
        } catch (IOException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
        }
    }

    public void addAsset(Asset newAsset)
    {
        try {
            outputStream.writeObject(Command.ADD_ASSET);
            outputStream.writeObject(newAsset);
            outputStream.flush();
        } catch (IOException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();

        }
    }

    public boolean removeAsset(Asset removedAsset)
    {
        //Return true if successful, otherwise false
        try {
            outputStream.writeObject(Command.REMOVE_ASSET);
            outputStream.writeObject(removedAsset);
            outputStream.flush();
            final Command command = (Command) inputStream.readObject();
            if (command == Command.SUCCESS)
            {
                //GUI Authentication
                return true;
            }
            else
            {
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
            return false;
        }
    }

    public Set<String> getAsset()
    {
        //Remember we need to flush between read and write
        try {
            outputStream.writeObject(Command.GET_ASSET);
            outputStream.flush();
            currentAssets = (Set<String>) inputStream.readObject();
            return currentAssets;
        } catch (IOException | ClassNotFoundException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
            return null;
        }
    }

    public Integer getAssetSize() {
        try {
            outputStream.writeObject(Command.GET_ASSET_SIZE);
            outputStream.flush();
            final Integer assetSize = (Integer) inputStream.readObject();
            return assetSize;
        } catch (IOException | ClassNotFoundException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
            return null;
        }
    }

    public void addOUAsset(AssetPossession newOUAsset)
    {
        if (newOUAsset == null)
        {
            throw new IllegalArgumentException("OU Asset cannot be null");
        }
        try{
            outputStream.writeObject(Command.ADD_OU_ASSET);
            /** Change this to just send a list of strings, though possibly not so multiple offers
             * can be sent across easily in GetOffers
             */
            AssetPossession createdOUAsset = new AssetPossession("","",0);
            outputStream.writeObject(createdOUAsset);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean removeOUAsset(AssetPossession removedOUAsset)
    {
        try {
            outputStream.writeObject(Command.DELETE_OU_ASSET);
            outputStream.writeObject(removedOUAsset);
            outputStream.flush();
            final Command command = (Command) inputStream.readObject();
            if (command == Command.SUCCESS)
            {
                //GUI Authentication
                return true;
            }
            else
            {
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
            return false;
        }
    }

    public List<AssetPossession> getOUAsset()
    {
        //Remember we need to flush between read and write
        try {
            outputStream.writeObject(Command.GET_OU_ASSET);
            outputStream.flush();
            currentOUAsset = (List<AssetPossession>) inputStream.readObject();
            return currentOUAsset;
        } catch (IOException | ClassNotFoundException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
            return null;
        }
    }

    public Set<String> getOUAssetList(OU singleOU)
    {
        try{
            outputStream.writeObject(Command.GET_OU_ASSET_LIST);
            outputStream.writeObject(singleOU);
            outputStream.flush();
            OUAssetList = (Set<String>) inputStream.readObject();
            return OUAssetList;
        }catch (IOException | ClassNotFoundException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
            return null;
        }

    }

    public AssetPossession getSingleOUAsset(AssetPossession selectedOUAsset)
    {
        try{
            outputStream.writeObject(Command.GET_SINGLE_OU_ASSET);
            outputStream.writeObject(selectedOUAsset);
            outputStream.flush();
            final AssetPossession singleOUAsset = (AssetPossession) inputStream.readObject();
            return singleOUAsset;
        }catch (IOException | ClassNotFoundException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
            return null;
        }

    }
    public void editOUAsset(AssetPossession editedOUAsset)
    {
        try {
                outputStream.writeObject(Command.EDIT_OU_ASSET);
                outputStream.writeObject(editedOUAsset);
                outputStream.flush();
            } catch (IOException e) {
                // Print the exception, but no need for a fatal error
                // if the connection with the server happens to be down
                e.printStackTrace();
            }
    }


    public boolean addOU(OU newOU)
    {
        if (newOU == null)
        {
            throw new IllegalArgumentException("OU Asset cannot be null");
        }
        try{
            outputStream.writeObject(Command.ADD_OU);
            /** Change this to just send a list of strings, though possibly not so multiple offers
             * can be sent across easily in GetOffers
             */
            OU createdOU = new OU(newOU.getOuName(),0);
            outputStream.writeObject(createdOU);
            outputStream.flush();
            final Command command = (Command) inputStream.readObject();
            if (command == Command.SUCCESS)
            {
                return true;
            }
            else
            {
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public HashMap<String,Integer> getOU()
    {
        try {
            outputStream.writeObject(Command.GET_OU);
            outputStream.flush();
            currentOUs = (HashMap<String,Integer>) inputStream.readObject();
            return currentOUs;
        } catch (IOException | ClassNotFoundException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
            return null;
        }
    }

    public OU getSingleOU(OU singleOU)
    {
        try{
            outputStream.writeObject(Command.GET_SINGLE_OU);
            outputStream.writeObject(singleOU);
            outputStream.flush();
            final OU ou = (OU) inputStream.readObject();
            return ou;
        }catch (IOException | ClassNotFoundException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
            return null;
        }

    }

    public void editOUCredit(OU editedOU)
    {
        try {
            outputStream.writeObject(Command.EDIT_OU_CREDIT);
            outputStream.writeObject(editedOU);
            outputStream.flush();
        } catch (IOException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
        }
    }

    public boolean removeOU(OU removedOU)
    {
        try {
            outputStream.writeObject(Command.REMOVE_OU);
            outputStream.writeObject(removedOU);
            outputStream.flush();
            final Command command = (Command) inputStream.readObject();
            if (command == Command.SUCCESS)
            {
                //GUI Authentication
                return true;
            }
            else
            {
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
            return false;
        }
    }


    public void addUser(User newUser)
    {
        if (newUser == null)
        {
            throw new IllegalArgumentException("User cannot be null");
        }
        try{
            outputStream.writeObject(Command.ADD_USER);
            /** Change this to just send a list of strings, though possibly not so multiple offers
             * can be sent across easily in GetOffers
             */
            outputStream.writeObject(newUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<String> getUser()
    {
        try {
            outputStream.writeObject(Command.GET_USER);
            outputStream.flush();
            currentUsers = (Set<String>) inputStream.readObject();
            return currentUsers;
        } catch (IOException | ClassNotFoundException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
            return null;
        }
    }

    public boolean removeUser(User removedUser)
    {
        try {
            outputStream.writeObject(Command.REMOVE_USER);
            outputStream.writeObject(removedUser);
            outputStream.flush();
            final Command command = (Command) inputStream.readObject();
            if (command == Command.SUCCESS)
            {
                //GUI Authentication
                return true;
            }
            else
            {
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
            return false;
        }
    }

    public User getSingleUser(String userName)
    {
        try{
            outputStream.writeObject(Command.GET_SINGLE_USER);
            outputStream.writeObject(userName);
            outputStream.flush();
            final User user = (User) inputStream.readObject();
            return user;
        }catch (IOException | ClassNotFoundException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
            return null;
        }

    }

    public User login(User loginUser)
    {
        //Return user if they exist, otherwise null
        try {
            outputStream.writeObject(Command.LOGIN);
            outputStream.writeObject(loginUser);
            outputStream.flush();
            final User existingUser = (User) inputStream.readObject();
            return existingUser;
        } catch (IOException | ClassNotFoundException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
            return null;
        }
    }


    public String checkPassword(User user) {
        try {
            outputStream.writeObject(Command.PASSWORD_CHECK);
            outputStream.writeObject(user);
            outputStream.flush();
            final String password = (String) inputStream.readObject();
            return password;
        } catch (IOException | ClassNotFoundException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
            return null;
        }
    }

    public void changePassword(User changedUser)
    {
        try {
            outputStream.writeObject(Command.CHANGE_PASSWORD);
            outputStream.writeObject(changedUser);
            outputStream.flush();
        } catch (IOException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();

        }
    }
}
