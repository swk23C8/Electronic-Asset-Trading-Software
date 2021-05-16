package Common;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Interface for the commands' methods so the server and client
 * can implement a class from this with the same methods for the same commands
 * but with their relevant implementation of the method
 */
public interface CommandMethods {



    /**
     * Method for logging a user in/ authenticating their details are correct
     */
    void doLogin();

    void addOffer();

    void removeOffer();
}
