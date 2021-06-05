package Client;

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

    public void enableuser(boolean yesno){
//        WHATEVERONLYADMINSEES.setVisible(yesno);
    }
    public MenuForm() {
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
    }
    public static void main(String[] args) {
        MenuForm menuForm = new MenuForm();
        menuForm.setContentPane(new MenuForm().menuPanel);
        menuForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuForm.setVisible(true);
        menuForm.pack();
        menuForm.setTitle("CAB302");
    }
}
