@param boolean autofocus

<form action="/stock/search" method="get"
      data-controller="stock-search" id="search_form"
      class="flex flex-col relative col-span-12 lg:col-start-5 lg:col-end-11 xl:col-start-6 xl:col-end-10 2xl:col-start-7 2xl:col-end-9 2xl:w-[24rem] 2xl:place-self-center">

    <input type="text" name="search"
           data-stock-search-target="search"
           data-action="stock-search#submit"
           class="border rounded-md px-2 md:px-3 h-12 text-2xl lg:w-full lg:mx-auto"
           placeholder="APPL Apple..." autofocus="${autofocus}" autocomplete="off">

    <div class="flex justify-between lg:w-[24rem] lg:mx-auto mt-2 gap-1 sm:gap-1 pb-[0.7rem] overflow-x-auto"
         data-stock-search-target="symbolHotbar">
        <a href="/stock/AAPL" up-follow up-preload
           class="border rounded-md px-2 sm:w-[4rem] text-center betterhover:hover:bg-black betterhover:hover:text-white focus:bg-black focus:text-white outline-0">AAPL</a>
        <a href="/stock/MSFT" up-follow up-preload
           class="border rounded-md px-2 sm:w-[4rem] text-center betterhover:hover:bg-[#005DA6] betterhover:hover:text-white focus:bg-[#005DA6] focus:text-white outline-0">MSFT</a>
        <a href="/stock/AMZN" up-follow up-preload
           class="border rounded-md px-2 sm:w-[4rem] text-center betterhover:hover:bg-[#FF9900] betterhover:hover:text-white focus:bg-[#FF9900] focus:text-white outline-0">AMZN</a>
        <a href="/stock/TSLA" up-follow up-preload
           class="border rounded-md px-2 sm:w-[4rem] text-center betterhover:hover:bg-[#E31937] betterhover:hover:text-white focus:bg-[#E31937] focus:text-white outline-0">TSLA</a>
        <a href="/stock/BRK.B" up-follow up-preload
           class="border rounded-md px-2 sm:w-[4rem] text-center betterhover:hover:bg-[#2D2D94] betterhover:hover:text-white focus:bg-[#2D2D94] focus:text-white outline-0">BRK.B</a>
        <a href="/stock/JPM" up-follow up-preload
           class="border rounded-md px-2 sm:w-[4rem] text-center betterhover:hover:bg-[#503629] betterhover:hover:text-white focus:bg-[#503629] focus:text-white outline-0">JPM</a>
    </div>

    @template.stock_search.components.search_results()

</form>