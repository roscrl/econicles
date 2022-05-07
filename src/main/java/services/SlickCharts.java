package services;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jodd.jerry.Jerry;
import models.Ticker;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SlickCharts {

    public static final SlickCharts INSTANCE = new SlickCharts();

    private final URI        slickCharts = URI.create("https://www.slickcharts.com/sp500");
    private final HttpClient httpClient  = HttpClient.newHttpClient();

    private final Cache<String, List<Ticker>> cache = Caffeine.newBuilder()
            .expireAfterWrite(7, TimeUnit.DAYS)
            .build();

    // Scraping from slickcharts.com, but respectfully. Maximum of one request every 7 days.
    public List<Ticker> getSpy500() {

        var cached = cache.getIfPresent("spy500");
        if (cached != null) return cached;

        Jerry doc = null;
        try {
            doc = Jerry.of(httpClient.send(HttpRequest.newBuilder().uri(slickCharts).GET().build(), HttpResponse.BodyHandlers.ofString()).body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Request to %s failed".formatted(slickCharts), e);
        }


        var tickers = new ArrayList<Ticker>();
        doc.s("tbody tr").each((html, index) -> {
            tickers.add(new Ticker(html.children().get(2).getTextContent(), html.children().get(1).getTextContent()));
            return true;
        });

        // Last 4 td's are not needed
        cache.put("spy500", tickers.subList(0, tickers.size() - 4));
        return tickers.subList(0, tickers.size() - 4);
    }

}
