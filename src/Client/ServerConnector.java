package Client;
import Common.Command;
import Common.Offer;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Properties;

/** Class handling Client to Server Tasks**/
public class ServerConnector {

    /** Method that pushes info to server **/

    /** Possibly will merge ReadConfigClient here as a method/ part of the connection **/
    private String HOSTIP;
    private int PORT;
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private HashSet<Offer> currentOffers = new HashSet<>();

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
    private void GetOffers() {
        try {
            outputStream.writeObject(Command.GET_OFFERS);
            outputStream.flush();
            currentOffers = (HashSet<Offer>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
        }
    }

    public void removeOffer()
    {

    }

}
