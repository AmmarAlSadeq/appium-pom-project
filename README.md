# ApiDemos Appium POM Automation Framework

![Build Status](https://github.com/YOUR_USERNAME/appium-pom-automation/actions/workflows/appium.yml/badge.svg)
![Java 11](https://img.shields.io/badge/Java-11-blue.svg)
![Appium 2.0](https://img.shields.io/badge/Appium-2.0-green.svg)
![TestNG](https://img.shields.io/badge/TestNG-7.7.0-orange.svg)

## Project Description

Mobile UI automation framework for the **ApiDemos** Android application using Appium, Java, and TestNG. The framework implements Page Object Model with `@AndroidFindBy` annotations and `PageFactory` for maintainable and scalable test automation. Built with ExtentReports for reporting and GitHub Actions for CI/CD.

**Target App:** [ApiDemos-debug.apk v6.0.6](https://github.com/appium/android-apidemos/releases/download/v6.0.6/ApiDemos-debug.apk)

---

## Tech Stack

| Component         | Technology          | Version  |
|-------------------|---------------------|----------|
| Language          | Java                | 11       |
| Mobile Automation | Appium Java Client  | 9.4.0    |
| Appium Server     | Appium              | 2.0      |
| Web Driver        | Selenium            | 4.28.1   |
| Test Framework    | TestNG              | 7.7.0    |
| Build Tool        | Maven               | 3.6+     |
| Reporting         | ExtentReports       | 5.1.1    |
| CI Platform       | GitHub Actions      | -        |

---

## Prerequisites

| Requirement         | Version | How to Verify            |
|---------------------|---------|--------------------------|
| JDK                 | 11      | `java -version`          |
| Maven               | 3.6+    | `mvn -version`           |
| Node.js             | 18+     | `node -v`                |
| Appium Server       | 2.0+    | `appium -v`              |
| UiAutomator2 Driver | Latest  | `appium driver list`     |
| Android SDK         | API 29+ | `echo $ANDROID_HOME`     |
| Android Emulator    | API 29  | `adb devices`            |

Install Appium and UiAutomator2 driver:
```bash
npm install -g appium@2.0
appium driver install uiautomator2
```

Verify connected device:
```bash
adb devices
# Expected output:
# List of devices attached
# emulator-5554   device
```

---

## Setup & Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/appium-pom-automation.git
   cd appium-pom-automation
   ```

2. **Install dependencies**
   ```bash
   mvn clean install -DskipTests
   ```

3. **Start Appium server**
   ```bash
   npm install -g appium@2.0
   appium driver install uiautomator2
   appium &
   ```

   Download [ApiDemos-debug.apk v6.0.6](https://github.com/appium/android-apidemos/releases/download/v6.0.6/ApiDemos-debug.apk) and place it in:
   ```
   src/main/java/org/automation/resources/ApiDemos-debug.apk
   ```

4. **Configure device**

   Start your Android emulator or connect a real device:
   ```bash
   emulator -avd Pixel3 -no-snapshot-load
   adb devices
   ```
   Expected: `emulator-5554   device`

5. **Update config.properties**

   Edit `src/main/java/org/automation/resources/config.properties` to match your device:
   ```properties
   androidDeviceName = Pixel3
   ```

6. **Run tests**
   ```bash
   mvn clean test
   ```

---

## Running Tests

| Action                    | Command                                            |
|---------------------------|----------------------------------------------------|
| Run all tests             | `mvn clean test`                                   |
| Run a single test class   | `mvn test -Dtest=HomeTest`                         |
| Run a single test method  | `mvn test -Dtest=HomeTest#testAppLaunchAndHomeScreen` |
| Run tests by group/tag    | `mvn test -Dgroups=smoke`                          |
| Run with specific device  | `mvn test -DipAddress=192.168.1.100`               |
| Compile only (no tests)   | `mvn clean compile`                                |

---

## Configuration Reference

Config file: `src/main/java/org/automation/resources/config.properties`

| Key                  | Purpose                        | Default Value                     |
|----------------------|--------------------------------|-----------------------------------|
| `ipAddress`          | Appium server IP address       | `127.0.0.1`                       |
| `port`               | Appium server port             | `4723`                            |
| `androidDeviceName`  | Emulator or device name        | `Pixel3`                          |
| `appPackage`         | Android app package name       | `io.appium.android.apis`          |
| `appActivity`        | Main launch activity           | `io.appium.android.apis.ApiDemos` |
| `apkFileName`        | APK file name in resources/    | `ApiDemos-debug.apk`              |

---

## Project Structure

```
appium-pom-automation/
├── .github/workflows/
│   └── appium.yml                         # CI/CD pipeline (GitHub Actions)
├── docs/
│   └── ARCHITECTURE.md                    # Framework architecture documentation
├── src/
│   ├── main/java/org/automation/
│   │   ├── config/
│   │   │   └── DriverFactory.java         # Singleton driver lifecycle management
│   │   ├── locators/                      # LOCATORS LAYER - selector strings only
│   │   │   ├── HomeLocators.java          # Home screen accessibility IDs
│   │   │   └── ViewsLocators.java         # Views sub-menu accessibility IDs
│   │   ├── pages/                         # PAGES LAYER - methods and interactions
│   │   │   ├── HomePage.java              # Home screen: categories, navigation
│   │   │   └── ViewsPage.java             # Views sub-menu: animation verification
│   │   ├── resources/
│   │   │   └── config.properties          # Appium & device configuration
│   │   └── utils/                         # Utility classes
│   │       ├── AndroidActions.java         # Gesture methods (long press, swipe, drag)
│   │       ├── AppiumUtils.java            # Server start/stop, ExtentReports, JSON reader
│   │       ├── ScrollHelper.java           # UiScrollable + W3C scroll actions
│   │       ├── SwipeHelper.java            # W3C swipe actions (left/right/up/down)
│   │       └── WaitHelper.java             # Explicit waits (WebDriverWait wrapper)
│   └── test/java/org/automation/
│       ├── testUtils/                      # Test infrastructure
│       │   ├── AndroidBaseClass.java       # Test lifecycle hooks (@BeforeClass/@AfterClass)
│       │   ├── Listeners.java              # ExtentReports + screenshot on failure
│       │   └── RetryAnalyzer.java          # Auto-retry failed tests (max 2)
│       └── tests/                          # TEST LAYER - test logic and assertions
│           └── HomeTest.java               # Home screen tests
├── testng.xml                              # TestNG suite configuration
├── pom.xml                                 # Maven dependencies and plugins
├── .gitignore                              # Excludes /target, *.apk, .env, reports
└── README.md                               # This file
```

---

## Test Scenarios Covered

| TC ID   | Test Name                          | Description                                                          | Screen/Flow                                  |
|---------|------------------------------------|----------------------------------------------------------------------|----------------------------------------------|
| TC-001  | Home Screen Verification           | Launch app, verify all 11 categories displayed, navigate to Views    | Home -> Views                                |
| TC-002  | ScrollView Bottom/Top              | Navigate to ScrollView, scroll to bottom, verify content, scroll top | Home -> Views -> Layouts -> ScrollView       |
| TC-003  | Drag and Drop                      | Long-press drag dot to target, verify EditText result updates        | Home -> Views -> Drag and Drop               |
| TC-004  | Expandable Lists                   | Expand/collapse groups, verify children appear/disappear             | Home -> Views -> Expandable Lists            |
| TC-005  | Controls Light Theme               | Interact with checkbox, radio, toggle, seekbar controls              | Home -> Views -> Controls                    |
| TC-006  | Alert Dialogs                      | Dismiss OK Cancel, List, and Single choice dialogs                   | Home -> App -> Alert Dialogs                 |
| TC-007  | LogTextBox                         | Type text entries, tap Add, verify log appends both entries           | Home -> Text -> LogTextBox                   |
| TC-008  | Lists Dynamic Scroll               | Scroll dynamically until "Gingerbread" found, tap, verify selection  | Home -> Views -> Lists                       |
| TC-009  | Progress Bar Incremental           | Increase/decrease progress bar, verify value changes                 | Home -> Views -> Progress Bar                |
| TC-010  | E2E Horizontal Swipe               | Full navigation flow with horizontal swipe left/right verification   | Home -> Views -> Layouts -> HorizontalScrollView |

---

## Viewing Reports

After a test run, the ExtentReport is generated at:

```
src/reports/extent-report.html
```

Open it in a browser:
```bash
# Windows
start src/reports/extent-report.html

# macOS
open src/reports/extent-report.html

# Linux
xdg-open src/reports/extent-report.html
```

The report includes:
- Pass/fail status per test
- Failure screenshots (Base64-encoded, embedded)
- System information
- Timestamps

---

## CI/CD

The GitHub Actions workflow is defined in `.github/workflows/appium.yml`.

### What triggers it
- **Push** to `main` or `develop` branches
- **Pull requests** targeting `main`

### What it does
1. Checks out the code
2. Sets up JDK 11 and Node.js 18
3. Installs Appium 2.0 + UiAutomator2 driver
4. Launches an Android emulator (API 29, Pixel 3a)
5. Downloads the APK from GitHub releases
6. Runs `mvn clean test`
7. Uploads test reports and failure screenshots as artifacts

### Where to view results
- Go to **Actions** tab in the GitHub repository
- Click on the latest workflow run
- Download the `test-reports` or `failure-screenshots` artifacts

---

## Known Issues / Limitations

- **Emulator-only tested:** Tests are verified on Android emulators (API 29). Real device behavior may vary for gesture-based tests.
- **APK not committed:** The APK must be downloaded separately and placed in `src/main/java/org/automation/resources/`.
- **Sequential execution:** Currently runs tests sequentially. Parallel execution will be enabled as more tests are added.
- **Appium path:** The Appium server path in `AppiumUtils.java` is hardcoded to the Windows npm global path (`AppData/Roaming/npm/node_modules/appium`). Adjust for macOS/Linux (`/usr/local/lib/node_modules/appium`).
- **Implicit wait:** A 10-second implicit wait is set globally via `DriverFactory`. Explicit waits via `WaitHelper` handle most timing needs.

---

## Contributing

### Branch naming convention
```
feature/TC-002-scrollview-tests
bugfix/TC-005-controls-seekbar
hotfix/config-emulator-name
```

### Commit message style
Use imperative mood with a short description:
```
feat: add ScrollView page object and locators
test: add TC-002 scroll to bottom and back test
fix: update wait timeout for slow emulators
docs: update README with new test scenarios
refactor: extract scroll logic into ScrollHelper
```

### How to add a new Page class and test

1. **Create a locators class** in `src/main/java/org/automation/locators/`
   - Define selector constants as `public static final String`
   - Use accessibility IDs preferred, then resource IDs, then XPath

2. **Create a page class** in `src/main/java/org/automation/pages/`
   - Extend `AndroidActions`
   - Add a `WaitHelper` field
   - Use locators from the corresponding locators class (never inline selectors)
   - Add Javadoc with `@param`, `@return`, `@throws` on all public methods

3. **Create a test class** in `src/test/java/org/automation/tests/`
   - Extend `AndroidBaseClass`
   - Use `@Test(description = "[TC-XXX] ...")` annotation
   - Keep assertions in the test layer only (never in page objects)

4. **Register in testng.xml**
   ```xml
   <test name="Your Test Name">
       <classes>
           <class name="org.automation.tests.YourTest"/>
       </classes>
   </test>
   ```

5. **Update documentation**
   - Add test scenario to the Test Scenarios table in this README
   - Update the project structure diagram in ARCHITECTURE.md
