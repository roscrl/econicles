@import models.Ticker
@import org.apache.commons.lang3.StringUtils
@param java.util.List<Ticker> tickers


<turbo-stream action="update" target="search_results">
    <template>
        <div>
            <ol class="flex flex-col items-center gap-1">
                @for(Ticker ticker : tickers)
                    <a href="/stock/${ticker.symbol()}"
                       tabindex="0"
                       class="border w-full rounded-md flex justify-between items-center h-12 p-2 hover:bg-blue-50">
                        <span class="inline-block">${ticker.symbol()}</span>
                        <span class="ml-4 block truncate">${ticker.name()}</span>
                    </a>
                @endfor
            </ol>
        </div>
    </template>
</turbo-stream>