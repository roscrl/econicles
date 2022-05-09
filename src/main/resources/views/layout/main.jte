@param String title = "Econicles"
@param gg.jte.Content searchBar = null
@param gg.jte.Content content

<!DOCTYPE html>
<html lang="en" class="overflow-x-hidden">

<body>

@template.components.head(title = title)

${searchBar == null ? @`@template.components.searchBar(autofocus = false)` : searchBar}

${content}

</body>

</html>