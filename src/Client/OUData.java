package Client;


import Common.Asset;
import Common.OU;

import javax.swing.*;

public class OUData {
    private DefaultComboBoxModel comboBoxModel;
    private ServerConnector connector;

    public OUData() {
        comboBoxModel = new DefaultComboBoxModel();
        connector = new ServerConnector();

        for (String name : connector.getOU().keySet()) {
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
            connector.addOU(ou);
        }

    }

    public void remove(Object key) {

        // remove from both list and database
        comboBoxModel.removeElement(key);
        connector.removeOU(new OU((String) key));
    }

    public ComboBoxModel getModel() {
        return comboBoxModel;
    }
}
