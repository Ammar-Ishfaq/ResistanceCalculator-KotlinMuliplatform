# Resistance Calculator (Kotlin Multiplatform App)

[![official project](http://jb.gg/badges/official.svg)](https://confluence.jetbrains.com/display/ALL/JetBrains+on+GitHub)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

This is a basic Kotlin Multiplatform Resistance calculator app for **Android** and **iOS**. It includes shared business logic and data handling, and a shared UI implementation using Compose Multiplatform.

### App Preview
| Android Preview                             | iOS Preview                                      |
| -------------------------------------------- | ------------------------------------------------ |
| <img src="./other_data/resitance_calculator_gif.gif" width="25%" height="25%"> | <img src="./other_data/Simulator_Screen_Recording_iPhone_15_Pro.gif" width="25%" height="25%"> |


### Technologies
The app uses the following multiplatform dependencies in its implementation:

- [Compose Multiplatform](https://jb.gg/compose) for UI
- [Ktor](https://ktor.io/) for networking
- [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) for JSON handling
- [Kamel](https://github.com/Kamel-Media/Kamel) for image loading
- [moko-resources](https://github.com/icerockdev/moko-resources) for string resources
- [Koin](https://github.com/InsertKoinIO/koin) for dependency injection
- [Voyager](https://github.com/adrielcafe/voyager) for navigation and screen models

> These are just some of the possible libraries to use for these tasks with Kotlin Multiplatform, and their usage here isn't a strong recommendation for these specific libraries over the available alternatives. You can find a wide variety of curated multiplatform libraries in the [kmp-awesome](https://github.com/terrakok/kmp-awesome) repository.

### License
