//Conversor de Moneda Challenge Alura - Oracle ONE G7 Angelo Padilla//

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

public class Main {
    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());
    private static CurrencyService currencyService;
    private static JComboBox<String> fromCurrencyCombo;
    private static JComboBox<String> toCurrencyCombo;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Could not set system look and feel", e);
        }

        currencyService = new CurrencyService(new APIClient());
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Currency Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel controlsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        fromCurrencyCombo = new JComboBox<>();
        toCurrencyCombo = new JComboBox<>();
        JTextField amountField = new JTextField(10);
        JButton convertButton = new JButton("Convert");
        JLabel resultLabel = new JLabel("Result: ");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));

        loadAvailableCurrencies();

        // Añadir componentes al panel
        addComponentsToPanel(controlsPanel, gbc, amountField, convertButton, resultLabel);

        mainPanel.add(controlsPanel, BorderLayout.CENTER);

        // Configurar el botón de conversión
        configureConvertButton(convertButton, amountField, resultLabel, frame);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static void addComponentsToPanel(JPanel panel, GridBagConstraints gbc,
                                             JTextField amountField, JButton convertButton, JLabel resultLabel) {
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("From Currency:"), gbc);
        gbc.gridx = 1;
        panel.add(fromCurrencyCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("To Currency:"), gbc);
        gbc.gridx = 1;
        panel.add(toCurrencyCombo, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        panel.add(amountField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(convertButton, gbc);

        gbc.gridy = 4;
        panel.add(resultLabel, gbc);
    }

    private static void configureConvertButton(JButton convertButton, JTextField amountField,
                                               JLabel resultLabel, JFrame frame) {
        convertButton.addActionListener(e -> {
            try {
                String selectedFrom = (String) fromCurrencyCombo.getSelectedItem();
                String selectedTo = (String) toCurrencyCombo.getSelectedItem();

                if (selectedFrom == null || selectedTo == null) {
                    throw new IllegalStateException("Please select both currencies");
                }

                String fromCurrency = selectedFrom.split(" - ")[0];
                String toCurrency = selectedTo.split(" - ")[0];
                double amount = Double.parseDouble(amountField.getText());

                double result = currencyService.convert(fromCurrency, toCurrency, amount);
                resultLabel.setText(String.format("Result: %.2f %s", result, toCurrency));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                LOGGER.log(Level.SEVERE, "Error during conversion", ex);
                JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private static void loadAvailableCurrencies() {
        try {
            Map<String, String> currencies = currencyService.getAvailableCurrencies();
            currencies.forEach((code, name) -> {
                String item = code + " - " + name;
                fromCurrencyCombo.addItem(item);
                toCurrencyCombo.addItem(item);
            });
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading currencies", e);
            JOptionPane.showMessageDialog(null,
                    "Error loading currencies: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
