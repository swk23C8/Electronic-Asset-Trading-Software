package Client;

import Common.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginForm extends JFrame{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel loginPanel;
    private LoginForm loginForm;
    private ServerConnector serverConnection;

    public JPanel getLoginPanel()
    {
        return loginPanel;
    }


    /**
     * @param evt Function for the login button action,
     *            check the user and log them into their account
     */
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
                User providedUser = new User(username, password);
                User existingUser = serverConnection.login(providedUser);
                System.out.println("Begin");
                if (existingUser == null)
                {
                    System.out.println("fail");
                    JOptionPane.showMessageDialog(rootPane, "wrong username or password");
                }
                else if(existingUser.getType().equals("admin")){
                    MenuForm mainMenu = new MenuForm(existingUser, serverConnection);
                    this.dispose();
                    mainMenu.setVisible(true);
                    mainMenu.pack();
                    mainMenu.setLocationRelativeTo(null);
                    mainMenu.enableUser(true);
                    System.out.println("admin");
                }
                else if(existingUser.getType().equals("user")) {
                    MenuForm mainMenu = new MenuForm(existingUser, serverConnection);
                    this.dispose();
                    mainMenu.setVisible(true);
                    mainMenu.pack();
                    mainMenu.setLocationRelativeTo(null);
                    mainMenu.enableUser(false);
                    System.out.println("user");
                }
            }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(loginButton, "Wrong Username or Password");
                Logger.getLogger(LoginForm.class.getName()).log(Level.SEVERE,null,ex);
            }
        }
    }


    /**
     * @param serverConnection Setup the form and actionListener for the login button
     */
    public LoginForm(ServerConnector serverConnection) {
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginButtonActionPerformed(e);
            }
        });
        this.setContentPane(this.getLoginPanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(300,200);
        this.setTitle("CAB302");
        this.serverConnection = serverConnection;
    }
}
