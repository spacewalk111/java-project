
import javax.swing.*;
import java.awt.*;

public class SportCarnivalRegistration {
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private FormPanel formPanel;
    private ReceiptPanel receiptPanel; // Store reference to ReceiptPanel

    public SportCarnivalRegistration() {
        frame = new JFrame("Sport Carnival Registration System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize panels and store references
        formPanel = new FormPanel(this);
        receiptPanel = new ReceiptPanel(this);

        // Add panels to the main panel
        mainPanel.add(new HomePanel(this).createPanel(), "Home");
        mainPanel.add(formPanel.createPanel(), "Form");
        mainPanel.add(receiptPanel.createPanel(), "Receipt");
        mainPanel.add(new LoginPanel(this).createPanel(), "Login");
        mainPanel.add(new SignUpPanel(this).createPanel(), "SignUp");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    public ReceiptPanel getReceiptPanel() {
        return receiptPanel; // Getter for ReceiptPanel
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SportCarnivalRegistration::new);
    }
}
