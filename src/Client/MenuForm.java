package Client;

import Common.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuForm extends JFrame{
    public JPanel menuPanel;
    private JMenuBar MenuBar;
    private JButton organisationButton;
    private JButton allOrdersButton;
    private JLabel welcomeLabel;
    private JButton adminMenuButton;
    private JButton changePasswordButton;
    private User currentUser;
    private ServerConnector serverConnection;

    public void enableuser(boolean yesno){
//        WHATEVERONLYADMINSEES.setVisible(yesno);
    }
    public MenuForm(User currentUser, ServerConnector serverConnection) {
        organisationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        allOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        adminMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        this.currentUser = currentUser;
        this.setContentPane(this.menuPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("CAB302");
    }
}
