package controllers;

import io.javalin.core.util.Header;
import io.javalin.http.Context;
import services.Fred;
import services.SlickCharts;

import java.util.Map;

import static java.util.Map.entry;

public class HomeController {

    private final Fred        fred;
    private final SlickCharts slickCharts;


    public HomeController(Fred fred, SlickCharts slickCharts) {
        this.fred = fred;
        this.slickCharts = slickCharts;
    }

    public void indexHandler(Context ctx) {

        // try http streaming

        ctx.render("pages/home.jte", data());

        ctx.header(Header.CACHE_CONTROL, "max-age=604800"); // 1 week

    }

    private Map<String, ?> data() {

        return Map.ofEntries(
                entry("m2MoneySupply", fred.m2MoneySupply()),
                entry("housePrices", fred.housePrices()),
                entry("houseSupplyEstimate", fred.houseSupplyEstimate()),
                entry("consumerIndexPrices", fred.consumerPriceIndex()),
                entry("birthRateWorld", fred.worldBirthRates()),
                entry("worldLifeExpectancies", fred.worldLifeExpectancies()),
                entry("unemploymentRates", fred.unemploymentRates()),
                entry("grossDomesticProduct", fred.grossDomesticProduct()),
                entry("regularGasPrices", fred.regularGasPrices()),
                entry("networthTop1Percent", fred.networthHeldByTop1Percent()),
                entry("medianPersonalIncomes", fred.medianPersonalIncomes()),
                entry("interestRates", fred.interestRates()),
                entry("jobVacancies", fred.jobVacancies()),
                entry("recessionProbability", fred.recessionProbabilities()),
                entry("spy500Prices", fred.spy500Prices()),
                entry("spy500Companies", slickCharts.getSpy500())
        );
    }

}
