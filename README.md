# ApiDemos Appium POM Automation Framework

## 1. Project Title & Badge

![Build Status](https://github.com/AmmarAlSadeq/appium-pom-project/actions/workflows/appium.yml/badge.svg)
![Java 11](https://img.shields.io/badge/Java-11-blue.svg)
![Appium 3.0](https://img.shields.io/badge/Appium-3.0-green.svg)
![TestNG](https://img.shields.io/badge/TestNG-7.7.0-orange.svg)

---

## 2. Project Description

Enterprise-grade mobile UI automation framework for the **ApiDemos** Android application built with Appium, Java 11, and TestNG. The framework implements a strict **three-layer Page Object Model** — locators, pages, and tests are fully separated with zero inline selectors in test classes. Supports parallel execution across 2 devices with ThreadLocal drivers, data-driven testing via JSON, automatic retry on failure, and ExtentReports with embedded screenshots.

**Target App:** [ApiDemos-debug.apk v6.0.6](https://github.com/appium/android-apidemos/releases/download/v6.0.6/ApiDemos-debug.apk)
**Package:** `io.appium.android.apis` | **Activity:** `io.appium.android.apis.ApiDemos`

---

## 3. Tech Stack

| Component         | Technology          | Version  |
|-------------------|---------------------|----------|
| Language          | Java                | 11       |
| Mobile Automation | Appium Java Client  | 9.4.0    |
| Appium Server     | Appium              | 3.0      |
| Web Driver        | Selenium            | 4.28.1   |
| Test Framework    | TestNG              | 7.7.0    |
| Build Tool        | Maven               | 3.6+     |
| Reporting         | ExtentReports       | 5.1.1    |
| JSON Parsing      | Jackson             | 2.15.3   |
| CI Platform       | GitHub Actions      | -        |

---

## 4. Prerequisites

| Requirement         | Version | Install / Verify Command                                    |
|---------------------|---------|-------------------------------------------------------------|
| JDK                 | 11      | `java -version`                                             |
| Maven               | 3.6+    | `mvn -version`                                              |
| Node.js             | 18+     | `node -v`                                                   |
| Appium Server       | 2.0+    | `npm install -g appium@3` then `appium -v`               |
| UiAutomator2 Driver | Latest  | `appium driver install uiautomator2` then `appium driver list` |
| Android SDK         | API 27+ | `echo $ANDROID_HOME` (or `%ANDROID_HOME%` on Windows)       |
| Android Emulator(s) | API 27+ | `adb devices` — at least 1 running (2 for parallel)         |

**First-time Appium setup:**
```bash
npm install -g appium@3
appium driver install uiautomator2
```

**Verify connected devices:**
```bash
adb devices
# Expected for parallel execution:
# List of devices attached
# emulator-5554   device
# emulator-5556   device
```

---

## 5. Setup & Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/AmmarAlSadeq/appium-pom-project.git
   cd appium-pom-project
   ```

2. **Install Maven dependencies**
   ```bash
   mvn clean install -DskipTests
   ```

3. **Download the APK**

   Download [ApiDemos-debug.apk v6.0.6](https://github.com/appium/android-apidemos/releases/download/v6.0.6/ApiDemos-debug.apk) and place it in:
   ```
   src/main/java/org/automation/resources/ApiDemos-debug.apk
   ```

4. **Start Android emulator(s)**

   For **sequential execution** (1 device):
   ```bash
   emulator -avd <your_avd_name> -no-snapshot-load
   adb devices
   ```

   For **parallel execution** (2 devices):
   ```bash
   emulator -avd <your_first_avd_name> -no-snapshot-load -port 5554 &
   emulator -avd <your_second_avd_name> -no-snapshot-load -port 5556 &
   adb devices
   ```

5. **Update `config.properties`**

   Edit `src/main/java/org/automation/resources/config.properties` to match your devices:
   ```properties
   # Device 1 (required):
   device1.deviceName = Your First Device Name
   device1.udid = emulator-5554
   device1.systemPort = 8200

   # Device 2 (required for parallel):
   device2.deviceName = Your Second Device Name
   device2.udid = emulator-5556
   device2.systemPort = 8201
   ```

6. **Run tests**
   ```bash
   mvn clean test
   ```

---

## 6. Running Tests

| Action                      | Command                                               |
|-----------------------------|-------------------------------------------------------|
| Run all tests (parallel)    | `mvn clean test`                                      |
| Run a single test class     | `mvn test -Dtest=HomeTest`                            |
| Run a single test method    | `mvn test -Dtest=HomeTest#testAppLaunchAndHomeScreen` |
| Compile only (no tests)     | `mvn clean compile`                                   |

### Parallel execution (2 devices)

1. Start 2 emulators with different ports (see Setup step 4)
2. Update `device1.*` and `device2.*` in `config.properties` with your device details
3. Run `mvn clean test` — `testng.xml` is configured with `parallel="classes" thread-count="2"`

### Sequential execution (1 device)

1. Start 1 emulator
2. Update `device1.*` in `config.properties` with your device details
3. Change `thread-count` to `"1"` in `testng.xml`
4. Run `mvn clean test`

### Running on a real device

1. Enable **Developer Options** on your phone: Settings > About Phone > tap "Build Number" 7 times
2. Enable **USB Debugging**: Settings > Developer Options > USB Debugging
3. Connect your phone via USB (use a data cable, not charge-only)
4. Accept the **"Allow USB debugging?"** popup on your phone
5. Verify connection: `adb devices` — should show your device serial (not "unauthorized")
6. Update `config.properties`:
   ```properties
   deviceCount = 1
   device1.deviceName = Your Phone Model
   device1.udid = YOUR_DEVICE_SERIAL   # from "adb devices"
   device1.systemPort = 8200
   ```
7. Change `thread-count` to `"1"` in `testng.xml`
8. Run `mvn clean test` — Appium will install the APK on your device automatically

---

## 7. Configuration Reference

Config file: `src/main/java/org/automation/resources/config.properties`

| Key                    | Purpose                                    | Default Value                     |
|------------------------|--------------------------------------------|-----------------------------------|
| `ipAddress`            | Appium server IP address                   | `127.0.0.1`                       |
| `port`                 | Appium server port                         | `4723`                            |
| `deviceCount`          | Number of devices in the pool              | `2`                               |
| `device1.deviceName`   | Device 1 emulator name                     | `Your First Device Name`          |
| `device1.udid`         | Device 1 UDID                              | `emulator-5554`                   |
| `device1.systemPort`   | Device 1 UiAutomator2 system port          | `8200`                            |
| `device2.deviceName`   | Device 2 emulator name                     | `Your Second Device Name`         |
| `device2.udid`         | Device 2 UDID                              | `emulator-5556`                   |
| `device2.systemPort`   | Device 2 UiAutomator2 system port          | `8201`                            |
| `appPackage`           | Android app package name                   | `io.appium.android.apis`          |
| `appActivity`          | Main launch activity                       | `io.appium.android.apis.ApiDemos` |
| `apkFileName`          | APK file name in resources/ folder         | `ApiDemos-debug.apk`              |
| `defaultWaitTimeout`   | Implicit & explicit wait timeout (seconds) | `15`                              |
| `retryCount`           | Max retry attempts for failed tests        | `1`                               |

---

## 8. Project Structure

```
appium-pom-project/
├── .github/workflows/
│   └── appium.yml                              # CI/CD pipeline (GitHub Actions)
├── docs/
│   └── ARCHITECTURE.md                         # Framework architecture documentation
├── src/
│   ├── main/java/org/automation/
│   │   ├── base/                               # BASE LAYER - shared page logic
│   │   │   └── AndroidActions.java             # driver, waitHelper, element state, gestures
│   │   │
│   │   ├── config/                             # Driver configuration
│   │   │   ├── ConfigReader.java               # config.properties reader
│   │   │   └── DriverFactory.java              # Singleton factory, ThreadLocal drivers
│   │   │
│   │   ├── locators/                           # LOCATORS LAYER - selector strings only
│   │   │   ├── HomeLocators.java
│   │   │   ├── ViewsLocators.java
│   │   │   ├── LayoutsLocators.java
│   │   │   ├── ScrollViewLocators.java
│   │   │   ├── DragDropLocators.java
│   │   │   ├── ExpandableListLocators.java
│   │   │   ├── ControlsLocators.java
│   │   │   ├── AlertDialogsLocators.java
│   │   │   ├── AppLocators.java
│   │   │   ├── TextLocators.java
│   │   │   ├── LogTextBoxLocators.java
│   │   │   ├── ListsLocators.java
│   │   │   ├── ProgressBarLocators.java
│   │   │   └── HorizontalScrollLocators.java
│   │   │
│   │   ├── pages/                              # PAGES LAYER - methods and interactions
│   │   │   ├── HomePage.java                   # Home screen: categories, navigation
│   │   │   ├── ViewsPage.java                  # Views sub-menu navigation
│   │   │   ├── LayoutsPage.java                # Layouts sub-menu navigation
│   │   │   ├── AppPage.java                    # App sub-menu navigation
│   │   │   ├── TextPage.java                   # Text sub-menu navigation
│   │   │   ├── ScrollViewPage.java             # TC-002: scroll to bottom and back
│   │   │   ├── DragDropPage.java               # TC-003: drag dot and verify result
│   │   │   ├── ExpandableListPage.java         # TC-004: expand/collapse groups
│   │   │   ├── ControlsPage.java               # TC-005: button, checkbox, radio, toggle
│   │   │   ├── AlertDialogsPage.java           # TC-006: OK Cancel, List, Single choice
│   │   │   ├── LogTextBoxPage.java             # TC-007: type text and verify log
│   │   │   ├── ListsPage.java                  # TC-008: dynamic scroll until item found
│   │   │   ├── ProgressBarPage.java            # TC-009: increment/decrement progress
│   │   │   └── HorizontalScrollPage.java       # TC-010: horizontal swipe verification
│   │   │
│   │   ├── utils/                              # Utility classes
│   │   │   ├── AppiumUtils.java                # Server start/stop, ExtentReports
│   │   │   ├── WaitHelper.java                 # Explicit waits (WebDriverWait wrapper)
│   │   │   ├── ScrollHelper.java               # UiScrollable + mobile:scroll actions
│   │   │   └── SwipeHelper.java                # W3C PointerInput swipes
│   │   │
│   │   └── resources/
│   │       └── config.properties               # Appium & device configuration
│   │
│   └── test/java/org/automation/
│       ├── tests/                              # TEST LAYER - test logic and assertions
│       │   ├── HomeTest.java                   # TC-001
│       │   ├── ScrollViewTest.java             # TC-002
│       │   ├── DragDropTest.java               # TC-003
│       │   ├── ExpandableListTest.java         # TC-004
│       │   ├── ControlsTest.java               # TC-005
│       │   ├── AlertDialogsTest.java           # TC-006
│       │   ├── LogTextBoxTest.java             # TC-007
│       │   ├── ListsTest.java                  # TC-008
│       │   ├── ProgressBarTest.java            # TC-009
│       │   └── HorizontalScrollTest.java       # TC-010
│       │
│       ├── testData/                           # External test data files (JSON)
│       │   ├── logTextBoxTestData.json         # TC-007: input text and expected log output
│       │   └── alertDialogsTestData.json       # TC-006: expected dialog selection message
│       │
│       └── testUtils/                          # Test infrastructure
│           ├── AndroidBaseClass.java           # Suite/class/method lifecycle hooks
│           ├── Listeners.java                  # ExtentReports + screenshot on failure + skip logging
│           └── RetryAnalyzer.java              # Auto-retry (count from config.properties)
│
├── testng.xml                                  # TestNG suite: parallel="classes" thread-count="2"
├── pom.xml                                     # Maven dependencies and plugins
├── .gitignore                                  # Excludes /target, *.apk, .env, reports
└── README.md                                   # This file
```

---

## 9. Test Scenarios Covered

| TC ID  | Test Name                  | Description                                                          | Screen / Flow                                        |
|--------|----------------------------|----------------------------------------------------------------------|------------------------------------------------------|
| TC-001 | Home Screen Verification   | Launch app, verify all 11 categories displayed, navigate to Views    | Home -> Views                                        |
| TC-002 | ScrollView Bottom/Top      | Scroll to bottom, verify last element; scroll to top, verify first   | Home -> Views -> Layouts -> ScrollView -> 2. Long    |
| TC-003 | Drag and Drop              | Long-press drag dot to target, verify EditText result updates        | Home -> Views -> Drag and Drop                       |
| TC-004 | Expandable Lists           | Expand/collapse groups, verify children appear/disappear             | Home -> Views -> Expandable Lists -> Custom Adapter  |
| TC-005 | Controls Light Theme       | Interact with button, checkbox, radio, toggle                        | Home -> Views -> Controls -> 1. Light Theme          |
| TC-006 | Alert Dialogs              | Dismiss OK Cancel, List (data-driven), Single choice dialogs         | Home -> App -> Alert Dialogs                         |
| TC-007 | LogTextBox                 | Type text entries, tap Add, verify log appends both entries (JSON)    | Home -> Text -> LogTextBox                           |
| TC-008 | Lists Dynamic Scroll       | Dynamic UiScrollable scroll until "King Lear" found                   | Home -> Views -> Lists -> 04. ListAdapter            |
| TC-009 | Progress Bar Incremental   | Increase/decrease progress bar and verify exact values                | Home -> Views -> Progress Bar -> 1. Incremental      |
| TC-010 | E2E Horizontal Swipe       | Full 4-screen navigation flow with horizontal swipe verification     | Home -> Views -> Layouts -> HorizontalScrollView     |

---

## 10. Viewing Reports

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
- Pass/fail/skip status per test with description
- Failure screenshots embedded in the report
- Skip reason logged via throwable stack trace
- System information and timestamps

---

## 11. CI/CD

The GitHub Actions workflow is defined in `.github/workflows/appium.yml`.

### What triggers it
- **Push** to `main` branch
- **Pull requests** targeting `main`
- **Scheduled** daily at midnight UTC

### What it does
1. Checks out the code
2. Sets up JDK 17 and Node.js 20
3. Installs Appium 3 + UiAutomator2 driver
4. Downloads the APK from GitHub releases
5. Launches an Android emulator (API 29, Pixel 5)
6. Starts Appium server and runs `mvn clean test`
7. Uploads ExtentReport, failure screenshots, and Surefire reports as artifacts

### Where to view results
- Go to **Actions** tab in the GitHub repository
- Click on the latest workflow run
- Download the `extent-report`, `failure-screenshots`, or `surefire-reports` artifacts

---

## 12. Known Issues / Limitations

- **CI pipeline:** The GitHub Actions workflow is configured to run tests on a single emulator. Full parallel CI execution with multiple emulators could be implemented later using a matrix strategy or cloud device farms.

---

## 13. Contributing

### Naming convention
All code uses **camelCase** for methods and variables, **PascalCase** for classes. Locators use `UPPER_SNAKE_CASE` constants.

### Branch naming convention
```
feature/TC-011-new-test-scenario
bugfix/TC-005-controls-seekbar
hotfix/config-emulator-name
docs/update-readme
```

### Commit message style
Use imperative mood with a type prefix:
```
feat: add ScrollView page object and locators
test: add TC-002 scroll to bottom and back test
fix: update wait timeout for slow emulators
docs: update README with new test scenarios
refactor: merge BasePage into AndroidActions
ci: add GitHub Actions workflow
```

### How to add a new Page class and test

1. **Create a locators class** in `src/main/java/org/automation/locators/`
   - Define `public static final String` selector constants
   - Priority: Accessibility ID > Resource ID > UiAutomator text > XPath

2. **Create a page class** in `src/main/java/org/automation/pages/`
   - Extend `AndroidActions` (from `org.automation.base`)
   - Use locators from the corresponding `*Locators` class (never inline selectors)
   - Return values for tests to assert — no `Assert` calls in pages
   - Add Javadoc with `@param`, `@return`, `@throws` on all public methods

3. **Create a test class** in `src/test/java/org/automation/tests/`
   - Extend `AndroidBaseClass`
   - Use `@Test(description = "[TC-XXX] ...", retryAnalyzer = RetryAnalyzer.class)`
   - Keep assertions in the test layer only
   - Never import from `locators/`

4. **Register in `testng.xml`**
   ```xml
   <class name="org.automation.tests.YourTest"/>
   ```

5. **Update documentation**
   - Add test scenario to the Test Scenarios table in this README
   - Update ARCHITECTURE.md with new files
