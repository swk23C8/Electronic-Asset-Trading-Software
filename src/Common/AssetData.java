package Common;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;


public class AssetData {

    DefaultListModel listModel;

    /* BEGIN MISSING CODE */
    AssetDataSource assetData;
    /* END MISSING CODE */


    public AssetData() {
        listModel = new DefaultListModel();
        /* BEGIN MISSING CODE */
        assetData = new AssetDataSource();
        /* END MISSING CODE */

        // add the retrieved data to the list model
        for (String name : assetData.nameSet()) {
            listModel.addElement(name);
        }
    }


    public void add(Asset a) {

        // check to see if the person is already in the book
        // if not add to the address book and the list model
        if (!listModel.contains(a.getAsset())) {
            listModel.addElement(a.getAsset());
            assetData.addAsset(a);
        }
    }


    public void remove(Object key) {

        // remove from both list and map
        listModel.removeElement(key);
        assetData.deletePerson((String) key);
    }


    public void persist() {
        assetData.close();
    }


    public Asset get(Object key) {
        return assetData.getAsset((String) key);
    }


    public ListModel getModel() {
        return listModel;
    }


    public int getSize() {
        return assetData.getSize();
    }
}
