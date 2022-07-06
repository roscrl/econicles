import { Controller } from 'stimulus';
import * as echarts from "echarts";

export default class extends Controller {
    static targets = ["chart"]

    connect() {
        const myChart = echarts.init(this.chartTarget, null, {renderer: 'svg', registerLocale: 'EN'});

        const dataset = this.element.dataset
        const dates = dataset.dates.split(" ")
        const values = dataset.values.split(" ")

        // zip dates + values together [ [date, value], [date,value]... ]
        const data = dates.map(function (date, i) {
            return [date, values[i]]
        });

        // clean data backwards until first pair with both values filled
        while (true) {
            const lastPair = data.at(-1)
            if (lastPair[0] === "." || lastPair[1] === ".") {
                data.pop()
            } else if (lastPair[0] === "" || lastPair[1] === "") {
                data.pop()
            } else {
                break
            }
        }

        const title = dataset.title;
        const link = dataset.link
        const yAxisTitle = dataset.ytitle
        const yAxisPaddingLeft = null == dataset.yleftpadding ? "14%" : dataset.yleftpadding;

        const dateFormat = new Intl.DateTimeFormat('en-US', {
            year: 'numeric', month: 'short', day: 'numeric'
        })
        const dateFormatMonthOnly = new Intl.DateTimeFormat('en-US', {
            month: 'short'
        })
        const numberFormat = new Intl.NumberFormat('en-US', {
            style: 'currency',
            currency: 'USD',
            minimumFractionDigits: 0,
            maximumFractionDigits: dataset.round ? parseInt(dataset.round) : 2,
        })

        // Even though we only pass in yyyy-MM-dd, we need to reset time after because user locale can change time
        const lastDate = new Date(data[data.length - 1][0])
        lastDate.setMilliseconds(0)
        lastDate.setSeconds(0)
        lastDate.setMinutes(0)
        lastDate.setHours(0)
        const now = new Date()

        const option = {
            title: {
                text: title,
                link: link
            },
            yAxis: {
                name: yAxisTitle,
                type: 'value',
                splitLine: {
                    show: false
                },
                axisLabel: {
                    margin: 2,
                    width: 200,
                    height: 20,
                    overflow: "truncate",
                    fontSize: 12
                }
            },
            xAxis: {
                axisPointer: {
                    label: {
                        show: false
                    },
                    lineStyle: {
                        color: "#000000",
                        opacity: 0.3,
                        type: "dashed"
                    }
                },
                type: 'time',
                axisTick: {
                    show: false,
                },
                axisLine: {
                    show: true,
                    lineStyle: {}
                },
                axisLabel: {
                    formatter: function (value) {
                        const current = new Date(value)
                        if (current.getTime() === lastDate.getTime() && now.getFullYear() === lastDate.getFullYear()) {
                            return dateFormatMonthOnly.format(current);
                        } else {
                            return current.getFullYear().toString()
                        }
                    },
                    showMaxLabel: true,
                    showMinLabel: true,
                },
            },
            tooltip: {
                trigger: 'axis',
                formatter: function (params) {
                    params = params[0].data
                    if (yAxisTitle.includes("$")) {
                        return dateFormat.format(new Date(params[0])) + " - " + numberFormat.format(params[1]);
                    } else {
                        return dateFormat.format(new Date(params[0])) + " - " + parseFloat(params[1]).toFixed(2);
                    }
                },
                axisPointer: {},
            },
            animation: false,
            series: [
                {
                    showSymbol: false,
                    data: data,
                    type: 'line',
                    lineStyle: {color: "#000000"},
                    itemStyle: {
                        color: "#000000"
                    }
                }
            ],
            grid: {
                left: yAxisPaddingLeft
            }
        };

        myChart.setOption(option);

    }

}