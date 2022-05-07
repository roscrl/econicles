package configuration;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;

import java.nio.file.Path;

public class Template {

    public static TemplateEngine createTemplateEngine() {
        return switch (Env.stage) {
            case PROD -> {
                var templateEngine = TemplateEngine.createPrecompiled(Path.of("jte-classes"), ContentType.Html);
                templateEngine.setCompileArgs("--enable-preview", "--release", "" + Runtime.version().feature());
                yield templateEngine;
            }
            case DEV -> {
                DirectoryCodeResolver codeResolver = new DirectoryCodeResolver(Path.of("src", "main", "resources", "views"));
                var templateEngine = TemplateEngine.create(codeResolver, ContentType.Html);
                templateEngine.setCompileArgs("--enable-preview", "--release", "" + Runtime.version().feature());
                yield templateEngine;
            }
        };
    }
}
