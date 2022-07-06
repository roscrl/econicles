@param String title = "Econicles"
@param gg.jte.Content searchBar = null
@param gg.jte.Content content

<!DOCTYPE html>
<html lang="en" class="overflow-x-hidden">

@template.layout.head(title = title)

<body up-main="root" class="overflow-visible">

<header>
    ${searchBar == null ? @`@template.stock_search.search_bar(autofocus = false)` : searchBar}
</header>

<main>
    ${content}
</main>

</body>

</html>