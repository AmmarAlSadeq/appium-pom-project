# ApiDemos Appium POM Automation Framework

## Framework Overview

Appium + Java + TestNG mobile automation framework using a strict **three-layer Page Object Model** against the ApiDemos Android app. Locators, pages, and tests are fully separated — no `@AndroidFindBy`, no `PageFactory`, no inline selectors in pages.

**Scope:** 10 automated test scenarios covering home screen navigation, scroll views, drag and drop, expandable lists, controls, alert dialogs, log text box, lists, progress bar, and horizontal swipe.

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
│  Extends BasePage for shared driver/wait logic       │
└─────────────────────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────┐
│                  LOCATORS LAYER                      │
│  public static final String constants only           │
│  No logic, no WebDriver imports                     │
└─────────────────────────────────────────────────────┘
```

### Inheritance Hierarchy

```
BasePage (base/)
  ├── driver + waitHelper fields
  ├── isElementDisplayed(), isChecked(), getElementText()
  │
  ├── AndroidActions extends BasePage (utils/)
  │     ├── longPressAction(), dragAndDropAction()
  │     └── 8 pages extend AndroidActions
  │
  └── 6 pages extend BasePage directly
```

---

## Design Principles

| Principle | Implementation |
|-----------|----------------|
| **Encapsulation** | Page objects encapsulate UI structure; tests interact through methods only |
| **Separation of Concerns** | Locators define selectors, pages define interactions, tests define assertions |
| **Reusability** | BasePage, ScrollHelper, SwipeHelper shared across pages; AndroidActions provides gestures |
| **Maintainability** | Selector changes require editing only the locators class — single source of truth |
| **DRY** | BasePage eliminates duplicated driver/waitHelper fields across all 14 page objects |

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
│   │   ├── base/                          # SHARED BASE LOGIC
│   │   │   └── BasePage.java             # driver, waitHelper, common element methods
│   │   │
│   │   ├── locators/                      # LOCATORS LAYER - selector strings only
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
│   │   ├── pages/                         # PAGES LAYER - methods and interactions
│   │   │   ├── HomePage.java              # TC-001: categories, navigation
│   │   │   ├── ViewsPage.java             # Views sub-menu navigation
│   │   │   ├── LayoutsPage.java           # Layouts sub-menu
│   │   │   ├── ScrollViewPage.java        # TC-002: scroll to bottom and back
│   │   │   ├── DragDropPage.java          # TC-003: drag dot and verify result
│   │   │   ├── ExpandableListPage.java    # TC-004: expand/collapse groups
│   │   │   ├── ControlsPage.java          # TC-005: button, checkbox, radio, toggle
│   │   │   ├── AlertDialogsPage.java      # TC-006: OK Cancel, List, Single choice
│   │   │   ├── AppPage.java               # App sub-menu navigation
│   │   │   ├── TextPage.java              # Text sub-menu navigation
│   │   │   ├── LogTextBoxPage.java        # TC-007: type text and verify log
│   │   │   ├── ListsPage.java             # TC-008: dynamic scroll until item found
│   │   │   ├── ProgressBarPage.java       # TC-009: increment/decrement progress
│   │   │   └── HorizontalScrollPage.java  # TC-010: horizontal swipe verification
│   │   │
│   │   ├── config/                        # Driver configuration
│   │   │   └── DriverFactory.java         # Singleton driver factory
│   │   │
│   │   ├── utils/                         # Utility classes
│   │   │   ├── AppiumUtils.java           # Server start/stop, ExtentReports, JSON reader
│   │   │   ├── AndroidActions.java        # Gestures: long press, drag, swipe (extends BasePage)
│   │   │   ├── WaitHelper.java            # Explicit waits (WebDriverWait wrapper)
│   │   │   ├── ScrollHelper.java          # UiScrollable + mobile:scroll actions
│   │   │   └── SwipeHelper.java           # W3C PointerInput swipes
│   │   │
│   │   └── resources/
│   │       ├── config.properties          # Appium & device configuration
│   │       └── ApiDemos-debug.apk         # Target APK
│   │
│   └── test/java/org/automation/
│       ├── tests/                         # TEST LAYER - test logic and assertions
│       │   ├── HomeTest.java              # TC-001
│       │   ├── ScrollViewTest.java        # TC-002
│       │   ├── DragDropTest.java          # TC-003
│       │   ├── ExpandableListTest.java    # TC-004
│       │   ├── ControlsTest.java          # TC-005
│       │   ├── AlertDialogsTest.java      # TC-006
│       │   ├── LogTextBoxTest.java        # TC-007
│       │   ├── ListsTest.java             # TC-008
│       │   ├── ProgressBarTest.java       # TC-009
│       │   └── HorizontalScrollTest.java  # TC-010
│       │
│       ├── testData/                      # External test data files
│       │   └── messages.json              # Expected messages for assertions
│       │
│       └── testUtils/                     # Test infrastructure
│           ├── AndroidBaseClass.java      # Suite/class lifecycle hooks
│           ├── Listeners.java             # ExtentReports + screenshot on failure
│           └── RetryAnalyzer.java         # Auto-retry (configurable via config.properties)
│
├── testng.xml                             # TestNG suite configuration
├── pom.xml                                # Maven dependencies and plugins
├── .gitignore                             # Excludes /target, *.apk, .env, reports
└── README.md                              # Project documentation
```

---

## Driver Factory & Configuration

Singleton `DriverFactory` manages the complete driver lifecycle. All capabilities externalized in `config.properties`.

### Configuration

```properties
# config.properties
ipAddress = 127.0.0.1
port = 4723
androidDeviceName = Pixel 5 API 27
appPackage = io.appium.android.apis
appActivity = io.appium.android.apis.ApiDemos
apkFileName = ApiDemos-debug.apk
defaultWaitTimeout = 15
retryCount = 2
```

### Driver Lifecycle

```
@BeforeSuite  → DriverFactory.startService()        # Appium server starts once
@BeforeClass  → DriverFactory.getDriver() + helpers  # Driver per test class
@AfterClass   → resetAppToHome()                     # App reset between classes
@AfterSuite   → DriverFactory.quitDriver() + stopService()  # Cleanup once
```

---

## Test Base Class

`AndroidBaseClass` provides the test lifecycle shared by all test classes:

| Hook | Annotation | Purpose |
|------|-----------|---------|
| Start server | `@BeforeSuite` | Appium server starts once for entire suite |
| Create driver | `@BeforeClass` | Driver + WaitHelper/ScrollHelper/SwipeHelper per class |
| Reset app | `@AfterClass` | Resets app to home screen between classes |
| Teardown | `@AfterSuite` | Quit driver + stop server once at suite end |

All test classes extend `AndroidBaseClass` and inherit `driver`, `waitHelper`, `scrollHelper`, `swipeHelper`.

---

## BasePage

`BasePage` in `org.automation.base` provides shared logic for all page objects:

| Method | Description |
|--------|-------------|
| `waitForElement(element)` | Waits for element visibility via WaitHelper |
| `isElementDisplayed(element)` | Safe visibility check with try/catch |
| `isElementNotDisplayed(element)` | Safe non-visibility check (for dismiss assertions) |
| `isChecked(element)` | Gets checked state via `getAttribute("checked")` |
| `getElementText(element)` | Safe text retrieval with empty string fallback |

---

## Utility Classes

### WaitHelper
WebDriverWait wrapper — timeout reads from `config.properties` (`defaultWaitTimeout`).

### ScrollHelper
UiScrollable text-based + mobile:scroll gesture-based scrolling.

### SwipeHelper
W3C PointerInput-based swiping with configurable duration and offset.

### AndroidActions
Gesture methods inherited by page objects: `longPressAction()`, `dragAndDropAction()`, `swipeToElementAction()`.

---

## Reporting & Listeners

**ExtentReports 5.1.1** with TestNG `ITestListener`:

- On failure: screenshot saved to `src/reports/screenshots/` and embedded in report
- Report output: `src/reports/extent-report.html`
- Retry: configurable via `retryCount` in `config.properties`

---

## Test Scenarios

| TC ID | Test Name | Navigation Path | Key Assertions |
|-------|-----------|-----------------|----------------|
| TC-001 | Home Screen Verification | Home | 11 categories present, Views tappable |
| TC-002 | ScrollView Bottom/Top | Home -> Views -> Layouts -> ScrollView -> 2. Long | Scroll bottom/top, first element visibility |
| TC-003 | Drag and Drop | Home -> Views -> Drag and Drop | Drag dot1 to dot2, assert "Dropped!" |
| TC-004 | Expandable Lists | Home -> Views -> Expandable Lists -> Custom Adapter | Expand/collapse groups, child visibility |
| TC-005 | Controls Light Theme | Home -> Views -> Controls -> 1. Light Theme | Button, checkbox, radio, toggle states |
| TC-006 | Alert Dialogs | Home -> App -> Alert Dialogs | OK Cancel, List (data-driven), Single choice |
| TC-007 | LogTextBox | Home -> Text -> LogTextBox | Type text, tap Add, verify log appends |
| TC-008 | Lists Dynamic Scroll | Home -> Views -> Lists -> 04. ListAdapter | UiScrollable scroll until target found |
| TC-009 | Progress Bar | Home -> Views -> Progress Bar -> 1. Incremental | Increase/decrease, value bounds 0-100 |
| TC-010 | Horizontal Swipe | Home -> Views -> Layouts -> HorizontalScrollView | Screenshot diff: swipe left changes, swipe right restores |

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

## Adding a New Test

1. Create `XxxLocators.java` in `locators/` with `public static final String` constants
2. Create `XxxPage.java` in `pages/` extending `BasePage` (or `AndroidActions` for gestures)
3. Create `XxxTest.java` in `tests/` extending `AndroidBaseClass`
4. Add test class to `testng.xml`
5. Update this ARCHITECTURE.md and README.md
