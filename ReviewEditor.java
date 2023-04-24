import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class ReviewEditor implements ActionListener, DocumentListener {

    private final boolean editReviewEntry;
    private final String userEmail;
    private final ReviewEntry selectedEntry;
    private final JFrame frame;
    private final JButton saveButton;
    private final JTextField reviewNameTextField;
    private final JTextField additionalCommentsTextField;
    private final JTextArea opinionOnSuccessTextArea;

    public ReviewEditor(String userEmail, ReviewEntry selectedEntry, boolean editReviewEntry) {
        this.userEmail = userEmail;
        this.selectedEntry = selectedEntry;
        this.editReviewEntry = editReviewEntry;
        frame = new JFrame();
        // initialises labels
        JLabel userEmailLabel = new JLabel(userEmail + " (Supervisor)");
        userEmailLabel.setBounds(10, 10, 800, 20);
        frame.add(userEmailLabel);
        JLabel reviewNameLabel = new JLabel("Review name:");
        reviewNameLabel.setBounds(50, 73, 800, 20);
        frame.add(reviewNameLabel);
        JLabel opinionOnSuccessLabel = new JLabel("Opinion on success:");
        opinionOnSuccessLabel.setBounds(50, 148, 800, 20);
        frame.add(opinionOnSuccessLabel);
        JLabel additionalCommentsLabel = new JLabel("Additional comments:");
        additionalCommentsLabel.setBounds(50, 403, 800, 20);
        frame.add(additionalCommentsLabel);
        // initialises buttons
        saveButton = new JButton("SAVE");
        saveButton.setBounds(50, 483, 100, 33);
        saveButton.addActionListener(this);
        saveButton.setEnabled(false);
        frame.add(saveButton);
        JButton cancelButton = new JButton("CANCEL");
        cancelButton.setBounds(170, 483, 100, 33);
        cancelButton.addActionListener(this);
        cancelButton.setEnabled(true);
        frame.add(cancelButton);
        // initialises text fields
        reviewNameTextField = new JTextField(selectedEntry.getEntryName());
        reviewNameTextField.setBounds(50, 98, 685, 20);
        reviewNameTextField.getDocument().addDocumentListener(this);
        frame.add(reviewNameTextField);
        additionalCommentsTextField = new JTextField(selectedEntry.getAdditionalComments());
        additionalCommentsTextField.setBounds(50, 428, 685, 20);
        additionalCommentsTextField.getDocument().addDocumentListener(this);
        frame.add(additionalCommentsTextField);
        // initialises scrollable text area
        opinionOnSuccessTextArea = new JTextArea(selectedEntry.getOpinionOnSuccess());
        opinionOnSuccessTextArea.setLineWrap(true);
        JScrollPane opinionOnSuccessScrollPane = new JScrollPane(opinionOnSuccessTextArea);
        opinionOnSuccessScrollPane.setBounds(50, 173, 685, 200);
        opinionOnSuccessTextArea.getDocument().addDocumentListener(this);
        frame.add(opinionOnSuccessScrollPane);
        // sets frame attributes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(800, 591);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "SAVE":
                // adds new review entry/edits existing review entry
                if (!editReviewEntry) {
                    if (!FileHandler.checkForEntryName("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Reviews", reviewNameTextField.getText())) {
                        FileHandler.addReviewEntry("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Reviews", userEmail, reviewNameTextField.getText(), opinionOnSuccessTextArea.getText(), additionalCommentsTextField.getText(), true);
                        try {
                            new SupervisorMenu(userEmail);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Review name already exists - please change name of experience");
                    }
                } else {
                    if (!FileHandler.checkForEntryName("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Reviews", reviewNameTextField.getText())) {
                        FileHandler.editReviewEntry("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Reviews", selectedEntry, new ReviewEntry(userEmail, reviewNameTextField.getText(), opinionOnSuccessTextArea.getText(), additionalCommentsTextField.getText()), false);
                        try {
                            new SupervisorMenu(userEmail);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        frame.dispose();
                    } else {
                        if (!reviewNameTextField.getText().equals(selectedEntry.getEntryName())) {
                            JOptionPane.showMessageDialog(frame, "Experience name already exists - please change name of experience");
                        } else {
                            FileHandler.editReviewEntry("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Reviews", selectedEntry, new ReviewEntry(userEmail, reviewNameTextField.getText(), opinionOnSuccessTextArea.getText(), additionalCommentsTextField.getText()), false);
                            try {
                                new SupervisorMenu(userEmail);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            frame.dispose();
                        }
                    }
                }
                break;
            case "CANCEL":
                // returns to supervisor menu
                try {
                    new SupervisorMenu(userEmail);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                frame.dispose();
                break;
        }
    }

    @Override
    public void changedUpdate(DocumentEvent event) {

    }

    @Override
    public void insertUpdate(DocumentEvent event) {
        // enables save button if text has changed/review name is entered
        if(editReviewEntry) {
            saveButton.setEnabled(true);
        } else {
            if(!reviewNameTextField.getText().isBlank()) {
                saveButton.setEnabled(true);
            }
        }
    }

    @Override
    public void removeUpdate(DocumentEvent event) {
        // disabled save button if no review name is entered
        if(reviewNameTextField.getText().isBlank()) {
            saveButton.setEnabled(false);
        }
    }



}
