package Server;

import Common.*;

import javax.swing.*;
import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;


/** Class with methods relating to server and client interaction
 * will likely need other classes to help implement this purpose
 */
public class ServerManagement {

    /** Provide user classes, loaded information, this information
     * is already read from the database
     */

    /** Need to send all buy/ sell offers, and update when offers reconcile**/


    /** Value for port, etc. stored here though retrieved in a method**/
    private int PORT;
    private static final int SOCKET_ACCEPT_TIMEOUT = 100;
    private AtomicBoolean running = new AtomicBoolean(true);
    private static final int SOCKET_READ_TIMEOUT = 5000;
    private AssetDataSource assetDatabase;
    private OUAssetDataSource OUAssetDatabase;
    private OfferDataSource offerDatabase;
    private OUDataSource OUDatabase;
    private UserDataSource userDatabase;

    /** Global variables (i.e. HashSets/ Maps) of relevant data are stored here **/

    /**
     * Returns the port the server will use via a configuration file
     *
     * @return The port number
     */
    public int getPort() {
        return PORT;
    }

    public ServerManagement()
    {
        try{
            start();
        }
        catch (IOException e){
            System.out.println("Failed to connect to server");
        }
    }
    /**
     *  Method from W8 to start server
     */
    public void start() throws IOException {
        sql_startup();
        Properties props = new Properties();
        FileInputStream in = null;
        try {
            /** Format taken from W7 Prac, change to own config file just to store
             * url, username and password info for database
             */
            in = new FileInputStream("./serverconfig.props");
            props.load(in);
            in.close();

            // This needs to be
            try {
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
        //Every 2 hours call reconcile to reconcile database
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> reconcile(), 0, 2, TimeUnit.HOURS);
        try (ServerSocket serverSocket = new ServerSocket(6666)) {
            serverSocket.setSoTimeout(SOCKET_ACCEPT_TIMEOUT);
            for (;;) {
                if (!running.get()) {
                    // The server is no longer running
                    break;
                }

                try {
                    final Socket socket = serverSocket.accept();

                    socket.setSoTimeout(SOCKET_READ_TIMEOUT);

                    // We have a new connection from a client. Use a runnable and thread to handle
                    // the client. The lambda here wraps the functional interface (Runnable).
                    final Thread clientThread = new Thread(() -> handleConnection(socket));
                    clientThread.start();
                } catch (SocketTimeoutException ignored) {
                    // Do nothing. A timeout is normal- we just want the socket to
                    // occasionally timeout so we can check if the server is still running
                } catch (Exception e) {
                    // We will report other exceptions by printing the stack trace, but we
                    // will not shut down the server. A exception can happen due to a
                    // client malfunction (or malicious client)
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            // If we get an error starting up, show an error dialog then exit
            e.printStackTrace();
            System.exit(1);
        }

        // Close down the server
        System.exit(0);
    }

    /**
     * Method on initialising the server that calls appropriate sql related methods
     */
    private void sql_startup() {
        assetDatabase = new AssetDataSource();
        offerDatabase = new OfferDataSource();
        OUDatabase = new OUDataSource();
        userDatabase = new UserDataSource();
        OUAssetDatabase = new OUAssetDataSource();
    }

    /**
     * Handles the connection received from ServerSocket, from W8
     * @param socket The socket used to communicate with the currently connected client
     */
    private void handleConnection(Socket socket) {
        try {
            final ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            final ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            while (true)
            {
                try {
                    /**
                     * Read the command, this tells us what to send the client back.
                     * If the client has disconnected, this will throw an exception.
                     */
                    final Command command = (Command) inputStream.readObject();
                    handleCommand(socket, inputStream, outputStream, command);
                } catch (SocketTimeoutException e) {
                    /**
                     * We catch SocketTimeoutExceptions, because that just means the client hasn't sent
                     * any new requests. We don't want to disconnect them otherwise. Another way to
                     * check if they're "still there would be with ping/pong commands.
                     */
                    continue;
                }
            }
        }
        catch (IOException | ClassCastException | ClassNotFoundException e)
        {
            System.out.println(String.format("Connection %s closed", socket.toString()));
        }
    }

    private void handleCommand(Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream,
                               Command command) throws IOException, ClassNotFoundException
    {
        //if the datasource classes are used, remember to do synchronized (instance) {} when using this
        switch (command)
        {
            case ADD_OFFER:{
                //Do stuff here
                final Offer newOffer = (Offer) inputStream.readObject();
                synchronized (offerDatabase)
                {
                    offerDatabase.addOffer(newOffer);
                }
                //flush if write is done (after write), so no stalling occurs
                //Print to gui for server String.format("Added person '%s' to database from client %s",
                //p.getName(), socket.toString())
            }
            break;
            case REMOVE_OFFER:{
                final Offer removeOffer = (Offer) inputStream.readObject();
                boolean isSuccessful;
                synchronized (offerDatabase)
                {
                    isSuccessful = offerDatabase.deleteOffer(removeOffer.getId());
                }
                if (isSuccessful == true)
                {
                    outputStream.writeObject(Command.SUCCESS);
                }
                else
                {
                    outputStream.writeObject(Command.FAIL);
                }
            }
            case GET_OFFERS: {
                final List<Offer> currentOffers;
                synchronized (offerDatabase) {
                    currentOffers = offerDatabase.offerSet();
                }
                outputStream.writeObject(currentOffers);
                outputStream.flush();

                // Print to Server GUI "Information"
            }
            break;
            case EDIT_OFFER:{
                final Offer editOffer = (Offer) inputStream.readObject();
                synchronized (offerDatabase)
                {
                    offerDatabase.editQty(editOffer);
                }
            }
            break;
            case ADD_ASSET: {
                final Asset newAsset = (Asset) inputStream.readObject();
                synchronized (assetDatabase)
                {
                    assetDatabase.addAsset(newAsset);
                }
                // Would we have the same done with the offer case?
            }
            break;
            case REMOVE_ASSET:{
                final Asset removedAsset = (Asset) inputStream.readObject();
                boolean isSuccessful;
                synchronized (assetDatabase)
                {
                    isSuccessful = assetDatabase.deleteAsset(removedAsset.getAsset());
                }
                if (isSuccessful == true)
                {
                    outputStream.writeObject(Command.SUCCESS);
                }
                else
                {
                    outputStream.writeObject(Command.FAIL);
                }
            }
            break;
            case GET_ASSET:{
                final List<Asset> currentAssets;
                synchronized (assetDatabase)
                {
                    currentAssets = assetDatabase.AssetSet();
                }
                outputStream.writeObject(currentAssets);
                outputStream.flush();
            }
            break;
            case ADD_OU_ASSET:{
                final AssetPossession newOUAsset = (AssetPossession) inputStream.readObject();
                synchronized (OUAssetDatabase)
                {
                    OUAssetDatabase.addOuAsset(newOUAsset);
                }
            }
            break;
            case GET_OU_ASSET:{
                final List<AssetPossession> currentOUAsset;
                synchronized (OUAssetDatabase)
                {
                    currentOUAsset = OUAssetDatabase.offerSet();
                }
                outputStream.writeObject(currentOUAsset);
                outputStream.flush();
            }
            break;
            case DELETE_OU_ASSET:{
                final AssetPossession removedOUAsset = (AssetPossession) inputStream.readObject();
                boolean IsSuccessful;
                synchronized (OUAssetDatabase)
                {
                    IsSuccessful = OUAssetDatabase.deleteOffer((removedOUAsset.getOu()), removedOUAsset.getAsset());
                }
                if (IsSuccessful == true)
                {
                    outputStream.writeObject(Command.SUCCESS);
                }
                else
                {
                    outputStream.writeObject(Command.FAIL);
                }
            }
            break;
            case EDIT_OU_ASSET:{
                final AssetPossession editedOUAsset = (AssetPossession) inputStream.readObject();
                synchronized (OUAssetDatabase)
                {
                    OUAssetDatabase.editQty(editedOUAsset);
                }
            }
            case ADD_OU:{
                final OU newOU = (OU) inputStream.readObject();
                synchronized (OUDatabase)
                {
                    OUDatabase.addOU(newOU);
                }
            }
            break;

            case GET_OU:{
                final HashMap<String,Integer> OUList;
                synchronized (OUDatabase)
                {
                    OUList = OUDatabase.ouList();
                }
                outputStream.writeObject(OUList);
                outputStream.flush();
            }
            break;
            case EDIT_OU:{
                final OU chosenOU = (OU) inputStream.readObject();
                synchronized (OUDatabase)
                {
                    OUDatabase.editCredit(chosenOU);
                }
            }
            break;
            case REMOVE_OU:{
                final OU removeOU = (OU) inputStream.readObject();
                boolean isSuccessful;
                synchronized (OUDatabase)
                {
                    isSuccessful = OUDatabase.deleteOU(removeOU.getOuName());
                }
                if (isSuccessful == true)
                {
                    outputStream.writeObject(Command.SUCCESS);
                }
                else
                {
                    outputStream.writeObject(Command.FAIL);
                }
            }
            break;

            case ADD_USER:{
                final User newUser = (User) inputStream.readObject();
                synchronized (userDatabase)
                {
                    userDatabase.addUser(newUser);
                }

            }
            break;

            case GET_USER:{
                final Set<String> currentUsers;
                synchronized (userDatabase)
                {
                    currentUsers = userDatabase.userSet();
                }
                outputStream.writeObject(currentUsers);
                outputStream.flush();
            }
            break;
            case REMOVE_USER:{
                final User removedUser = (User) inputStream.readObject();
                boolean isSuccessful;
                synchronized (userDatabase)
                {
                    isSuccessful = userDatabase.deleteUser(removedUser.getUsername());
                }
                if (isSuccessful == true)
                {
                    outputStream.writeObject(Command.SUCCESS);
                }
                else
                {
                    outputStream.writeObject(Command.FAIL);
                }
            }
            break;
            case LOGIN:
            {
                final User loginInformation = (User) inputStream.readObject();
                synchronized (userDatabase)
                {
                    final User confirmationInformation = userDatabase.getUser(loginInformation.getUsername());
                    System.out.println("prior check");

                    if (confirmationInformation != null && confirmationInformation.getPassword() ==
                            loginInformation.getPassword())
                    {
                        System.out.println("isright");
                        outputStream.writeObject(confirmationInformation);
                    }
                    else
                    {
                        System.out.println("null");
                        outputStream.writeObject(null);
                    }
                    //State if login successful on server gui, for user with name...
                }

            }
            break;
            case CHANGE_PASSWORD:
            {
                final User changeUser = (User) inputStream.readObject();
                synchronized (userDatabase)
                {
                    userDatabase.changePassword(changeUser);
                }
                //Output to server successful change in password for user "username"
            }
            break;

        }
    }

    /**
     * Requests the server to shut down
     */
    public void shutdown() {
        // Shut the server down
        running.set(false);
    }

    //Function for reconciling
    private static void reconcile()
    {
        OfferDataSource offerDatabase = new OfferDataSource();
        List<Offer> listOfOffers = offerDatabase.offerSet();

        OUDataSource OUDatabase = new OUDataSource();
        OUAssetDataSource OUAssetDatabase = new OUAssetDataSource();

        // Comparing a specific offer in the list with other offers in the list.
        for (int originOffer = 0; originOffer < listOfOffers.size(); originOffer++) {
            for (int comparedOffer = 0; comparedOffer < listOfOffers.size(); comparedOffer++){
                // if the offer is comparing with itself, skip the rest and continue
                if (originOffer == comparedOffer) {
                    continue;

                }

                // Store the instances of the offers into variables
                Integer id1 = listOfOffers.get(originOffer).getId();
                Integer id2 = listOfOffers.get(comparedOffer).getId();
                String type1 = listOfOffers.get(originOffer).getOfferType();
                String type2 = listOfOffers.get(comparedOffer).getOfferType();
                String ou1 = listOfOffers.get(originOffer).getOUName();
                String ou2 = listOfOffers.get(comparedOffer).getOUName();
                String asset1 = listOfOffers.get(originOffer).getAssetName();
                String asset2 = listOfOffers.get(comparedOffer).getAssetName();
                Integer qty1 = listOfOffers.get(originOffer).getQuantity();
                Integer qty2 = listOfOffers.get(comparedOffer).getQuantity();
                Integer price1 = listOfOffers.get(originOffer).getCreditsEach();
                Integer price2 = listOfOffers.get(comparedOffer).getCreditsEach();

                // if the origin offer is comparing with the offer comes after in the list && type and ou of the both offer are not same &&
                // asset of both offer is same
                if ((originOffer < comparedOffer) && (!type1.equals(type2)) && (!ou1.equals(ou2))
                        && (asset1.equals(asset2))) {

                    // if the origin offer is the buying offer && quantity of the buying offer is less or equal to selling offer but the price is
                    // more or equal to the selling offer
                    if (type1.equals("buy") && qty1 <= qty2 && price1 >= price2) {

                        // The effect on OU's credit
                        OU buyer = OUDatabase.getOU(ou1);
                        OU seller = OUDatabase.getOU(ou2);
                        OUDatabase.editCredit(new OU(ou1, buyer.getCredits() - (price2 * qty1)));
                        OUDatabase.editCredit(new OU(ou2, seller.getCredits() + (price2 * qty1)));

                        // The effect on asset qty OU possessing
                        if (OUAssetDatabase.getOuAsset(ou1, asset1) == null) {
                            OUAssetDatabase.addOuAsset(new AssetPossession(ou1, asset1, qty1));
                            AssetPossession s = OUAssetDatabase.getOuAsset(ou2, asset2);
                            OUAssetDatabase.editQty(new AssetPossession(ou2, asset2, s.getQuantity() - qty1));
                        }
                        else {
                            AssetPossession b = OUAssetDatabase.getOuAsset(ou1, asset1);
                            AssetPossession s = OUAssetDatabase.getOuAsset(ou2, asset2);
                            OUAssetDatabase.editQty(new AssetPossession(ou1, asset1, b.getQuantity() + qty1));
                            OUAssetDatabase.editQty(new AssetPossession(ou2, asset2, s.getQuantity() - qty1));
                        }

                        // The update on currentTrade
                        if (qty1.equals(qty2)) {
                            offerDatabase.deleteOffer(id1);
                            offerDatabase.addHistory(listOfOffers.get(originOffer));
                            offerDatabase.deleteOffer(id2);
                            offerDatabase.addHistory(listOfOffers.get(comparedOffer));
                            Offer tmp1 = listOfOffers.get(originOffer);
                            listOfOffers.remove(originOffer);
                            listOfOffers.add(0, tmp1);
                            Offer tmp2 = listOfOffers.get(comparedOffer);
                            listOfOffers.remove(comparedOffer);
                            listOfOffers.add(1, tmp2);
                        }
                        else {
                            listOfOffers.get(comparedOffer).setQuantity(qty2 - qty1);
                            offerDatabase.editQty(listOfOffers.get(comparedOffer));
                            offerDatabase.deleteOffer(id1);
                            offerDatabase.addHistory(listOfOffers.get(originOffer));
                            Offer tmp1 = listOfOffers.get(originOffer);
                            listOfOffers.remove(originOffer);
                            listOfOffers.add(0, tmp1);
                        }

                        break;

                    }

                    // if the origin offer is the selling offer && quantity of the buying offer is less or equal to selling offer but the price is
                    // more or equal to the selling offer
                    else if (type2.equals("buy") && qty2 <= qty1 && price2 >= price1) {

                        // The effect on OU's credit
                        OU buyer = OUDatabase.getOU(ou2);
                        OU seller = OUDatabase.getOU(ou1);
                        OUDatabase.editCredit(new OU(ou2, buyer.getCredits() - (price1 * qty2)));
                        OUDatabase.editCredit(new OU(ou1, seller.getCredits() + (price1 * qty2)));

                        // The effect on asset qty OU possessing
                        if (OUAssetDatabase.getOuAsset(ou2, asset2) == null) {
                            OUAssetDatabase.addOuAsset(new AssetPossession(ou2, asset2, qty2));
                            AssetPossession s = OUAssetDatabase.getOuAsset(ou1, asset1);
                            OUAssetDatabase.editQty(new AssetPossession(ou1, asset1, s.getQuantity() - qty2));
                        }
                        else {
                            AssetPossession b = OUAssetDatabase.getOuAsset(ou2, asset2);
                            AssetPossession s = OUAssetDatabase.getOuAsset(ou1, asset1);
                            OUAssetDatabase.editQty(new AssetPossession(ou2, asset2, b.getQuantity() + qty2));
                            OUAssetDatabase.editQty(new AssetPossession(ou1, asset1, s.getQuantity() - qty2));
                        }

                        // The update on the currentTrade
                        if (qty1.equals(qty2)) {
                            offerDatabase.deleteOffer(id1);
                            offerDatabase.addHistory(listOfOffers.get(originOffer));
                            offerDatabase.deleteOffer(id2);
                            offerDatabase.addHistory(listOfOffers.get(comparedOffer));
                            Offer tmp1 = listOfOffers.get(originOffer);
                            listOfOffers.remove(originOffer);
                            listOfOffers.add(0, tmp1);
                            Offer tmp2 = listOfOffers.get(comparedOffer);
                            listOfOffers.remove(comparedOffer);
                            listOfOffers.add(1, tmp2);
                        }
                        else {
                            listOfOffers.get(originOffer).setQuantity(qty1 - qty2);
                            offerDatabase.editQty(listOfOffers.get(originOffer));
                            offerDatabase.deleteOffer(id2);
                            offerDatabase.addHistory(listOfOffers.get(comparedOffer));
                            Offer tmp1 = listOfOffers.get(comparedOffer);
                            listOfOffers.remove(comparedOffer);
                            listOfOffers.add(0, tmp1);
                        }
//
                        break;
                    }

                }

            }


        }
        offerDatabase.close();
        //System.out.println(list.get(7).getOfferType());
    }


    //Delete this test method at a later point of time
    public static void main(String[] args) {

        //testing the password verifying function
        UserDataSource userDatabase = new UserDataSource();
        String password = "abc123";
        System.out.println(userDatabase.getUser("n10559540").getPassword().
                equals(userDatabase.passwordCheck(password, userDatabase.getUser("n10559540"))));


        //testing reconcile method
        reconcile();
    }
}
