import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

public class SupervisorMenu implements ActionListener {

    private final String userEmail;
    private final JFrame frame = new JFrame();
    private final JList<String> entriesList;
    private final JButton editButton;
    private final JButton deleteButton;
    private final JButton publishButton;

    public SupervisorMenu(String userEmail) throws Exception {
        this.userEmail = userEmail;
        String[] experienceNames = new String[FileHandler.readAllReviewEntries("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Reviews").size()];
        for(int i = 0; i < FileHandler.readAllReviewEntries("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Reviews").size(); i++) {
            experienceNames[i] = FileHandler.readAllReviewEntries("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Reviews").get(i).getEntryName();
        }
        // initialises labels
        JLabel userEmailLabel = new JLabel(userEmail + " (Supervisor)");;
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
                // opens review editor
                new ReviewEditor(userEmail, new ReviewEntry("", "", "", ""), false);
                frame.dispose();
                break;
            case "EDIT":
                // opens review editor and automatically inputs selected entry
                try {
                    new ReviewEditor(userEmail, FileHandler.checkForReviewEntry("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Reviews", selectedEntry), true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                frame.dispose();
                break;
            case "DELETE":
                // deletes selected review entry
                try {
                    FileHandler.deleteReviewEntry("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Reviews", FileHandler.checkForReviewEntry("C:Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Reviews", selectedEntry), false);
                    new SupervisorMenu(userEmail);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                frame.dispose();
                break;
            case "PUBLISH":
                // creates new file for selected review entry
                try {
                    File newReview = new File("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\Individual Entries\\Reviews" + FileHandler.checkForExperienceEntry("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Reviews", selectedEntry).getEntryName());
                    FileHandler.publishReviewEntry("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\Individual Entries\\Reviews" + FileHandler.checkForReviewEntry("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Reviews", selectedEntry).getEntryName(), FileHandler.checkForReviewEntry("C:Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\List of Entries\\Reviews", selectedEntry), false);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                break;
        }
    }

    private void valueChanged(ListSelectionEvent listSelectionEvent) {
        // enables buttons if review entry is selected
        editButton.setEnabled(true);
        deleteButton.setEnabled(true);
        publishButton.setEnabled(true);
    }

}
