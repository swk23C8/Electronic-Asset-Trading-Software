package Client;

import Server.DBConnection;
import Server.UserDataSource;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginForm extends JFrame{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel loginPanel;
    private LoginForm loginForm;

    public JPanel getLoginPanel()
    {
        return loginPanel;
    }


    // maybe this?
    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        if(username.trim().equals("") && password.trim().equals("")){
            JOptionPane.showMessageDialog(rootPane, "Please enter both username and password");
        }
        else if(username.trim().equals("")){
            JOptionPane.showMessageDialog(rootPane, "Please enter username");
        }
        else if (password.trim().equals("")){
            JOptionPane.showMessageDialog(rootPane, "Please enter password");
        }
        else{

            try {
                UserDataSource userDatabase = new UserDataSource();
                if(userDatabase.getUser(username).getPassword().
                        equals(userDatabase.passwordCheck(password, userDatabase.getUser(username)))){
                    //I cant seem to hide/close the window...
                    this.dispose();
                    MenuForm menuForm = new MenuForm();
                    menuForm.setContentPane(new MenuForm().menuPanel);
                    menuForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    menuForm.setVisible(true);
                    menuForm.pack();
                    menuForm.setTitle("CAB302");
                    menuForm.enableuser(true);
                }
                else
                    JOptionPane.showMessageDialog(loginButton, "Wrong Username or Password");
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(loginButton, "Wrong Username or Password");
                Logger.getLogger(LoginForm_1.class.getName()).log(Level.SEVERE,null,ex);
            }
        }
    }


    public LoginForm() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButtonActionPerformed(e);
            }
        });
        this.setContentPane(this.getLoginPanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("CAB302");
    }
}
