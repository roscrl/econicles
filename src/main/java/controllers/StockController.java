package controllers;

import database.Db;
import io.javalin.core.util.Header;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import org.apache.commons.lang3.StringUtils;
import services.AlphaVantage;
import services.IEXCloud;

import java.util.Map;

public class StockController {

    private final IEXCloud     iexCloud;
    private final AlphaVantage alphaVantage;

    public StockController(IEXCloud iexCloud, AlphaVantage alphaVantage) {
        this.iexCloud = iexCloud;
        this.alphaVantage = alphaVantage;
    }

    public void indexHandler(Context ctx) {
        var symbol = ctx.pathParamAsClass("symbol", String.class)
                .check(StringUtils::isNotBlank, "Empty symbol search")
                .check(Db.query::tickerExists, "Could not find ticker")
                .get();

        var annualIncomeStatements = alphaVantage.annualIncomeStatements(symbol);

        var model = Map.of(
                "logo", iexCloud.logo(symbol),
                "profile", iexCloud.companyProfile(symbol),
                "annualIncomeStatements", annualIncomeStatements);

        ctx.render("pages/stock_view.jte", model);
        ctx.header(Header.CACHE_CONTROL, "max-age=604800"); // 1 week cache
    }

    public void searchHandler(Context ctx) {
        var search = ctx.queryParamAsClass("search", String.class)
                .check(StringUtils::isNotBlank, "Empty stock search")
                .check(StringUtils::isAlphanumericSpace, "Non alphanumeric stock search")
                .get();

        var tickers = Db.query.tickerSearch(search);

        var model = Map.of("tickers", tickers);

        ctx.render("stock_search/components/search_results.jte", model);
        ctx.header(Header.CACHE_CONTROL, "max-age=604800"); // 1 week cache
    }

}
