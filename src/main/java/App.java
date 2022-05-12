import configuration.Env;
import configuration.Prometheus;
import configuration.Template;
import controllers.HomeController;
import controllers.StockController;
import io.javalin.Javalin;
import io.javalin.core.JavalinConfig;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.metrics.MicrometerPlugin;
import io.javalin.plugin.rendering.template.JavalinJte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.AlphaVantage;
import services.Fred;
import services.IEXCloud;
import services.SlickCharts;

import java.nio.file.Path;


public class App {

    private static final Logger log = LoggerFactory.getLogger(App.class);

    public final Javalin javalin;
    public final int     defaultPort = (Env.stage == Env.Stage.PROD) ? 80 : 8080;

    public App(HomeController home, StockController stock) {

        switch (Env.stage) {
            case PROD -> log.info("running in production");
            case DEV -> log.info("running in dev");
        }

        javalin = Javalin.create(config -> {
            JavalinJte.configure(Template.createTemplateEngine());
            config.registerPlugin(new MicrometerPlugin(Prometheus.registry));
            config.showJavalinBanner = false;
            setupStaticFiles(config);
            switch (Env.stage) {
                case DEV -> config.requestLogger((ctx, ms) -> log.info("{} {} {} {}ms", ctx.method(), ctx.path(), ctx.queryParamMap(), String.format("%.2f", ms)));
                case PROD -> config.requestLogger((ctx, ms) -> log.info("{} {} {} {} {}ms", ctx.ip(), ctx.method(), ctx.path(), ctx.queryParamMap(), String.format("%.2f", ms)));
            }
        });

        javalin.routes(Routing.routes(home, stock));
        setupErrorHandling(javalin);

    }

    public static App create() {
        return new App(
                new HomeController(Fred.INSTANCE, SlickCharts.INSTANCE),
                new StockController(IEXCloud.INSTANCE, AlphaVantage.INSTANCE)
        );
    }

    public static void main(String[] args) {
        var app = App.create();
        app.javalin.start(app.defaultPort);
    }

    private void setupStaticFiles(JavalinConfig config) {
        config.addStaticFiles(staticFiles -> {

            if (Env.stage == Env.Stage.DEV) {
                staticFiles.location = Location.EXTERNAL;
                staticFiles.directory = Path.of("src", "main", "resources", "views", "assets").toAbsolutePath().toString();
                staticFiles.precompress = false;
            } else {
                staticFiles.location = Location.CLASSPATH;
                staticFiles.directory = "views/assets";
            }

            staticFiles.hostedPath = "/assets";
        });
        config.enableWebjars();
    }

    private void setupErrorHandling(Javalin javalin) {
        javalin.exception(AlphaVantage.TooManyRequests.class, (e, ctx) -> {
            ctx.render("pages/too_many_requests.jte");
        });
        javalin.exception(IEXCloud.TooManyRequests.class, (e, ctx) -> {
            ctx.render("pages/too_many_requests.jte");
        });
        javalin.exception(IEXCloud.OutOfCredits.class, (e, ctx) -> {
            ctx.render("pages/out_of_credits.jte");
        });
        javalin.error(404, ctx -> {
            ctx.render("pages/not_found.jte");
        });
    }

}
