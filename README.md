# 🩸 LifeLink - Blood Donation & Request App

![Platform](https://img.shields.io/badge/Platform-Android-green)
![Language](https://img.shields.io/badge/Language-Java-blue)
![Database](https://img.shields.io/badge/Database-SQLite-orange)
![Status](https://img.shields.io/badge/Status-Completed-brightgreen)

A complete Android application that connects blood donors with recipients, enabling seamless blood donation and emergency requests.

---

## 🚀 Features

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

---

## 🛠 Tech Stack

- **Frontend**: XML (Android UI)  
- **Backend**: Java  
- **Database**: SQLite  
- **IDE**: Android Studio  

---

## ⚙️ Technical Details

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

---

## ⚡ Installation & Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/RashmiRShetty/lifelink
   cd Lifelink
