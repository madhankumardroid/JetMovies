# Movies App

A modern Android application built with Jetpack Compose that allows users to search and view movie information using the OMDB API.

## Features

- **Movie Search**: Search for movies by title using the OMDB API
- **Movie Details**: View detailed information about selected movies
- **Modern UI**: Built with Jetpack Compose and Material 3 design
- **Offline Support**: Local caching of movie data using Room database
- **Clean Architecture**: Follows MVVM pattern with clean separation of concerns
- **Test Coverage**: Comprehensive unit and UI tests

## Architecture

The app follows Clean Architecture principles with the following layers:

### Presentation Layer
- **UI Components**: Jetpack Compose screens and components
- **ViewModels**: Handle UI state and business logic
- **Navigation**: Jetpack Compose Navigation

### Domain Layer
- **Use Cases**: Business logic and rules
- **Models**: Domain entities
- **Repository Interface**: Data contract

### Data Layer
- **Repository Implementation**: Coordinates data sources
- **Remote Data Source**: OMDB API integration
- **Local Data Source**: Room database for caching
- **DTOs**: Data transfer objects for API responses

## Technology Stack

- **UI**: Jetpack Compose, Material 3
- **Architecture**: MVVM, Clean Architecture
- **Dependency Injection**: Hilt
- **Networking**: Retrofit, OkHttp
- **Database**: Room
- **Image Loading**: Coil
- **Testing**: JUnit, MockK, Turbine, Compose Testing
- **Coroutines**: For asynchronous operations

## Setup Instructions

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK 24 or higher
- Kotlin 1.8.20 or higher

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd MoviesApp
   ```

2. **Get OMDB API Key**
   - Visit [OMDB API](https://www.omdbapi.com/)
   - Sign up for a free API key
   - Replace `YOUR_API_KEY` in `OmdbApiService.kt` with your actual API key

3. **Build and Run**
   - Open the project in Android Studio
   - Sync Gradle files
   - Run the app on an emulator or physical device

### API Key Configuration

In `app/src/main/java/com/example/moviesapp/data/remote/OmdbApiService.kt`:

```kotlin
@GET("/")
suspend fun searchMovies(
    @Query("s") query: String,
    @Query("apikey") apiKey: String = "YOUR_API_KEY" // Replace with your API key
): SearchResponseDto
```

## Project Structure

```
app/src/main/java/com/example/moviesapp/
├── data/
│   ├── local/
│   │   ├── entity/
│   │   ├── MovieDao.kt
│   │   └── MovieDatabase.kt
│   ├── remote/
│   │   ├── dto/
│   │   └── OmdbApiService.kt
│   └── repository/
│       └── MovieRepositoryImpl.kt
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
├── presentation/
│   ├── ui/
│   │   ├── component/
│   │   ├── screen/
│   │   ├── state/
│   │   └── theme/
│   └── viewmodel/
├── di/
│   └── AppModule.kt
├── MainActivity.kt
└── MoviesApplication.kt
```

## Testing

The app includes comprehensive test coverage:

### Unit Tests
- **Use Cases**: Business logic testing
- **Repository**: Data layer testing
- **ViewModels**: Presentation logic testing

### UI Tests
- **Screen Tests**: User interface behavior testing
- **Component Tests**: Individual UI component testing

### Running Tests
```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

## Key Features Implementation

### 1. Movie List Screen
- Search functionality with debouncing
- Grid layout for movie cards
- Loading, error, and empty states
- Navigation to detail screen

### 2. Movie Detail Screen
- Comprehensive movie information display
- Movie poster with Coil image loading
- Scrollable content layout
- Back navigation

### 3. Data Management
- Offline-first approach with Room database
- Automatic caching of search results
- Error handling and retry mechanisms

## Dependencies

### Core Dependencies
- `androidx.core:core-ktx`: Core Android functionality
- `androidx.lifecycle:lifecycle-runtime-ktx`: Lifecycle management
- `androidx.activity:activity-compose`: Compose activity support

### Compose Dependencies
- `androidx.compose.ui:ui`: Compose UI components
- `androidx.compose.material3:material3`: Material 3 design
- `androidx.navigation:navigation-compose`: Navigation
- `androidx.lifecycle:lifecycle-viewmodel-compose`: ViewModel integration

### Networking
- `com.squareup.retrofit2:retrofit`: HTTP client
- `com.squareup.retrofit2:converter-gson`: JSON parsing
- `com.squareup.okhttp3:logging-interceptor`: Network logging

### Database
- `androidx.room:room-runtime`: Local database
- `androidx.room:room-ktx`: Kotlin extensions

### Dependency Injection
- `com.google.dagger:hilt-android`: Hilt DI
- `androidx.hilt:hilt-navigation-compose`: Navigation integration

### Image Loading
- `io.coil-kt:coil-compose`: Image loading

### Testing
- `junit:junit`: Unit testing
- `io.mockk:mockk`: Mocking
- `app.cash.turbine:turbine`: Flow testing
- `androidx.compose.ui:ui-test-junit4`: Compose testing

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- [OMDB API](https://www.omdbapi.com/) for providing movie data
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for modern UI development
- [Material Design](https://material.io/) for design guidelines 