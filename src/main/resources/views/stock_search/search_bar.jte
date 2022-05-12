@param boolean autofocus
@param services.IEXCloud.CompanyProfile profile = null
@param String logo = null

<div class="grid grid-cols-14 mt-1 sm:pt-[1rem] py-2 items-center mr-2 lg:mr-0">

    <%-- Home Button --%>
    <a href="/"
       up-follow
       class="p-6 hover:fill-blue-600 col-span-2 justify-self-center">
        <svg class="h-7 w-7 sm:h-10 sm:w-10" viewBox="0 0 48 48"
             xmlns="http://www.w3.org/2000/svg">
            <path d="M25 7h-2L5 26a1 1 0 0 0 0 1v18l1 2 2 1h10l1-1V31h10v16l1 1h10l2-1 1-2V27a1 1 0 0 0 0-1L25 7Z"/>
            <path d="m47 23-7-8V2h-8v5l-6-6-2-1-2 1L1 23l-1 2 1 1a2 2 0 0 0 2-1L23 4h2l20 21 1 1 2-1-1-2Z"/>
        </svg>
    </a>

    @template.stock_search._form(autofocus = autofocus)

    @template.stock_search.components.company_profile(profile = profile, logo = logo)

</div>
