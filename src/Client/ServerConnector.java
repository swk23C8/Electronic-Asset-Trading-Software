package Client;
import Common.SocketMessages;
import Common.Offer;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Properties;

/** Class handling Client to Server Tasks**/
public class ServerConnector {

    /** Method that pushes info to server **/

    /** Possibly will merge ReadConfigClient here as a method or part of the connection **/
    private String HOSTIP;
    private int PORT;

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
    }

    /**
     * Add offer client command setup
     */
    private void AddOffer() {

        try {
            Socket socket = new Socket(HOSTIP, PORT);

            try (
                    ObjectOutputStream objectOutputStream =
                            new ObjectOutputStream(socket.getOutputStream());
            ) {
                objectOutputStream.writeObject(SocketMessages.ADD_OFFER);
                /** Change this to just send a list of strings, though possibly not so multiple offers
                 * can be sent across easily in GetOffers
                 */
                Offer createdOffer = new Offer(0,"","","", 0,0);
                objectOutputStream.writeObject(createdOffer);
            }
        } catch (IOException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
        }
    }

    /**
     * Example setup, minus GUI and much functionality, for getting offers for display
     */
    private void GetOffers() {
        try {
            Socket socket = new Socket(HOSTIP, PORT);

            try (
                    ObjectOutputStream objectOutputStream =
                            new ObjectOutputStream(socket.getOutputStream());
            ) {
                objectOutputStream.writeObject(SocketMessages.GET_OFFERS);
                objectOutputStream.flush();

                try (
                        ObjectInputStream objectInputStream =
                                new ObjectInputStream(socket.getInputStream());
                ) {
                    currentOffers = (HashSet<Offer>) objectInputStream.readObject();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            // Print the exception, but no need for a fatal error
            // if the connection with the server happens to be down
            e.printStackTrace();
        }
    }



}
