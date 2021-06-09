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
    private User existingUser;
    private ServerConnector serverConnection;

    public void enableUser(boolean yesno){
        adminMenuButton.setVisible(yesno);
    }

    private void organisationButtonActionPerformed(java.awt.event.ActionEvent evt) {
        OrganisationForm organisationForm = new OrganisationForm();
        organisationForm.setContentPane(new OrganisationForm().organisationPanel);
        organisationForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        organisationForm.setVisible(true);
        organisationForm.pack();
        organisationForm.setTitle("CAB302");
    }

    private void allOrdersButtonActionPerformed(java.awt.event.ActionEvent evt) {
        OrderForm orderForm = new OrderForm();
        orderForm.setContentPane(orderForm.orderPanel);
        orderForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        orderForm.setVisible(true);
        orderForm.pack();
        orderForm.setTitle("CAB302");
    }

    private void adminMenuButtonActionPerformed(java.awt.event.ActionEvent evt) {
        AdminForm adminForm = new AdminForm(existingUser, serverConnection);
        adminForm.setContentPane(adminForm.adminPanel);
        adminForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminForm.setVisible(true);
        adminForm.pack();
        adminForm.setTitle("CAB302");
    }

    private void changePasswordButtonActionPerformed(java.awt.event.ActionEvent evt) {
        PasswordForm passwordForm = new PasswordForm(existingUser, serverConnection);
        passwordForm.setContentPane(new PasswordForm(existingUser, serverConnection).passwordPanel);
        passwordForm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        passwordForm.setVisible(true);
        passwordForm.pack();
        passwordForm.setTitle("CAB302");
    }
    public MenuForm(User existingUser, ServerConnector serverConnection) {
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
            public void actionPerformed(ActionEvent e) { adminMenuButtonActionPerformed(e); }
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
        this.serverConnection = serverConnection;
    }
}
