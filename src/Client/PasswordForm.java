package Client;

import Common.User;
import Server.UserData;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PasswordForm extends JFrame{
    private JPasswordField passwordField2;
    private JPasswordField passwordField3;
    private JPasswordField passwordField1;
    private JButton confirmUpdateButton;
    public JPanel passwordPanel;
    UserData data;



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
//            implement algorithm to UPDATE/EDIT logged in user's password
//            User u = new User(username.getText(), password.getText());
//            data.edit(u);
        }
    }
    public PasswordForm() {
        confirmUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmUpdateButtonActionPerformed(e);
            }
        });
    }
}
