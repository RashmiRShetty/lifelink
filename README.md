# LifeLink - Blood Donation & Request App

A complete Android application that connects blood donors with recipients, facilitating blood donation and emergency blood requests.

## Features

### 🔐 User Management
- **User Registration**: Create accounts with personal details, blood group, and donor status
- **User Login**: Secure authentication system
- **Profile Management**: Update personal information and preferences
- **User Logout**: Secure session management

### 🩸 Blood Donation (Donor Side)
- **Donate Blood**: Record blood donations with details like:
  - Blood group (O+, O-, A+, A-, B+, B-, AB+, AB-)
  - Number of units
  - Location/city
  - Date of donation
- **Donor Profile**: Mark yourself as a blood donor

### 🩺 Blood Requests (Recipient Side)
- **Request Blood**: Submit blood requests for patients with:
  - Patient name
  - Required blood group
  - Number of units needed
  - Contact information
  - Hospital/location
  - Date of request
- **View All Requests**: Browse all blood requests in the system

### 📱 User Interface
- **Modern Design**: Clean, intuitive Material Design interface
- **Responsive Layout**: Optimized for different screen sizes
- **Color Scheme**: Professional medical app appearance
- **Easy Navigation**: Simple and logical user flow

## Technical Details

### Architecture
- **Activities**: Separate screens for each major function
- **Database**: SQLite database with proper relationships
- **Data Models**: User, Request, and Donation entities
- **Navigation**: Intent-based activity transitions

### Database Schema
- **Users Table**: User profiles and authentication
- **Donations Table**: Blood donation records
- **Requests Table**: Blood request records

### Key Components
- `MainActivity`: Welcome screen with login/register options
- `LoginActivity`: User authentication
- `RegisterActivity`: New user registration
- `DashboardActivity`: Main hub with all functions
- `DonateActivity`: Blood donation form
- `RequestBloodActivity`: Blood request form
- `ViewRequestsActivity`: Browse all requests
- `ProfileActivity`: User profile management

## Installation & Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Lifelink
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Open the project folder
   - Sync Gradle files

3. **Build and Run**
   - Connect an Android device or start an emulator
   - Click "Run" button or press Shift+F10

## Usage

### For New Users
1. Launch the app
2. Tap "Register" to create an account
3. Fill in your details and blood group
4. Choose whether you're a donor or not
5. Complete registration

### For Existing Users
1. Launch the app
2. Tap "Login" and enter credentials
3. Access the dashboard with all features

### Donating Blood
1. From dashboard, tap "🩸 Donate Blood"
2. Select your blood group
3. Enter number of units
4. Specify location
5. Submit donation

### Requesting Blood
1. From dashboard, tap "🩺 Request Blood"
2. Fill in patient details
3. Specify blood group and units needed
4. Provide contact and location
5. Submit request

### Viewing Requests
1. From dashboard, tap "📋 View All Requests"
2. Browse all blood requests
3. See details like patient name, blood group, location, etc.

### Managing Profile
1. From dashboard, tap "👤 My Profile"
2. Update personal information
3. Change blood group or donor status
4. Logout when done

## Screenshots

The app features a modern, medical-themed interface with:
- Red primary color scheme (representing blood)
- Clean, card-based layouts
- Intuitive icons and emojis
- Professional typography
- Consistent spacing and alignment

## Requirements

- **Minimum SDK**: API 21 (Android 5.0)
- **Target SDK**: API 31 (Android 12)
- **Java Version**: 8 or higher
- **Android Studio**: 4.0 or higher

## Dependencies

- AndroidX AppCompat
- AndroidX ConstraintLayout
- AndroidX RecyclerView
- AndroidX CardView
- Material Design components

## Contributing

This is a complete, production-ready blood donation app. Feel free to:
- Report bugs or issues
- Suggest new features
- Improve the UI/UX
- Add additional functionality

## License

This project is open source and available under the MIT License.

## Support

For support or questions, please open an issue in the repository.

---

**LifeLink** - Connecting blood donors and recipients for a healthier world! 🩸❤️
