import configuration.Env;
import configuration.Prometheus;
import controllers.HomeController;
import controllers.StockController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routing {

    static EndpointGroup routes(HomeController home, StockController stock) {

        return () -> {

            get("/", home::indexHandler);
            path("stock", () -> {
                get("/search", stock::searchHandler);
                get("/{symbol}", stock::indexHandler);
            });

            get("/prometheus", Prometheus::scrapeHandler);

            if (Env.stage == Env.Stage.DEV) {
                sse("refreshDevMode", new Dev()::devMode);
            }
        };
    }

}
