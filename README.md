# Food Ordering App

A native Android application for food ordering that allows users to browse restaurants, view menus, manage cart, and place orders.

## Project Structure

```
app/
├── manifests/
│   └── AndroidManifest.xml
├── java/
│   └── com.example.foodorderingapp/
│       ├── activities/
│       │   ├── MainActivity.java
│       │   ├── RestaurantMenuActivity.java
│       │   ├── CartActivity.java
│       │   └── CheckoutActivity.java
│       ├── adapters/
│       │   ├── RestaurantAdapter.java
│       │   ├── MenuItemAdapter.java
│       │   └── CartItemAdapter.java
│       ├── models/
│       │   ├── Restaurant.java
│       │   ├── MenuItem.java
│       │   ├── CartItem.java
│       │   └── Order.java
│       ├── utils/
│       │   ├── ApiClient.java
│       │   ├── Constants.java
│       │   └── SharedPreferencesManager.java
│       └── viewmodels/
│           ├── RestaurantViewModel.java
│           ├── MenuViewModel.java
│           └── CartViewModel.java
├── res/
│   ├── layout/
│   │   ├── activity_main.xml
│   │   ├── activity_restaurant_menu.xml
│   │   ├── activity_cart.xml
│   │   ├── activity_checkout.xml
│   │   ├── item_restaurant.xml
│   │   ├── item_menu.xml
│   │   └── item_cart.xml
│   ├── drawable/
│   │   ├── restaurant_placeholder.png
│   │   ├── food_placeholder.png
│   │   └── custom drawables...
│   ├── values/
│   │   ├── colors.xml
│   │   ├── strings.xml
│   │   ├── styles.xml
│   │   └── dimens.xml
│   └── mipmap/
└── build.gradle
```

## Features

### Mandatory Features
1. Home Screen
   - Restaurant listing with images, names, and cuisine types
   - Search functionality for filtering restaurants
   
2. Restaurant Menu Screen
   - Detailed menu items with descriptions and prices
   - Add to cart functionality
   
3. Cart Screen
   - Cart management (add/remove items)
   - Quantity adjustment
   - Total price calculation
   
4. Checkout Screen
   - User information input
   - Order placement
   - Success confirmation

### Optional Features
- API Integration with mock data
- Offline data caching
- Basic unit tests
- UI tests for critical features

## Technical Details

### Architecture
- MVVM (Model-View-ViewModel) architecture
- Repository pattern for data management
- LiveData for reactive data handling
- ViewBinding for view interactions

### Libraries Used
- AndroidX
- Material Design Components
- Retrofit for API calls
- Glide for image loading
- Room for local storage
- JUnit and Espresso for testing

## Setup Instructions

1. Clone the repository
```bash
git clone https://github.com/DAISHINKAN7/Front-End-ProjectSubmission.git
```

2. Open Android Studio and select "Open an existing Android Studio project"

3. Navigate to the cloned repository and click "OK"

4. Wait for the Gradle sync to complete

5. Run the app on an emulator or physical device

## Build Configuration

```gradle
android {
    compileSdkVersion 34
    defaultConfig {
        applicationId "com.example.foodorderingapp"
        minSdkVersion 24
        targetSdkVersion 34
        versionCode 1
        versionName "1.0"
    }
    buildTypes {
        release {
            minifyEnabled true
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}
```

## Dependencies

```gradle
dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'com.google.android.material:material:1.11.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    
    // ViewModel and LiveData
    implementation 'androidx.lifecycle:lifecycle-viewmodel:2.7.0'
    implementation 'androidx.lifecycle:lifecycle-livedata:2.7.0'
    
    // Retrofit for API calls
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
    
    // Glide for image loading
    implementation 'com.github.bumptech.glide:glide:4.16.0'
    
    // Room for local storage
    implementation 'androidx.room:room-runtime:2.6.1'
    annotationProcessor 'androidx.room:room-compiler:2.6.1'
    
    // Testing
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
}
```

## API Integration

The app uses mock API data from:
- JSONPlaceholder (https://jsonplaceholder.typicode.com/)
- Mocky.io (https://www.mocky.io/)

API endpoints are configured in `ApiClient.java` and can be modified as needed.

## Testing

The project includes both unit tests and UI tests:

### Unit Tests
- ViewModel tests
- Repository tests
- Utility function tests

### UI Tests
- Restaurant listing navigation
- Cart operations
- Checkout process

## Known Issues and Future Improvements

1. Currently implemented:
   - All mandatory features
   - Basic API integration
   - Local storage for offline support
   - Basic unit tests

2. Pending improvements:
   - Advanced search filters
   - User authentication
   - Payment gateway integration
   - Order tracking
   - Push notifications
   - Could make sure different images be integrated for different dishes


## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE.md file for details

## Acknowledgments

- Material Design guidelines
- Android development best practices
- Open-source libraries used in the project

## Contact

Your Name - [kunalajgaonkar31682@gmail.com](mailto:your.email@example.com)
Project Link: [https://github.com/DAISHINKAN7/Front-End-ProjectSubmission.git](https://github.com/yourusername/food-ordering-app)