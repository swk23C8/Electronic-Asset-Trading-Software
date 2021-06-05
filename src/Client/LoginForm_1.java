package Client;

import javax.swing.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import Server.*;

public class LoginForm_1 extends JFrame{
    private JTextField usernameField;
    private JButton loginButton;
    private JPasswordField passwordField;
    private JLabel accountLoginLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPanel loginPanel;


    public LoginForm_1() {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());


                if(username.trim().equals("") && password.trim().equals("")){
                    JOptionPane.showMessageDialog(rootPane, "please enter both username and password");
                }
                else if(username.trim().equals("")){
                    JOptionPane.showMessageDialog(rootPane, "please enter username");
                }
                else if (password.trim().equals("")){
                    JOptionPane.showMessageDialog(rootPane, "please enter password");
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
                            MenuForm menuForm = new MenuForm();
                            menuForm.setContentPane(new MenuForm().menuPanel);
                            menuForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            menuForm.setVisible(true);
                            menuForm.pack();
                            menuForm.setTitle("CAB302");
                        }
                        else if(rs1.next()){
                            MenuForm menuForm = new MenuForm();
                            menuForm.setVisible(true);
                            menuForm.pack();
                            menuForm.setLocationRelativeTo(null);
                            menuForm.enableuser(true);
                            dispose();
                        }
                        else
                            JOptionPane.showMessageDialog(rootPane, "wrong username or password");
                    }
                    catch (Exception ex) {
                        Logger.getLogger(LoginForm_1.class.getName()).log(Level.SEVERE,null,ex);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        LoginForm_1 loginPanel = new LoginForm_1();
        loginPanel.setContentPane(new LoginForm_1().loginPanel);
        loginPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginPanel.setVisible(true);
        loginPanel.pack();
        loginPanel.setTitle("CAB302");
    }

}

