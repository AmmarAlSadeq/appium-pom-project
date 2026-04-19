# ApiDemos Appium POM Automation Framework

## Framework Overview

Appium + Java + TestNG mobile automation framework implementing a three-layer Page Object Model for the ApiDemos Android app. The framework provides a structured approach to mobile UI testing with emphasis on maintainability, reusability, and clear separation of concerns.

**Scope:** This framework covers test scenarios against ApiDemos-debug.apk (home screen navigation, category verification, and sub-menu validation). Additional test scenarios will be added incrementally.

**Target App:** [ApiDemos-debug.apk v6.0.6](https://github.com/appium/android-apidemos/releases/download/v6.0.6/ApiDemos-debug.apk) - Package: `io.appium.android.apis` | Activity: `io.appium.android.apis.ApiDemos`

---

## Architecture Layers

The framework follows a three-layer architecture:

| Layer | Location | Purpose |
|-------|----------|---------|
| **Locators** | `src/main/java/org/automation/locators/` | Selector strings separated from page logic |
| **Page Objects** | `src/main/java/org/automation/pages/` | Methods and interactions |
| **Tests** | `src/test/java/org/automation/tests/` | Test classes calling page methods |

```
┌─────────────────────────────────────────────────────┐
│                    TEST LAYER                        │
│         tests/ - Calls page methods only            │
└─────────────────────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────┐
│                   PAGES LAYER                        │
│       pages/ - Methods and interactions             │
└─────────────────────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────┐
│                  LOCATORS LAYER                      │
│       locators/ - Selector strings only             │
└─────────────────────────────────────────────────────┘
```

---

## Design Principles

| Principle | Implementation |
|-----------|----------------|
| **Encapsulation** | Page objects encapsulate UI structure and behavior; tests interact through methods only |
| **Separation of Concerns** | Locators define selectors, pages define interactions, tests define intent |
| **Reusability** | WaitHelper, ScrollHelper, SwipeHelper shared across all pages |
| **Maintainability** | Single source of truth for locators reduces change impact |
| **Test Independence** | Each test runs in isolation with fresh driver via DriverFactory |

---

## Project Structure

```
appium-pom-automation/
├── .github/workflows/
│   └── appium.yml                         # CI/CD pipeline
├── docs/
│   └── ARCHITECTURE.md                    # This file
├── src/
│   ├── main/java/org/automation/
│   │   ├── locators/                      # LOCATORS LAYER - selector strings only
│   │   │   ├── HomeLocators.java          # Home screen selectors
│   │   │   └── ViewsLocators.java         # Views sub-menu selectors
│   │   │
│   │   ├── pages/                         # PAGES LAYER - methods and interactions
│   │   │   ├── HomePage.java              # Home screen: categories, navigation
│   │   │   └── ViewsPage.java             # Views sub-menu: animation verification
│   │   │
│   │   ├── config/                        # Driver configuration
│   │   │   └── DriverFactory.java         # Singleton driver factory with capabilities
│   │   │
│   │   ├── utils/                         # Utility classes
│   │   │   ├── AppiumUtils.java           # Server start/stop, ExtentReports, JSON reader
│   │   │   ├── AndroidActions.java        # Gesture methods (long press, swipe, drag)
│   │   │   ├── WaitHelper.java            # Explicit waits (WebDriverWait wrapper)
│   │   │   ├── ScrollHelper.java          # UiScrollable + W3C scroll actions
│   │   │   └── SwipeHelper.java           # W3C swipe actions (left/right/up/down)
│   │   │
│   │   └── resources/
│   │       └── config.properties          # Appium & device configuration
│   │
│   └── test/java/org/automation/
│       ├── tests/                         # TEST LAYER - test logic and assertions
│       │   └── HomeTest.java              # Home screen: categories, navigate to Views
│       │
│       └── testUtils/                     # Test infrastructure
│           ├── AndroidBaseClass.java      # Test lifecycle hooks (@BeforeClass/@AfterClass)
│           ├── Listeners.java             # ExtentReports + screenshot on failure
│           └── RetryAnalyzer.java         # Auto-retry failed tests (max 2)
│
├── testng.xml                             # TestNG suite configuration
├── pom.xml                                # Maven dependencies and plugins
├── .gitignore                             # Excludes /target, *.apk, .env, reports
└── README.md                              # Project documentation
```

---

## Driver Factory & Configuration

The framework uses a **Singleton DriverFactory** that manages the complete driver lifecycle. It reads all capabilities from `config.properties` and supports both emulator and real device targets.

### Configuration

```properties
# config.properties
ipAddress = 127.0.0.1                # Appium server IP
port = 4723                           # Appium server port
androidDeviceName = Pixel3            # Emulator/device name
appPackage = io.appium.android.apis   # App package
appActivity = io.appium.android.apis.ApiDemos   # Launch activity
apkFileName = ApiDemos-debug.apk      # APK in resources/
```

### Driver Lifecycle

```
┌─────────────────────────────────────────────────────────┐
│              DRIVER FACTORY (Singleton)                  │
├─────────────────────────────────────────────────────────┤
│  1. Read config.properties                              │
│     Loads IP, port, device name, app package/activity   │
│                                                         │
│  2. Start Appium Server                                 │
│     AppiumDriverLocalService via AppiumUtils            │
│     SESSION_OVERRIDE enabled                            │
│                                                         │
│  3. Create AndroidDriver                                │
│     UiAutomator2Options with capabilities from config   │
│     10s implicit wait configured                        │
│                                                         │
│  4. Provide Driver                                      │
│     getDriver() returns singleton instance              │
│                                                         │
│  5. Teardown                                            │
│     quitDriver() + stopService() in @AfterClass         │
└─────────────────────────────────────────────────────────┘
```

The `ipAddress` can be overridden at runtime: `mvn test -DipAddress=192.168.1.100`

---

## Test Base Class

`AndroidBaseClass` provides the test lifecycle shared by all test classes:

| Hook | Annotation | Purpose |
|------|-----------|---------|
| Setup | `@BeforeClass` | Start server, create driver, init helpers |
| Teardown | `@AfterClass` | Quit driver, stop server |

All test classes extend `AndroidBaseClass` and inherit:
- `driver` - AndroidDriver instance
- `waitHelper` - WaitHelper for explicit waits
- `scrollHelper` - ScrollHelper for scroll operations
- `swipeHelper` - SwipeHelper for swipe gestures

---

## Utility Classes

### WaitHelper

WebDriverWait wrapper eliminating all `Thread.sleep` calls. All waits are configurable via the constructor (default 15 seconds).

| Method | Returns | Description |
|--------|---------|-------------|
| `waitForVisibility(element)` | WebElement | Waits for element to be visible |
| `waitForClickable(element)` | WebElement | Waits for element to be clickable |
| `waitForTextPresent(element, text)` | boolean | Waits for text in element |
| `waitUntilInvisible(element)` | boolean | Waits for element to disappear |
| `waitForVisibilityByLocator(by)` | WebElement | Waits for element by locator |

### ScrollHelper

Dual-strategy scrolling using UiScrollable for text-based discovery and W3C Actions for gesture-based navigation.

| Method | Returns | Description |
|--------|---------|-------------|
| `scrollToText(text)` | boolean | UiScrollable scroll to visible text |
| `scrollDown()` / `scrollUp()` | boolean | mobile:scroll via JavascriptExecutor |
| `scrollDownWithActions()` / `scrollUpWithActions()` | boolean | W3C PointerInput swipe |
| `scrollToBottom()` / `scrollToTop()` | boolean | UiScrollable scrollToEnd/beginning |
| `scrollToTextWithRetry(text, max)` | boolean | Dynamic loop: scroll until found |

### SwipeHelper

W3C PointerInput-based swiping with configurable duration and offset. No deprecated TouchAction APIs.

| Method | Parameters | Description |
|--------|------------|-------------|
| `swipeLeft()` / `swipeRight()` | durationMs, offset | Horizontal swipe with percentage |
| `swipeUp()` / `swipeDown()` | durationMs, offset | Vertical swipe with percentage |
| `horizontalSwipe(startX, startY, endX)` | coordinates | Precise horizontal swipe |
| `verticalSwipe(startX, startY, endY)` | coordinates | Precise vertical swipe |

### AndroidActions (Gesture Methods)

Inherited by all page objects for native gesture support:

| Method | Gesture | Description |
|--------|---------|-------------|
| `longPressAction(element)` | Long Press | 2-second press via mobile:longClickGesture |
| `swipeToElementAction(element, direction)` | Swipe | Directional swipe at 75% via mobile:swipeGesture |
| `dragAndDropAction(element, endX, endY)` | Drag & Drop | Drag to coordinates via mobile:dragGesture |

---

## Reporting & Listeners

The framework uses **ExtentReports 5.1.1** with a custom TestNG `ITestListener` for automatic report generation.

| Event | Action |
|-------|--------|
| `onTestStart` | Creates ExtentReports test entry |
| `onTestSuccess` | Logs PASS status |
| `onTestFailure` | Logs FAIL + captures throwable + attaches screenshot (Base64) |
| `onTestSkipped` | Logs SKIP status |
| `onFinish` | Flushes report to HTML file |

Report output: `src/reports/extent-report.html`

### Retry Mechanism

`RetryAnalyzer` automatically retries failed tests up to **2 times** before marking as failed. Integrated via TestNG listener.

---

## Test Scenarios

| TC ID | Test Name | Navigation Path | Key Assertions |
|-------|-----------|-----------------|----------------|
| TC-001 | Home Screen Verification | Home | 11 categories present, Views tappable |
| TC-001 | Navigate to Views | Home -> Views | Animation item visible in Views sub-menu |

Additional scenarios (TC-002 through TC-010) will be added incrementally as new page objects and locators are created.

---

## Execution Flow

```
1. mvn clean test
2. TestNG loads testng.xml
3. Listener registered (Listeners.java)
4. @BeforeClass (AndroidBaseClass)
   ├── DriverFactory.startService()  - Appium server starts
   ├── DriverFactory.getDriver()     - AndroidDriver created
   └── WaitHelper / ScrollHelper / SwipeHelper initialized
5. @Test methods execute
   ├── Page objects call locator-defined elements
   ├── Interactions via WaitHelper / ScrollHelper / SwipeHelper
   └── Assertions in test layer only
6. Listener captures pass/fail -> ExtentReports
   └── On failure: screenshot captured (Base64) and attached
7. @AfterClass (AndroidBaseClass)
   ├── DriverFactory.quitDriver()
   └── DriverFactory.stopService()
8. ExtentReports.flush() -> src/reports/extent-report.html generated
```

---

## Framework Stack

| Component         | Technology          | Version  |
|-------------------|---------------------|----------|
| Language          | Java                | 11       |
| Build Tool        | Maven               | 3.6+     |
| Test Framework    | TestNG              | 7.7.0    |
| Mobile Automation | Appium Java Client  | 9.4.0    |
| Appium Server     | Appium              | 2.0      |
| Web Driver        | Selenium            | 4.28.1   |
| Reporting         | ExtentReports       | 5.1.1    |
| JSON Parsing      | Jackson             | 2.15.3   |
| CI Platform       | GitHub Actions      | -        |

---

## Guidelines & Standards

### Test Design
- One concept per test - focus on single behavior verification
- Test classes named by feature: `HomeTest`, `ControlsTest`
- Independent tests - no dependencies between test cases
- Data-driven tests use `@DataProvider` with external JSON files
- Minimal test data - include only what the scenario requires

### Framework Conventions
- All selectors in `locators/` - never inline in page objects or tests
- Page methods for all interactions - tests express intent, not implementation
- No `Thread.sleep` - always use `WaitHelper` methods
- No `@AndroidFindBy` - use `By` locators from locators layer
- No assertions in page objects - all assertions live in test classes
- Tests never import from `locators/` layer

### Selector Strategy
Preferred locator priority:

1. **Accessibility ID** - most stable for ApiDemos
2. **Resource ID** - `By.id("android:id/list")`
3. **UiAutomator text selectors** - `new UiSelector().text("Views")`
4. **XPath** - fallback for dynamic elements

**Avoid**:
- Hardcoded coordinates (except for swipe/scroll gestures)
- Fragile DOM paths
- `@AndroidFindBy` annotations (breaks three-layer architecture)

### Adding a New Test
1. Create locators class in `src/main/java/org/automation/locators/`
2. Create page class in `src/main/java/org/automation/pages/`
3. Create test class in `src/test/java/org/automation/tests/`
4. Add test class to `testng.xml`
5. Update this ARCHITECTURE.md and README.md

### Documentation
- Javadoc for public methods in page objects and utilities
- `@param` for each parameter
- `@return` for methods returning values
- `@throws` when method can throw checked exceptions
