## Build tools
* IDE: Android Studio Electric Eel | 2022.1.1 Patch 2
* Gradle JDK: JetBrains Runtime Version 11.0.15
* Kotlin: 1.8.0

## Architecture
* MVVM is used
* Jetpack Navigation is used for navigation between screens
* MainActivity works as a container for the fragments
* NewsList is fetched in NewsService
* NewsRepository uses NewsService as a dependency
* NewsListViewModel uses NewsRepository as a dependency for data
* NewsListFragment is updated by NewsViewModel
* NewsListFragments shows a list of NewsItem
* NewsItem is a custom view that holds data for a single News

## Libraries used
* Retrofit is used to fetch data from API
* GSON is used to parse API response
* Glide is used to load images
* Jetpack Navigation is used for navigation purpose
* Koin is used for dependency injection
* Mockito is used for mocking data for tests
* Espresso is used for instrumented tests