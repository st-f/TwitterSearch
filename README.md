Twitter Search
====================

Demo application

<img src="screenshot_0.png" alt="capture" width="216" height="384"/>


---

Specifications, frameworks and libraries
------------------------

- SDK version: 23
- Build tools version: 23.0.3
- Minimum SDK version: 16
- Gradle Android tools version: 2.1.2
   
The app revolving around the Twitter API, we'll be using the Twitter Android SDK. The images will be loaded in memory using Volley.


Architecture
------------

Due to the limited scope, the architecture is minimal:

- The application
- The activity
- The service layer
- A Volley singleton to load images
- A ListAdapter


Testing
-------

As we rely entirely on the Twitter SDK, there is no need for pure unit tests.
An instrumentation test will be used to make sure the correct data is shown to the user.
In complement, a Calabash UI test will check the following scenario:
  
    Scenario: As a user I can tap on the search icon and trigger a search after typing a text
         When I see the text "To begin"
         Then I press view with id "search"
         Then I enter text "art" into field with id "search_src_text"
         Then I press the enter button
         Then I wait for the view with id "list" to appear
         Then I wait for 10 seconds
         Then I take a picture
     
Instructions to install Calabash can be found [here](https://github.com/calabash/calabash-android).

The calabash test can be run after generating a debug version of the APK with the following command:

	calabash-android run app/build/outputs/apk/app-debug.apk 