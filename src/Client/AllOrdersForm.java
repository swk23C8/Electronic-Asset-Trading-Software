package Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AllOrdersForm extends JFrame{
    private JPanel Main;
    private JMenuBar MenuBar;
    private JTable table1;
    private JTable AllOrdersTable;
    private DefaultTableModel defaultTableModel;

    public AllOrdersForm(){
        defaultTableModel = new DefaultTableModel();
        AllOrdersTable = new JTable(defaultTableModel);
        AllOrdersTable.setPreferredScrollableViewportSize(new Dimension(300, 100));
        AllOrdersTable.setFillsViewportHeight(true);
        Main.add(new JScrollPane(AllOrdersTable));
        defaultTableModel.addColumn("Username");
        defaultTableModel.addColumn("Roll No");
        defaultTableModel.addColumn("Department");
    }
    public static void main(String[] args) {
        AllOrdersForm allOrdersForm = new AllOrdersForm();
        allOrdersForm.setContentPane(new AllOrdersForm().Main);
        allOrdersForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        allOrdersForm.setVisible(true);
        allOrdersForm.pack();
        allOrdersForm.setTitle("CAB302");
    }
}
