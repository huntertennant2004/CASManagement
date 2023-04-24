import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.*;

public class SignInPopUp implements ActionListener, DocumentListener {

    private final boolean createNewAccount;
    private final JFrame frame;
    private final JButton okButton;
    private final JCheckBox accountTypeCheckBox = new JCheckBox("Supervisor?");
    private final JTextField emailTextField;
    private final JTextField passwordTextField;

    public SignInPopUp(boolean createNewAccount) {
        this.createNewAccount = createNewAccount;
        // initialises frame
        frame = new JFrame();
        // initialises labels
        JLabel emailLabel = new JLabel("Enter email:");
        emailLabel.setBounds(67, 25, 400, 25);
        frame.add(emailLabel);
        JLabel passwordLabel = new JLabel("Enter password:");
        passwordLabel.setBounds(238, 25, 400, 25);
        frame.add(passwordLabel);
        // initialises buttons
        okButton = new JButton("OK");
        okButton.setBounds(92, 90, 100, 33);
        okButton.addActionListener(this);
        okButton.setEnabled(false);
        frame.add(okButton);
        JButton clearButton = new JButton("CLEAR");
        clearButton.setBounds(191, 90, 100, 33);
        clearButton.addActionListener(this);
        frame.add(clearButton);
        // initialises check box
        if(createNewAccount) {
            accountTypeCheckBox.setBounds(141, 131, 100, 20);
            frame.add(accountTypeCheckBox);
        }
        // initialises text fields
        emailTextField = new JTextField();
        emailTextField.setBounds(28, 50, 150, 25);
        emailTextField.getDocument().addDocumentListener(this);
        frame.add(emailTextField);
        passwordTextField = new JTextField();
        passwordTextField.setBounds(208, 50, 150, 25);
        passwordTextField.getDocument().addDocumentListener(this);
        frame.add(passwordTextField);
        // sets frame attributes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
            case "OK" -> {
                // creates new account details/logs in to existing account and sorts file
                String enteredEmail = emailTextField.getText();
                String enteredPassword = passwordTextField.getText();
                // creates required files if they does not already exist
                try {
                    FileHandler.createNewFiles();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                if (createNewAccount) {
                    if (!FileHandler.checkForEmailAddress("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\logins", enteredEmail) && !FileHandler.checkForLoginDetails("logins", enteredEmail, enteredPassword)) {
                        FileHandler.addLoginDetails("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\logins", enteredEmail, enteredPassword, accountTypeCheckBox.isSelected(), true);
                        FileHandler.sortLoginDetails("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\logins", false);
                        JOptionPane.showMessageDialog(frame, "Account created - please log in using new details.");
                        new LaunchPage();
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Account using requested email address already exists - please enter different email address.");
                    }
                } else {
                    if (FileHandler.checkForLoginDetails("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\logins", enteredEmail, enteredPassword)) {
                        try {
                            if (FileHandler.checkForAccountType("C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\CAS Management Storage\\logins", enteredEmail)) {
                                new SupervisorMenu(enteredEmail);
                            } else {
                                new StudentMenu(enteredEmail);
                            }
                            frame.dispose();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Account not detected - please enter correct details.");
                    }
                }
            }
            case "CLEAR" -> {
                // clears text
                emailTextField.setText("");
                passwordTextField.setText("");
            }
        }
    }

    @Override
    public void changedUpdate(DocumentEvent event) {

    }

    @Override
    public void insertUpdate(DocumentEvent event) {
        // enables buttons when text is entered
        if(!emailTextField.getText().isBlank() && !passwordTextField.getText().isBlank()) {
            okButton.setEnabled(true);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent event) {
        // disables buttons when no text is entered
        if(emailTextField.getText().isBlank() || passwordTextField.getText().isBlank()) {
            okButton.setEnabled(false);
        }
    }



}
