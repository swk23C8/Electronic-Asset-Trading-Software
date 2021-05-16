package DBtestUI;

import DBtestUI.UserUI;
import Server.UserData;

import javax.swing.*;

public class UserDBtest {
    /**
     * Create the GUI.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        new UserUI(new UserData());
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
