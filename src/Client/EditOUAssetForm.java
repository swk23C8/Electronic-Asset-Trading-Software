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
    private OUAssetData ouAssetData;
    private ServerConnector connector;
    private AssetData allAssetData;

    /**
     * @param evt Display the asset
     */
    private void displayAsset (ListSelectionEvent evt){
        label1.setText(list1.getSelectedValue().toString());
        if (connector.getSingleOUAsset(new AssetPossession(selectedOU.getOuName(), list1.getSelectedValue().toString())).getQuantity() == null) {
            label2.setText("null");
        }
        else {
            label2.setText(connector.getSingleOUAsset(new AssetPossession(selectedOU.getOuName(), list1.getSelectedValue().toString())).getQuantity().toString());
        }

//        label2.setText(connector.getSingleOUAsset(new AssetPossession(selectedOU.getOuName(), list1.getSelectedValue().toString())).getQuantity().toString());
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
                JOptionPane.showMessageDialog(rootPane, "Quantity successfully added");            }
            else {
                int changeQuantityCount = Integer.parseInt(textField5.getText());
                connector.editOUAsset(new AssetPossession(selectedOU.getOuName(), list1.getSelectedValue().toString(), changeQuantityCount));
                label2.setText(String.valueOf(changeQuantityCount));
                JOptionPane.showMessageDialog(rootPane, "Quantity successfully edited");
            }
//            int changeQuantityCount = Integer.parseInt(textField5.getText());
//            connector.editOUAsset(new AssetPossession(selectedOU.getOuName(), list1.getSelectedValue().toString(), changeQuantityCount));
//            label2.setText(String.valueOf(changeQuantityCount));
//            JOptionPane.showMessageDialog(rootPane, "Quantity successfully edited");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Please enter the quantity");
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
        allAssetData = new AssetData(connector);
        ouAssetData = new OUAssetData(selectedOU);
//        list1 = new JList(ouAssetData.getModel());
//        add(list1);
        list1 = new JList(allAssetData.getModel());
        add(list1);


    }
}
