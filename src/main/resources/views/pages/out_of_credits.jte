<!DOCTYPE html>
<html lang="en">

@template.components.head(title = "Econicles")

<body>

@template.components.searchBar(autofocus = false)

<div class="absolute top-1/4 left-1/2 transform -translate-y-1/2 -translate-x-1/2 px-2 w-full sm:w-96 mt-4 sm:mt-0">
    <div class="flex gap-1">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor"
             stroke-width="2">
            <path stroke-linecap="round" stroke-linejoin="round" d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"/>
        </svg>
        <span class="font-semibold">Out of Credits!</span>
    </div>
    <div class="mt-2">
        Dont worry, it's not you it's me. This site makes use of a <a href="https://iexcloud.io/"
                                                                      class="text-blue-500" target="_blank">generous
            free stock API
            provider</a> works on a credit system. Unfortunately it looks like we ran out of credits for this
        month.
    </div>
</div>


</body>

</html>