package Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrganisationForm extends JFrame{
    private JList list1;
    private JTable table1;
    private JTable table2;
    private JButton cancelOrderButton;
    public JPanel organisationPanel;

    public OrganisationForm() {
        cancelOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                implement algorithm for canceling sell order
            }
        });
    }
}
