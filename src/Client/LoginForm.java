package Client;

import Server.DBConnection;

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
            String loginQuery = "SELECT * FROM user WHERE `username` = ? and `password` = ? ";
            try {

                PreparedStatement ps = DBConnection.getInstance().prepareStatement(loginQuery);
                PreparedStatement ps1 = DBConnection.getInstance().prepareStatement(loginQuery);
                ps.setString(1, username);
                ps.setString(2, password);
                ps1.setString(1, username);
                ps1.setString(2, password);
                ResultSet rs = ps.executeQuery();
                ResultSet rs1 = ps1.executeQuery();
                if(rs.next()){
                    //I cant seem to hide/close the window...
                    this.dispose();
                    MenuForm menuForm = new MenuForm();
                    menuForm.setContentPane(new MenuForm().menuPanel);
                    menuForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    menuForm.setVisible(true);
                    menuForm.pack();
                    menuForm.setTitle("CAB302");
                }
                else if(rs1.next()){
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
                Logger.getLogger(LoginForm_1.class.getName()).log(Level.SEVERE,null,ex);
            }
        }
    }


    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm();
        loginForm.setContentPane(new LoginForm().loginPanel);
        loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginForm.setVisible(true);
        loginForm.pack();
        loginForm.setTitle("CAB302");
    }

    public LoginForm() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButtonActionPerformed(e);
            }
        });
    }
}
