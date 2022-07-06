package database;

import configuration.Env;
import models.Ticker;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlite3.SQLitePlugin;
import services.IEXCloud;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class Db {

    public static final Db query = new Db();

    private final Jdbi jdbi;

    private Db() {

        var dbPath = switch (Env.stage) {
            case PROD -> Path.of("main.sqlite3").toAbsolutePath();
            case DEV -> Path.of("src", "main", "resources", "db", "main.sqlite3").toAbsolutePath();
        };

        jdbi = Jdbi.create("jdbc:sqlite:" + dbPath)
                .installPlugin(new SQLitePlugin());

    }

    public List<Ticker> tickerSearch(String search) {
        return jdbi.withHandle(handle -> handle.createQuery("""
                        select *
                        from tickers
                        where symbol match :search
                           OR name   match :search
                        order by rank
                        LIMIT 20;
                        """).bind("search", search + "*")
                .map((rs, ctx) -> new Ticker(rs.getString("symbol"), rs.getString("name")))
                .list());
    }


    public boolean tickerExists(String symbol) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT EXISTS(SELECT 1 FROM tickers where symbol match :symbol) as present")
                .bind("symbol", "\"" + symbol + "\"")
                .map((rs, ctx) -> Objects.equals(rs.getString("present"), "1"))
                .first());
    }

    public List<Ticker> allTickers() {
        return jdbi.withHandle(handle -> handle.createQuery("""
                        SELECT name, symbol FROM tickers
                        """)
                .map((rs, ctx) -> new Ticker(rs.getString("symbol"), rs.getString("name")))
                .list());
    }

    public void updateTickerTable(List<List<IEXCloud.Ticker>> tickerLists) {
        jdbi.useHandle(handle -> {
            handle.useTransaction(transactionHandle -> {
                transactionHandle.execute("""
                            DELETE FROM tickers
                        """);

                var sql = new StringBuilder();
                sql.append("INSERT INTO tickers ('symbol','name','exchange','exchangeName','currency') VALUES ");
                for (List<IEXCloud.Ticker> tickerList : tickerLists) {
                    for (IEXCloud.Ticker ticker : tickerList) {
                        sql.append("('")
                                .append(ticker.symbol()).append("',")
                                .append("'").append(ticker.name()).append("',").append("'")
                                .append(ticker.exchange()).append("',").append("'")
                                .append(ticker.exchangeName()).append("',")
                                .append("'").append(ticker.currency())
                                .append("'),");
                    }
                }

                sql.deleteCharAt(sql.length() - 1);

                transactionHandle.execute(sql.toString());

            });
        });
    }

}
