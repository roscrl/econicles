<!DOCTYPE html>
<html lang="en">

@template.components.head(title = "Econicles")

<body>

@template.components.searchBar(autofocus = false)

<div class="absolute top-1/4 left-1/2 transform -translate-y-1/2 -translate-x-1/2 px-4 w-full sm:w-96">
    <div class="flex gap-1">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor"
             stroke-width="2">
            <path stroke-linecap="round" stroke-linejoin="round"
                  d="M8.228 9c.549-1.165 2.03-2 3.772-2 2.21 0 4 1.343 4 3 0 1.4-1.278 2.575-3.006 2.907-.542.104-.994.54-.994 1.093m0 3h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
        </svg>
        <span class="font-semibold">Not Found</span>
    </div>
    <div class="mt-2">
        Good old 404, could not find that url in our books.
    </div>
</div>


</body>

</html>