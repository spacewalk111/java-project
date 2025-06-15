import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FormPanel {
    private SportCarnivalRegistration app;
    private HashMap<String, String> sportDates;
    private HashMap<String, Double> sportPrices;
    private ArrayList<String[]> registrations; // To store multiple registrations
    private double taxRate = 0.1; // Tax rate: 10%
    private double discountRate = 0.2; // Discount rate: 20% for children

    public FormPanel(SportCarnivalRegistration app) {
        this.app = app;
        this.registrations = new ArrayList<>();
        initializeSportData();
    }

    private void initializeSportData() {
        sportDates = new HashMap<>();
        sportDates.put("Pickleball", "2025-02-01");
        sportDates.put("Running Race", "2025-02-02");
        sportDates.put("Archery", "2025-02-03");
        sportDates.put("Dart", "2025-02-04");

        sportPrices = new HashMap<>();
        sportPrices.put("Pickleball", 35.0);
        sportPrices.put("Running Race", 50.0);
        sportPrices.put("Archery", 40.0);
        sportPrices.put("Dart", 15.0);
    }

    public JPanel createPanel() {
        // Main panel with a background image
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Background image
        JLabel backgroundLabel = new JLabel();
        backgroundLabel.setHorizontalAlignment(JLabel.CENTER);
        ImageIcon backgroundIcon = new ImageIcon("bk8.jpg"); // Replace with your image path
        Image backgroundImage = backgroundIcon.getImage();
        Image scaledBackgroundImage = backgroundImage.getScaledInstance(800, 400, Image.SCALE_SMOOTH);
        backgroundLabel.setIcon(new ImageIcon(scaledBackgroundImage));
        backgroundLabel.setLayout(new GridBagLayout()); // Center components

        // Create the form panel
        JPanel formPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        formPanel.setOpaque(false); // Transparent panel to show the background

        JLabel nameLabel = new JLabel("Participant Name:");
        JTextField nameField = new JTextField();

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();

        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField();

        JLabel ageCategoryLabel = new JLabel("Age Category:");
        JTextField ageCategoryField = new JTextField();
        ageCategoryField.setEditable(false);

        JLabel sportLabel = new JLabel("Select Sport:");
        JComboBox<String> sportComboBox = new JComboBox<>(new String[]{"Select a Sport", "Pickleball", "Running Race", "Archery", "Dart"});

        JLabel dateLabel = new JLabel("Event Date:");
        JTextField dateField = new JTextField();
        dateField.setEditable(false);

        JLabel feeLabel = new JLabel("Participation Fee (RM):");
        JTextField feeField = new JTextField();
        feeField.setEditable(false);

        sportComboBox.addActionListener(e -> {
            String selectedSport = (String) sportComboBox.getSelectedItem();
            if (!selectedSport.equals("Select a Sport")) {
                dateField.setText(sportDates.get(selectedSport));
                feeField.setText(String.format("%.2f", sportPrices.get(selectedSport)));
            } else {
                // Clear fields if the placeholder is selected
                dateField.setText("");
                feeField.setText("");
            }
        });

        // Age field action listener
        ageField.addCaretListener(e -> {
            String ageText = ageField.getText().trim();
            try {
                int age = Integer.parseInt(ageText);
                if (age < 13) {
                    ageCategoryField.setText("Child");
                } else {
                    ageCategoryField.setText("Adult");
                }
                String selectedSport = (String) sportComboBox.getSelectedItem();
                if (!selectedSport.equals("Select a Sport")) {
                    double fee = sportPrices.get(selectedSport);
                    if (age < 13) {
                        fee *= (1 - discountRate);
                    }
                    feeField.setText(String.format("%.2f", fee));
                }
            } catch (NumberFormatException ex) {
                ageCategoryField.setText("");  // Clear if invalid
                feeField.setText("");
            }
        });

        JButton addAnotherSportButton = new JButton("Add Sport");
        JButton finishButton = new JButton("Finish Registration");
        JButton backButton = new JButton("Back to Home");

        // Add Another Sport button action
        addAnotherSportButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();
            String email = emailField.getText().trim();
            String ageCategory = ageCategoryField.getText().trim();
            String sport = (String) sportComboBox.getSelectedItem();
            String date = dateField.getText().trim();
            if (sport.equals("Select a Sport") || name.isEmpty() || ageText.isEmpty() || email.isEmpty() || ageCategory.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all the fields!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double fee = Double.parseDouble(feeField.getText().trim());
            double originalFee = sportPrices.get(sport); // Store the original fee

            registrations.add(new String[]{name, email, ageCategory, sport, date, String.valueOf(fee), String.valueOf(originalFee)});
            saveRegistrationToFile(name, email, ageCategory, sport, date, fee, originalFee);
            JOptionPane.showMessageDialog(null, "Sport added successfully! You can add more or finish registration.", "Success", JOptionPane.INFORMATION_MESSAGE);

            // Reset form
            nameField.setText("");
            ageField.setText("");
            emailField.setText("");
            ageCategoryField.setText("");
            sportComboBox.setSelectedIndex(0);
            dateField.setText("");
            feeField.setText("");
        });

        // Finish Registration button action
        finishButton.addActionListener(e -> {
            if (registrations.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please add at least one sport!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Generate the receipt details
            double grandTotal = 0.0;
            double totalTax = 0.0;
            StringBuilder receiptDetails = new StringBuilder();
            
            receiptDetails.append("********************\n");
            receiptDetails.append("SIS SPORT CARNIVAL\n");
            receiptDetails.append("********************\n\n");
            
            for (String[] registration : registrations) {
                String name = registration[0];
                String email = registration[1];
                String ageCategory = registration[2];
                String sport = registration[3];
                String date = registration[4];
                double fee = Double.parseDouble(registration[5]);
                double originalFee = Double.parseDouble(registration[6]);
                double tax = fee * taxRate;
                double discount = ageCategory.equals("Child") ? originalFee * discountRate : 0.0;

                totalTax += tax;
                grandTotal += fee + tax;

                receiptDetails.append(String.format(
                        "Name: %s\nEmail: %s\nAge Category: %s\nSport: %s\nEvent Date: %s\nOriginal Fee: RM %.2f\nDiscount: RM %.2f\nTax: RM %.2f\nFee after Discount: RM %.2f\n\n",
                        name, email, ageCategory, sport, date, originalFee, discount, tax, fee));
            }

            app.getReceiptPanel().updateReceipt(receiptDetails.toString(), grandTotal, totalTax);
            app.getCardLayout().show(app.getMainPanel(), "Receipt");
        });

        // Back to Home button action
        backButton.addActionListener(e -> app.getCardLayout().show(app.getMainPanel(), "Home"));

        // Add components to form panel
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(ageLabel);
        formPanel.add(ageField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(ageCategoryLabel);
        formPanel.add(ageCategoryField);
        formPanel.add(sportLabel);
        formPanel.add(sportComboBox);
        formPanel.add(dateLabel);
        formPanel.add(dateField);
        formPanel.add(feeLabel);
        formPanel.add(feeField);

        formPanel.add(addAnotherSportButton);
        formPanel.add(finishButton);
        formPanel.add(backButton);

        // Center the form panel on the background
        backgroundLabel.add(formPanel, new GridBagConstraints());
        mainPanel.add(backgroundLabel, BorderLayout.CENTER);

        return mainPanel;
    }

    private void saveRegistrationToFile(String name, String email, String ageCategory, String sport, String date, double fee, double originalFee) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("registrations.txt", true))) {
            writer.write(String.format("%s,%s,%s,%s,%s,%.2f,%.2f\n", name, email, ageCategory, sport, date, fee, originalFee));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving registration to file!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}



