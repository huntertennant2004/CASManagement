import java.awt.event.*;
import javax.swing.*;

public class LaunchPage implements ActionListener {

    private final JFrame frame;

    public LaunchPage() {
        // initialises frame
        frame = new JFrame();
        // initialises buttons
        JButton logInButton = new JButton("LOG IN");
        logInButton.setBounds(93, 423, 300, 100);
        logInButton.addActionListener(this);
        frame.add(logInButton);
        JButton createAccountButton = new JButton("SIGN UP");
        createAccountButton.setBounds(392, 423, 300, 100);
        createAccountButton.addActionListener(this);
        frame.add(createAccountButton);
        // sets frame attributes
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // opens log in pop-up
        switch (e.getActionCommand()) {
            case "LOG IN" -> {
                new SignInPopUp(false);
                frame.dispose();
            }
            case "SIGN UP" -> {
                new SignInPopUp(true);
                frame.dispose();
            }
        }
    }

}


