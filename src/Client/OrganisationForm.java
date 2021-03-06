package Client;

import Common.AssetPossession;
import Common.OU;
import Common.Offer;
import Common.User;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


public class OrganisationForm extends JFrame{
    private DefaultListModel defaultList1;
    private JList list1;
    private JTable table1;
    private JTable table2;
    private JButton cancelOrderButton;
    public JPanel organisationPanel;
    private JLabel label1;
    private JLabel label2;
    private Offer cancelOrder;
    private ServerConnector serverConnection;
    private User user;
    private DefaultTableModel currentTable;
    private DefaultTableModel historyTable;


    /**
     * Update the Asset in list, asset has to relate to the chosen OU
     */
    private void updateAssetInformation(){
        List<AssetPossession> OUAsset = serverConnection.getOUAsset();
        defaultList1.removeAllElements();
        for (int idx = 0; idx < OUAsset.size(); idx++)
        {
            if (OUAsset.get(idx).getOu().equals(user.getOu()))
            {
                defaultList1.addElement(OUAsset.get(idx).getAsset() + "     Quantity:"+OUAsset.get(idx).getQuantity());
            }
        }
        list1.setModel(defaultList1);
    }

    /**
     * Update the offer in table, offers should also relate to the OU
     */
    private void updateOfferInformation() {
        List<Offer> OfferList = serverConnection.GetOffers();
        for (int idx = 0; idx < currentTable.getRowCount(); idx++) {
            currentTable.removeRow(idx);
        }
        Object[] row = new Object[7];
        String[] column = {"offerID", "offerType", "ouName", "assetName", "assetQty", "price", "date"};
        currentTable.setColumnIdentifiers(column);
        for (int idx = 0; idx < OfferList.size(); idx++) {
            if (OfferList.get(idx).getOUName().equals(user.getOu())) {
                row[0] = OfferList.get(idx).getId();
                row[1] = OfferList.get(idx).getOfferType();
                row[2] = OfferList.get(idx).getOUName();
                row[3] = OfferList.get(idx).getAssetName();
                row[4] = OfferList.get(idx).getQuantity();
                row[5] = OfferList.get(idx).getCreditsEach();
                row[6] = OfferList.get(idx).getDate();
                currentTable.addRow(row);
            }
        }
    }

    /**
     * Update the offers history table, must also relate with the chosen  OU
     */
    private void updateHistory() {
        List<Offer> OfferList = serverConnection.getHistory();
        for (int idx = 0; idx < historyTable.getRowCount(); idx++) {
            historyTable.removeRow(idx);
        }
        Object[] row = new Object[7];
        String[] column = {"offerID", "offerType", "ouName", "assetName", "assetQty", "price", "date"};
        historyTable.setColumnIdentifiers(column);
        for (int idx = 0; idx < OfferList.size(); idx++) {
            if (OfferList.get(idx).getOUName().equals(user.getOu())) {
                row[0] = OfferList.get(idx).getId();
                row[1] = OfferList.get(idx).getOfferType();
                row[2] = OfferList.get(idx).getOUName();
                row[3] = OfferList.get(idx).getAssetName();
                row[4] = OfferList.get(idx).getQuantity();
                row[5] = OfferList.get(idx).getCreditsEach();
                row[6] = OfferList.get(idx).getDate();
                historyTable.addRow(row);
            }
        }
    }

    /**
     * @param evt Function for canceling order button
     */
    private void cancelOrder(ActionEvent evt) {
        try {
            int selectedRow = table1.getSelectedRow();
            int selectedColumn = 0;
            int offerID = (int)table1.getValueAt(selectedRow,selectedColumn);

            cancelOrder = new Offer(offerID);
            if (cancelOrder.getId().equals("") || cancelOrder.getId().equals(null))
            {
                JOptionPane.showMessageDialog(rootPane, "Please select an order");
            }
            else
            {
                if (serverConnection.removeOffer(cancelOrder)) {
                    updateOfferInformation();
                    JOptionPane.showMessageDialog(rootPane, "Offer cancelled successfully!");
                }
                else {
                    JOptionPane.showMessageDialog(rootPane, "Error canceling with server");
                }
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(rootPane, "Offer hasn't been selected or Order already canceled or does not exist?");
        }
    }


    /**
     * @param user
     * @param serverConnection
     * Set up the form
     */
    public OrganisationForm(User user, ServerConnector serverConnection ) {
        cancelOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelOrder(e);
            }
        });
        this.serverConnection = serverConnection;
        this.user = user;
        label1.setText(user.getOu());
        label2.setText(user.getUsername());
        updateAssetInformation();
        updateOfferInformation();
        updateHistory();
    }

    /**
     * Import database data into GUI
     */
    private void createUIComponents() {
        defaultList1 = new DefaultListModel();
        list1 = new JList(defaultList1);
        add(list1);
        currentTable = new DefaultTableModel(0, 7);
        table1 = new JTable(currentTable) {
            public boolean isCellEditable(int data, int columns) {
                return false;
            }
        };
        add(table1);
        historyTable = new DefaultTableModel(0, 7);
        table2 = new JTable(historyTable) {
            public boolean isCellEditable(int data, int columns) {
                return false;
            }
        };
        add(table2);
    }
}