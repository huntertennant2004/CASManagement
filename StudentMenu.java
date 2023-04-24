import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

public class StudentMenu implements ActionListener{

    private final String userEmail;
    private final JFrame frame = new JFrame();
    private final JList<String> entriesList;
    private final JButton editButton;
    private final JButton deleteButton;
    private final JButton publishButton;

    public StudentMenu(String userEmail) throws Exception {
        this.userEmail = userEmail;
        String[] experienceNames = new String[FileHandler.readAllExperienceEntries("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Experiences").size()];
        for(int i = 0; i < FileHandler.readAllExperienceEntries("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Experiences").size(); i++) {
            experienceNames[i] = FileHandler.readAllExperienceEntries("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Experiences").get(i).getEntryName();
        }
        // initialises labels
        JLabel userEmailLabel = new JLabel(userEmail + " (Student)");
        userEmailLabel.setBounds(10, 4, 800, 20);
        frame.add(userEmailLabel);
        JLabel existingProfilesLabel = new JLabel("Here is a list of your already existing entries:");
        existingProfilesLabel.setBounds(91, 75, 800, 20);
        frame.add(existingProfilesLabel);
        // initialises scrollable list
        entriesList = new JList<>(experienceNames);
        entriesList.addListSelectionListener(this::valueChanged);
        JScrollPane profileScrollPane = new JScrollPane(entriesList);
        profileScrollPane.setBounds(91, 115, 600, 335);
        frame.add(profileScrollPane);
        // initialises buttons
        JButton addButton = new JButton("ADD");
        addButton.setBounds(91, 488, 100, 33);
        addButton.addActionListener(this);
        frame.add(addButton);
        editButton = new JButton("EDIT");
        editButton.setBounds(260, 488, 100, 33);
        editButton.addActionListener(this);
        editButton.setEnabled(false);
        frame.add(editButton);
        deleteButton = new JButton("DELETE");
        deleteButton.setBounds(425, 488, 100, 33);
        deleteButton.addActionListener(this);
        deleteButton.setEnabled(false);
        frame.add(deleteButton);
        publishButton = new JButton("PUBLISH");
        publishButton.setBounds(590, 488, 100, 33);
        publishButton.addActionListener(this);
        publishButton.setEnabled(false);
        frame.add(publishButton);
        // sets frame attributes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String selectedEntry = entriesList.getSelectedValue();
        switch (event.getActionCommand()) {
            case "ADD":
                // opens experience editor
                new ExperienceEditor(userEmail, new ExperienceEntry("", "", "", "", ""), false);
                frame.dispose();
                break;
            case "EDIT":
                // opens experience editor and automatically inputs selected entry
                try {
                    new ExperienceEditor(userEmail, FileHandler.checkForExperienceEntry("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Experiences", selectedEntry), true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                frame.dispose();
                break;
            case "DELETE":
                // deletes selected experience entry
                try {
                    FileHandler.deleteExperienceEntry("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Experiences", FileHandler.checkForExperienceEntry("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Experiences", selectedEntry), false);
                    new StudentMenu(userEmail);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                frame.dispose();
                break;
            case "PUBLISH":
                // creates new file for selected experience entry
                try {
                    File newExperience = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\Individual Entries\\Experiences" + FileHandler.checkForExperienceEntry("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Experiences", selectedEntry).getEntryName());
                    FileHandler.publishExperienceEntry("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\Individual Entries\\Experiences" + FileHandler.checkForExperienceEntry("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Experiences", selectedEntry).getEntryName(), FileHandler.checkForExperienceEntry("C:Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Experiences", selectedEntry), false);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
        }
    }

    private void valueChanged(ListSelectionEvent event) {
        // enables buttons if experience entry is selected
        editButton.setEnabled(true);
        deleteButton.setEnabled(true);
        publishButton.setEnabled(true);
    }

}
