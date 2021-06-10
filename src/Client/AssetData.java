package Client;

import Common.Asset;

import javax.swing.*;

public class AssetData {
    DefaultListModel listModel;
    ServerConnector connector;

    public AssetData() {
        listModel = new DefaultListModel();
        connector = new ServerConnector();

        for (String name : connector.getAsset()) {
            listModel.addElement(name);
        }
    }

    public void add(Asset asset) {

        // check to see if the person is already in the book
        // if not add to the address book and the list model
        if (!listModel.contains(asset.getAsset())) {
            listModel.addElement(asset.getAsset());
            connector.addAsset(asset);
        }
    }

    public void remove(Object key) {

        // remove from both list and database
        listModel.removeElement(key);
        connector.removeAsset(new Asset((String) key));
    }

    public ListModel getModel() {
        return listModel;
    }

    public int getSize() {
        return connector.getAssetSize();
    }
}
