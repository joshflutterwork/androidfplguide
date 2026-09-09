# FPL Guide

Android companion app for Fantasy Premier League managers — browse, search,
filter and compare all ~650 players using the public FPL API. Built with Kotlin,
Jetpack Compose (Material 3), Hilt, Retrofit + kotlinx.serialization and Coil.

## Requirements

- Android Studio (Ladybug or newer recommended)
- JDK 17 (bundled with Android Studio)
- Android SDK 35 (installed via Android Studio's SDK Manager)
- A device/emulator running Android 8.0 (API 26) or newer

## Run the app

### Option A — Android Studio (easiest)

1. Open this folder in Android Studio (`File → Open…`).
2. Let Gradle sync finish (first sync downloads dependencies).
3. Pick a device in the device dropdown (create an emulator via `Device Manager` if you
   don't have one — any Pixel profile with API 34+ works).
4. Press **Run ▶** (`Control + R`).

### Option B — Command line

```bash
# List connected devices/emulators
adb devices

# Build + install + launch on the connected device (debug build)
./gradlew installDebug
adb shell am start -n com.fplguide.debug/com.fplguide.MainActivity
```

No device? Create and boot an emulator from the CLI:

```bash
# One-time: list available system images, then create an AVD
sdkmanager --list | grep system-images
avdmanager create avd -n test -k "system-images;android-35;google_apis;arm64-v8a"
emulator -avd test
# Once it boots, run the installDebug commands above
```

## Tests & verification

```bash
./gradlew assembleDebug        # compile gate
./gradlew test                 # unit tests (mappers, filtering, domain models)
./gradlew :app:lintDebug       # Android lint
```
pshot is cached in-memory for the session (pull-to-refresh forces a fresh fetch).
