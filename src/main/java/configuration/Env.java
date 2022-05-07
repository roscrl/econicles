package configuration;

public class Env {

    public static Stage  stage;
    public static String alphaVantageSecret;
    public static String fredSecret;
    public static String iexCloudSecret;

    static {
        stage = switch (System.getProperty("env")) {
            case "prod" -> Stage.PROD;
            case "dev" -> Stage.DEV;
            case null -> throw new IllegalStateException("no -Denv property set, use -Denv=prod or -Denv=dev in VM args");
            default -> throw new IllegalStateException("-Denv should be prod or dev");
        };

        alphaVantageSecret = switch (System.getProperty("alphaVantageSecret")) {
            case null -> throw new IllegalStateException("-DalphaVantageSecret should be set");
            case String s -> s;
        };

        fredSecret = switch (System.getProperty("fredSecret")) {
            case null -> throw new IllegalStateException("-DfredSecret should be set");
            case String s -> s;
        };

        iexCloudSecret = switch (System.getProperty("iexCloudSecret")) {
            case null -> throw new IllegalStateException("-DiexCloudSecret should be set");
            case String s -> s;
        };
    }

    public enum Stage {
        PROD,
        DEV,
    }

}
