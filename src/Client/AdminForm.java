package Client;

import Common.Asset;
import Common.AssetPossession;
import Common.OU;
import Common.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

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
    public String userType;
    private ButtonGroup userTypeButtonGroup;
    private User existingUser;
    private ServerConnector serverConnection;

    private void addAsset(java.awt.event.ActionEvent evt) {
        String newAsset = textField1.getText();
        serverConnection.addAsset(new Asset(newAsset));
    }
    private void removeAsset(java.awt.event.ActionEvent evt) {
        Asset deleteAsset = new Asset(list1.getName());
        serverConnection.removeAsset(deleteAsset);
    }

    // NOT NEEDED?
    private void assignAdmin(java.awt.event.ActionEvent evt) {
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
        String selectedOU = (String) comboBox2.getSelectedItem();
        String finalPassword = String.valueOf(passwordField2.getPassword());

        if (textField2 != null && !textField2.equals("")
        && passwordField2 != null && !passwordField2.equals("")
        && passwordField4 != null && !passwordField4.equals("")
        && passwordField2 == passwordField4)
        {
            User newUser = new User(textField2.getText(), finalPassword, selectedOU, userType);
            serverConnection.addUser(newUser);
        }
    }

    public AdminForm(User existingUser, ServerConnector serverConnection) {
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
        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

            }
        });
        this.existingUser = existingUser;
        this.serverConnection = serverConnection;
        HashMap<String,Integer> OUInfo = serverConnection.getOU();
        String[] OUNames = OUInfo.keySet().toArray(new String[0]);
        for (int idx = 0; idx < OUInfo.size(); idx++){
            comboBox1.addItem(OUNames[idx]);
        }
    }

    private void createUIComponents() {
        comboBox1 = new JComboBox<>();
        add(comboBox1);
    }
}
