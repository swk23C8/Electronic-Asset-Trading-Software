package Client;

import Common.AssetPossession;
import Common.OU;
import Common.Offer;
import Common.User;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class OrderForm extends JFrame{
    private JButton BUYButton;
    private JButton SELLButton;
    private JTextField textField1;
    private JTextField textField2;
    private JList list1;
    public JPanel orderPanel;
    private JLabel organisationNameLabel;
    private JLabel currentUserLabel;
    private JLabel currentCreditsLabel;
    private JTable table1;
    private JLabel label1;
    private ServerConnector serverConnection;
    private User existingUser;
    private DefaultListModel listModel;
    private DefaultTableModel tableModel;

    private void updateAssetInformation(){
        Set<String> AssetInfo = serverConnection.getAsset();
        String[] AssetNames = AssetInfo.toArray(new String[0]);
        listModel.removeAllElements();
        for (int idx = 0; idx < AssetInfo.size(); idx++){
            listModel.addElement(AssetNames[idx]);
        }
        list1.setModel(listModel);
    }

    private void displayAssetQuantity (ListSelectionEvent evt){
        if (serverConnection.getSingleOUAsset(new AssetPossession(existingUser.getOu(), list1.getSelectedValue().toString())).getQuantity() == null) {
            label1.setText("0");
        }
        else {
            label1.setText(serverConnection.getSingleOUAsset(new AssetPossession(existingUser.getOu(), list1.getSelectedValue().toString())).getQuantity().toString());
        }
    }

    private void updateOfferInformation() {
        List<Offer> OfferList = serverConnection.GetOffers();
        for (int idx = 0; idx < tableModel.getRowCount(); idx++) {
            tableModel.removeRow(idx);
        }
        Object[] row = new Object[7];
        String[] column = {"offerID", "offerType", "ouName", "assetName", "assetQty", "price", "date"};
        tableModel.setColumnIdentifiers(column);
        for (int idx = 0; idx < OfferList.size(); idx++) {
            row[0] = OfferList.get(idx).getId();
            row[1] = OfferList.get(idx).getOfferType();
            row[2] = OfferList.get(idx).getOUName();
            row[3] = OfferList.get(idx).getAssetName();
            row[4] = OfferList.get(idx).getQuantity();
            row[5] = OfferList.get(idx).getCreditsEach();
            row[6] = OfferList.get(idx).getDate();
            tableModel.addRow(row);
        }
    }

    private void buyButtonPerformed(ActionEvent evt) {
        try {
            String quantity = textField1.getText();
            String price = textField2.getText();
            if (quantity.trim().equals("") || price.trim().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Please enter quantity and price");
            }
            else if (serverConnection.getSingleOU(new OU(existingUser.getOu())).getCredits() < Integer.parseInt(price)) {
                JOptionPane.showMessageDialog(rootPane, "Invalid Price! Insufficient OU credit!");
            }
            else {
                serverConnection.AddOffer(new Offer("buy", existingUser.getOu(), list1.getSelectedValue().toString(), Integer.parseInt(quantity), Integer.parseInt(price)));
                JOptionPane.showMessageDialog(rootPane, "Buy offer successfully added");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Please enter in number format");
        }

    }

    private void sellButtonPerformed(ActionEvent evt) {
        try {
            String quantity = textField1.getText();
            String price = textField2.getText();
            if (quantity.trim().equals("") || price.trim().equals("")) {
                JOptionPane.showMessageDialog(rootPane, "Please enter quantity and price");
            }
            else if (serverConnection.getSingleOUAsset(new AssetPossession(existingUser.getOu(), list1.getSelectedValue().toString())).getQuantity() == null ||
                    serverConnection.getSingleOUAsset(new AssetPossession(existingUser.getOu(), list1.getSelectedValue().toString())).getQuantity() < Integer.parseInt(quantity)) {
                JOptionPane.showMessageDialog(rootPane, "Invalid quantity! Insufficient asset quantity!");
            }
            else {
                serverConnection.AddOffer(new Offer("sell", existingUser.getOu(), list1.getSelectedValue().toString(), Integer.parseInt(quantity), Integer.parseInt(price)));
                serverConnection.editOUAsset(new AssetPossession(existingUser.getOu(), list1.getSelectedValue().toString(),
                        serverConnection.getSingleOUAsset(new AssetPossession(existingUser.getOu(), list1.getSelectedValue().toString())).getQuantity() - Integer.parseInt(quantity)));
                JOptionPane.showMessageDialog(rootPane, "Sell offer successfully added");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Please enter in number format");
        }

    }




    public OrderForm(User existingUser, ServerConnector serverConnection) {
        BUYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyButtonPerformed(e);
            }
        });
        SELLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sellButtonPerformed(e);
            }
        });
        this.existingUser = existingUser;
        this.serverConnection = serverConnection;
        organisationNameLabel.setText(existingUser.getOu());
        currentUserLabel.setText(existingUser.getUsername());
        currentCreditsLabel.setText(serverConnection.getSingleOU(new OU(existingUser.getOu())).getCredits().toString());

        updateAssetInformation();
        updateOfferInformation();

        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                displayAssetQuantity(e);
            }
        });
    }
    private void createUIComponents() {
        listModel = new DefaultListModel();
        list1 = new JList(listModel);
        add(list1);
        tableModel = new DefaultTableModel(0, 7);
        table1 = new JTable(tableModel) {
            public boolean isCellEditable(int data, int columns) {
                return false;
            }
        };
        add(table1);
    }
}
