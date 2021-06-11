package Client;

import Common.OU;

import javax.swing.*;

public class OUAssetData {
    private DefaultListModel listModel;
    private ServerConnector connector;
    private OU selectedOU;

    public OUAssetData(OU selectedOU) {
        this.selectedOU = selectedOU;
        listModel = new DefaultListModel();
        connector = new ServerConnector();

        for (String name : connector.getOUAssetList(selectedOU)) {
            listModel.addElement(name);
        }
    }

    public DefaultListModel getModel() {
        return listModel;
    }
}
