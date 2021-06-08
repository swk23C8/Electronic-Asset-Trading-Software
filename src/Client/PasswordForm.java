package Client;

import Common.User;
import Server.UserData;
import Server.UserDataSource;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordForm extends JFrame{
    private JPasswordField passwordField2;
    private JPasswordField passwordField3;
    private JPasswordField passwordField1;
    private JButton confirmUpdateButton;
    public JPanel passwordPanel;
    private ServerConnector connector;
    private User user;



    private void confirmUpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {

        String password = String.valueOf(passwordField2.getPassword());
        String newPassword = String.valueOf(passwordField1.getPassword());
        String confirmNewPassword = String.valueOf(passwordField3.getPassword());

        if (password.trim().equals("")){
            JOptionPane.showMessageDialog(rootPane, "Please enter original password");
        }
        else if (newPassword.trim().equals("")){
            JOptionPane.showMessageDialog(rootPane, "Please enter new password");
        }
        else if (confirmNewPassword.trim().equals("")){
            JOptionPane.showMessageDialog(rootPane, "Please enter the new password again");
        }
        else {
            User mockUser = new User(user.getUsername(), password);
            if (connector.getSingleUser(user.getUsername()).getPassword().
                    equals(connector.checkPassword(mockUser))) {
                if (newPassword.equals(confirmNewPassword)) {
                    connector.changePassword(new User (user.getUsername(), newPassword));
                    this.dispose();
                }
                else {
                    JOptionPane.showMessageDialog(rootPane, "New password and Comfirm password is not same!");
                }

            }
            else {
                JOptionPane.showMessageDialog(rootPane, "Wrong Current Password!");
            }
        }
    }
    public PasswordForm(User user, ServerConnector connector) {
        this.user = user;
        this.connector = connector;
        confirmUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmUpdateButtonActionPerformed(e);
            }
        });
    }
}
