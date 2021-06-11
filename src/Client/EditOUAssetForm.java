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

    private void displayAsset (ListSelectionEvent evt){
        label1.setText(list1.getSelectedValue().toString());
        label2.setText(connector.getSingleOUAsset(new AssetPossession(selectedOU.getOuName(), list1.getSelectedValue().toString())).getQuantity().toString());
    }

    private void editAssetQty (java.awt.event.ActionEvent evt) {
        try {
            int changeQuantityCount = Integer.parseInt(textField5.getText());
            connector.editOUAsset(new AssetPossession(selectedOU.getOuName(), list1.getSelectedValue().toString(), changeQuantityCount));
            label2.setText(String.valueOf(changeQuantityCount));
            JOptionPane.showMessageDialog(rootPane, "Quantity successfully edited");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Please enter the quantity");
        }
    }

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

    private void createUIComponents() {
        ouAssetData = new OUAssetData(selectedOU);
        list1 = new JList(ouAssetData.getModel());
        add(list1);


    }
}
