package helpers;

import io.javalin.http.Context;
import io.javalin.plugin.rendering.template.JavalinJte;

import java.util.Map;

public class Turbo {

    public static void renderFrame(String filePath, Map<String, ?> model, Context ctx) {
        ctx.render(filePath, model);
        ctx.header("Content-Type", "text/vnd.turbo-stream.html");
    }

    public static String sseStream(String template, Map<String, Object> model, Context ctx) {
        try {
            var rendered = JavalinJte.INSTANCE.render(template, model, ctx);
            var htmlOnOneLine = rendered.replaceAll("\n", "");

            return htmlOnOneLine;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Failed to render template");
    }

}
