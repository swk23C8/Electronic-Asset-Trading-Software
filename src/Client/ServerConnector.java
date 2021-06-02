package Client;
import Common.*;

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

    private List<Offer> currentOffers = new LinkedList<>();
    private List<Asset> currentAssets = new LinkedList<>();
    private List<AssetPossession> currentOUAsset = new LinkedList<>();
    private HashMap<String,Integer> currentOUs = new HashMap<>();
    private Set<String> currentUsers = new TreeSet<>();



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
                this.HOSTIP = props.getProperty("jdbc.IP");
                this.PORT = Integer.parseInt(props.getProperty("jdbc.PORT"));
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
            socket = new Socket(HOSTIP, PORT);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
            inputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            // If the server connection fails, we're going to throw exceptions
            // whenever the application actually tries to query anything.
            // But it wasn't written to handle this, so make sure your
            // server is running beforehand!
            System.out.println("Failed to connect to server");
        }
    }

    //ADD INTERFACE!!!!!!

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

    public void addAsset(Asset newAsset)
    {
        if (newAsset == null)
        {
            throw new IllegalArgumentException("Offer cannot be null");
        }
        try{
            outputStream.writeObject(Command.ADD_OFFER);
            /** Change this to just send a list of strings, though possibly not so multiple offers
             * can be sent across easily in GetOffers
             */
            Asset createdAsset = new Asset("");
            outputStream.writeObject(createdAsset);
        } catch (IOException e)
        {
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

    public List<Asset> getAsset()
    {
        //Remember we need to flush between read and write
        try {
            outputStream.writeObject(Command.GET_ASSET);
            outputStream.flush();
            currentAssets = (List<Asset>) inputStream.readObject();
            return currentAssets;
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

    public void addOU(OU newOU)
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
            OU createdOU = new OU("",0);
            outputStream.writeObject(createdOU);
        } catch (IOException e) {
            e.printStackTrace();
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

    public void editOU(OU editedOU)
    {
        try {
            outputStream.writeObject(Command.EDIT_OU);
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
            User createdUser = new User("","");
            outputStream.writeObject(createdUser);
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

    public boolean login(User loginUser)
    {
        //Return true if successful, otherwise false
        try {
            outputStream.writeObject(Command.LOGIN);
            outputStream.writeObject(loginUser);
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
