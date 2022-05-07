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
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Fred {

    public static final Fred INSTANCE = new Fred();

    private final String baseUrl = "https://api.stlouisfed.org/fred/";
    private final String tokenSecret;

    private final HttpClient   httpClient = HttpClient.newHttpClient();
    private final ObjectMapper mapper     = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).registerModule(new EmptyStringAsNullModule());

    private final Cache<CacheKey, Object> cache = Caffeine.newBuilder()
            .expireAfterWrite(7, TimeUnit.DAYS)
            .build();

    public Fred() {
        tokenSecret = Env.fredSecret;
    }

    public List<M2MoneySupplyDataPoint> m2MoneySupply() {

        var cached = (List<M2MoneySupplyDataPoint>) cache.getIfPresent(CacheKey.M2_MONEY_SUPPLY);
        if (cached != null) return cached;

        var uri = URI.create(baseUrl + "series/observations?series_id=WM2NS&frequency=m&file_type=json&api_key=" + tokenSecret);
        var response = request(uri);
        var m2MoneySupplyResponse = jsonParse(response, M2MoneySupplyResponse.class);

        cache.put(CacheKey.M2_MONEY_SUPPLY, m2MoneySupplyResponse.observations());
        return m2MoneySupplyResponse.observations();

    }

    public List<HousePriceDataPoint> housePrices() {

        var cached = (List<HousePriceDataPoint>) cache.getIfPresent(CacheKey.HOUSE_PRICES);
        if (cached != null) return cached;

        var uri = URI.create(baseUrl + "series/observations?series_id=MSPUS&frequency=q&file_type=json&api_key=" + tokenSecret);
        var response = request(uri);
        var housePriceIndexResponse = jsonParse(response, HousePriceIndexResponse.class);

        cache.put(CacheKey.HOUSE_PRICES, housePriceIndexResponse.observations());
        return housePriceIndexResponse.observations();

    }

    public List<HouseSupplyEstimateDataPoint> houseSupplyEstimate() {

        var cached = (List<HouseSupplyEstimateDataPoint>) cache.getIfPresent(CacheKey.HOUSE_SUPPLY_ESTIMATE);
        if (cached != null) return cached;

        var uri = URI.create(baseUrl + "series/observations?series_id=ETOTALUSQ176N&frequency=q&file_type=json&api_key=" + tokenSecret);
        var response = request(uri);
        var houseSupplyEstimateIndexResponse = jsonParse(response, HouseSupplyEstimateIndexResponse.class);

        cache.put(CacheKey.HOUSE_SUPPLY_ESTIMATE, houseSupplyEstimateIndexResponse.observations());
        return houseSupplyEstimateIndexResponse.observations();

    }

    public List<ConsumerPriceDataPoint> consumerPriceIndex() {

        var cached = (List<ConsumerPriceDataPoint>) cache.getIfPresent(CacheKey.CONSUMER_PRICE_INDEX);
        if (cached != null) return cached;

        var uri = URI.create(baseUrl + "series/observations?series_id=USACPIALLMINMEI&frequency=m&file_type=json&api_key=" + tokenSecret);
        var response = request(uri);
        var consumerPriceIndexResponse = jsonParse(response, ConsumerPriceIndexResponse.class);

        // First 59 dates from API are empty valued, clean up
        consumerPriceIndexResponse.observations().subList(0, 60).clear();

        cache.put(CacheKey.CONSUMER_PRICE_INDEX, consumerPriceIndexResponse.observations());
        return consumerPriceIndexResponse.observations();

    }

    public List<Spy500DataPoint> spy500Prices() {

        var cached = (List<Spy500DataPoint>) cache.getIfPresent(CacheKey.SPY_500_PRICES);
        if (cached != null) return cached;

        var uri = URI.create(baseUrl + "series/observations?series_id=SP500&frequency=w&file_type=json&api_key=" + tokenSecret);
        var response = request(uri);
        var spy500Response = jsonParse(response, Spy500Response.class);

        cache.put(CacheKey.SPY_500_PRICES, spy500Response.observations());
        return spy500Response.observations();

    }

    public List<CrudeBirthRateDataPoint> worldBirthRates() {

        var cached = (List<CrudeBirthRateDataPoint>) cache.getIfPresent(CacheKey.WORLD_BIRTH_RATES);
        if (cached != null) return cached;

        var uri = URI.create(baseUrl + "series/observations?series_id=SPDYNCBRTINWLD&frequency=a&file_type=json&api_key=" + tokenSecret);
        var response = request(uri);
        var crudeBirthRateResponse = jsonParse(response, CrudeWorldBirthRateResponse.class);

        cache.put(CacheKey.WORLD_BIRTH_RATES, crudeBirthRateResponse.observations());
        return crudeBirthRateResponse.observations();

    }

    public List<WorldLifeExpectancyDataPoint> worldLifeExpectancies() {

        var cached = (List<WorldLifeExpectancyDataPoint>) cache.getIfPresent(CacheKey.WORLD_LIFE_EXPECTANCY);
        if (cached != null) return cached;

        var uri = URI.create(baseUrl + "series/observations?series_id=SPDYNLE00INWLD&frequency=a&file_type=json&api_key=" + tokenSecret);
        var response = request(uri);
        var worldLifeExpectancyRateResponse = jsonParse(response, WorldLifeExpectancyRateResponse.class);

        // First 10 years from 1950-1959 (inclusive) are empty valued, clean up
        worldLifeExpectancyRateResponse.observations().subList(0, 10).clear();

        cache.put(CacheKey.WORLD_LIFE_EXPECTANCY, worldLifeExpectancyRateResponse.observations());
        return worldLifeExpectancyRateResponse.observations();

    }

    public List<UnemploymentRateDataPoint> unemploymentRates() {

        var cached = (List<UnemploymentRateDataPoint>) cache.getIfPresent(CacheKey.UNEMPLOYMENT_RATES);
        if (cached != null) return cached;

        var uri = URI.create(baseUrl + "series/observations?series_id=UNRATE&frequency=m&file_type=json&api_key=" + tokenSecret);
        var response = request(uri);
        var unemploymentRateResponse = jsonParse(response, UnemploymentRateResponse.class);

        cache.put(CacheKey.UNEMPLOYMENT_RATES, unemploymentRateResponse.observations());
        return unemploymentRateResponse.observations();

    }

    public List<GrossDomesticProductDataPoint> grossDomesticProduct() {

        var cached = (List<GrossDomesticProductDataPoint>) cache.getIfPresent(CacheKey.GROSS_DOMESTIC_PRODUCT);
        if (cached != null) return cached;

        var uri = URI.create(baseUrl + "series/observations?series_id=GDP&frequency=q&file_type=json&api_key=" + tokenSecret);
        var response = request(uri);
        var grossDomesticProductResponse = jsonParse(response, GrossDomesticProductResponse.class);

        // First 4 dates from API are empty valued, clean up
        grossDomesticProductResponse.observations().subList(0, 4).clear();

        cache.put(CacheKey.GROSS_DOMESTIC_PRODUCT, grossDomesticProductResponse.observations());
        return grossDomesticProductResponse.observations();

    }

    public List<RegularGasPriceDataPoint> regularGasPrices() {

        var cached = (List<RegularGasPriceDataPoint>) cache.getIfPresent(CacheKey.REGULAR_GAS_PRICES);
        if (cached != null) return cached;

        var uri = URI.create(baseUrl + "series/observations?series_id=GASREGCOVW&frequency=q&file_type=json&api_key=" + tokenSecret);
        var response = request(uri);
        var regularGasPricesResponse = jsonParse(response, RegularGasPricesResponse.class);

        cache.put(CacheKey.REGULAR_GAS_PRICES, regularGasPricesResponse.observations());
        return regularGasPricesResponse.observations();

    }

    public List<NetworthHeldByTopOnePercentDataPoint> networthHeldByTop1Percent() {

        var cached = (List<NetworthHeldByTopOnePercentDataPoint>) cache.getIfPresent(CacheKey.NETWORTH_HELD_TOP_1_PERCENT);
        if (cached != null) return cached;

        var uri = URI.create(baseUrl + "series/observations?series_id=WFRBLT01026&frequency=q&file_type=json&api_key=" + tokenSecret);
        var response = request(uri);
        var networthHeldByTopOnePercentResponse = jsonParse(response, NetworthHeldByTopOnePercentResponse.class);

        cache.put(CacheKey.NETWORTH_HELD_TOP_1_PERCENT, networthHeldByTopOnePercentResponse.observations());
        return networthHeldByTopOnePercentResponse.observations();

    }

    public List<MedianPersonalIncomeDataPoint> medianPersonalIncomes() {

        var cached = (List<MedianPersonalIncomeDataPoint>) cache.getIfPresent(CacheKey.MEDIAN_PERSONAL_INCOMES);
        if (cached != null) return cached;

        var uri = URI.create(baseUrl + "series/observations?series_id=MEPAINUSA646N&frequency=a&file_type=json&api_key=" + tokenSecret);
        var response = request(uri);
        var medianPersonalIncomeResponse = jsonParse(response, MedianPersonalIncomeResponse.class);

        cache.put(CacheKey.MEDIAN_PERSONAL_INCOMES, medianPersonalIncomeResponse.observations());
        ;
        return medianPersonalIncomeResponse.observations();

    }

    public List<InterestRatesDataPoint> interestRates() {

        var cached = (List<InterestRatesDataPoint>) cache.getIfPresent(CacheKey.INTEREST_RATES);
        if (cached != null) return cached;

        var uri = URI.create(baseUrl + "series/observations?series_id=FEDFUNDS&frequency=m&file_type=json&api_key=" + tokenSecret);
        var response = request(uri);
        var interestRatesResponse = jsonParse(response, InterestRatesResponse.class);

        cache.put(CacheKey.INTEREST_RATES, interestRatesResponse.observations());
        return interestRatesResponse.observations();

    }

    public List<JobVacanciesDataPoint> jobVacancies() {

        var cached = (List<JobVacanciesDataPoint>) cache.getIfPresent(CacheKey.JOB_VACANCIES);
        if (cached != null) return cached;

        var uri = URI.create(baseUrl + "series/observations?series_id=LMJVTTUVUSQ647S&frequency=q&file_type=json&api_key=" + tokenSecret);
        var response = request(uri);
        var jobVacanciesResponse = jsonParse(response, JobVacanciesResponse.class);

        cache.put(CacheKey.JOB_VACANCIES, jobVacanciesResponse.observations());
        return jobVacanciesResponse.observations();

    }

    public List<RecessionProbabilityDataPoint> recessionProbabilities() {

        var cached = (List<RecessionProbabilityDataPoint>) cache.getIfPresent(CacheKey.RECESSION_PROBABILITIES);
        if (cached != null) return cached;

        var uri = URI.create(baseUrl + "series/observations?series_id=RECPROUSM156N&frequency=m&file_type=json&api_key=" + tokenSecret);
        var response = request(uri);
        var recessionProbabilityResponse = jsonParse(response, RecessionProbabilityResponse.class);

        cache.put(CacheKey.RECESSION_PROBABILITIES, recessionProbabilityResponse.observations());
        return recessionProbabilityResponse.observations();

    }

    private HttpResponse<String> request(URI uri) {
        try {
            return httpClient.send(HttpRequest.newBuilder().uri(uri).GET().build(), HttpResponse.BodyHandlers.ofString());
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

    enum CacheKey {
        M2_MONEY_SUPPLY,
        HOUSE_PRICES,
        HOUSE_SUPPLY_ESTIMATE,
        CONSUMER_PRICE_INDEX,
        GROSS_DOMESTIC_PRODUCT,
        SPY_500_PRICES,
        NETWORTH_HELD_TOP_1_PERCENT,
        UNEMPLOYMENT_RATES,
        REGULAR_GAS_PRICES,
        WORLD_BIRTH_RATES,
        WORLD_LIFE_EXPECTANCY,
        MEDIAN_PERSONAL_INCOMES,
        INTEREST_RATES,
        JOB_VACANCIES,
        RECESSION_PROBABILITIES,
    }

    private record M2MoneySupplyResponse(List<M2MoneySupplyDataPoint> observations) {}

    public record M2MoneySupplyDataPoint(String date, String value) {}

    private record HousePriceIndexResponse(List<HousePriceDataPoint> observations) {}

    public record HousePriceDataPoint(String date, String value) {}

    private record HouseSupplyEstimateIndexResponse(List<HouseSupplyEstimateDataPoint> observations) {}

    public record HouseSupplyEstimateDataPoint(String date, String value) {}

    private record ConsumerPriceIndexResponse(List<ConsumerPriceDataPoint> observations) {}

    public record ConsumerPriceDataPoint(String date, String value) {}

    private record Spy500Response(List<Spy500DataPoint> observations) {}

    public record Spy500DataPoint(String date, String value) {}

    private record CrudeWorldBirthRateResponse(List<CrudeBirthRateDataPoint> observations) {}

    public record CrudeBirthRateDataPoint(String date, String value) {}

    private record WorldLifeExpectancyRateResponse(List<WorldLifeExpectancyDataPoint> observations) {}

    public record WorldLifeExpectancyDataPoint(String date, String value) {}

    private record UnemploymentRateResponse(List<UnemploymentRateDataPoint> observations) {}

    public record UnemploymentRateDataPoint(String date, String value) {}

    private record GrossDomesticProductResponse(List<GrossDomesticProductDataPoint> observations) {}

    public record GrossDomesticProductDataPoint(String date, String value) {}

    private record RegularGasPricesResponse(List<RegularGasPriceDataPoint> observations) {}

    public record RegularGasPriceDataPoint(String date, String value) {}

    private record NetworthHeldByTopOnePercentResponse(List<NetworthHeldByTopOnePercentDataPoint> observations) {}

    public record NetworthHeldByTopOnePercentDataPoint(String date, String value) {}

    private record MedianPersonalIncomeResponse(List<MedianPersonalIncomeDataPoint> observations) {}

    public record MedianPersonalIncomeDataPoint(String date, String value) {}

    private record InterestRatesResponse(List<InterestRatesDataPoint> observations) {}

    public record InterestRatesDataPoint(String date, String value) {}

    private record JobVacanciesResponse(List<JobVacanciesDataPoint> observations) {}

    public record JobVacanciesDataPoint(String date, String value) {}

    private record RecessionProbabilityResponse(List<RecessionProbabilityDataPoint> observations) {}

    public record RecessionProbabilityDataPoint(String date, String value) {}

}
