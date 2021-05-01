package Common;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;


public class OUData {

    DefaultListModel listModel;

    /* BEGIN MISSING CODE */
    OUDataSource ouData;
    /* END MISSING CODE */


    public OUData() {
        listModel = new DefaultListModel();
        /* BEGIN MISSING CODE */
        ouData = new OUDataSource();
        /* END MISSING CODE */

        // add the retrieved data to the list model
        for (String name : ouData.ouList().keySet()) {
            listModel.addElement(name);
        }
    }


    public void add(OU o) {

        // check to see if the person is already in the book
        // if not add to the address book and the list model
        if (!listModel.contains(o.getOuName())) {
            listModel.addElement(o.getOuName());
            ouData.addOU(o);
        }
    }

    public void edit(OU o) {
        if (listModel.contains(o.getOuName())) {
            ouData.editCredit(o);
        }
    }


    public void remove(Object key) {

        // remove from both list and map
        listModel.removeElement(key);
        ouData.deletePerson((String) key);
    }


    public void persist() {
        ouData.close();
    }


    public OU get(Object key) {
        return ouData.getOU((String) key);
    }


    public ListModel getModel() {
        return listModel;
    }


    public int getSize() {
        return ouData.getSize();
    }
}