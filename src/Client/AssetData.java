package Client;

import Common.Asset;

import javax.swing.*;

public class AssetData {
    private DefaultListModel listModel;
    private ServerConnector serverConnection;

    public AssetData(ServerConnector serverConnection) {
        listModel = new DefaultListModel();
        this.serverConnection = serverConnection;

        for (String name : serverConnection.getAsset()) {
            listModel.addElement(name);
        }
    }

    public void add(Asset asset) {

        // check to see if the person is already in the book
        // if not add to the address book and the list model
        if (!listModel.contains(asset.getAsset())) {
            listModel.addElement(asset.getAsset());
            serverConnection.addAsset(asset);
        }
    }

    public void remove(Object key) {

        // remove from both list and database
        listModel.removeElement(key);
        serverConnection.removeAsset(new Asset((String) key));
    }

    public ListModel getModel() {
        return listModel;
    }

    public int getSize() {
        return serverConnection.getAssetSize();
    }
}
