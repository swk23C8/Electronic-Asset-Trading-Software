package Server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Form for the Server to start or shutdown
 */
public class ServerForm extends JFrame{
    private JButton startServerButton;
    private JPanel serverPanel;
    private ServerManagement serverManagement;

    private JPanel getLoginPanel(){
        return serverPanel;
    }

    public void startProgram(){
        serverManagement = new ServerManagement();
    }

    public void stopProgram(){
        serverManagement.shutdown();
    }
    public ServerForm() {
        startServerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startProgram();
            }
        });
        this.setContentPane(this.getLoginPanel());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setSize(500,200);
        this.setTitle("Server");
    }
}
