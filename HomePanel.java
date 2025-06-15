import javax.swing.*;
import java.awt.*;

class HomePanel {
    private SportCarnivalRegistration app;

    public HomePanel(SportCarnivalRegistration app) {
        this.app = app;
    }

    public JPanel createPanel() {
        // Main panel with a layered layout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create the background image
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setHorizontalAlignment(JLabel.CENTER);
        ImageIcon icon = new ImageIcon("bk6.png"); // Replace with your background image path
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(650, 400, Image.SCALE_SMOOTH); // Adjust size as needed
        backgroundLabel.setIcon(new ImageIcon(scaledImg));
        backgroundLabel.setLayout(new BorderLayout()); // Allow adding components over the background


        // Button Section at the bottom
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonPanel.setOpaque(false); // Make the button panel transparent

        JButton registerButton = new JButton("Register for an Event");
        registerButton.addActionListener(e -> app.getCardLayout().show(app.getMainPanel(), "Login"));
        buttonPanel.add(registerButton);

        JButton organiserDetailsButton = new JButton("Event Organiser Details");
        organiserDetailsButton.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "Organised by: SIS Sport Carnival Team\nContact: sissportcarnival@events.com\nPhone: +6018-456-7890\nAddress : Metropolitan Sports Centre, Jalan Alamanda 1,\n\t62000, Putrajaya, Malaysia",
                "Organiser Details", JOptionPane.INFORMATION_MESSAGE));
        buttonPanel.add(organiserDetailsButton);

        JButton categoriesAndFeesButton = new JButton("Categories and Fees");
        categoriesAndFeesButton.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "Available Sports and Fees:\n1. Pickleball: RM35\nDate : 2025-02-01\nVenue : Court A, Metropolitan Sports Centre\n\n2. Running Race: RM50\nDate : 2025-02-02\nVenue : Court B, Metropolitan Sports Centre\n\n3. Archery: RM40\nDate : 2025-02-03\nVenue : Court C, Metropolitan Sports Centre\n\n4. Dart: RM15\nDate : 2025-02-04\nVenue : Court D, Metropolitan Sports Centre  ",
                "Categories and Fees", JOptionPane.INFORMATION_MESSAGE));
        buttonPanel.add(categoriesAndFeesButton);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> app.getCardLayout().show(app.getMainPanel(), "Login"));
        buttonPanel.add(loginButton);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> app.getCardLayout().show(app.getMainPanel(), "SignUp"));
        buttonPanel.add(signUpButton);

        backgroundLabel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the backgroundLabel (with its content) to the main panel
        panel.add(backgroundLabel, BorderLayout.CENTER);

        return panel;
    }
}
