package Client;

import Common.User;
import Server.UserData;
import Server.UserDataSource;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            User providedUser = new User(user.getUsername(), password);
            User existingUser = connector.login(providedUser);
            System.out.println("Begin");
            if (existingUser == null)
            {
                System.out.println("fail");
                JOptionPane.showMessageDialog(rootPane, "wrong current password");
            }
            else {
                if (newPassword.equals(confirmNewPassword)) {
                    System.out.println("success");
                    connector.changePassword(new User(user.getUsername(), newPassword));
                    JOptionPane.showMessageDialog(rootPane, "Password successfully changed");
                }
                else {
                    System.out.println("fail");
                    JOptionPane.showMessageDialog(rootPane, "New and confirm password are not same!");
                }
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
