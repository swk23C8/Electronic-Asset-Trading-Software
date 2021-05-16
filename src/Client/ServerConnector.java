package Client;
import Common.ClientCommands;
import Common.AdminCommands;
import Common.Offer;

import java.net.Socket;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

/** Class handling Client to Server Tasks**/
public class ServerConnector {

    /** Method that pushes info to server **/

    /** Possibly will merge ReadConfigClient here as a method or part of the connection **/
    private String HOSTIP;
    private int PORT;

    private HashSet<Offer> currentOffers = new HashSet<>();

    /** Constructor for class**/
    public ServerConnector() {
        /** Initialisation go here**/
        try {
            /** Get HostIP and Port for server from config here**/
        } catch (Exception e) {

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
                objectOutputStream.writeObject(ClientCommands.ADDOFFER);
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
                objectOutputStream.writeObject(ClientCommands.GETOFFERS);
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
