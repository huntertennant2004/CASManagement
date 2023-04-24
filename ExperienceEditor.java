import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExperienceEditor implements ActionListener, DocumentListener  {

    private final boolean editExperienceEntry;
    private final String userEmail;
    private final ExperienceEntry selectedEntry;
    private final JFrame frame;
    private final JButton saveButton;
    private final JTextField experienceNameTextField;
    private final JTextField datesTextField;
    private final JTextField learningOutcomesTextField;
    private final JTextArea descriptionTextArea;

    public ExperienceEditor(String userEmail, ExperienceEntry selectedEntry, boolean editExperienceEntry) {
        this.userEmail = userEmail;
        this.selectedEntry = selectedEntry;
        this.editExperienceEntry = editExperienceEntry;
        frame = new JFrame();
        // initialises labels
        JLabel userEmailLabel = new JLabel(userEmail + " (Student)");
        userEmailLabel.setBounds(10, 10, 800, 20);
        frame.add(userEmailLabel);
        JLabel experienceNameLabel = new JLabel("Experience name:");
        experienceNameLabel.setBounds(50, 73, 800, 20);
        frame.add(experienceNameLabel);
        JLabel datesLabel = new JLabel("Date(s):");
        datesLabel.setBounds(50, 148, 800, 20);
        frame.add(datesLabel);
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(50, 223, 800, 20);
        frame.add(descriptionLabel);
        JLabel learningOutcomesLabel = new JLabel("Learning outcome(s):");
        learningOutcomesLabel.setBounds(50, 478, 800, 20);
        frame.add(learningOutcomesLabel);
        // initialises buttons
        saveButton = new JButton("SAVE");
        saveButton.setBounds(50, 558, 100, 33);
        saveButton.addActionListener(this);
        saveButton.setEnabled(false);
        frame.add(saveButton);
        JButton cancelButton = new JButton("CANCEL");
        cancelButton.setBounds(170, 558, 100, 33);
        cancelButton.addActionListener(this);
        cancelButton.setEnabled(true);
        frame.add(cancelButton);
        // initialises text fields
        experienceNameTextField = new JTextField(selectedEntry.getEntryName());
        experienceNameTextField.setBounds(50, 98, 685, 20);
        experienceNameTextField.getDocument().addDocumentListener(this);
        frame.add(experienceNameTextField);
        datesTextField = new JTextField(selectedEntry.getDates());
        datesTextField.setBounds(50, 173, 685, 20);
        datesTextField.getDocument().addDocumentListener(this);
        frame.add(datesTextField);
        learningOutcomesTextField = new JTextField(selectedEntry.getLearningOutcomes());
        learningOutcomesTextField.setBounds(50, 503, 685, 20);
        learningOutcomesTextField.getDocument().addDocumentListener(this);
        frame.add(learningOutcomesTextField);
        // initialises scrollable text area
        descriptionTextArea = new JTextArea(selectedEntry.getDescription());
        descriptionTextArea.getDocument().addDocumentListener(this);
        descriptionTextArea.setLineWrap(true);
        JScrollPane descriptionScrollPane = new JScrollPane(descriptionTextArea);
        descriptionScrollPane.setBounds(50, 248, 685, 200);
        frame.add(descriptionScrollPane);
        // sets frame attributes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(800, 666);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "SAVE":
                // adds new experience entry/edits existing experience entry
                if (!editExperienceEntry) {
                    if (!FileHandler.checkForEntryName("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Experiences", experienceNameTextField.getText())) {
                        FileHandler.addExperienceEntry("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Experiences", userEmail, experienceNameTextField.getText(), datesTextField.getText(), descriptionTextArea.getText(), learningOutcomesTextField.getText(), true);
                        try {
                            new StudentMenu(userEmail);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Experience name already exists - please change name of experience");
                    }
                } else {
                    if (!FileHandler.checkForEntryName("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Experiences", experienceNameTextField.getText())) {
                        FileHandler.editExperienceEntry("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Experiences", selectedEntry, new ExperienceEntry(userEmail, experienceNameTextField.getText(), datesTextField.getText(), descriptionTextArea.getText(), learningOutcomesTextField.getText()), false);
                        try {
                            new StudentMenu(userEmail);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        frame.dispose();
                    } else {
                        if (!experienceNameTextField.getText().equals(selectedEntry.getEntryName())) {
                            JOptionPane.showMessageDialog(frame, "Experience name already exists - please change name of experience");
                        } else {
                            FileHandler.editExperienceEntry("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Experiences", selectedEntry, new ExperienceEntry(userEmail, experienceNameTextField.getText(), datesTextField.getText(), descriptionTextArea.getText(), learningOutcomesTextField.getText()), false);
                            try {
                                new StudentMenu(userEmail);
                            } catch (Exception exception) {
                                exception.printStackTrace();
                            }
                            frame.dispose();
                        }
                    }
                }
                break;
            case "CANCEL":
                // returns to student menu
                try {
                    new StudentMenu(userEmail);
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
        // enables save button if text has changed/experience name is entered
        if(editExperienceEntry) {
            saveButton.setEnabled(true);
        } else {
            if(!experienceNameTextField.getText().isBlank()) {
                saveButton.setEnabled(true);
            }
        }
    }

    @Override
    public void removeUpdate(DocumentEvent event) {
        // disabled save button if no experience name is entered
        if(experienceNameTextField.getText().isBlank()) {
            saveButton.setEnabled(false);
        }
    }

}
