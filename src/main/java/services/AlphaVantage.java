package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import configuration.Env;
import configuration.jackson.EmptyStringAsNullModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

// Limits: 5 API requests per minute and 500 requests per day
public class AlphaVantage {

    public static final AlphaVantage INSTANCE = new AlphaVantage();

    private final String baseUrl = "https://www.alphavantage.co/query?";
    private final String tokenSecret = Env.alphaVantageSecret;

    private final HttpClient   httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper     = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new EmptyStringAsNullModule());

    private final Cache<String, Object> cache = Caffeine.newBuilder()
            .expireAfterWrite(7, TimeUnit.DAYS)
            .build();

    public List<IncomeStatement> annualIncomeStatements(String symbol) {

        var cached = (List<IncomeStatement>) cache.getIfPresent("annualIncomeStatements-" + symbol);
        if (cached != null) return cached;

        var uri = URI.create(baseUrl + "function=INCOME_STATEMENT&symbol=" + symbol + "&apikey=" + tokenSecret);
        var response = request(uri);

        var incomeStatementResponse = jsonParse(response, IncomeStatementResponse.class);

        if (incomeStatementResponse.annualReports() == null) incomeStatementResponse = new IncomeStatementResponse(List.of());
        cache.put("annualIncomeStatements-" + symbol, incomeStatementResponse.annualReports());
        return incomeStatementResponse.annualReports();

    }

    private HttpResponse<String> request(URI uri) {
        try {
            var response = httpClient.send(HttpRequest.newBuilder().uri(uri).GET().build(), HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 429) throw new TooManyRequests("Tried to get %s but recieved status code 429".formatted(response.request().uri()));
            return response;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Request to %s failed".formatted(uri), e);
        }
    }

    private <T> T jsonParse(HttpResponse<String> response, Class<T> jsonType) {
        try {
            return mapper.readValue(response.body(), jsonType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JsonProcessing for endpoint %s failed, body: %s".formatted(response.uri(), response.body()), e);
        }
    }

    public record IncomeStatementResponse(List<IncomeStatement> annualReports) {

    }

    public record IncomeStatement(String fiscalDateEnding,
                                  String reportedCurrency,
                                  String grossProfit,
                                  String totalRevenue,
                                  String costOfRevenue,
                                  String costofGoodsAndServicesSold,
                                  String operatingIncome,
                                  String sellingGeneralAndAdministrative,
                                  String researchAndDevelopment,
                                  String operatingExpenses,
                                  String investmentIncomeNet,
                                  String netInterestIncome,
                                  String interestIncome,
                                  String interestExpense,
                                  String nonInterestIncome,
                                  String otherNonOperatingIncome,
                                  String depreciation,
                                  String depreciationAndAmortization,
                                  String incomeBeforeTax,
                                  String incomeTaxExpense,
                                  String interestAndDebtExpense,
                                  String netIncomeFromContinuingOperations,
                                  String comprehensiveIncomeNetOfTax,
                                  String ebit,
                                  String ebitda,
                                  String netIncome) {

        public String formatted(String number) {
            if (number.equals("None"))
                return "None";

            if ("USD".equals(reportedCurrency)) {
                var format = NumberFormat.getCurrencyInstance(Locale.US);
                format.setMaximumFractionDigits(0);

                return format.format(Long.parseLong(number));
            } else {
                var format = NumberFormat.getCurrencyInstance();
                format.setCurrency(Currency.getInstance(reportedCurrency));
                format.setMaximumFractionDigits(0);
                return format.format(Long.parseLong(number));
            }

        }

    }

    public static class TooManyRequests extends RuntimeException {
        public TooManyRequests(String message) {
            super(message);
        }
    }

}
