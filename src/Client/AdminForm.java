package Client;

import Common.Asset;
import Common.AssetPossession;
import Common.OU;
import Common.User;
import Server.UserData;

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
    private JComboBox comboBox2;
    private JRadioButton userRadioButton;
    private JRadioButton adminRadioButton;
    private JPasswordField passwordField5;
    public String userType;
    private ButtonGroup userTypeButtonGroup;
    UserData data;
    private User user;
    private ServerConnector connector;

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
    private void getSelectedRadioButton(ActionEvent evt) {
        userType = userTypeButtonGroup.getSelection().getActionCommand();
        System.out.println(userType);
    }
    private void createAccount(java.awt.event.ActionEvent evt) {
        ServerConnector serverConnection = new ServerConnector();
        String selectedOU = (String) comboBox2.getSelectedItem();
        String finalPassword = String.valueOf(passwordField2.getPassword());

        if (textField2 != null && !textField2.equals("")
        && passwordField2 != null && !passwordField2.equals("")
        && passwordField4 != null && !passwordField4.equals("")
        && passwordField2 == passwordField4)
        {
            User u = new User(textField2.getText(), finalPassword, selectedOU, userType);
            data.add(u);
        }
    }
    private void updatePassword(java.awt.event.ActionEvent evt) {
        String username = String.valueOf(textField3.getText());
        String password = String.valueOf(passwordField5.getPassword());
        String newPassword = String.valueOf(passwordField2.getPassword());
        String confirmNewPassword = String.valueOf(passwordField3.getPassword());

        if (username.trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Please enter username");
        } else if (newPassword.trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Please enter current password");
        } else if (newPassword.trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Please enter new password");
        } else if (confirmNewPassword.trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Please enter the new password again");
        } else {
            User providedUser = new User(user.getUsername(), password);
            User existingUser = connector.login(providedUser);
            System.out.println("Begin");
            if (existingUser == null) {
                System.out.println("fail");
                JOptionPane.showMessageDialog(rootPane, "wrong current password");
            } else {
                if (newPassword.equals(confirmNewPassword)) {
                    System.out.println("success");
                    connector.changePassword(new User(user.getUsername(), newPassword));
                    JOptionPane.showMessageDialog(rootPane, "Password successfully changed");
                } else {
                    System.out.println("fail");
                    JOptionPane.showMessageDialog(rootPane, "New and confirm password are not same!");
                }
            }
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
        userRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getSelectedRadioButton(e);
            }
        });
        adminRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getSelectedRadioButton(e);
            }
        });
        confirmUpdatePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePassword(e);
            }
        });
    }
}
