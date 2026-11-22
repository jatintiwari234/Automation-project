# Fixes Applied (review notes)

| # | Area | Problem | Fix |
|---|---|---|---|
| 1 | Login tests | Expected messages lacked SauceDemo's "Epic sadface: " prefix, so 5 login tests failed | Corrected expected text in `LoginDataProvider` and `LoginTests` |
| 2 | Waits | `findAll()` used without waiting after SPA navigation (inventory, cart) - flaky counts | Page-load waits (`waitUntilLoaded`), URL waits and `visibilityOfAllElementsLocatedBy` |
| 3 | Waits | Add/remove actions had no confirmation, cart badge read immediately | Wait for button to flip Add <-> Remove; `invisibilityOfElementLocated` for empty cart |
| 4 | Waits | Login, checkout and finish steps did not wait for next page | `urlContains` waits on each transition |
| 5 | Waits | Sorting dropdown did not wait for re-render | Wait until selected option matches and list is visible |
| 6 | Browser | Chrome "Change your password" pop-up can block the demo | Password manager / leak detection disabled in `ChromeOptions` |
| 7 | Input | `sendKeys("")` on empty fields | Skip `sendKeys` when value is empty (field is still cleared) |
| 8 | Config | `config.properties` lived in `src/test` but was read by `src/main` code | Moved to `src/main/resources` |
| 9 | Maven | `-Dgroups=smoke` documented in README but ignored with a suite file | Added `testng-smoke.xml` and `-Psmoke` profile |
| 10 | Listener | `onTestSkipped` could throw when a `@BeforeMethod` failed; data-driven tests shared one report name | Listener creates the report entry on demand; parameters added to test names |
| 11 | Driver | Driver stored in ThreadLocal before configuration finished | Stored only after setup succeeds; duplicate property lookup removed |
| 12 | Logging | log4j configured but never used | `BasePage` logs every click/type/open |
| 13 | Tests | Logout test asserted on the stale page object | Asserts on the page object returned by `logout()` |
| 14 | Naming | `submitValidLogin` | Renamed `loginAs` |
| 15 | Coverage | - | Added sort Name (Z to A) test |
| 16 | Maven | `chrome` profile was `activeByDefault`, which Maven disables when another profile (e.g. `-Psmoke`) is used, leaving `browser` unset | Defaults for `browser` and `headless` moved to the main `<properties>`; profiles only override them |
