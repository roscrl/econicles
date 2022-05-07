package jobs;

import database.Db;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.IEXCloud;

import java.util.List;

public class UpdateSymbols {

    private static final Logger   log = LoggerFactory.getLogger(UpdateSymbols.class);
    private final        IEXCloud iexCloud;

    public UpdateSymbols(IEXCloud iexCloud) {
        this.iexCloud = iexCloud;
    }

    public void refreshTickerData() {
        var usTickers = iexCloud.tickers("us");

        if (usTickers.isEmpty()) {
            log.error("Tried to refresh ticker data but received empty response one of ticker lists");
            return;
        }

        Db.query.updateTickerTable(List.of(usTickers));
    }

}
