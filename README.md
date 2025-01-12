# PotterPedia
Harry Potter Character Encyclopedia

🌟 **PotterPedia**

PotterPedia is an Android application built using Jetpack Compose that lists characters fetched from an API, allowing users to view their info. 
This offline-first application ensures data is available even without an internet connection. 
With a search bar for quick lookups, dark/light themes for personal preferences and scalable architecture, it's designed for an optimal user experience.

📱 **Features**

Character Listing: View a list of characters fetched from the API on the home screen.
Character Details: Tap on a character to navigate to an info page with more details.
Offline-First: Data is cached locally for seamless offline usage.
Search Functionality: Search characters quickly by their actor name or character name.
Scalable Architecture: Designed for future scalability and feature additions.
Modern Libraries and Tools:
Jetpack Compose: For a modern, declarative UI.
Retrofit: For network requests.
Koin: Dependency injection made simple.
Coil (AsyncImage): Efficient image loading.

🎯 **App Architecture**

PotterPedia is built with a clean, modular, and scalable architecture for maintainability and ease of extension. The core principles include:

Min SDK - 28
Compile/Target SDK - 34
MVVM (Model-View-ViewModel): Separation of concerns for better testability and maintainability.
Offline-First Approach: Cached data is prioritized for faster load times and offline support.
Dependency Injection: Handled using Koin for a loosely coupled and testable codebase.
Composable UIs: Dynamic and reactive UIs built with Jetpack Compose.

📖 **How to Build and Run**

Clone the Repository:
git clone: https://github.com/ANAHATA6302/PotterPediaApplication.git

Open the Project: Open the project in Android Studio.
Build and Run: Sync the Gradle files and build the project.

🌟 **Future Plans**

Error Handling: Display snackbars to notify users when data fetching fails.
UI Enhancements: Introduce animations and better visuals for a richer user experience.
Pagination: Implement pagination for larger datasets.
Unit and UI Tests: Add comprehensive test coverage using JUnit and Compose testing libraries.
Improved ktLint and detekt: adding more comprehensive cases for detekt and lint to maintain code standards and practice.
Pipeline integration and rules: Adding rules before commits and merge to have a sanitized code.
