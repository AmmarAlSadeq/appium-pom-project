# ApiDemos Appium POM Automation Framework

## Framework Overview

Appium + Java + TestNG mobile automation framework using a strict **three-layer Page Object Model** against the ApiDemos Android app. Locators, pages, and tests are fully separated — no `@AndroidFindBy`, no `PageFactory`, no inline selectors in pages.

**Scope:** This framework covers test scenarios against ApiDemos-debug.apk (home screen navigation, category verification, drag and drop, controls, dialogs, and scroll interactions). Additional test scenarios will be added incrementally.

**Target App:** [ApiDemos-debug.apk v6.0.6](https://github.com/appium/android-apidemos/releases/download/v6.0.6/ApiDemos-debug.apk) - Package: `io.appium.android.apis` | Activity: `io.appium.android.apis.ApiDemos`

---

## Architecture — Three-Layer Page Object Model

The framework enforces strict separation across three layers:

```
┌─────────────────────────────────────────────────────┐
│                    TEST LAYER                        │
│         tests/ - Assertions and test flow only      │
└─────────────────────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────┐
│                   PAGES LAYER                        │
│  Private WebElement methods + public actions         │
│  References locators from *Locators constants        │
└─────────────────────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────┐
│                  LOCATORS LAYER                      │
│  public static final String constants only           │
│  No logic, no WebDriver imports                     │
└─────────────────────────────────────────────────────┘
```

### Page Object Pattern

Each page class follows this structure:

```java
public class XxxPage extends AndroidActions {

    AndroidDriver driver;

    public XxxPage(AndroidDriver driver) {
        super(driver);
        this.driver = driver;
    }

    private WebElement elementName() {
        return driver.findElement(AppiumBy.accessibilityId(XxxLocators.ELEMENT_NAME));
    }

    public void doSomething() {
        elementName().click();
    }
}
```

- Elements defined as **private methods** returning `WebElement` using `By`/`AppiumBy` with locator constants
- **NO `@AndroidFindBy`**, **NO `PageFactory`**, **NO `AppiumFieldDecorator`**
- Locator priority: Accessibility ID > Resource ID > UiAutomator text > XPath
- Pages that need gestures extend `AndroidActions`

---

## Design Principles

| Principle | Implementation |
|-----------|----------------|
| **Encapsulation** | Page objects encapsulate UI structure and behavior; tests interact through methods only |
| **Separation of Concerns** | Locators define selectors, pages define interactions, tests define assertions |
| **Reusability** | ScrollHelper, SwipeHelper shared across pages; AndroidActions provides gesture methods |
| **Maintainability** | Selector changes require editing only the locators class — single source of truth |
| **Test Independence** | Each test runs in isolation with fresh driver via DriverFactory |

---

## Project Structure

```
appium-pom-project/
├── .github/workflows/
│   └── appium.yml                         # CI/CD pipeline
├── docs/
│   └── ARCHITECTURE.md                    # This file
├── src/
│   ├── main/java/org/automation/
│   │   ├── locators/                      # LOCATORS LAYER - selector strings only
│   │   │   ├── HomeLocators.java          # Home screen accessibility IDs
│   │   │   ├── ViewsLocators.java         # Views sub-menu accessibility IDs
│   │   │   ├── LayoutsLocators.java       # Layouts sub-menu
│   │   │   ├── ScrollViewLocators.java    # ScrollView selectors
│   │   │   ├── DragDropLocators.java      # Drag and Drop resource IDs
│   │   │   ├── ExpandableListLocators.java # Expandable list text selectors
│   │   │   ├── ControlsLocators.java      # Controls accessibility IDs + resource IDs
│   │   │   ├── AlertDialogsLocators.java  # Alert dialog accessibility IDs + resource IDs
│   │   │   └── AppLocators.java           # App sub-menu accessibility IDs
│   │   │
│   │   ├── pages/                         # PAGES LAYER - methods and interactions
│   │   │   ├── HomePage.java              # Home screen: categories, navigation
│   │   │   ├── ViewsPage.java             # Views sub-menu: navigation to sub-screens
│   │   │   ├── LayoutsPage.java           # Layouts sub-menu: ScrollView navigation
│   │   │   ├── ScrollViewPage.java        # TC-002: scroll to bottom and back
│   │   │   ├── DragDropPage.java          # TC-003: drag dot and verify result
│   │   │   ├── ExpandableListPage.java    # TC-004: expand/collapse groups
│   │   │   ├── ControlsPage.java          # TC-005: button, checkbox, radio, toggle
│   │   │   ├── AlertDialogsPage.java      # TC-006: OK Cancel, List, Single choice dialogs
│   │   │   └── AppPage.java               # App sub-menu: Alert Dialogs navigation
│   │   │
│   │   ├── config/                        # Driver configuration
│   │   │   └── DriverFactory.java         # Singleton driver factory with capabilities
│   │   │
│   │   ├── utils/                         # Utility classes
│   │   │   ├── AppiumUtils.java           # Server start/stop, ExtentReports, JSON reader
│   │   │   ├── AndroidActions.java        # Gesture methods (long press, swipe, drag)
│   │   │   ├── WaitHelper.java            # Explicit waits (WebDriverWait wrapper)
│   │   │   ├── ScrollHelper.java          # UiScrollable + mobile:scroll actions
│   │   │   └── SwipeHelper.java           # W3C swipe actions (left/right/up/down)
│   │   │
│   │   └── resources/
│   │       ├── config.properties          # Appium & device configuration
│   │       └── ApiDemos-debug.apk         # Target APK
│   │
│   └── test/java/org/automation/
│       ├── tests/                         # TEST LAYER - test logic and assertions
│       │   ├── HomeTest.java              # TC-001: home screen categories
│       │   ├── ScrollViewTest.java        # TC-002: scroll to bottom and back
│       │   ├── DragDropTest.java          # TC-003: drag and drop
│       │   ├── ExpandableListTest.java    # TC-004: expandable lists
│       │   ├── ControlsTest.java          # TC-005: controls light theme
│       │   └── AlertDialogsTest.java      # TC-006: alert dialogs
│       │
│       ├── testData/                      # External test data files
│       │   └── messages.json              # Expected messages for assertions
│       │
│       └── testUtils/                     # Test infrastructure
│           ├── AndroidBaseClass.java      # Test lifecycle hooks + IHookable retry
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
| Setup | `@BeforeClass` | Start server, create driver |
| Teardown | `@AfterClass` | Quit driver, stop server |

All test classes extend `AndroidBaseClass` and inherit:
- `driver` - AndroidDriver instance
- `waitHelper` - WaitHelper for explicit waits
- `scrollHelper` - ScrollHelper for scroll operations
- `swipeHelper` - SwipeHelper for swipe gestures

`AndroidBaseClass` implements `IHookable` to automatically attach `RetryAnalyzer` to all tests.

---

## Data Layer

Test data is externalized in JSON files under `src/test/java/org/automation/testData/`.

### messages.json

Central store for all expected message strings used in assertions:

```json
{
  "alertDialogs": {
    "listDialogSelection": "You selected: 0 , Command one"
  }
}
```

Tests read from this file using Jackson `ObjectMapper`, keeping expected text out of test code.

---

## Utility Classes

### WaitHelper

WebDriverWait wrapper eliminating all `Thread.sleep` calls.

| Method | Returns | Description |
|--------|---------|-------------|
| `waitForVisibility(element)` | WebElement | Waits for element to be visible |
| `waitForClickable(element)` | WebElement | Waits for element to be clickable |
| `waitForTextPresent(element, text)` | boolean | Waits for text in element |
| `waitUntilInvisible(element)` | boolean | Waits for element to disappear |

### ScrollHelper

UiScrollable for text-based discovery and mobile:scroll for gesture-based navigation.

| Method | Returns | Description |
|--------|---------|-------------|
| `scrollToText(text)` | boolean | UiScrollable scroll to visible text |
| `scrollDown()` / `scrollUp()` | boolean | mobile:scroll via JavascriptExecutor |
| `scrollToBottom()` / `scrollToTop()` | boolean | UiScrollable scrollToEnd/beginning |
| `scrollToTextWithRetry(text, max)` | boolean | Dynamic loop: scroll until found |

### SwipeHelper

W3C PointerInput-based swiping with configurable duration and offset.

| Method | Parameters | Description |
|--------|------------|-------------|
| `swipeLeft()` / `swipeRight()` | durationMs, offset | Horizontal swipe with percentage |
| `swipeUp()` / `swipeDown()` | durationMs, offset | Vertical swipe with percentage |
| `horizontalSwipe(startX, startY, endX)` | coordinates | Precise horizontal swipe |

### AndroidActions (Gesture Methods)

Inherited by page objects that need gesture support:

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

`RetryAnalyzer` automatically retries failed tests up to **2 times** via `AndroidBaseClass` implementing `IHookable`.

---

## Test Scenarios

| TC ID | Test Name | Navigation Path | Key Assertions |
|-------|-----------|-----------------|----------------|
| TC-001 | Home Screen Verification | Home | 11 categories present, Views tappable |
| TC-002 | ScrollView Bottom/Top | Home -> Views -> Layouts -> ScrollView -> 2. Long | Scroll bottom, scroll top, first element visibility |
| TC-003 | Drag and Drop | Home -> Views -> Drag and Drop | Drag dot1 to dot2, assert "Dropped!" text |
| TC-004 | Expandable Lists | Home -> Views -> Expandable Lists -> Custom Adapter | Expand/collapse groups, child count changes |
| TC-005 | Controls Light Theme | Home -> Views -> Controls -> 1. Light Theme | Button, checkbox, radio, toggle (`getAttribute("checked")`) |
| TC-006 | Alert Dialogs | Home -> App -> Alert Dialogs | OK Cancel, List (message verified from messages.json), Single choice dialogs |

Additional scenarios (TC-007 through TC-010) will be added incrementally.

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
   ├── Page objects instantiate with AndroidDriver
   ├── Private WebElement methods reference locator constants
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
- Data-driven: expected messages stored in `testData/messages.json`

### Page Object Conventions
- Elements defined as **private methods** returning `WebElement` using `By`/`AppiumBy` with locator constants
- **NO `@AndroidFindBy`**, **NO `PageFactory`**, **NO `AppiumFieldDecorator`**
- Pages needing gestures extend `AndroidActions`
- No assertions in page objects - all assertions live in test classes
- Tests never reference locators directly - only call page methods
- Android checkbox/radio/toggle states use `getAttribute("checked")` not `isSelected()`

### Selector Strategy

1. **Accessibility ID** - `AppiumBy.accessibilityId("Views")`
2. **Resource ID** - `By.id("io.appium.android.apis:id/button")`
3. **UiAutomator** - `AppiumBy.androidUIAutomator("new UiSelector().text(...)")` for dynamic lookups

### Adding a New Test
1. Create locators class in `src/main/java/org/automation/locators/` with `public static final String` constants
2. Create page class in `src/main/java/org/automation/pages/` with private WebElement methods referencing locators
3. Create test class in `src/test/java/org/automation/tests/`
4. Add test class to `testng.xml`
5. Update this ARCHITECTURE.md and README.md
