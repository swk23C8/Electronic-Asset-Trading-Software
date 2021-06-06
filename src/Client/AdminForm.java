package Client;

import Common.OU;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminForm extends JFrame{
    private JComboBox comboBox1;
    private JTextField textField1;
    private JButton ADDASSETButton;
    private JButton REMOVEASSETButton;
    private JList list1;
    private JTextField textField2;
    private JPasswordField passwordField1;
    private JTextField textField3;
    private JPasswordField passwordField2;
    private JPasswordField passwordField3;
    private JTextField textField4;
    private JList list2;
    private JButton confirmAssetAmountButton;
    private JTextField textField5;
    private JTextField textField6;
    private JButton USERButton;
    private JButton ADMINButton;
    private JPasswordField passwordField4;
    private JButton confirmCreditAmountButton;
    public JPanel adminPanel;

    private void addAsset(java.awt.event.ActionEvent evt) {
        ServerConnector serverConnection = new ServerConnector();
//        add command to add asset
    }
    private void removeAsset(java.awt.event.ActionEvent evt) {
        ServerConnector serverConnection = new ServerConnector();
//        add command to remove asset
    }
    private void assignAdmin(java.awt.event.ActionEvent evt) {
        ServerConnector serverConnection = new ServerConnector();
//        add command to assign user type ADMIN to user
    }
    private void assignUser(java.awt.event.ActionEvent evt) {
        ServerConnector serverConnection = new ServerConnector();
//        add command to assign user type USER to user
    }
    private void changeAssetAmount(java.awt.event.ActionEvent evt) {
        ServerConnector serverConnection = new ServerConnector();
//        add command to change asset amount for logged in OU

    }
    private void changeCreditAmount(java.awt.event.ActionEvent evt) {
        ServerConnector serverConnection = new ServerConnector();
//        add command to change credit amount for logged in OU
//        if (name.getText() != null && !name.getText().equals("")) {
//            OU o = new OU(name.getText(), Integer.parseInt(credit.getText()));
//            data.edit(o);
//        }
    }
    public AdminForm() {
        ADDASSETButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAsset(e);
            }
        });
        REMOVEASSETButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeAsset(e);

            }
        });
        USERButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assignUser(e);
            }
        });
        ADMINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assignAdmin(e);
            }
        });
        confirmAssetAmountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeAssetAmount(e);
            }
        });
        confirmCreditAmountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCreditAmount(e);
            }
        });
    }
}
