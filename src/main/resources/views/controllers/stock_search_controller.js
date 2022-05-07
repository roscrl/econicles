import { Controller } from 'stimulus';


export default class extends Controller {
    static targets = ["search"]

    initialize() {
        this.watchForOutsideClicks()
    }

    submit() {
        if (document.getElementById("stock_search_input").value === "") {
            this.clearSearchResults()
            return
        }
        this.element.requestSubmit()
        this.disableTabbingSymbolHotBar()
    }

    disableTabbingSymbolHotBar() {
        for (const element of document.getElementById("symbol_hotbar").children) {
            element.tabIndex = "-1"
        }
    }

    enableTabbingSymbolHotBar() {
        for (const element of document.getElementById("symbol_hotbar").children) {
            element.tabIndex = "0"
        }
    }

    clearSearchResults() {
        this.enableTabbingSymbolHotBar();
        document.getElementById("search_results").replaceChildren()
    }

    watchForOutsideClicks() {
        document.addEventListener('click', (event) => {
            const withinBoundaries = event.composedPath().includes(this.searchTarget)

            if (!withinBoundaries) {
                this.clearSearchResults();
            }
        })
    }

}