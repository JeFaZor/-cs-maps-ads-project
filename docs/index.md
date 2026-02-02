# CS Maps Ads Project Documentation

Welcome to the CS Maps Ads Project documentation!

---

## 🎯 Quick Links

- **[SDK Integration Guide](SDK_INTEGRATION.md)** - Get started with the SDK
- **[API Reference](API_REFERENCE.md)** - Complete API documentation
- **[Main README](../README.md)** - Project overview
- **[GitHub Repository](https://github.com/JeFaZor/-cs-maps-ads-project)** - Source code

---

## 📦 What is CS Maps Ads?

CS Maps Ads is a complete advertising solution for Android applications featuring:

- **Android SDK** - Easy-to-integrate advertising library
- **Cloud Backend** - RESTful API with MongoDB database
- **Analytics** - Real-time tracking of impressions and clicks
- **Demo App** - CS Maps Explorer showcasing the SDK

---

## 🚀 Getting Started

### For Android Developers

1. **Add JitPack Repository** to your project
2. **Add Dependency**: `com.github.JeFaZor:cs-maps-ads-project:1.0.4`
3. **Add AdView** to your layout
4. **Initialize and load ads**

**[Full Integration Guide →](SDK_INTEGRATION.md)**

---

### For Backend Developers

**Live API Endpoint:**
```
https://cs-maps-ads-project.vercel.app
```

**Available Endpoints:**
- `GET /api/ads` - Get all ads
- `GET /api/ads/random` - Get random ad
- `POST /api/analytics/impression` - Track impression
- `POST /api/analytics/click` - Track click
- `GET /api/analytics/stats` - Get statistics

**[Full API Documentation →](API_REFERENCE.md)**

---

## 📱 Installation

```kotlin
// settings.gradle.kts
repositories {
    maven { url = uri("https://jitpack.io") }
}

// app/build.gradle.kts
dependencies {
    implementation("com.github.JeFaZor:cs-maps-ads-project:1.0.4")
}
```

---

## 💻 Usage Example

```kotlin
import com.example.adssdk.AdView
import com.example.adssdk.AdManager

@Composable
fun MyScreen() {
    AndroidView(
        factory = { context ->
            AdView(context).apply {
                val adManager = AdManager("https://cs-maps-ads-project.vercel.app/")
                initialize(adManager)
                loadAd()
            }
        },
        modifier = Modifier.fillMaxWidth().height(300.dp)
    )
}
```

---

## 🛠️ Tech Stack

### Android
- Kotlin
- Jetpack Compose
- Retrofit
- Glide

### Backend
- Flask
- MongoDB Atlas
- Vercel (Serverless)

---

## 📊 Features

### SDK Features
- ✅ One-line integration
- ✅ Automatic analytics
- ✅ Material Design
- ✅ Compose support
- ✅ Error handling

### Backend Features
- ✅ RESTful API
- ✅ Cloud database
- ✅ Real-time tracking
- ✅ Scalable deployment

---

## 📚 Documentation

- **[SDK Integration Guide](SDK_INTEGRATION.md)** - Step-by-step integration
- **[API Reference](API_REFERENCE.md)** - Complete API documentation

---

## 🔗 Links

- **Repository:** [github.com/JeFaZor/-cs-maps-ads-project](https://github.com/JeFaZor/-cs-maps-ads-project)
- **JitPack:** [jitpack.io/#JeFaZor/cs-maps-ads-project](https://jitpack.io/#JeFaZor/cs-maps-ads-project)
- **Live API:** [cs-maps-ads-project.vercel.app](https://cs-maps-ads-project.vercel.app)

---

## 📄 License

MIT License - see [LICENSE](../LICENSE)

---

## 🤝 Support

- **Issues:** [GitHub Issues](https://github.com/JeFaZor/-cs-maps-ads-project/issues)
- **Questions:** Open an issue on GitHub

---

**Built with ❤️ for Android developers**
