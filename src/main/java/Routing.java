import configuration.Env;
import configuration.Prometheus;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routing {

    static EndpointGroup routes(App.Controllers controllers) {

        return () -> {

            get("/", controllers.homeController()::indexHandler);
            path("stock", () -> {
                get("/search", controllers.stockController()::searchHandler);
                get("/{symbol}", controllers.stockController()::indexHandler);
            });

            get("/prometheus", Prometheus::scrapeHandler);

            if (Env.stage == Env.Stage.DEV) {
                sse("refreshDevMode", new Dev()::devMode);
            }
        };
    }

}
