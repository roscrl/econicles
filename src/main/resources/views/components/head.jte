@import configuration.Env
@param String title

<head>
    <meta charset="UTF-8">
    <title>${title}</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="/assets/tailwind.css">
    <link rel="stylesheet" type="text/css" href="/assets/main.css">
    <link rel="icon" type="image/svg+xml" href="/assets/favicon.svg" sizes="any"/>

    <link rel="stylesheet" type="text/css" href="/webjars/unpoly/2.5.3/unpoly.css">

    <script async src="/webjars/es-module-shims/1.5.2/dist/es-module-shims.js"></script>

    <script type="importmap">
            {
              "imports": {
                "controllers": "/assets/controllers",

                "stimulus": "/webjars/hotwired__stimulus/3.0.1/dist/stimulus.js",
                "unpoly":   "/webjars/unpoly/2.5.3/unpoly.js",
                "echarts":  "/webjars/echarts/5.3.1/dist/echarts.esm.js"
              }
            }
    </script>

    <script type="module">
        import "unpoly"

        // stop highlighting changed fragment when navigating links
        up.fragment.config.navigateOptions.focus = false
        up.network.config.progressBar = true
        up.boot() // safari needs this for unpoly and importmap shim

        import { Application } from "stimulus"
        import StockSearchController from "/assets/controllers/stock_search_controller.js"
        import BasicChartController from "/assets/controllers/basic_chart_controller.js"

        window.Stimulus = Application.start()
        Stimulus.register("stock-search", StockSearchController)
        Stimulus.register("basic-chart", BasicChartController)

        @if(Env.stage == configuration.Env.Stage.DEV)
        // enable unpoly logging for console
        up.log.enable();

        // see Dev.java
        const evtSource = new EventSource("/refreshDevMode");
        evtSource.addEventListener("refresh", (data) => {
            location.reload()
        })
        @endif

    </script>

</head>