# 🏋️ Barbell

![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=android&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)
![Room](https://img.shields.io/badge/Room-FF6F00?style=flat-square&logo=android&logoColor=white)

> **Barbell** is a focused Android workout tracker built for lifters who care about progress. Log your sets, track your weights, and watch your progressive overload numbers climb — all organized cleanly by muscle group.

---

## ✨ Features

* 💪 **Exercise Library** Create custom exercises and assign them to fixed muscle group categories: Chest, Biceps, Shoulders, Triceps, Legs, Abs, Back, or Forearms.
* 📋 **Detailed Workout Logging** Record weight, reps, sets, and an optional equipment photo for each session.
* 📈 **Progression Timeline** View a per-exercise history chart to visualize your improvement over time.
* 🔢 **Progressive Overload Tracker** Instantly see your percentage gain from your first logged weight to your latest — the core metric for strength progress.
* 🏠 **Clean Home Screen** All exercises are grouped by muscle area for quick navigation to what you're training today.

---

## 🚀 Quick Start

No Android Studio required. Run from the project root:

```bash
# Build the debug APK
.\gradlew.bat assembleDebug

# Install to a connected device or emulator
.\gradlew.bat installDebug

# Launch the app
adb shell am start -n com.example.barbell/.MainActivity
```

> If `adb` is not found, install [Android SDK Platform Tools](https://developer.android.com/tools/releases/platform-tools) and add it to your `PATH`.

---

## 🧪 Testing

```bash
# Run unit tests
.\gradlew.bat testDebugUnitTest

# Build debug variant
.\gradlew.bat assembleDebug
```

---

## 💻 Tech Stack

* **Language:** Kotlin
* **UI:** Jetpack Compose (Material 3)
* **Database:** Room
* **Navigation:** Navigation Compose
* **Image Loading:** Coil
* **Shared Logic:** Kotlin Multiplatform (`shared` module) — houses domain logic for muscle groups and the progressive overload calculator

---

## 📁 Project Structure

```
barbell/
├── app/        # Android application module
└── shared/     # Kotlin Multiplatform shared domain logic
```

---

## 📱 iPhone Support

The `shared` KMP module is in place for cross-platform domain logic, but a full iOS UI is not yet implemented.

To ship on iPhone you'd still need:
* A macOS machine with Xcode
* An iOS app target (SwiftUI or Compose Multiplatform)
* Wiring to consume the `shared` module

---

## 🚢 Play Store Release

1. Create a release keystore
2. Configure release signing in your Gradle build files
3. Build the Android App Bundle:

```bash
.\gradlew.bat bundleRelease
```

4. Upload the `.aab` to [Google Play Console](https://play.google.com/console)

---

## ⚙️ Requirements

* JDK 21 (recommended for Gradle toolchain)
* Android SDK + Platform Tools

---

## 📄 License

No license is currently defined for this repository.
