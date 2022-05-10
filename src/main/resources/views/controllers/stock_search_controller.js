import { Controller } from 'stimulus';


export default class extends Controller {
    static targets = ["search", "symbolHotbar", "searchResults"]

    initialize() {
        this.watchForOutsideClicks()
    }

    submit() {
        if (this.searchTarget.value === "") {
            this.clearSearchResults()
            return
        }
        up.submit(this.searchTarget, {target: '#search_results', animation: "move-to-bottom"});
        this.disableTabbingSymbolHotBar()
    }

    disableTabbingSymbolHotBar() {
        for (const element of this.symbolHotbarTarget.children) {
            element.tabIndex = "-1"
        }
    }

    enableTabbingSymbolHotBar() {
        for (const element of this.symbolHotbarTarget.children) {
            element.tabIndex = "0"
        }
    }

    clearSearchResults() {
        this.enableTabbingSymbolHotBar()
        this.searchResultsTarget.replaceChildren()
    }

    watchForOutsideClicks() {
        document.addEventListener('click', (event) => {
            const withinBoundaries = event.composedPath().includes(this.searchTarget)

            if (!withinBoundaries) {
                this.clearSearchResults()
            }
        })
    }

}