package Client;

import Common.Asset;
import Common.AssetPossession;
import Common.OU;
import Common.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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
    private JPasswordField passwordField5;
    private JButton createNewOrganisationUnitButton;
    private JComboBox comboBox3;
    private JLabel label1;
    private JButton editOUAssetQtyButton;
    private User existingUser;
    private ServerConnector serverConnection;
    private AssetData assetData;
    private OUData ouData;


    /**
     * @param evt Function for the button that add the Asset
     */
    private void addAsset(java.awt.event.ActionEvent evt) {
        String newAsset = textField1.getText();
        if (newAsset.trim().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Please enter new asset");
        }
        else if (serverConnection.getAsset().contains(newAsset)) {
            JOptionPane.showMessageDialog(rootPane, "Asset already exist");
        }
        else {
            assetData.add(new Asset(newAsset));
            JOptionPane.showMessageDialog(rootPane, "New asset successfully added");
        }
    }

    /**
     * @param evt Function for the button that remove the asset
     */
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

    /**
     * @param evt Function for the button that add the OU
     */
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
            ouData.add(new OU(newOU));
            JOptionPane.showMessageDialog(rootPane, "New OU Successfully added");
        }
    }


    private void displayCurrentCredit (ItemEvent evt) {
        String selectedOU = (String) comboBox1.getSelectedItem();
        label1.setText(serverConnection.getSingleOU(new OU(selectedOU)).getCredits().toString());
    }


    /**
     * @param evt Function for the button that change the credit of OU
     */
    private void changeCreditAmount(java.awt.event.ActionEvent evt) {
        //add command to change credit amount for logged in OU
        String OUName = comboBox1.getSelectedItem().toString();
        try {
            int changeCreditCount = Integer.parseInt(textField6.getText());
            serverConnection.editOUCredit(new OU(OUName, changeCreditCount));
            label1.setText(String.valueOf(changeCreditCount));
            JOptionPane.showMessageDialog(rootPane, "Credit successfully edited");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(rootPane, "Please enter the credit");
        }
    }

    /**
     * @param evt Function that create an account for the logged in admin
     */
    private void createAccount(java.awt.event.ActionEvent evt) {
        String newUsername = textField2.getText();
        String initPassword = String.valueOf(passwordField1.getPassword());
        String finalPassword = String.valueOf(passwordField4.getPassword());
        String selectedOU = (String) comboBox2.getSelectedItem();
        String selectedType = (String) comboBox3.getSelectedItem();

        if (newUsername.equals("") || initPassword.equals("") || finalPassword.equals("") ||
        selectedOU.equals("") || selectedType.equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Please enter all user information");
        }
        else if (!initPassword.equals(finalPassword)) {
            JOptionPane.showMessageDialog(rootPane, "Please confirm your password");
        }
        else {
            serverConnection.addUser(new User(newUsername, initPassword, selectedOU, selectedType));
            JOptionPane.showMessageDialog(rootPane, "New user successfully added");
        }
    }


    /**
     * @param evt Function for the update password button
     */
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

    /**
     * @param evt Function for after editing the OU Quantity
     */
    private void editOUQtyButtonActionPerformed(ActionEvent evt) {
        String ouName = comboBox1.getSelectedItem().toString();
        EditOUAssetForm editOUAssetForm = new EditOUAssetForm(new OU(ouName), serverConnection);
        editOUAssetForm.setContentPane(editOUAssetForm.editPanel);
        editOUAssetForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        editOUAssetForm.setVisible(true);
        editOUAssetForm.pack();
        editOUAssetForm.setTitle("CAB302");
    }

    /**
     * Set actionListeners to each button on the form
     * @param existingUser
     * @param serverConnection
     */
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

        confirmCreditAmountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeCreditAmount(e);
            }
        });

        comboBox1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                displayCurrentCredit(e);
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
//        HashMap<String,Integer> OUInfo = serverConnection.getOU();
//        String[] OUNames = OUInfo.keySet().toArray(new String[0]);
//        for (int idx = 0; idx < OUInfo.size(); idx++){
//            comboBox1.addItem(OUNames[idx]);
//            comboBox2.addItem(OUNames[idx]);
//        }
        comboBox3.addItem("admin");
        comboBox3.addItem("user");

        createNewOrganisationUnitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOU(e);
            }
        });

        editOUAssetQtyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editOUQtyButtonActionPerformed(e);
            }
        });
    }

    /**
     * Importing data into the UI
     */
    private void createUIComponents() {
        ouData = new OUData();
        comboBox1 = new JComboBox<>(ouData.getModel());
        add(comboBox1);
        comboBox2 = new JComboBox<>(ouData.getModel());
        add(comboBox2);
        comboBox3 = new JComboBox<>();
        add(comboBox3);
        assetData = new AssetData();
        list1 = new JList(assetData.getModel());
        add(list1);


    }
}
