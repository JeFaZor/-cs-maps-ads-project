# SDK Integration Guide

Complete guide for integrating CS Maps Ads SDK into your Android application.

---

## Prerequisites

- Android Studio Arctic Fox or later
- Minimum SDK: 26 (Android 8.0)
- Kotlin 1.9.0+
- Internet connection

---

## Installation

### 1. Add JitPack Repository

Open your **project's** `settings.gradle.kts` file and add JitPack:

```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

### 2. Add SDK Dependency

In your **app module's** `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.JeFaZor:cs-maps-ads-project:1.0.4")
    
    // Your other dependencies
}
```

### 3. Add Permissions

In `app/src/main/AndroidManifest.xml`:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    
    <uses-permission android:name="android.permission.INTERNET" />
    
    <application>
        <!-- Your app -->
    </application>
</manifest>
```

### 4. Sync Project

Click "Sync Now" in Android Studio to download the SDK.

---

## Basic Usage

### Jetpack Compose (Recommended)

```kotlin
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.adssdk.AdView
import com.example.adssdk.AdManager

@Composable
fun MyScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Your app content
        Text("My App Content")
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Ad placement
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

### Traditional View System

**Layout XML:**

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">
    
    <!-- Your app content -->
    
    <com.example.adssdk.AdView
        android:id="@+id/adView"
        android:layout_width="match_parent"
        android:layout_height="300dp"
        android:layout_marginTop="16dp" />
        
</LinearLayout>
```

**Activity/Fragment:**

```kotlin
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.adssdk.AdView
import com.example.adssdk.AdManager

class MainActivity : AppCompatActivity() {
    
    private lateinit var adManager: AdManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Initialize AdManager
        adManager = AdManager("https://cs-maps-ads-project.vercel.app/")
        
        // Initialize AdView
        val adView = findViewById<AdView>(R.id.adView)
        adView.initialize(adManager)
        adView.loadAd()
    }
}
```

---

## Advanced Usage

### Multiple Ad Placements

You can add multiple AdViews in different parts of your app:

```kotlin
@Composable
fun MyApp() {
    LazyColumn {
        items(myItems) { item ->
            ItemCard(item)
        }
        
        // Ad after every 5 items
        item {
            AndroidView(
                factory = { context ->
                    AdView(context).apply {
                        initialize(adManager)
                        loadAd()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(300.dp)
            )
        }
    }
}
```

### Custom API Endpoint

For development or custom backend:

```kotlin
// Production
val adManager = AdManager("https://cs-maps-ads-project.vercel.app/")

// Development
val adManager = AdManager("http://192.168.1.100:5000/")

// Custom backend
val adManager = AdManager("https://your-api.com/")
```

### Error Handling

The SDK handles errors internally and displays an error message. Errors are logged to Logcat:

```kotlin
// Check Logcat for errors
// Filter by "AdView" or "AdManager"
```

---

## Configuration

### Network Security (Development)

If using HTTP (not HTTPS) for development, create:

**`res/xml/network_security_config.xml`:**

```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true">
        <trust-anchors>
            <certificates src="system" />
        </trust-anchors>
    </base-config>
</network-security-config>
```

**`AndroidManifest.xml`:**

```xml
<application
    android:networkSecurityConfig="@xml/network_security_config"
    ...>
```

---

## Testing

### Test with Demo Backend

Use our live demo backend:

```kotlin
val adManager = AdManager("https://cs-maps-ads-project.vercel.app/")
```

This provides sample ads for testing.

### Verify Integration

Run your app and check:
1. ✅ Ad displays with image, title, description
2. ✅ Clicking ad opens browser
3. ✅ New ad loads on `loadAd()` call
4. ✅ No crashes or errors in Logcat

---

## Troubleshooting

### Problem: No ads showing

**Solutions:**
- Check internet connection
- Verify `INTERNET` permission in manifest
- Check API URL is correct
- Review Logcat for errors

### Problem: Network security error

**Error:** `CLEARTEXT communication not permitted`

**Solution:** Add Network Security Config (see Configuration section)

### Problem: Ads load slowly

**Possible causes:**
- Slow internet connection
- Large ad images
- Backend server response time

**Solutions:**
- Use HTTPS for better performance
- Optimize images on backend
- Check network connection

---

## Next Steps

- [API Reference](API_REFERENCE.md)
- [Demo Application](../android-demo-app/)
- [Main Documentation](../README.md)

---

**Need help?** Open an issue on [GitHub](https://github.com/JeFaZor/-cs-maps-ads-project/issues)
