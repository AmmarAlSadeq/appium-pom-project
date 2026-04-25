# Architecture — Appium POM Automation Framework

## Architecture — Three-Layer Page Object Model

The framework enforces strict separation across three layers. All naming follows **camelCase** convention.

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
│  Extends AndroidActions for shared logic             │
└─────────────────────────────────────────────────────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────┐
│                  LOCATORS LAYER                      │
│  public static final String constants only           │
│  No logic, no WebDriver imports                     │
└─────────────────────────────────────────────────────┘
```

---

## Design Principles

| Principle | Implementation |
|-----------|----------------|
| **Encapsulation** | Page objects encapsulate UI structure; tests interact through methods only |
| **Separation of Concerns** | Locators define selectors, pages define interactions, tests define assertions |
| **Reusability** | AndroidActions provides shared element state + gestures; ScrollHelper, SwipeHelper, WaitHelper in utils |
| **Maintainability** | Selector changes require editing only the locators class — single source of truth |
| **DRY** | AndroidActions eliminates duplicated driver/waitHelper fields across all 14 page objects |

---

## Driver Factory & Parallel Execution

Singleton `DriverFactory` manages the complete driver lifecycle with ThreadLocal drivers for parallel execution.

### Parallel Architecture

```
@BeforeSuite  → startService()                          # Appium server starts once
@BeforeClass  → getDriver() → thread-to-device mapping   # 1 driver per thread, reused across classes
@BeforeMethod → resetApp()                               # App reset before each test
@AfterSuite   → quitAllDrivers() + stopService()         # Cleanup once at suite end
```

- **ThreadLocal drivers:** Each thread gets its own `AndroidDriver` instance
- **Sticky device mapping:** `ConcurrentHashMap<Long, Integer>` permanently maps each thread to a device
- **Driver reuse:** Driver created once per thread and reused across all test classes (not per-class)
- **Suite-level cleanup:** `quitAllDrivers()` quits all active drivers at `@AfterSuite`

---

## Test Base Class

`AndroidBaseClass` provides the test lifecycle shared by all test classes:

| Hook | Annotation | Purpose |
|------|-----------|---------|
| Start server | `@BeforeSuite` | Appium server starts once for entire suite |
| Create driver | `@BeforeClass` | Thread-local driver + WaitHelper/ScrollHelper/SwipeHelper |
| Reset app | `@BeforeMethod` | Terminates and reactivates app before each test |
| Teardown | `@AfterSuite` | Quit all drivers + stop server once at suite end |

All test classes extend `AndroidBaseClass` and inherit `driver`, `waitHelper`, `scrollHelper`, `swipeHelper`.

---

## AndroidActions (Base Class)

`AndroidActions` in `org.automation.base` is the single base class for all page objects:

| Method | Description |
|--------|-------------|
| `isElementDisplayed(element)` | Safe visibility check with try/catch |
| `isElementNotDisplayed(element)` | Safe non-visibility check (for dismiss assertions) |
| `isChecked(element)` | Gets checked state via `getAttribute("checked")` |
| `getElementText(element)` | Safe text retrieval with empty string fallback |
| `longPressAction(element)` | `mobile: longClickGesture` via JavascriptExecutor |
| `dragAndDropAction(element, x, y)` | `mobile: dragGesture` via JavascriptExecutor |

All page objects extend `AndroidActions` and inherit `driver`, `waitHelper`, and all shared methods.

---

## Utility Classes

### WaitHelper
WebDriverWait wrapper — timeout reads from `config.properties` (`defaultWaitTimeout`). Includes `waitForVisibility`, `waitForClickable`, `waitForTextPresent`, `waitUntilInvisibleByLocator`.

### ScrollHelper
UiScrollable text-based + mobile:scroll gesture-based scrolling.

### SwipeHelper
W3C PointerInput-based swiping with configurable direction, duration, and offset. No deprecated TouchAction.

---

## Data-Driven Testing

Test data is externalized in JSON files under `src/test/java/org/automation/testData/`:

| File | Used By | Purpose |
|------|---------|---------|
| `logTextBoxTestData.json` | TC-007 LogTextBoxTest | Input strings and expected log output |
| `alertDialogsTestData.json` | TC-006 AlertDialogsTest | Expected list dialog selection message |

Data is loaded via `TestDataReader.getData()` using Jackson.

---

## Reporting & Listeners

**ExtentReports 5.1.1** with TestNG `ITestListener`:

| Event | Behavior |
|-------|----------|
| Test starts | Creates ExtentTest entry with description |
| Test passes | Logs PASS status |
| Test fails | Logs FAIL + throwable + screenshot (embedded in report) |
| Test skipped | Logs SKIP + skip reason from throwable |
| Suite ends | Flushes report to `src/reports/extent-report.html` |

Screenshot capture is protected against dead sessions — gracefully skipped if Appium session is no longer active.

### Retry Mechanism
`RetryAnalyzer` auto-retries failed tests up to `retryCount` (from `config.properties`). Wired per test via `@Test(retryAnalyzer = RetryAnalyzer.class)`.

