import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginPanel {

    private SportCarnivalRegistration app;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Image backgroundImage; // Image for the background

    public LoginPanel(SportCarnivalRegistration app) {
        this.app = app;

        // Load the background image
        backgroundImage = new ImageIcon("C:/Users/hp/Desktop/OOP SPORT SYSTEM/SPORT REGISTRATION/bk3.png").getImage(); // Replace with your image path
    }

    public JPanel createPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the background image
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panel.setLayout(null); // Use absolute positioning for full control

        // Username and Password labels
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(150, 100, 80, 25);
        usernameLabel.setForeground(Color.WHITE); // Make text visible on the background
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(240, 100, 150, 25);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(150, 140, 80, 25);
        passwordLabel.setForeground(Color.WHITE); // Make text visible on the background
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(240, 140, 150, 25);
        panel.add(passwordField);

        // Buttons
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(410, 140, 100, 25);
        panel.add(loginButton);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(410, 100, 100, 25);
        panel.add(signUpButton);

        JButton backButton = new JButton("Back to Home");
        backButton.setBounds(240, 200, 150, 25);
        panel.add(backButton);

        // Login button action
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in both fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validate login credentials
            try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
                String line;
                boolean isAuthenticated = false;

                while ((line = reader.readLine()) != null) {
                    String[] credentials = line.split(",");
                    if (credentials[0].equals(username) && credentials[1].equals(password)) {
                        isAuthenticated = true;
                        break;
                    }
                }

                if (isAuthenticated) {
                    JOptionPane.showMessageDialog(null, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    app.getCardLayout().show(app.getMainPanel(), "Form");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error reading user data!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Sign-Up button action
        signUpButton.addActionListener(e -> {
            clearLoginFields();
            app.getCardLayout().show(app.getMainPanel(), "SignUp");
        });

        // Back button action
        backButton.addActionListener(e -> app.getCardLayout().show(app.getMainPanel(), "Home"));

        return panel;
    }

    /**
     * Clears the username and password fields.
     */
    private void clearLoginFields() {
        usernameField.setText("");
        passwordField.setText("");
    }
}
