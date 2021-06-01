package Server;

import Common.CommandMethods;
import Common.SocketMessages;
import Common.Offer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Properties;
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
    private static final int SOCKET_TIMEOUT = 100;
    private AtomicBoolean running = new AtomicBoolean(true);

    /** Global variables (i.e. HashSets/ Maps) of relevant data are stored here **/

    /**
     * Returns the port the server will use via a configuration file
     *
     * @return The port number
     */
    public int getPort() {
        return PORT;
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
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            serverSocket.setSoTimeout(SOCKET_TIMEOUT);
            for (;;) {
                if (!running.get()) {
                    // The server is no longer running
                    break;
                }
                try {
                    Socket socket = serverSocket.accept();
                    handleConnection(socket);
                } catch (SocketTimeoutException ignored) {
                    // Do nothing. A timeout is normal- we just want the socket to
                    // occasionally timeout so we can check if the server is still running
                } catch (IOException | ClassNotFoundException e) {
                    // We will report an IOException by printing the stack trace, but we
                    // will not shut down the server. An IOException can happen due to a
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
     * Method on initilising the server that calls appropriate sql related methods
     */
    private void sql_startup() {


    }

    /**
     * Handles the connection received from ServerSocket, from W8
     * @param socket The socket used to communicate with the currently connected client
     */
    private void handleConnection(Socket socket) throws IOException, ClassNotFoundException {
        try (
                ObjectInputStream objectInputStream =
                        new ObjectInputStream(socket.getInputStream())
        ) {
            String command = (String) objectInputStream.readObject();
            /** Implement command handling here, possibly add methods or new class due to number
             */
            try (
                    ObjectOutputStream objectOutputStream =
                            new ObjectOutputStream(socket.getOutputStream());
            ) {
                SocketCommandHandling socketHandler = new SocketCommandHandling(objectInputStream, objectOutputStream);

                if (command.equals(SocketMessages.ADD_OFFER)) {
                    socketHandler.addOffer();
                    Offer newOffer = (Offer) objectInputStream.readObject();
                    /** Do relevant actions here with info, or call method/ class that uses this**/
                }
            }
        }
    }

    /**
     * Command handling for server
     */




    public void Reconcile()
    {
        OfferDataSource o = new OfferDataSource();
        List<Offer> list = o.offerSet();
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++){
                if (i == j) {
                    continue;
                }
                Integer id1 = list.get(i).getId();
                Integer id2 = list.get(j).getId();
                String type1 = list.get(i).getOfferType();
                String type2 = list.get(j).getOfferType();
                String ou1 = list.get(i).getOUName();
                String ou2 = list.get(j).getOUName();
                String asset1 = list.get(i).getAssetName();
                String asset2 = list.get(j).getAssetName();
                Integer qty1 = list.get(i).getQuantity();
                Integer qty2 = list.get(j).getQuantity();
                Integer price1 = list.get(i).getCreditsEach();
                Integer price2 = list.get(j).getCreditsEach();

                if ((i < j) && (!type1.equals(type2))
                        && (!ou1.equals(ou2))
                        && (asset1.equals(asset2))
                        && (qty1.equals(qty2)) && (price1.equals(price2))) {
                    o.deleteOffer(id1);
                    o.deleteOffer(id2);
                    Offer tmp1 = list.get(i);
                    Offer tmp2 = list.get(j);
                    list.remove(i);
                    list.add(0, tmp1);
                    list.remove(j);
                    list.add(1, tmp2);
                    break;
                }
            }
        }

        System.out.println(list.get(7).getOfferType());

//        Hashset<OU> ous = ...;

//        OU has creditbalance, quantity of each asset/ type of asset as ke;
//        HashMap<AssetName, Int> ouassets = ;
//        int credit = 0;
//        Hashset<Offer> alltrades;
//        Offer
    }


    /**
     * Requests the server to shut down
     */
    public void shutdown() {
        // Shut the server down
        running.set(false);
    }


}
