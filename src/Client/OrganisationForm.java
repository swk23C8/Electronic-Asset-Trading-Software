package Client;

import Common.Offer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrganisationForm extends JFrame{
    private JList list1;
    private JTable table1;
    private JTable table2;
    private JButton cancelOrderButton;
    public JPanel organisationPanel;
    private ServerConnector connection;
    private Offer cancelorder;
    private Offer offer;


    /**
     * @param evt Function for canceling order button
     */
    private void cancelSellOrder(java.awt.event.ActionEvent evt) {
        try {
            connection = new ServerConnector();
            cancelorder = new Offer();
            // get offer from list / tables
            if (cancelorder.getOfferType().equals("buy"))
            {
                JOptionPane.showMessageDialog(rootPane, "Please select a sell order");
            }
            if (cancelorder.getOfferType().equals("sell"))
            {
                connection.removeOffer(cancelorder);
            }
        }
        catch (Exception ex)
        {
            JOptionPane.showMessageDialog(cancelOrderButton, "Order already canceled or does not exist?");
        }
    }


    public OrganisationForm() {
        cancelOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {cancelSellOrder(e);}

        });
    }
}
