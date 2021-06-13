package Client;

import Common.AssetPossession;
import Common.OU;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;

public class EditOUAssetForm extends JFrame{
    private JButton confirmAssetAmountButton;
    private JTextField textField5;
    public JPanel editPanel;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JComboBox comboBox1;
    private JLabel label4;
    private JList list1;
    private OU selectedOU;
    private ServerConnector connector;
    private DefaultListModel listModel;

    /**
     * @param evt Display the asset
     */
    private void displayAsset (ListSelectionEvent evt){
        label1.setText(list1.getSelectedValue().toString());
        if (connector.getSingleOUAsset(new AssetPossession(selectedOU.getOuName(), list1.getSelectedValue().toString())).getQuantity() == null) {
            label2.setText("0");
        }
        else {
            label2.setText(connector.getSingleOUAsset(new AssetPossession(selectedOU.getOuName(), list1.getSelectedValue().toString())).getQuantity().toString());
        }
    }

    /**
     * Update assets information in the GUI
     */
    private void updateAssetInformation(){
        Set<String> AssetInfo = connector.getAsset();
        String[] AssetNames = AssetInfo.toArray(new String[0]);
        listModel.removeAllElements();
        for (int idx = 0; idx < AssetInfo.size(); idx++){
            listModel.addElement(AssetNames[idx]);
        }
        list1.setModel(listModel);
    }

    /**
     * @param evt Edit the asset quantity
     */
    private void editAssetQty (java.awt.event.ActionEvent evt) {
        try {
            if (connector.getSingleOUAsset(new AssetPossession(selectedOU.getOuName(), list1.getSelectedValue().toString())).getQuantity()== null) {
                int changeQuantityCount = Integer.parseInt(textField5.getText());
                connector.addOUAsset(new AssetPossession(selectedOU.getOuName(), list1.getSelectedValue().toString(), changeQuantityCount));
                label2.setText(String.valueOf(changeQuantityCount));
                JOptionPane.showMessageDialog(rootPane, "Quantity successfully added");
            }
            else if (connector.getSingleOUAsset(new AssetPossession(selectedOU.getOuName(), list1.getSelectedValue().toString())).getQuantity()== 0) {
                int changeQuantityCount = Integer.parseInt(textField5.getText());
                connector.editOUAsset(new AssetPossession(selectedOU.getOuName(), list1.getSelectedValue().toString(), changeQuantityCount));
                label2.setText(String.valueOf(changeQuantityCount));
                JOptionPane.showMessageDialog(rootPane, "Quantity successfully edited");
            }
            else {
                int changeQuantityCount = Integer.parseInt(textField5.getText());
                connector.editOUAsset(new AssetPossession(selectedOU.getOuName(), list1.getSelectedValue().toString(), changeQuantityCount));
                label2.setText(String.valueOf(changeQuantityCount));
                JOptionPane.showMessageDialog(rootPane, "Quantity successfully edited");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Asset hasn't been selected or Please enter the quantity in number format");
        }
    }

    /**
     * Set button listener to each button on the form
     * @param selectedOU
     * @param connector
     */
    public EditOUAssetForm(OU selectedOU, ServerConnector connector) {
        this.selectedOU = selectedOU;
        this.connector = connector;
        label4.setText(selectedOU.getOuName());
        updateAssetInformation();
        confirmAssetAmountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editAssetQty(e);
            }
        });

        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                displayAsset(e);
            }
        });
    }

    /**
     * Import data into the UI
     */
    private void createUIComponents() {

        listModel = new DefaultListModel();
        list1 = new JList(listModel);
        add(list1);


    }
}
