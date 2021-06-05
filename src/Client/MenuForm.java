package Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuForm extends JFrame{
    public JPanel menuPanel;
    private JMenuBar MenuBar;
    private JButton assetsButton;
    private JButton tradeHistoryButton;
    private JButton allOrdersButton;
    private JButton currentOrdersButton;
    private JLabel welcomeLabel;
    private JButton adminMenuButton;

    public void enableuser(boolean yesno){
//        WHATEVERONLYADMINSEES.setVisible(yesno);
    }
    public MenuForm() {
        assetsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        currentOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        tradeHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        allOrdersButton.addActionListener(new ActionListener() {
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
