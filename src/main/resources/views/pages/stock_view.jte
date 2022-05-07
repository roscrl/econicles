@import services.AlphaVantage
@import java.text.NumberFormat
@import java.util.List

@param String logo
@param services.IEXCloud.CompanyProfile profile
@param List<AlphaVantage.IncomeStatement> annualIncomeStatements

<!DOCTYPE html>
<html lang="en">

@template.components.head(title = profile.symbol())

<body>

@template.components.searchBar(autofocus = false, profile = profile, logo = logo)


<div class="flex flex-col items-center justify-center mt-6 mb-6">
    <div class="w-full sm:w-[30rem]">
        @for(var st : annualIncomeStatements)
            <div class="mt-4 w-full px-4">
                <span class="font-bold text-xl">${st.fiscalDateEnding().substring(0,4)}</span>
                <span class="flex justify-between block text-lg"><span>Total Revenue</span> <span>${st.formatted(st.totalRevenue())}</span></span>
                <span class="flex justify-between block text-lg"><span>Net Income</span> <span>${st.formatted(st.netIncome())}</span></span>
            </div>
        @endfor

    </div>
    @if(annualIncomeStatements.isEmpty())
        <div class="flex items-center gap-2 mt-10 sm:w-[24rem] px-3 sm:px-0">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 inline-block" fill="none" viewBox="0 0 24 24"
                 stroke="currentColor" stroke-width="2">
                <path stroke-linecap="round" stroke-linejoin="round"
                      d="M8.228 9c.549-1.165 2.03-2 3.772-2 2.21 0 4 1.343 4 3 0 1.4-1.278 2.575-3.006 2.907-.542.104-.994.54-.994 1.093m0 3h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
            </svg>
            <span class="inline-block">Oops, could not find annual income statements for ${profile.companyName()}</span>
        </div>
    @endif
</div>
</body>

</html>