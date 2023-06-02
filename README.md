# [econicles]

economy & stock viewer<br>

![econicles](https://i.imgur.com/0L7Jfa4.png)

API's used [AlphaVantage](https://www.alphavantage.co), [IEXCloud](https://iexcloud.io), [FRED](https://fred.stlouisfed.org) & [SlickCharts](https://www.slickcharts.com/sp500)

## local setup

```bash
  mvn install
```

Java 17 or use with [Jetbrains JDK 17](https://github.com/JetBrains/JetBrainsRuntime/releases/tag/jbr17.0.2b396.4) for hotreload

Run App.main() in debug mode (for hotswap) with arguments

```bash
-Denv=dev
-DiexCloudSecret=XXX
-DalphaVantageSecret=XXX
-DfredSecret=XXX
-Dlog4j2.configurationFile=log4j2-dev.xml
-XX:+AllowEnhancedClassRedefinition # if hotreload!
```

