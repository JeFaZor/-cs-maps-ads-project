# CS Maps Ads SDK

[![JitPack](https://img.shields.io/badge/JitPack-v1.0.4-blue)](https://jitpack.io/#JeFaZor/cs-maps-ads-project)
[![API](https://img.shields.io/badge/API-26%2B-brightgreen.svg)](https://android-arsenal.com/api?level=26)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](../LICENSE)

Android SDK for displaying image-based advertisements in your applications.

---

## 🚀 Quick Start

### Step 1: Add JitPack Repository

In your **project's** `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### Step 2: Add Dependency

In your **app's** `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.JeFaZor:cs-maps-ads-project:1.0.4")
}
```

### Step 3: Add Internet Permission

In your `AndroidManifest.xml`:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

---

## 📱 Usage

### Jetpack Compose

```kotlin
import androidx.compose.ui.viewinterop.AndroidView
import com.example.adssdk.AdView
import com.example.adssdk.AdManager

@Composable
fun YourScreen() {
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
```

### XML Layout

**Layout file:**

```xml
<com.example.adssdk.AdView
    android:id="@+id/adView"
    android:layout_width="match_parent"
    android:layout_height="300dp" />
```

**Activity/Fragment:**

```kotlin
import com.example.adssdk.AdView
import com.example.adssdk.AdManager

val adView = findViewById<AdView>(R.id.adView)
val adManager = AdManager("https://cs-maps-ads-project.vercel.app/")
adView.initialize(adManager)
adView.loadAd()
```

---

## 🎯 API Reference

### AdManager

```kotlin
class AdManager(private val baseUrl: String)
```

**Methods:**

| Method | Description |
|--------|-------------|
| `fetchRandomAd(onSuccess, onError)` | Fetch random ad |
| `trackImpression(adId)` | Track impression |
| `trackClick(adId)` | Track click |

### AdView

```kotlin
class AdView(context: Context, attrs: AttributeSet? = null)
```

**Methods:**

| Method | Description |
|--------|-------------|
| `initialize(adManager)` | Initialize with AdManager |
| `loadAd()` | Load and display ad |

---

## 📦 Dependencies

- Retrofit 2.9.0
- Glide 4.16.0
- Kotlin Coroutines 1.7.3

---

## 🔧 Requirements

- **Minimum SDK:** 26
- **Target SDK:** 35
- **Internet Permission:** Required

---

## 📚 More Info

- [Full Documentation](../docs/)
- [Demo App](../android-demo-app/app/)
- [Main README](../README.md)

---

**MIT License** - Made with ❤️ for Android developers
