package Client;

import Common.*;
import Server.ServerManagement;
import org.junit.jupiter.api.Order;

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
    private User existingUser;
    private ServerConnector connector;

    public void enableUser(boolean yesno){
        adminMenuButton.setVisible(yesno);
    }

    private void organisationButtonActionPerformed(java.awt.event.ActionEvent evt) {
        OrganisationForm organisationForm = new OrganisationForm();
        organisationForm.setContentPane(new OrganisationForm().organisationPanel);
        organisationForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        organisationForm.setVisible(true);
        organisationForm.pack();
        organisationForm.setTitle("CAB302");
    }

    private void allOrdersButtonActionPerformed(java.awt.event.ActionEvent evt) {
        OrderForm orderForm = new OrderForm();
        orderForm.setContentPane(new OrderForm().orderPanel);
        orderForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        orderForm.setVisible(true);
        orderForm.pack();
        orderForm.setTitle("CAB302");
    }

    private void adminMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {
        AdminForm adminForm = new AdminForm();
        adminForm.setContentPane(new AdminForm().adminPanel);
        adminForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminForm.setVisible(true);
        adminForm.pack();
        adminForm.setTitle("CAB302");
    }

    private void changePasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {
        PasswordForm passwordForm = new PasswordForm();
        passwordForm.setContentPane(new PasswordForm().passwordPanel);
        passwordForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        passwordForm.setVisible(true);
        passwordForm.pack();
        passwordForm.setTitle("CAB302");
    }
    public MenuForm(User existingUser, ServerConnector connector) {
        organisationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                organisationButtonActionPerformed(e);
            }
        });

        allOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                allOrdersButtonActionPerformed(e);
            }
        });

        adminMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminMenuButtonActionPerformed(e);
            }
        });
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePasswordButtonActionPerformed(e);
            }
        });
        this.setContentPane(this.menuPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
        this.setTitle("CAB302");
        this.existingUser = existingUser;
        this.connector = connector;
    }
}
