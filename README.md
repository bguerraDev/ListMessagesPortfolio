# 📱 ListMessagesPortfolio

📘 [Read this in Spanish](README.es.md)

Native Android app built with **Jetpack Compose** to visualize messages stored in Firestore. It includes JWT authentication via a Django REST backend and a modern design with dark mode support. Used exclusively for the bguerraDev Portfolio

---

## 🛠️ Tech Stack

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Retrofit](https://img.shields.io/badge/Retrofit-009688?style=for-the-badge)
![DataStore](https://img.shields.io/badge/DataStore-FF6F00?style=for-the-badge)
![Django REST](https://img.shields.io/badge/Django%20REST-092E20?style=for-the-badge&logo=django&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FC6D26?style=for-the-badge&logo=firebase&logoColor=white)

---

## 🧱 Architecture

- **MVVM (Model-View-ViewModel)** 📐
- **Repository Pattern** for clean data access
- **Jetpack Navigation** for screen transitions
- **Jetpack Compose** for declarative UI
- **DataStore Preferences** for JWT persistence

---

## 🔐 Auth Flow

1. User enters `username` and `password`.
2. App sends a request to `/api/login/` in the Django backend.
3. On success, a JWT token is saved locally.
4. The app then requests `/api/messages/` using the token.

---

## 📄 Features

- 🔐 JWT Login + Firestore Integration
- 📜 Messages list with sender & timestamp
- 🔍 Search by content, name or date
- 🔃 Ascending/Descending sorting
- 🌙 Dark/Light theme support
- 🚪 Secure logout with navigation
- 🧊 Shimmer loading placeholder
- 📩 Toggleable message content

---

## 🗂️ Project Structure

```bash
├── data
│   ├── domain.model        # Models (Message, LoginRequest...)
│   ├── network             # Retrofit + API interface
│   └── repository          # Repository layer
├── ui
│   ├── login               # Login screen and logic
│   ├── messages            # Messages screen and logic
│   ├── common              # Reusable UI components
│   └── theme               # Compose theme config
├── utils                   # Helpers and constants
└── MainActivity.kt         # Entry point