@import java.util.List
@import models.Ticker
@import services.Fred

@param List<Fred.M2MoneySupplyDataPoint> m2MoneySupply
@param List<Fred.HousePriceDataPoint> housePrices
@param List<Fred.HouseSupplyEstimateDataPoint> houseSupplyEstimate
@param List<Fred.ConsumerPriceDataPoint> consumerIndexPrices
@param List<Fred.Spy500DataPoint> spy500Prices
@param List<Fred.CrudeBirthRateDataPoint> birthRateWorld
@param List<Fred.WorldLifeExpectancyDataPoint> worldLifeExpectancies
@param List<Fred.UnemploymentRateDataPoint> unemploymentRates
@param List<Fred.GrossDomesticProductDataPoint> grossDomesticProduct
@param List<Fred.RegularGasPriceDataPoint> regularGasPrices
@param List<Fred.NetworthHeldByTopOnePercentDataPoint> networthTop1Percent
@param List<Fred.MedianPersonalIncomeDataPoint> medianPersonalIncomes
@param List<Fred.InterestRatesDataPoint> interestRates
@param List<Fred.JobVacanciesDataPoint> jobVacancies
@param List<Fred.RecessionProbabilityDataPoint> recessionProbability

@param List<Ticker> spy500Companies

<!DOCTYPE html>
<html lang="en" class="overflow-x-hidden" style="-webkit-scrollbar">

@template.components.head(title = "Econicles")

<body>

@template.components.searchBar(autofocus = true)

<div class="flex flex-wrap justify-center mx-[0.2rem] sm:mx-0 sm:mt-0 md:mt-2 lg:mx-1 lg:gap-4 xl:mt-6 2xl:max-w-[150rem] 2xl:mx-auto 2xl:gap-x-12 2xl:mt-10">

    <div class="w-full sm:w-[22rem] lg:w-[28rem]  h-[22rem] fastest-fade" data-controller="basic-chart"
         data-basic-chart-target="chart"
         data-title="M2 Money Supply 🇺🇸"
         data-yTitle="$ billions"
         data-link="https://fred.stlouisfed.org/series/WM2NS"
         data-values="@for(var pair : m2MoneySupply)${pair.value()} @endfor"
         data-dates="@for(var pair : m2MoneySupply)${pair.date()} @endfor">
    </div>

    <div class="w-full sm:w-[22rem] lg:w-[28rem] h-[22rem] fastest-fade" data-controller="basic-chart"
         data-basic-chart-target="chart"
         data-title="Median House Price 🇺🇸"
         data-yTitle="$ dollars"
         data-link="https://fred.stlouisfed.org/series/MSPUS"
         data-values="@for(var pair : housePrices)${pair.value()} @endfor"
         data-dates="@for(var pair : housePrices)${pair.date()} @endfor">
    </div>

    <div class="w-full sm:w-[22rem] lg:w-[28rem] h-[22rem] fastest-fade" data-controller="basic-chart"
         data-basic-chart-target="chart"
         data-title="Total Housing Inventory Estimate 🇺🇸"
         data-yTitle="units"
         data-link="https://fred.stlouisfed.org/series/ETOTALUSQ176N"
         data-values="@for(var pair : houseSupplyEstimate)${pair.value()} @endfor"
         data-dates="@for(var pair : houseSupplyEstimate)${pair.date()} @endfor">
    </div>

    <div class="w-full sm:w-[22rem] lg:w-[28rem] h-[22rem] fastest-fade" data-controller="basic-chart"
         data-basic-chart-target="chart"
         data-title="Consumer Price Index 🇺🇸"
         data-yTitle="index"
         data-link="https://fred.stlouisfed.org/series/USACPIALLMINMEI"
         data-values="@for(var pair : consumerIndexPrices)${pair.value()} @endfor"
         data-dates="@for(var pair : consumerIndexPrices)${pair.date()} @endfor">
    </div>

    <div class="w-full sm:w-[22rem] lg:w-[28rem] h-[22rem] fastest-fade" data-controller="basic-chart"
         data-basic-chart-target="chart"
         data-title="S&P 500"
         data-yTitle="index"
         data-link="https://fred.stlouisfed.org/series/SP500"
         data-values="@for(var pair : spy500Prices)${pair.value()} @endfor"
         data-dates="@for(var pair : spy500Prices)${pair.date()} @endfor">
    </div>

    <div class="w-full sm:w-[22rem] lg:w-[28rem] h-[22rem] fastest-fade" data-controller="basic-chart"
         data-basic-chart-target="chart"
         data-title="World Birth Rate"
         data-yTitle="per 1,000 people"
         data-link="https://fred.stlouisfed.org/series/SPDYNCBRTINWLD"
         data-values="@for(var pair : birthRateWorld)${pair.value()} @endfor"
         data-dates="@for(var pair : birthRateWorld)${pair.date()} @endfor">
    </div>

    <div class="w-full sm:w-[22rem] lg:w-[28rem] h-[22rem] fastest-fade" data-controller="basic-chart"
         data-basic-chart-target="chart"
         data-title="World Life Expectancy"
         data-yTitle="age"
         data-link="https://fred.stlouisfed.org/series/SPDYNLE00INWLD"
         data-values="@for(var pair : worldLifeExpectancies)${pair.value()} @endfor"
         data-dates="@for(var pair : worldLifeExpectancies)${pair.date()} @endfor">
    </div>

    <div class="w-full sm:w-[22rem] lg:w-[28rem] h-[22rem] fastest-fade" data-controller="basic-chart"
         data-basic-chart-target="chart"
         data-title="Unemployment Rate 🇺🇸"
         data-yTitle="age 16 and over"
         data-link="https://fred.stlouisfed.org/series/UNRATE"
         data-values="@for(var pair : unemploymentRates)${pair.value()} @endfor"
         data-dates="@for(var pair : unemploymentRates)${pair.date()} @endfor">
    </div>

    <div class="w-full sm:w-[22rem] lg:w-[28rem] h-[22rem] fastest-fade" data-controller="basic-chart"
         data-basic-chart-target="chart"
         data-title="Gross Domestic Product 🇺🇸"
         data-yTitle="$ billions"
         data-link="https://fred.stlouisfed.org/series/GDP"
         data-values="@for(var pair : grossDomesticProduct)${pair.value()} @endfor"
         data-dates="@for(var pair : grossDomesticProduct)${pair.date()} @endfor">
    </div>

    <div class="w-full sm:w-[22rem] lg:w-[28rem] h-[22rem] fastest-fade" data-controller="basic-chart"
         data-basic-chart-target="chart"
         data-title="Regular Gas Price 🇺🇸"
         data-yTitle="$ dollars per gallon"
         data-link="https://fred.stlouisfed.org/series/GASREGCOVW"
         data-values="@for(var pair : regularGasPrices)${pair.value()} @endfor"
         data-dates="@for(var pair : regularGasPrices)${pair.date()} @endfor">
    </div>

    <div class="w-full sm:w-[22rem] lg:w-[28rem] h-[22rem] fastest-fade" data-controller="basic-chart"
         data-basic-chart-target="chart"
         data-title="Net Worth Held by the Top 1% 🇺🇸"
         data-yTitle="$ millions"
         data-yLeftPadding="19%"
         data-link="https://fred.stlouisfed.org/series/WFRBLT01026"
         data-values="@for(var pair : networthTop1Percent)${pair.value()} @endfor"
         data-dates="@for(var pair : networthTop1Percent)${pair.date()} @endfor">
    </div>

    <div class="w-full sm:w-[22rem] lg:w-[28rem] h-[22rem] fastest-fade" data-controller="basic-chart"
         data-basic-chart-target="chart"
         data-title="Median Personal Income 🇺🇸"
         data-yTitle="$ dollars"
         data-link="https://fred.stlouisfed.org/series/MEPAINUSA646N"
         data-values="@for(var pair : medianPersonalIncomes)${pair.value()} @endfor"
         data-dates="@for(var pair : medianPersonalIncomes)${pair.date()} @endfor">
    </div>

    <div class="w-full sm:w-[22rem] lg:w-[28rem] h-[22rem] fastest-fade" data-controller="basic-chart"
         data-basic-chart-target="chart"
         data-title="FED Interest Rate 🇺🇸"
         data-yTitle="%"
         data-link="https://fred.stlouisfed.org/series/FEDFUNDS"
         data-values="@for(var pair : interestRates)${pair.value()} @endfor"
         data-dates="@for(var pair : interestRates)${pair.date()} @endfor">
    </div>

    <div class="w-full sm:w-[22rem] lg:w-[28rem] h-[22rem] fastest-fade" data-controller="basic-chart"
         data-basic-chart-target="chart"
         data-title="Unfilled Job Vacancies 🇺🇸"
         data-yTitle="persons"
         data-yLeftPadding="19%"
         data-link="https://fred.stlouisfed.org/series/LMJVTTUVUSQ647S"
         data-round="0"
         data-values="@for(var pair : jobVacancies)${pair.value()} @endfor"
         data-dates="@for(var pair : jobVacancies)${pair.date()} @endfor">
    </div>

    <div class="w-full sm:w-[22rem] lg:w-[28rem] h-[22rem] fastest-fade" data-controller="basic-chart"
         data-basic-chart-target="chart"
         data-title="Recession Probabilities 🇺🇸"
         data-yTitle="%"
         data-link="https://fred.stlouisfed.org/series/RECPROUSM156N"
         data-values="@for(var pair : recessionProbability)${pair.value()} @endfor"
         data-dates="@for(var pair : recessionProbability)${pair.date()} @endfor">
    </div>
</div>

<div>
    <ol class="mx-2 2xl:mx-64 list-decimal list-inside lg:columns-3 md:columns-2">
        @for(var ticker : spy500Companies)
            <a href="/stock/${ticker.symbol()}" class="">
                <li class="hover:bg-black hover:text-white rounded-md px-2  mx-auto truncate">${ticker.name()}
                    <span class="float-right">${ticker.symbol()}</span></li>
            </a>
        @endfor
    </ol>
</div>

<footer class="float-right inline-block text-sm mb-4 mr-4">

    <a href="https://roscrl.com/" target="_blank" class="hover:bg-black hover:text-white rounded-md p-2">Created
        by
        Ross
        Carlisle</a>
</footer>

</body>


</html>