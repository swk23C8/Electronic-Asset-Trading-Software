package DBtestUI;

import Common.OU;
import Server.OUData;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class OuUI extends JFrame {

    private static final long serialVersionUID = -4165744919842016081L;

    private JList nameList;

    private JTextField name;

    private JTextField credit;

    private JButton newButton;

    private JButton saveButton;

    private JButton deleteButton;

    private JButton updateButton;

    private JButton editButton;

    OUData data;

    /**
     * Constructor sets up user interface, adds listeners and displays.
     *
     * @param data The underlying data/model class the UI needs.
     */
    public OuUI(OUData data) {
        this.data = data;
        initUI();
        checkListSize();

        // add listeners to interactive components
        addButtonListeners(new ButtonListener());
        addNameListListener(new NameListListener());
        addClosingListener(new ClosingListener());

        // decorate the frame and make it visible
        setTitle("OU");
        setMinimumSize(new Dimension(400, 300));
        pack();
        setVisible(true);

    }

    /**
     * Places the detail panel and the button panel in a box layout with vertical
     * alignment and puts a 20 pixel gap between the components and the top and
     * bottom edges of the frame.
     */
    private void initUI() {
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeDetailsPanel());
        contentPane.add(Box.createVerticalStrut(20));
        contentPane.add(makeButtonsPanel());
        contentPane.add(Box.createVerticalStrut(20));
    }

    /**
     * Makes a JPanel consisiting of (1) the list of names and (2) the OU
     * fields in a box layout with horizontal alignment and puts a 20 pixel gap
     * between the components and the left and right edges of the panel.
     *
     * @return the detail panel.
     */
    private JPanel makeDetailsPanel() {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.X_AXIS));
        detailsPanel.add(Box.createHorizontalStrut(20));
        detailsPanel.add(makeNameListPane());
        detailsPanel.add(Box.createHorizontalStrut(20));
        detailsPanel.add(makeOuFieldsPanel());
        detailsPanel.add(Box.createHorizontalStrut(20));
        return detailsPanel;
    }

    /**
     * Makes a JScrollPane that holds a JList for the list of names in the
     * ou.
     *
     * @return the scrolling name list panel
     */
    private JScrollPane makeNameListPane() {
        nameList = new JList(data.getModel());
        nameList.setFixedCellWidth(200);

        JScrollPane scroller = new JScrollPane(nameList);
        scroller.setViewportView(nameList);
        scroller
                .setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller
                .setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setMinimumSize(new Dimension(200, 150));
        scroller.setPreferredSize(new Dimension(250, 150));
        scroller.setMaximumSize(new Dimension(250, 200));

        return scroller;
    }

    /**
     * Makes a JPanel containing labels and textfields for each of the pieces of
     * data that are to be recorded for each ou. The labels and fields are
     * layed out using a GroupLayout, with the labels vertically grouped, the
     * fields vertically grouped and each label/group pair horizontally grouped.
     *
     * @return a panel containing the address fields
     */
    private JPanel makeOuFieldsPanel() {
        JPanel ouPanel = new JPanel();
        GroupLayout layout = new GroupLayout(ouPanel);
        ouPanel.setLayout(layout);

        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);

        // Turn on automatically creating gaps between components that touch
        // the edge of the container and the container.
        layout.setAutoCreateContainerGaps(true);

        JLabel nameLabel = new JLabel("Name");
        JLabel creditLabel = new JLabel("Credit");

        name = new JTextField(20);
        credit = new JTextField(20);
        setFieldsEditable(false);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        hGroup.addGroup(layout.createParallelGroup().addComponent(nameLabel).addComponent(creditLabel));
        hGroup.addGroup(layout.createParallelGroup().addComponent(name).addComponent(credit));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        // The sequential group contains five parallel groups that align
        // the contents along the baseline. The first parallel group contains
        // the first label and text field, and the second parallel group contains
        // the second label and text field etc. By using a sequential group
        // the labels and text fields are positioned vertically after one another.
        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(nameLabel).addComponent(name));

        vGroup.addGroup(layout.createParallelGroup(Alignment.BASELINE)
                .addComponent(creditLabel).addComponent(credit));

        layout.setVerticalGroup(vGroup);

        return ouPanel;
    }

    /**
     * Adds the New, Save and Delete buttons to a panel
     */
    private JPanel makeButtonsPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        newButton = new JButton("New");
        saveButton = new JButton("Save");
        saveButton.setEnabled(false);
        deleteButton = new JButton("Delete");
        updateButton = new JButton("Update");
        updateButton.setEnabled(false);
        editButton = new JButton("Edit");
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(newButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(deleteButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(editButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        buttonPanel.add(updateButton);
        buttonPanel.add(Box.createHorizontalStrut(50));
        return buttonPanel;
    }

    /**
     * Adds a listener to the new, save and delete buttons
     */
    private void addButtonListeners(ActionListener listener) {
        newButton.addActionListener(listener);
        saveButton.addActionListener(listener);
        deleteButton.addActionListener(listener);
        updateButton.addActionListener(listener);
        editButton.addActionListener(listener);
    }

    /**
     * Adds a listener to the name list
     */
    private void addNameListListener(ListSelectionListener listener) {
        nameList.addListSelectionListener(listener);
    }

    /**
     * Adds a listener to the JFrame
     */
    private void addClosingListener(WindowListener listener) {
        addWindowListener(listener);
    }

    /**
     * Sets the text in the address text fields to the empty string.
     */
    private void clearFields() {
        name.setText("");
        credit.setText("");
    }

    /**
     * Sets whether or not the address fields are editable.
     */
    private void setFieldsEditable(boolean editable) {
        name.setEditable(editable);
        credit.setEditable(editable);
    }

    /**
     * Displays the details of a Person in the OU fields.
     * @param ou teh ou to display.
     */
    private void display(OU ou) {
        if (ou != null) {
            name.setText(ou.getOuName());
            credit.setText(String.valueOf(ou.getUnitCredits()));
        }
    }

    /**
     * Checks size of data/model and enables/disables the delete button
     *
     */
    private void checkListSize() {
        deleteButton.setEnabled(data.getSize() != 0);
    }

    /**
     * Handles events for the three buttons on the UI.
     *
     * @author Malcolm Corney
     * @version $Id: Exp $
     *
     */
    private class ButtonListener implements ActionListener {

        /**
         * @see ActionListener#actionPerformed(ActionEvent)
         */
        public void actionPerformed(ActionEvent e) {
            int size = data.getSize();

            JButton source = (JButton) e.getSource();
            if (source == newButton) {
                newPressed();
            } else if (source == saveButton) {
                savePressed();
            } else if (source == deleteButton) {
                deletePressed();
            } else if (source == updateButton) {
                updatePressed();
            } else if (source == editButton) {
                editPressed();
            }
        }

        /**
         * When the new button is pressed, clear the field display, make them
         * editable and enable the save button.
         */
        private void newPressed() {
            clearFields();
            setFieldsEditable(true);
            saveButton.setEnabled(true);
        }

        /**
         * When the save button is pressed, check that the name field contains
         * something. If it does, create a new OU object and attempt to add it
         * to the data model. Change the fields back to not editable and make the
         * save button inactive.
         *
         * Check the list size to see if the delete button should be enabled.
         */
        private void savePressed() {
            if (name.getText() != null && !name.getText().equals("")) {
                OU o = new OU(name.getText(), Integer.parseInt(credit.getText()));
                data.add(o);
            }

            setFieldsEditable(false);
            saveButton.setEnabled(false);
            checkListSize();
        }

        /**
         * When the delete button is pressed remove the selected name from the
         * data model.
         *
         * Clear the fields that were displayed and check to see if the delete
         * button should be displayed.
         *
         * The index here handles cases where the first element of the list is
         * deleted.
         */
        private void deletePressed() {
            int index = nameList.getSelectedIndex();
            data.remove(nameList.getSelectedValue());
            clearFields();
            index--;
            if (index == -1) {
                if (data.getSize() != 0) {
                    index = 0;
                }
            }
            nameList.setSelectedIndex(index);
            checkListSize();
        }

        private void editPressed() {
            credit.setEditable(true);
            updateButton.setEnabled(true);
        }

        private void updatePressed() {
            if (name.getText() != null && !name.getText().equals("")) {
                OU o = new OU(name.getText(), Integer.parseInt(credit.getText()));
                data.edit(o);
            }

            setFieldsEditable(false);
            updateButton.setEnabled(false);
            checkListSize();
        }
    }

    /**
     * Implements a ListSelectionListener for making the UI respond when a
     * different name is selected from the list.
     */
    private class NameListListener implements ListSelectionListener {

        /**
         * @see ListSelectionListener#valueChanged(ListSelectionEvent)
         */
        public void valueChanged(ListSelectionEvent e) {
            if (nameList.getSelectedValue() != null
                    && !nameList.getSelectedValue().equals("")) {
                display(data.get(nameList.getSelectedValue()));
            }
        }
    }

    /**
     * Implements the windowClosing method from WindowAdapter/WindowListener to
     * persist the contents of the data/model.
     */
    private class ClosingListener extends WindowAdapter {

        /**
         * @see WindowAdapter#windowClosing(WindowEvent)
         */
        public void windowClosing(WindowEvent e) {
            data.persist();
            System.exit(0);
        }
    }
}
