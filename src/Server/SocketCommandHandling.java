package Server;

import Common.CommandMethods;
import Common.Offer;
import Common.User;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Class for socket handling methods server-side
 */
public class SocketCommandHandling implements CommandMethods {

    // Properties for sending data across socket
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;

    /**
     * Constructor for the command handling, with input/ output streams initialised
     * @param input
     * @param output
     */
    public SocketCommandHandling(ObjectInputStream input,ObjectOutputStream output)
    {
        this.outputStream = output;
        this.inputStream = input;
    }

    /**
     * Currently a skeleton of the different socket methods server-side
     */
    public void doLogin()
    {

    }

    public void addOffer()
    {

    }

    public void removeOffer()
    {

    }
}