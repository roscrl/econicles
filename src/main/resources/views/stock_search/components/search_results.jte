@import models.Ticker
@import java.util.List

@param  List<Ticker> tickers = java.util.List.of()

<div id="search_results"
     data-stock-search-target="searchResults"
     class="absolute top-0 left-0 bg-white w-full translate-y-12 mt-1 font-semibold fast-fade z-10">
    <ol class="flex flex-col items-center gap-1">
        @for(Ticker ticker : tickers)
            <a href="/stock/${ticker.symbol()}"
               tabindex="0"
               up-follow
               class="border w-full rounded-md flex justify-between items-center h-12 p-2 hover:bg-blue-50">
                <span class="inline-block">${ticker.symbol()}</span>
                <span class="ml-4 block truncate">${ticker.name()}</span>
            </a>
        @endfor
    </ol>
</div>