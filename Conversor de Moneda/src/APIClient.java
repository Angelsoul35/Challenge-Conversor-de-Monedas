import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Cliente para interactuar con la API de ExchangeRate
 */
public class APIClient {
    private static final String API_KEY = "2734b00906524db275bac1df";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY;
    private static final Duration TIMEOUT = Duration.ofSeconds(10);

    private final HttpClient client;

    public APIClient() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(TIMEOUT)
                .build();
    }

    /**
     * Obtiene las tasas de cambio para una moneda base
     */
    public JsonObject getRates(String baseCurrency) throws Exception {
        if (baseCurrency == null || baseCurrency.trim().isEmpty()) {
            throw new IllegalArgumentException("La moneda base no puede estar vacía");
        }

        String url = BASE_URL + "/latest/" + baseCurrency.toUpperCase();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(TIMEOUT)
                .header("Accept", "application/json")
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new Exception("Error en la API: " + response.statusCode());
            }

            return JsonParser.parseString(response.body()).getAsJsonObject();
        } catch (Exception e) {
            throw new Exception("Error al obtener las tasas de cambio: " + e.getMessage(), e);
        }
    }

    /**
     * Obtiene la lista de monedas disponibles
     */
    public Map<String, String> getSupportedCurrencies() throws Exception {
        // Usaremos USD como moneda base para obtener todas las monedas disponibles
        JsonObject response = getRates("USD");
        Map<String, String> currencies = new HashMap<>();

        // Obtenemos las tasas de conversión que contienen todos los códigos de moneda
        JsonObject conversionRates = response.getAsJsonObject("conversion_rates");

        // Para cada código de moneda, agregamos una entrada al mapa
        conversionRates.keySet().forEach(currencyCode ->
                currencies.put(currencyCode, getCurrencyName(currencyCode))
        );

        return currencies;
    }

    /**
     * Obtiene el nombre descriptivo de la moneda basado en su código
     */
    private String getCurrencyName(String code) {
        Map<String, String> currencyNames = new HashMap<>();
        currencyNames.put("USD", "US Dollar");
        currencyNames.put("EUR", "Euro");
        currencyNames.put("GBP", "British Pound");
        currencyNames.put("JPY", "Japanese Yen");
        currencyNames.put("AUD", "Australian Dollar");
        currencyNames.put("CAD", "Canadian Dollar");
        currencyNames.put("CHF", "Swiss Franc");
        currencyNames.put("CNY", "Chinese Yuan");
        currencyNames.put("NZD", "New Zealand Dollar");
        currencyNames.put("MXN", "Mexican Peso");
        currencyNames.put("BRL", "Brazilian Real");
        currencyNames.put("ARS", "Argentine Peso");
        currencyNames.put("COP", "Colombian Peso");
        currencyNames.put("PEN", "Peruvian Sol");
        currencyNames.put("CLP", "Chilean Peso");
        // Se puede añadir más nombres de monedas según necesidad

        return currencyNames.getOrDefault(code, code + " Currency");
    }
}
