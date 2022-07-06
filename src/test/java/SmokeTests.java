import controllers.HomeController;
import controllers.StockController;
import org.junit.jupiter.api.Test;
import services.AlphaVantage;
import services.Fred;
import services.IEXCloud;
import services.SlickCharts;

import java.io.IOException;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class SmokeTests {

    @Test
    public void GET_to_symbol_page() {

        try (var javalin = App.create().javalin.start(0)) {

            given().port(javalin.port())
                    .when()
                    .get("/stock/AAPL")
                    .then()
                    .statusCode(200);
        }
    }

    @Test
    public void GET_to_search() {

        try (var javalin = App.create().javalin.start(0)) {

            given().port(javalin.port())
                    .when()
                    .get("/stock/search?search=appl")
                    .then()
                    .statusCode(200)
                    .body(containsString("Apple"));
        }
    }

    @Test
    public void GET_to_home() {

        var fred = spy(new Fred());
        doReturn(List.of()).when(fred).m2MoneySupply();
        doReturn(List.of()).when(fred).housePrices();
        doReturn(List.of()).when(fred).houseSupplyEstimate();
        doReturn(List.of()).when(fred).consumerPriceIndex();
        doReturn(List.of()).when(fred).spy500Prices();
        doReturn(List.of()).when(fred).worldBirthRates();
        doReturn(List.of()).when(fred).worldLifeExpectancies();
        doReturn(List.of()).when(fred).unemploymentRates();
        doReturn(List.of()).when(fred).grossDomesticProduct();
        doReturn(List.of()).when(fred).regularGasPrices();
        doReturn(List.of()).when(fred).networthHeldByTop1Percent();
        doReturn(List.of()).when(fred).medianPersonalIncomes();
        doReturn(List.of()).when(fred).interestRates();
        doReturn(List.of()).when(fred).jobVacancies();
        doReturn(List.of()).when(fred).recessionProbabilities();

        try (var javalin = new App(
                new App.Controllers(
                        new HomeController(fred, SlickCharts.INSTANCE),
                        new StockController(IEXCloud.INSTANCE, AlphaVantage.INSTANCE)
                )
        ).javalin.start(0)) {

            given().port(javalin.port())
                    .when()
                    .get("/")
                    .then()
                    .statusCode(200);
        }
    }

}
