import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SignUpPanel {
    private SportCarnivalRegistration app;

    public SignUpPanel(SportCarnivalRegistration app) {
        this.app = app;
    }

    public JPanel createPanel() {
        // Main panel with layered layout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Background image
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setHorizontalAlignment(JLabel.CENTER);
        ImageIcon backgroundIcon = new ImageIcon("bk4.png"); // Replace with your image path
        Image backgroundImage = backgroundIcon.getImage();
        Image scaledBackgroundImage = backgroundImage.getScaledInstance(586, 500, Image.SCALE_SMOOTH); // Adjust size
        backgroundLabel.setIcon(new ImageIcon(scaledBackgroundImage));
        backgroundLabel.setLayout(new GridBagLayout()); // Allows components to center on the background

        // Form panel (centered fields)
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setOpaque(false); // Make the form panel transparent

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE); // Make text visible on the background
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        JPasswordField passwordField = new JPasswordField();

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setForeground(Color.WHITE);
        JPasswordField confirmPasswordField = new JPasswordField();

        JButton signUpButton = new JButton("Sign Up");
        JButton backButton = new JButton("Back to Home");

        // Sign Up button action
        signUpButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(null, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Save the new user to a file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true))) {
                writer.write(username + "," + password + "\n");
                JOptionPane.showMessageDialog(null, "Sign up successful! You can now log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
                app.getCardLayout().show(app.getMainPanel(), "Login");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error saving user data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Back button action
        backButton.addActionListener(e -> app.getCardLayout().show(app.getMainPanel(), "Home"));

        // Add components to the form panel
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        formPanel.add(confirmPasswordLabel);
        formPanel.add(confirmPasswordField);
        formPanel.add(signUpButton);
        formPanel.add(backButton);

        // Center the form panel on the background
        backgroundLabel.add(formPanel, new GridBagConstraints());

        // Add the background label to the main panel
        mainPanel.add(backgroundLabel, BorderLayout.CENTER);

        return mainPanel;
    }
}
