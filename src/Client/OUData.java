package Client;


import Common.OU;

import javax.swing.*;

public class OUData {
    private DefaultComboBoxModel comboBoxModel;
    private ServerConnector serverConnection;

    public OUData(ServerConnector serverConnection) {
        comboBoxModel = new DefaultComboBoxModel();
        this.serverConnection = serverConnection;

        for (String name : serverConnection.getOU().keySet()) {
            comboBoxModel.addElement(name);
        }

    }

    public void add(OU ou) {
        boolean exist = false;
        for (int i = 0; i < comboBoxModel.getSize(); i++) {
            if (ou.getOuName().equals(comboBoxModel.getElementAt(i))) {
                exist = true;
            }
        }
        if (exist == false) {
            comboBoxModel.addElement(ou.getOuName());
            serverConnection.addOU(ou);
        }

    }

    public void remove(Object key) {

        // remove from both list and database
        comboBoxModel.removeElement(key);
        serverConnection.removeOU(new OU((String) key));
    }

    public ComboBoxModel getModel() {
        return comboBoxModel;
    }
}
