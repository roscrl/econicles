@param boolean autofocus
@param services.IEXCloud.CompanyProfile profile = null
@param String logo = null

<div class="grid grid-cols-14 mt-1 sm:pt-[1rem] py-2 items-center mr-2 lg:mr-0">

    <a href="/" class="p-6 hover:fill-blue-600 col-span-2 justify-self-center">
        <svg class="h-7 w-7 sm:h-10 sm:w-10" viewBox="0 0 48 48"
             xmlns="http://www.w3.org/2000/svg">
            <path d="M25 7h-2L5 26a1 1 0 0 0 0 1v18l1 2 2 1h10l1-1V31h10v16l1 1h10l2-1 1-2V27a1 1 0 0 0 0-1L25 7Z"/>
            <path d="m47 23-7-8V2h-8v5l-6-6-2-1-2 1L1 23l-1 2 1 1a2 2 0 0 0 2-1L23 4h2l20 21 1 1 2-1-1-2Z"/>
        </svg>
    </a>

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
            <a href="/stock/AAPL"
               class="border rounded-md px-2 sm:w-[4rem] text-center betterhover:hover:bg-black focus-within:bg-black betterhover:hover:text-white">AAPL</a>
            <a href="/stock/MSFT"
               class="border rounded-md px-2 sm:w-[4rem] text-center betterhover:hover:bg-[#005DA6] betterhover:hover:text-white">MSFT</a>
            <a href="/stock/AMZN"
               class="border rounded-md px-2 sm:w-[4rem] text-center betterhover:hover:bg-[#FF9900] betterhover:hover:text-white">AMZN</a>
            <a href="/stock/TSLA"
               class="border rounded-md px-2 sm:w-[4rem] text-center betterhover:hover:bg-[#E31937] betterhover:hover:text-white">TSLA</a>
            <a href="/stock/BRK.B"
               class="border rounded-md px-2 sm:w-[4rem] text-center betterhover:hover:bg-[#2D2D94] betterhover:hover:text-white">BRK.B</a>
            <a href="/stock/JPM"
               class="border rounded-md px-2 sm:w-[4rem] text-center betterhover:hover:bg-[#503629] betterhover:hover:text-white">JPM</a>
        </div>

        @template.stock_search.components.stock_search_results()

    </form>

    <div class="col-span-full justify-self-center mt-4 xl:mt-0 xl:col-start-11 xl:col-end-15">
        @if(profile != null)
            <div class="flex justify-between items-center max-w-[120rem] px-2 sm:px-0">

                <div class="grid gap-x-3 grid-cols-2 grid-rows-2 mr-5 h-24">
                    @if(profile.companyName() != null)
                        <div class="flex gap-3 items-center ${profile.CEO() == null ? "col-span-2" : ""}">
                            <svg width="24" height="24" class="min-w-[24px] min-h-[24px]" viewBox="0 0 24 24"
                                 fill="none"
                                 xmlns="http://www.w3.org/2000/svg">
                                <path d="M21 14.255C18.1405 15.4112 15.0844 16.0038 12 16C8.817 16 5.78 15.38 3 14.255M12 13H12.01M16 7V5C16 4.46957 15.7893 3.96086 15.4142 3.58579C15.0391 3.21071 14.5304 3 14 3H10C9.46957 3 8.96086 3.21071 8.58579 3.58579C8.21071 3.96086 8 4.46957 8 5V7H16ZM5 21H19C19.5304 21 20.0391 20.7893 20.4142 20.4142C20.7893 20.0391 21 19.5304 21 19V9C21 8.46957 20.7893 7.96086 20.4142 7.58579C20.0391 7.21071 19.5304 7 19 7H5C4.46957 7 3.96086 7.21071 3.58579 7.58579C3.21071 7.96086 3 8.46957 3 9V19C3 19.5304 3.21071 20.0391 3.58579 20.4142C3.96086 20.7893 4.46957 21 5 21Z"
                                      stroke="black" stroke-width="2" stroke-linecap="round"
                                      stroke-linejoin="round"/>
                            </svg>
                            <a href="https://en.wikipedia.org/wiki/${profile.companyName()}" target="_blank"
                               class="truncate ${profile.CEO() != null ? "max-w-[10rem]" : "max-w-[20rem]"} hover:text-blue-600 py-2 pr-3">${profile.companyName()}</a>
                        </div>
                    @endif

                    @if(profile.CEO() != null)
                        <div class="flex gap-3 items-center">
                            <svg width="24" height="24" class="min-w-[24px] min-h-[24px]" viewBox="0 0 24 24"
                                 fill="none"
                                 xmlns="http://www.w3.org/2000/svg">
                                <path d="M16 7C16 8.06087 15.5786 9.07828 14.8284 9.82843C14.0783 10.5786 13.0609 11 12 11C10.9391 11 9.92172 10.5786 9.17157 9.82843C8.42143 9.07828 8 8.06087 8 7C8 5.93913 8.42143 4.92172 9.17157 4.17157C9.92172 3.42143 10.9391 3 12 3C13.0609 3 14.0783 3.42143 14.8284 4.17157C15.5786 4.92172 16 5.93913 16 7V7ZM12 14C10.1435 14 8.36301 14.7375 7.05025 16.0503C5.7375 17.363 5 19.1435 5 21H19C19 19.1435 18.2625 17.363 16.9497 16.0503C15.637 14.7375 13.8565 14 12 14V14Z"
                                      stroke="black" stroke-width="2" stroke-linecap="round"
                                      stroke-linejoin="round"/>
                            </svg>
                            <a href="https://en.wikipedia.org/wiki/${profile.CEO()}" target="_blank"
                               class="truncate max-w-[10rem] hover:text-blue-600 py-2 pr-3">${profile.CEO()}</a>
                        </div>
                    @endif

                    @if(profile.employees() != null)
                        <div class="flex gap-3 items-center">
                            <svg width="24" height="24" class="min-w-[24px] min-h-[24px]" viewBox="0 0 24 24"
                                 fill="none"
                                 xmlns="http://www.w3.org/2000/svg">
                                <path d="M17 20H22V18C22 17.3765 21.8057 16.7686 21.4441 16.2606C21.0826 15.7527 20.5718 15.37 19.9827 15.1658C19.3937 14.9615 18.7556 14.9459 18.1573 15.121C17.5589 15.2962 17.03 15.6534 16.644 16.143M17 20L16.644 16.143M17 20H7M17 20V18C17 17.344 16.874 16.717 16.644 16.143M16.644 16.143C16.2726 15.215 15.6318 14.4195 14.804 13.8591C13.9762 13.2988 12.9996 12.9993 12 12.9993C11.0004 12.9993 10.0238 13.2988 9.196 13.8591C8.36825 14.4195 7.72736 15.215 7.356 16.143M7 20H2V18C2.00005 17.3765 2.19434 16.7686 2.55586 16.2606C2.91739 15.7527 3.42819 15.37 4.01725 15.1658C4.60632 14.9615 5.24438 14.9459 5.84274 15.121C6.4411 15.2962 6.97003 15.6534 7.356 16.143M7 20L7.356 16.143M7 20V18C7 17.344 7.126 16.717 7.356 16.143M15 7C15 7.79565 14.6839 8.55871 14.1213 9.12132C13.5587 9.68393 12.7956 10 12 10C11.2044 10 10.4413 9.68393 9.87868 9.12132C9.31607 8.55871 9 7.79565 9 7C9 6.20435 9.31607 5.44129 9.87868 4.87868C10.4413 4.31607 11.2044 4 12 4C12.7956 4 13.5587 4.31607 14.1213 4.87868C14.6839 5.44129 15 6.20435 15 7ZM21 10C21 10.5304 20.7893 11.0391 20.4142 11.4142C20.0391 11.7893 19.5304 12 19 12C18.4696 12 17.9609 11.7893 17.5858 11.4142C17.2107 11.0391 17 10.5304 17 10C17 9.46957 17.2107 8.96086 17.5858 8.58579C17.9609 8.21071 18.4696 8 19 8C19.5304 8 20.0391 8.21071 20.4142 8.58579C20.7893 8.96086 21 9.46957 21 10ZM7 10C7 10.5304 6.78929 11.0391 6.41421 11.4142C6.03914 11.7893 5.53043 12 5 12C4.46957 12 3.96086 11.7893 3.58579 11.4142C3.21071 11.0391 3 10.5304 3 10C3 9.46957 3.21071 8.96086 3.58579 8.58579C3.96086 8.21071 4.46957 8 5 8C5.53043 8 6.03914 8.21071 6.41421 8.58579C6.78929 8.96086 7 9.46957 7 10Z"
                                      stroke="black" stroke-width="2" stroke-linecap="round"
                                      stroke-linejoin="round"/>
                            </svg>
                            <span class="truncate max-w-[10rem]">${profile.employeesCommaFormatted()}</span>
                        </div>
                    @else
                        <div>
                        </div>
                    @endif

                    @if(profile.sector() != null)
                        <div class="flex gap-3 items-center">
                            <svg width="24" height="24" class="min-w-[24px] min-h-[24px]" viewBox="0 0 24 24"
                                 fill="none"
                                 xmlns="http://www.w3.org/2000/svg">
                                <path d="M12 21H15M3 6L6 7L3 6ZM6 7L3 16C3.8657 16.649 4.91852 16.9999 6.0005 16.9999C7.08248 16.9999 8.1353 16.649 9.001 16L6 7ZM6 7L9 16L6 7ZM6 7L12 5L6 7ZM18 7L21 6L18 7ZM18 7L15 16C15.8657 16.649 16.9185 16.9999 18.0005 16.9999C19.0825 16.9999 20.1353 16.649 21.001 16L18 7ZM18 7L21 16L18 7ZM18 7L12 5L18 7ZM12 3V5V3ZM12 21V5V21ZM12 21H9H12Z"
                                      stroke="black" stroke-width="2" stroke-linecap="round"
                                      stroke-linejoin="round"/>
                            </svg>

                            <div class="flex-col max-w-[10rem] leading-tight">
                                <span class="truncate block">${profile.industry()}</span>
                                <span class="truncate block">${profile.sector()}</span>
                            </div>

                        </div>
                    @else
                        <div>
                        </div>
                    @endif

                </div>

                @if(profile.website() != null)
                    <a href="${profile.websiteFormatted()}" data-turbo="false" target="_blank">
                        <img width="96px" height="96px" alt="${profile.companyName()}"
                             class="rounded-md fast-fade hidden sm:inline" src="${logo}"/>
                    </a>
                @else
                    <img width="96px" height="96px" alt="${profile.companyName()}"
                         class="rounded-md fast-fade hidden sm:inline"
                         src="${logo}"/>
                @endif

            </div>
        @endif
    </div>

</div>
