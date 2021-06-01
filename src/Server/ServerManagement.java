package Server;

import Common.Command;
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
    private static final int SOCKET_ACCEPT_TIMEOUT = 100;
    private AtomicBoolean running = new AtomicBoolean(true);
    private static final int SOCKET_READ_TIMEOUT = 5000;

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
     * Method on initilising the server that calls appropriate sql related methods
     */
    private void sql_startup() {


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
                final Offer newOffer = new Offer();
                outputStream.writeObject(newOffer);
            }
            break;
            case GET_OFFERS:{
                //Do stuff here
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

    private void reconcile()
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
    }
}
