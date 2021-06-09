package Client;

import Common.Asset;
import Common.AssetPossession;
import Common.OU;
import Common.User;

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
    private JPasswordField passwordField4;
    private JButton confirmCreditAmountButton;
    private JButton userButton;
    private JButton adminButton;
    public JPanel adminPanel;
    private JButton confirmCreateAccountButton;
    private JButton confirmUpdatePasswordButton;

    private void addAsset(java.awt.event.ActionEvent evt) {
        ServerConnector serverConnection = new ServerConnector();
        String newAsset = textField1.getText();
        serverConnection.addAsset(new Asset(newAsset));
    }
    private void removeAsset(java.awt.event.ActionEvent evt) {
        ServerConnector serverConnection = new ServerConnector();
        Asset deleteAsset = new Asset(list1.getName());
        serverConnection.removeAsset(deleteAsset);
    }

    // NOT NEEDED?
    private void assignAdmin(java.awt.event.ActionEvent evt) {
        ServerConnector serverConnection = new ServerConnector();
        String username = textField4.getText();
        User changeUser = serverConnection.getSingleUser(username);
        if (changeUser.getType() == "admin")
        {
            JOptionPane.showMessageDialog(rootPane, "User is already an admin");
        }
        else if (changeUser.getType() == "user") {
            serverConnection.removeUser(changeUser);
            serverConnection.addUser(new User(changeUser.getUsername(),
                    changeUser.getPassword(),
                    changeUser.getOu(), "admin"));
            JOptionPane.showMessageDialog(rootPane, "User type has been changed");
        }
    }

    // NOT NEEDED?
    private void assignUser(java.awt.event.ActionEvent evt) {
        ServerConnector serverConnection = new ServerConnector();
        String username = textField4.getText();
        User changeUser = serverConnection.getSingleUser(username);
        if (changeUser.getType() == "user")
        {
            JOptionPane.showMessageDialog(rootPane, "User is already an user");
        }
        else if (changeUser.getType() == "admin" ) {
            serverConnection.removeUser(changeUser);
            serverConnection.addUser(new User(changeUser.getUsername(),
                    changeUser.getPassword(),
                    changeUser.getOu(), "user"));
            JOptionPane.showMessageDialog(rootPane, "User type has been changed");
        }

    }
    private void changeAssetAmount(java.awt.event.ActionEvent evt) {
        ServerConnector serverConnection = new ServerConnector();
        String OUName = comboBox1.getSelectedItem().toString();
        String selectedAsset = list2.getSelectedValue().toString();
        // Add condition that it must be int??
        int changeAssetCount = Integer.parseInt(textField5.getText());
        if (OUName != null && !OUName.equals(""))
        {
            serverConnection.editOUAsset(new AssetPossession(OUName, list2.getSelectedValue().toString(), changeAssetCount));
        }
    }
    private void changeCreditAmount(java.awt.event.ActionEvent evt) {
        ServerConnector serverConnection = new ServerConnector();
        //add command to change credit amount for logged in OU
        String OUName = comboBox1.getSelectedItem().toString();
        int changeCreditCount = Integer.parseInt(textField6.getText());
        if (OUName != null && !OUName.equals(""))
        {
            serverConnection.editOUCredit(new OU(OUName, changeCreditCount));
        }
    }
//    implementing create account functionality
    private void createAccount(java.awt.event.ActionEvent evt) {
        ServerConnector serverConnection = new ServerConnector();
        if (textField2 != null && !textField2.equals("")
        && passwordField2 != null && !passwordField2.equals("")
        && passwordField4 != null && !passwordField4.equals("")
        && passwordField2 == passwordField4)
        {
            User u = new User(textField2.getText(), passwordField2.getPassword(), ou.getText(), type.getText());
        }
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
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assignUser(e);
            }
        });
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                assignAdmin(e);
            }
        });
        confirmCreateAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount(e);
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
