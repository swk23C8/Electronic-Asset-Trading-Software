package Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrderForm extends JFrame{
    private JButton BUYButton;
    private JButton SELLButton;
    private JTextField textField1;
    private JTextField textField2;
    private JList list1;
    public JPanel orderPanel;

    public OrderForm() {
        BUYButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServerConnector serverConnection = new ServerConnector();
                double totalCost = Double.parseDouble(textField1.getText()) * Double.parseDouble(textField2.getText());
//                implement algorithm to make buy order
//                get logged in user's OU
//                check if user's OU has enough credits to make buy order
//                check if the asset is available
            }
        });
        SELLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServerConnector serverConnection = new ServerConnector();
//                implement algorithm to make sell order
//                get logged in user's OU
//                check if OU has sufficient asset(s) user is attempting to sell
            }
        });
    }
}
