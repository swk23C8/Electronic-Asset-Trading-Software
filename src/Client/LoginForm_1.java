package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginForm_1 extends JFrame{
    private JTextField usernameField;
    private JButton loginButton;
    private JPasswordField passwordField;
    private JLabel accountLoginLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JPanel loginPanel;
    private JPanel Main;

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
                    String userQuery = "SELECT * FROM users WHERE `username` = ? and `password` = ? ";
                    String adminQuery = "SELECT * FROM admin WHERE `username` = ? and `password` = ? ";
                    try {
                        /**
                         * ServerConnector connector = new ServerConnector();
                         *                         User providedUser = new User(username, password);
                         *                         User existingUser = connector.login(providedUser);
                         *                         if (existingUser == null)
                         *                         {
                         *                             JOptionPane.showMessageDialog(rootPane, "wrong username or password");
                         *                         }
                         *                         else if(existingUser.getType() == "admin"){
                         *                             MenuForm mainMenu = new MenuForm();
                         *                             mainMenu.setVisible(true);
                         *                             mainMenu.pack();
                         *                             mainMenu.setLocationRelativeTo(null);
                         *                             mainMenu.enableuser(false);
                         *                             dispose();
                         *                         }
                         *                         else if(existingUser.getType() == "user") {
                         *                             MenuForm mainMenu = new MenuForm();
                         *                             mainMenu.setVisible(true);
                         *                             mainMenu.pack();
                         *                             mainMenu.setLocationRelativeTo(null);
                         *                             mainMenu.enableuser(true);
                         *                             dispose();
                         *                         }
                         */
                        PreparedStatement ps = DbConnection.getConnection().prepareStatement(userQuery);
                        PreparedStatement ps1 = DbConnection.getConnection().prepareStatement(adminQuery);
                        ps.setString(1, username);
                        ps.setString(2, password);
                        ps1.setString(1, username);
                        ps1.setString(2, password);
                        ResultSet rs = ps.executeQuery();
                        ResultSet rs1 = ps1.executeQuery();
                        if(rs.next()){
                            MenuForm mainMenu = new MenuForm();
                            mainMenu.setVisible(true);
                            mainMenu.pack();
                            mainMenu.setLocationRelativeTo(null);
                            mainMenu.enableuser(false);
                            dispose();
                        }
                        else if(rs1.next()){
                            MenuForm mainMenu = new MenuForm();
                            mainMenu.setVisible(true);
                            mainMenu.pack();
                            mainMenu.setLocationRelativeTo(null);
                            mainMenu.enableuser(true);
                            dispose();
                        }
                        else
                            JOptionPane.showMessageDialog(rootPane, "wrong username or password");
                    } catch (Exception ex) {
                        Logger.getLogger(LoginForm_1.class.getName()).log(Level.SEVERE,null,ex);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        LoginForm_1 loginForm = new LoginForm_1();
        loginForm.setContentPane(new LoginForm_1().Main);
        loginForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginForm.setVisible(true);
        loginForm.pack();
        loginForm.setTitle("CAB302");
    }
}

