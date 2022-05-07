package services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import configuration.Env;
import configuration.jackson.EmptyStringAsNullModule;
import org.apache.commons.lang3.NotImplementedException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class IEXCloud {

    public static final IEXCloud INSTANCE = new IEXCloud(false);

    private final String baseUrl;
    private final String tokenSecret;

    private final HttpClient   httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper     = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new EmptyStringAsNullModule());

    private final Cache<String, Object> cache = Caffeine.newBuilder()
            .expireAfterWrite(7, TimeUnit.DAYS)
            .build();

    // cash flow
    // income statement
    // balance sheet

    private IEXCloud(boolean useSandbox) {
        if (useSandbox) {
            baseUrl = "https://sandbox.iexapis.com/stable/";
            tokenSecret = "Tpk_b2976e74890149aa924881cc9fe19496"; // can be public
        } else {
            baseUrl = "https://cloud.iexapis.com/stable/";
            tokenSecret = Env.iexCloudSecret;
        }
    }

    public List<Ticker> tickers(String region /* ISO 3166-1 */) {

        var uri = URI.create(baseUrl + "ref-data/region/" + region + "/symbols?token=" + tokenSecret);
        var response = request(uri);
        var iexTickerResponses = Arrays.asList(jsonParse(response, Ticker[].class));
        return iexTickerResponses;

    }

    public String logo(String symbol) {

        var cached = (String) cache.getIfPresent("logo-" + symbol);
        if (cached != null) return cached;

        var uri = URI.create(baseUrl + "/stock/" + symbol + "/logo?token=" + tokenSecret);
        var response = request(uri);

        record IEXLogoResponse(String url) {}

        var iexLogoResponse = jsonParse(response, IEXLogoResponse.class);
        cache.put("logo-" + symbol, iexLogoResponse.url());

        return iexLogoResponse.url();

    }

    public CompanyProfile companyProfile(String symbol) {

        var cached = (CompanyProfile) cache.getIfPresent("profile-" + symbol);
        if (cached != null) return cached;

        var uri = URI.create(baseUrl + "/stock/" + symbol + "/company?token=" + tokenSecret);
        var response = request(uri);

        var iexCompanyProfileResponse = jsonParse(response, CompanyProfile.class);
        cache.put("profile-" + symbol, iexCompanyProfileResponse);
        return iexCompanyProfileResponse;

    }

    public CompanyProfile fxRates(String baseCurrency) {

        var uri = URI.create(baseUrl + "/fx/latest" + baseCurrency + "?token=" + tokenSecret);
        var response = request(uri);

        var iexCompanyProfileResponse = jsonParse(response, CompanyProfile.class);
        throw new NotImplementedException();

    }

    public String stockPrice(String symbol) {
        var uri = URI.create(baseUrl + "/stock/" + symbol + "/price?token=" + tokenSecret);
        var response = request(uri);

        return response.body(); // e.g "143.28"
    }

    private HttpResponse<String> request(URI uri) {
        try {
            var response = httpClient.send(HttpRequest.newBuilder().uri(uri).GET().build(), HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 402) throw new OutOfCredits(response.toString());
            if (response.statusCode() == 429) throw new TooManyRequests(response.toString());
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

    public record Ticker(String symbol, String name, String exchange, String exchangeName, String currency) {}

    public record CompanyProfile(String symbol, String companyName, String industry, String sector, String website, String description, String CEO,
                                 String employees) {
        public String websiteFormatted() {
            if (website == null) return null;
            if (website.startsWith("http")) {
                return website;
            } else {
                return "https://" + website;
            }
        }

        public String employeesCommaFormatted() {
            if (employees == null) return null;
            return NumberFormat.getInstance().format(Integer.parseInt(employees));
        }
    }

    public record BalanceSheet(
            String reportDate,
            String filingType,
            String fiscalDate,
            int fiscalQuarter,
            int fiscalYear,
            String currency,
            long currentCash,
            Object shortTermInvestments,
            long receivables,
            long inventory,
            long otherCurrentAssets,
            long currentAssets,
            long longTermInvestments,
            long propertyPlantEquipment,
            Object goodwill,
            Object intangibleAssets,
            long otherAssets,
            long totalAssets,
            long accountsPayable,
            Object currentLongTermDebt,
            Object otherCurrentLiabilities,
            long totalCurrentLiabilities,
            long longTermDebt,
            Object otherLiabilities,
            int minorityInterest,
            long totalLiabilities,
            long commonStock,
            long retainedEarnings,
            Object treasuryStock,
            Object capitalSurplus,
            long shareholderEquity,
            long netTangibleAssets,
            String id,
            String key,
            String subkey,
            long date,
            long updated
    ) {}

    public class OutOfCredits extends RuntimeException {
        public OutOfCredits(String message) {
            super(message);
        }
    }

    public class TooManyRequests extends RuntimeException {
        public TooManyRequests(String message) {
            super(message);
        }
    }

}
