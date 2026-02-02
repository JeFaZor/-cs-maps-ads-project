# 🎮 CS Maps Ads Project

> A comprehensive Android advertising SDK with cloud backend, demonstrating professional ad integration in a Counter-Strike maps explorer application.

[![Build Status](https://img.shields.io/badge/build-passing-brightgreen)](https://github.com/JeFaZor/-cs-maps-ads-project)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![API](https://img.shields.io/badge/API-26%2B-brightgreen.svg)](https://android-arsenal.com/api?level=26)
[![JitPack](https://img.shields.io/badge/JitPack-v1.0.4-blue)](https://jitpack.io/#JeFaZor/cs-maps-ads-project)

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
- [Components](#components)
- [Getting Started](#getting-started)
- [SDK Integration](#sdk-integration)
- [API Documentation](#api-documentation)
- [Demo Application](#demo-application)
- [Deployment](#deployment)
- [Screenshots](#screenshots)
- [Tech Stack](#tech-stack)
- [License](#license)

---

## 🎯 Overview

The **CS Maps Ads Project** is a complete advertising solution for Android applications, featuring:

- **Custom Android SDK** for seamless ad integration
- **Cloud-based Backend API** with MongoDB database
- **Analytics Tracking** for impressions and clicks
- **Demo Application** showcasing SDK usage in a real-world app
- **Production Deployment** on Vercel with JitPack distribution

**Live API:** https://cs-maps-ads-project.vercel.app

**SDK on JitPack:** `com.github.JeFaZor:cs-maps-ads-project:1.0.4`

---

## ✨ Features

### SDK Features
- ✅ **Easy Integration** - Single line of code to add ads
- ✅ **Custom AdView** - Material Design compatible ad component
- ✅ **Automatic Analytics** - Impression and click tracking built-in
- ✅ **Network Caching** - Efficient data loading with Retrofit
- ✅ **Error Handling** - Graceful fallbacks for network issues
- ✅ **Compose Support** - Works seamlessly with Jetpack Compose

### Backend Features
- ✅ **RESTful API** - Clean, documented endpoints
- ✅ **MongoDB Atlas** - Cloud-hosted NoSQL database
- ✅ **Real-time Analytics** - Track ad performance instantly
- ✅ **CORS Enabled** - Cross-origin requests supported
- ✅ **Scalable** - Serverless deployment on Vercel

### Demo App Features
- ✅ **CS Maps Explorer** - Browse 7 competitive CS maps
- ✅ **Multiple Ad Placements** - List view and detail screens
- ✅ **Material 3 Design** - Modern Android UI
- ✅ **Random Ad Rotation** - Dynamic content loading

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────┐
│                   CS Maps Ads Project                    │
└─────────────────────────────────────────────────────────┘
                            │
        ┌───────────────────┼───────────────────┐
        │                   │                   │
┌───────▼────────┐  ┌──────▼──────┐  ┌────────▼────────┐
│  Android SDK   │  │   Backend   │  │   Demo App      │
│   (Library)    │  │     API     │  │  (CS Maps)      │
└───────┬────────┘  └──────┬──────┘  └────────┬────────┘
        │                   │                   │
        │         ┌─────────▼──────────┐        │
        │         │   MongoDB Atlas    │        │
        │         │  (Cloud Database)  │        │
        │         └────────────────────┘        │
        │                                       │
        └───────────────────┬───────────────────┘
                            │
                    ┌───────▼────────┐
                    │   JitPack      │
                    │  Distribution  │
                    └────────────────┘
```

### Component Flow

```
User Opens App
     │
     ├─> Demo App Loads
     │        │
     │        ├─> AdView Component Renders
     │        │        │
     │        │        └─> AdManager.fetchRandomAd()
     │        │                 │
     │        │                 └─> Retrofit HTTP Request
     │        │                          │
     │        │                          └─> Backend API (Vercel)
     │        │                                   │
     │        │                                   └─> MongoDB Query
     │        │                                            │
     │        └─────────────────────────────────────────<─┘
     │                  (Ad Data Returned)
     │
     ├─> User Views Ad → POST /api/analytics/impression
     │
     └─> User Clicks Ad → POST /api/analytics/click
```

---

## 📦 Components

### 1. **Android SDK** (`adssdk/`)

The core advertising library for Android developers.

**Key Classes:**
- `AdView` - Custom view component for displaying ads
- `AdManager` - Handles API communication and ad lifecycle
- `Ad` - Data model for advertisements
- `AdService` - Retrofit interface for API calls

**Published on JitPack:** `v1.0.4`

---

### 2. **Backend API** (`backend-api/`)

RESTful Flask API deployed on Vercel.

**Endpoints:**

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/ads` | Get all active ads |
| GET | `/api/ads/random` | Get random ad |
| POST | `/api/ads` | Create new ad |
| POST | `/api/analytics/impression` | Track impression |
| POST | `/api/analytics/click` | Track click |
| GET | `/api/analytics/stats` | Get analytics stats |

**Database:** MongoDB Atlas (Cloud)

---

### 3. **Demo Application** (`android-demo-app/app/`)

CS Maps Explorer - showcases SDK integration.

**Features:**
- Browse 7 Counter-Strike competitive maps
- View detailed map information
- Ads displayed in list and detail screens
- Material 3 design with Jetpack Compose

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** Arctic Fox or later
- **JDK 11+**
- **Minimum Android SDK:** 26 (Android 8.0)
- **Internet connection** for API calls

---

## 📲 SDK Integration

### Step 1: Add JitPack Repository

In your project's `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### Step 2: Add SDK Dependency

In your app's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.JeFaZor:cs-maps-ads-project:1.0.4")
}
```

### Step 3: Add Internet Permission

In `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

### Step 4: Use AdView in Your App

**In Jetpack Compose:**

```kotlin
import com.example.adssdk.AdView
import com.example.adssdk.AdManager

@Composable
fun YourScreen() {
    Column {
        // Your content here
        
        // Add AdView
        AndroidView(
            factory = { context ->
                AdView(context).apply {
                    val adManager = AdManager("https://cs-maps-ads-project.vercel.app/")
                    initialize(adManager)
                    loadAd()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
    }
}
```

**In XML Layout:**

```xml
<com.example.adssdk.AdView
    android:id="@+id/adView"
    android:layout_width="match_parent"
    android:layout_height="300dp" />
```

```kotlin
val adView = findViewById<AdView>(R.id.adView)
val adManager = AdManager("https://cs-maps-ads-project.vercel.app/")
adView.initialize(adManager)
adView.loadAd()
```

---

## 🌐 API Documentation

### Base URL
```
https://cs-maps-ads-project.vercel.app
```

### Authentication
No authentication required (public API for demo purposes).

### Endpoints

#### Get All Ads
```http
GET /api/ads
```

**Response:**
```json
{
  "status": "success",
  "count": 3,
  "data": [
    {
      "ad_id": "697f44579725d5a14ec59d24",
      "title": "Gaming Mouse Sale",
      "description": "50% off on all gaming mice!",
      "image_url": "https://picsum.photos/400/300",
      "link_url": "https://example.com/gaming-mouse",
      "impressions": 10,
      "clicks": 1,
      "active": true
    }
  ]
}
```

#### Get Random Ad
```http
GET /api/ads/random
```

**Response:**
```json
{
  "status": "success",
  "data": {
    "ad_id": "697f44579725d5a14ec59d24",
    "title": "Gaming Mouse Sale",
    "description": "50% off on all gaming mice!",
    "image_url": "https://picsum.photos/400/300",
    "link_url": "https://example.com/gaming-mouse"
  }
}
```

#### Track Impression
```http
POST /api/analytics/impression
Content-Type: application/json

{
  "ad_id": "697f44579725d5a14ec59d24"
}
```

**Response:**
```json
{
  "status": "success",
  "message": "Impression tracked"
}
```

#### Track Click
```http
POST /api/analytics/click
Content-Type: application/json

{
  "ad_id": "697f44579725d5a14ec59d24"
}
```

**Response:**
```json
{
  "status": "success",
  "message": "Click tracked"
}
```

#### Get Analytics Stats
```http
GET /api/analytics/stats
```

**Response:**
```json
{
  "status": "success",
  "data": {
    "total_impressions": 15,
    "total_clicks": 2,
    "ctr": 13.33
  }
}
```

---

## 📱 Demo Application

### CS Maps Explorer

A Counter-Strike maps browser demonstrating SDK integration.

**Features:**
- 7 competitive CS maps (Dust 2, Mirage, Inferno, Nuke, Overpass, Ancient, Anubis)
- Map details with navigation
- Ad placements in list and detail views
- Material 3 design

**Run the Demo:**

```bash
# Clone repository
git clone https://github.com/JeFaZor/-cs-maps-ads-project.git

# Open in Android Studio
cd cs-maps-ads-project/android-demo-app

# Run on device/emulator
./gradlew installDebug
```

---

## 🚢 Deployment

### Backend Deployment (Vercel)

The backend is automatically deployed via GitHub integration.

**Live API:** https://cs-maps-ads-project.vercel.app

**Manual Deploy:**
```bash
cd backend-api
vercel --prod
```

### SDK Publishing (JitPack)

SDK is automatically published on git tag push.

**Current Version:** `v1.0.4`

**To publish new version:**
```bash
git tag -a v1.0.5 -m "Release v1.0.5"
git push origin v1.0.5
```

### Database (MongoDB Atlas)

**Cluster:** CSAdsCluster (Bahrain region)

**Collections:**
- `ads` - Advertisement data
- `analytics` - Impression/click tracking

---

## 📸 Screenshots

### Demo Application
- CS Maps list with ad placement
- Map detail screen with ad
- Ad loading and display

### Backend Dashboard
- Vercel deployment status
- MongoDB Atlas cluster

*(Add actual screenshots to docs/ folder)*

---

## 🛠️ Tech Stack

### Android
- **Language:** Kotlin
- **UI:** Jetpack Compose + Material 3
- **Networking:** Retrofit 2.9.0
- **Image Loading:** Glide 4.16.0
- **Coroutines:** kotlinx-coroutines-android 1.7.3

### Backend
- **Framework:** Flask 3.1.2
- **Database:** MongoDB Atlas (pymongo 4.16.0)
- **Deployment:** Vercel (Serverless)
- **CORS:** flask-cors 6.0.2

### DevOps
- **Version Control:** Git + GitHub
- **CI/CD:** Vercel Auto-Deploy
- **Package Manager:** JitPack

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2026 CS Maps Ads Project

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
```

---

## 👥 Author

**Lior Toledano**

- GitHub: [@JeFaZor](https://github.com/JeFaZor)
- Project: [CS Maps Ads Project](https://github.com/JeFaZor/-cs-maps-ads-project)

---

## 🙏 Acknowledgments

- MongoDB Atlas for cloud database hosting
- Vercel for serverless deployment
- JitPack for SDK distribution
- Counter-Strike community for map inspiration

---

## 📚 Additional Documentation

For detailed documentation, see:

- [SDK Integration Guide](docs/SDK_INTEGRATION.md)
- [API Reference](docs/API_REFERENCE.md)
- [Demo App Guide](docs/DEMO_APP.md)
- [Backend Setup](docs/BACKEND_SETUP.md)

**Documentation Website:** https://jefazor.github.io/-cs-maps-ads-project/

---

## 🚀 Status

**Current Version:** v1.0.4

**Project Status:** ✅ Production Ready

- ✅ Backend API deployed and operational
- ✅ MongoDB Atlas connected
- ✅ SDK published on JitPack
- ✅ Demo app functional
- ✅ Analytics tracking active

---

**Built with ❤️ for Android developers**
