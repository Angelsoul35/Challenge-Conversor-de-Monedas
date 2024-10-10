import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class UI {
    private static final Logger LOGGER = Logger.getLogger(UI.class.getName());
    private final CurrencyService currencyService;
    private final List<CurrencyRecord> records;

    public UI() {
        this.currencyService = new CurrencyService(new APIClient());
        this.records = new ArrayList<>();
    }

    public void start() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                processCurrencyConversion(scanner);

                System.out.println("¿Desea realizar otra conversión? (s/n): ");
                String again = scanner.nextLine().toLowerCase();
                if (!again.equals("s")) {
                    break;
                }
            }

            printConversionHistory();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error en la ejecución del programa", e);
        }
    }

    private void processCurrencyConversion(Scanner scanner) {
        try {
            System.out.println("Seleccione la moneda base (ej: USD): ");
            String fromCurrency = scanner.nextLine().toUpperCase();

            System.out.println("Seleccione la moneda destino (ej: COP): ");
            String toCurrency = scanner.nextLine().toUpperCase();

            System.out.println("Ingrese el monto a convertir: ");
            double amount = Double.parseDouble(scanner.nextLine());

            double result = currencyService.convert(fromCurrency, toCurrency, amount);
            CurrencyRecord record = new CurrencyRecord(fromCurrency, toCurrency, amount, result);
            records.add(record);
            System.out.println("Resultado: " + result);
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error en la conversión", e);
            System.out.println("Error al realizar la conversión: " + e.getMessage());
        }
    }

    private void printConversionHistory() {
        System.out.println("Historial de conversiones:");
        records.forEach(System.out::println);
    }

    public static void main(String[] args) {
        new UI().start();
    }
}
