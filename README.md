# Barbell

Barbell is a Kotlin Android workout tracker focused on logging strength progress per exercise.

## Features

- Create exercises and assign fixed body area categories:
  - Chest, Biceps, Shoulders, Triceps, Legs, Abs, Back, Forearms
- Log workouts with:
  - weight
  - reps
  - sets
  - optional equipment photo
- View per-exercise progression timeline
- Track progressive overload percentage (first logged weight vs latest logged weight)
- Clean grouped home screen by muscle area

## Tech Stack

- Kotlin + Jetpack Compose (Material 3)
- Room database
- Navigation Compose
- Coil (image loading)
- Kotlin Multiplatform shared module (`shared`) for shared domain logic:
  - muscle groups
  - progressive overload calculator

## Requirements

- Windows/macOS/Linux
- JDK 21 recommended for Gradle daemon/toolchain
- Android SDK + platform tools (`adb`) for device install/testing

## Run Locally (No Android Studio Required)

From project root:

```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat installDebug
adb shell am start -n com.example.barbell/.MainActivity
```

If `adb` is not found, install Android SDK platform-tools and add it to your PATH.

## Test

```powershell
.\gradlew.bat testDebugUnitTest
.\gradlew.bat assembleDebug
```

## Project Structure

- `app/` - Android application
- `shared/` - Kotlin Multiplatform shared domain logic

## Notes on iPhone Support

This repo now includes a KMP `shared` module for cross-platform domain logic, but the full iOS app target/UI is not yet implemented in this repository.

To ship on iPhone, you still need:

- a macOS machine + Xcode
- iOS app target (SwiftUI or Compose Multiplatform UI)
- wiring that iOS app to consume the `shared` module

## Play Store Release (High Level)

1. Create release keystore
2. Configure release signing
3. Build AAB:

```powershell
.\gradlew.bat bundleRelease
```

4. Upload AAB to Google Play Console

## License

No license file is currently defined in this repository.
