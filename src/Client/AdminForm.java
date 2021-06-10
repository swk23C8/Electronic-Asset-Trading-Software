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
    private JPasswordField passwordField5;
    private JButton createNewOrganisationUnitButton;
    public String userType;
    private ButtonGroup userTypeButtonGroup;
    private User existingUser;
    private ServerConnector serverConnection;
    private AssetData assetData;

    private void addAsset(java.awt.event.ActionEvent evt) {
        String newAsset = textField1.getText();
        if (newAsset.trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Please enter new asset");
        }
        else {
            assetData.add(new Asset(newAsset));
            JOptionPane.showMessageDialog(rootPane, "New asset successfully added");
        }
    }
    private void removeAsset(java.awt.event.ActionEvent evt) {
        int index = list1.getSelectedIndex();
        assetData.remove(list1.getSelectedValue());
        JOptionPane.showMessageDialog(rootPane, "Asset successfully deleted");
        index--;
        if (index == -1) {
            if (assetData.getSize() != 0) {
                index = 0;
            }
        }
        list1.setSelectedIndex(index);
    }

    private void addOU(java.awt.event.ActionEvent evt){
        String newOU = textField4.getText();
        System.out.println(newOU);
        if (newOU.trim().equals(""))
        {
            JOptionPane.showMessageDialog(rootPane, "Please specify an OU name");
        }
        else if (serverConnection.getOU().containsKey(newOU)){
            JOptionPane.showMessageDialog(rootPane, "OU already exist");
        }
        else {
        serverConnection.addOU(new OU(newOU));
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
            User providedUser = existingUser;
            User existingUser = serverConnection.login(providedUser);
            System.out.println("Begin");
            if (existingUser == null) {
                System.out.println("fail");
                JOptionPane.showMessageDialog(rootPane, "wrong current password");
            } else {
                if (newPassword.equals(confirmNewPassword)) {
                    System.out.println("success");
                    serverConnection.changePassword(existingUser);
                    JOptionPane.showMessageDialog(rootPane, "Password successfully changed");
                } else {
                    System.out.println("fail");
                    JOptionPane.showMessageDialog(rootPane, "New and confirm password are not same!");
                }
            }
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
        confirmUpdatePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updatePassword(e);
            }
        });
        this.existingUser = existingUser;
        this.serverConnection = serverConnection;
        HashMap<String,Integer> OUInfo = serverConnection.getOU();
        String[] OUNames = OUInfo.keySet().toArray(new String[0]);
        for (int idx = 0; idx < OUInfo.size(); idx++){
            comboBox1.addItem(OUNames[idx]);
        }
        createNewOrganisationUnitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOU(e);
            }
        });
    }

    private void createUIComponents() {
        comboBox1 = new JComboBox<>();
        add(comboBox1);
        assetData = new AssetData();
        list1 = new JList(assetData.getModel());
        add(list1);
    }
}
