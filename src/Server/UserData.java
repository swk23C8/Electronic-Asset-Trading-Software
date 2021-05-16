package Server;

import Common.User;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;


public class UserData {

    DefaultListModel listModel;

    /* BEGIN MISSING CODE */
    UserDataSource userData;
    /* END MISSING CODE */


    public UserData() {
        listModel = new DefaultListModel();
        /* BEGIN MISSING CODE */
        userData = new UserDataSource();
        /* END MISSING CODE */

        // add the retrieved data to the list model
        for (String name : userData.userSet()) {
            listModel.addElement(name);
        }
    }


    public void add(User u) {

        // check to see if the person is already in the book
        // if not add to the address book and the list model
        if (!listModel.contains(u.getUsername())) {
            listModel.addElement(u.getUsername());
            userData.addUser(u);
        }
    }


    public void remove(Object key) {

        // remove from both list and map
        listModel.removeElement(key);
        userData.deleteUser((String) key);
    }


    public void persist() {
        userData.close();
    }


    public User get(Object key) {
        return userData.getUser((String) key);
    }


    public ListModel getModel() {
        return listModel;
    }


    public int getSize() {
        return userData.getSize();
    }
}