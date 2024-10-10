import com.google.gson.JsonObject;
import java.util.Map;
import java.util.HashMap;

public class CurrencyService {
    private final APIClient apiClient;
    private final Map<String, JsonObject> ratesCache;
    private long lastCacheUpdate;
    private static final long CACHE_DURATION = 3600000; // 1 hora en milisegundos

    public CurrencyService(APIClient apiClient) {
        this.apiClient = apiClient;
        this.ratesCache = new HashMap<>();
    }

    public double convert(String fromCurrency, String toCurrency, double amount) throws Exception {
        if (amount < 0) {
            throw new IllegalArgumentException("El monto no puede ser negativo");
        }

        JsonObject rates = getRatesFromCache(fromCurrency);
        double rate = rates.getAsJsonObject("conversion_rates").get(toCurrency).getAsDouble();
        return Math.round(amount * rate * 100.0) / 100.0;
    }

    private JsonObject getRatesFromCache(String currency) throws Exception {
        long currentTime = System.currentTimeMillis();
        JsonObject cachedRates = ratesCache.get(currency);

        if (cachedRates == null || currentTime - lastCacheUpdate > CACHE_DURATION) {
            JsonObject newRates = apiClient.getRates(currency);
            ratesCache.put(currency, newRates);
            lastCacheUpdate = currentTime;
            return newRates;
        }

        return cachedRates;
    }

    public Map<String, String> getAvailableCurrencies() throws Exception {
        return apiClient.getSupportedCurrencies();
    }
}
