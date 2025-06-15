import javax.swing.*;
import java.awt.*;

public class ReceiptPanel {
    private JTextArea receiptArea;

    public ReceiptPanel(SportCarnivalRegistration app) {
    }

    public JPanel createPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Registration Receipt", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel.add(titleLabel, BorderLayout.NORTH);

        receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        panel.add(new JScrollPane(receiptArea), BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(e -> SwingUtilities.getWindowAncestor(panel).dispose());
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    public void updateReceipt(String receiptDetails, double grandTotal, double totalTax) {
        receiptArea.setText (
            receiptDetails + 
            String.format(
                "Total Tax: RM %.2f\nGrand Total: RM %.2f\n\n", 
                totalTax, grandTotal
        )+ 
        "The invoice will be sent to your email soon. \n"+
        "For any enquiries, please contact : 018-4567890\n" +
        "Thank you for your registration !"
        );
        
        
    }
}



















