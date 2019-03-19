# SimpleWeather

How to use app:
The app is simple, open it and you can get the weather information for the searched cities which is using "auckland" as keyword, slide
view and you can get different weather information for different cities.

Feature implemented:
Display city details, Display current weather, Display forecast weather, Search city by keyword, Search city by location, Store the favourite cities, State restoration.

How to approach(Steps):

1.Transfer the business requirement into technical requirement, to follow the MVVM,  divde the app into three modules: simpleweatherapi as Module, simpleweatherlogic as ViewModel, app as View.

2.Create simpleweatherapi(Model): use ROOM to store the favourite cities as persistence, use Retrofit to search the cities and get weather information.

3.Create simpleweatherlogic(ViewModel): use RxAndroid and Observer DesignPatern to async the UI callback and backend task, for each of business case, provide specified function for it. Alternative, we can use Android Architecture Component such as ViewModel and LiveData in this layer.

4.Create app(View): use Dagger for Dependency Injection, make it provide ViewModel to different UI element.


The improvment:

1. Create more annotation about how some module works especially for the public api of it.

2. Create more Unit Test Case to test each module, this can help us to diagnose and analysis, and give you confidence about some core logic work that is working.

3. To follow the UI design, create more view, and especially we need to use Expresso to give some Unit Test of UI layer.

4. If it is not a example, we need to use Jenkins to create Continuous Integration (running Unit Test), and create build of debug app for testers.

5. If we want to put it onto the Google Play, we need to upload you debug build for alpha and beta testing before releasing them.
