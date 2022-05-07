@import configuration.Env
@param String title

<head>
    <meta charset="UTF-8">
    <title>${title}</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="/assets/tailwind.css">
    <link rel="stylesheet" type="text/css" href="/assets/main.css">
    <link rel="icon" type="image/svg+xml" href="/assets/favicon.svg" sizes="any"/>
    <meta name="turbo-cache-control" content="no-cache">

    <script async src="/webjars/es-module-shims/1.5.2/dist/es-module-shims.js"></script>

    <script type="importmap">
            {
              "imports": {
                "turbo": "/webjars/hotwired__turbo/7.1.0/dist/turbo.es2017-esm.js",
                "stimulus": "/webjars/hotwired__stimulus/3.0.1/dist/stimulus.js",
                "echarts": "/webjars/echarts/5.3.1/dist/echarts.esm.js",
                "controllers": "../controllers/"
              }
            }


    </script>

    <script type="module">
        import * as Turbo from "turbo";
        import { Application } from 'stimulus';
        import StockSearchController from "/controllers/stock_search_controller.js"
        import BasicChartController from "/controllers/basic_chart_controller.js"


        window.Stimulus = Application.start()
        Stimulus.register("stock-search", StockSearchController)
        Stimulus.register("basic-chart", BasicChartController)

        @if(Env.stage == configuration.Env.Stage.DEV)
        const evtSource = new EventSource("/refreshDevMode");
        evtSource.addEventListener("refresh", (data) => {
            location.reload()
        })
        @endif

    </script>

</head>