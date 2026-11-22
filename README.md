# SauceDemo E-commerce Automation Framework

A maintainable UI automation framework for [SauceDemo](https://www.saucedemo.com/) built with Java 17, Selenium 4, TestNG and Maven.

## Technology stack

- Java 17
- Selenium WebDriver 4
- TestNG
- Maven
- Page Object Model (POM)
- TestNG DataProvider and listeners
- ExtentReports
- Failure screenshots
- GitHub Actions CI

## Framework capabilities

- Page Object Model with separate page and test layers
- ThreadLocal WebDriver storage, suitable for later parallel execution
- Selenium Manager for browser driver management
- Explicit waits; implicit wait is set to zero
- Browser selection through Maven profiles / system properties
- Data-driven negative login tests
- Smoke and regression TestNG groups
- Extent HTML report
- Automatic screenshot capture on test failure
- GitHub Actions headless Chrome execution

## Prerequisites

1. JDK 17 installed and `JAVA_HOME` configured.
2. Maven 3.8+ installed.
3. Google Chrome installed for the default profile.
4. Internet access for Maven dependencies and Selenium Manager.

Firefox can be used with the `firefox` Maven profile and Firefox installed locally.

## Run the tests

Run the full suite in Chrome:

```bash
mvn clean test
```

Run headless Chrome:

```bash
mvn clean test -Pheadless
```

Run Firefox:

```bash
mvn clean test -Pfirefox
```

Run only the smoke group:

```bash
mvn clean test -Psmoke
```

Profiles can be combined, e.g. headless smoke run: `mvn clean test -Psmoke,headless`.

Override browser and headless settings:

```bash
mvn clean test -Dbrowser=chrome -Dheadless=true
```

The TestNG suite is configured in `src/test/resources/testng.xml`.

## Reports and artifacts

After execution, inspect:

- Extent report: `target/extent-reports/index.html`
- TestNG / Surefire reports: `target/surefire-reports/`
- Failure screenshots: `target/screenshots/`
- Log file: `target/logs/automation.log`

Screenshots are captured by the TestNG listener before the WebDriver is closed.

## Project structure

```text
src/
├── main/
│   ├── java/com/jatin/saucedemo/
│   │   ├── base/          # Reusable page actions
│   │   ├── config/        # Configuration reader
│   │   ├── driver/        # WebDriver factory
│   │   ├── pages/         # Page Object classes
│   │   └── utils/         # Reporting and screenshots
│   └── resources/
│       ├── config.properties
│       └── log4j2.xml
└── test/
    ├── java/com/jatin/saucedemo/
    │   ├── base/          # Test setup and teardown
    │   ├── dataproviders/ # Test data providers
    │   ├── listeners/     # TestNG listener
    │   └── tests/         # Test classes
    └── resources/
        ├── testng.xml
        └── testng-smoke.xml
```

## Current automated coverage

The initial suite covers:

- Login: valid user, invalid credentials, empty username/password, locked user
- Inventory: product count, price and name sorting, add/remove cart badge, logout
- Cart: added product, removed product, multiple products
- Checkout: required-field validation and successful purchase

This is a starter portfolio framework, not a claim of exhaustive application coverage. Add further tests as you learn the application and verify behaviour.

## Test accounts

The demo application publishes test credentials on its login page. This framework uses the public `standard_user` and `locked_out_user` accounts with the published demo password. Do not use real credentials or store private secrets in this repository.

## Design decisions

- Page Objects contain locators and user-facing page actions.
- Test classes contain test flow and assertions.
- Explicit waits are centralized in `BasePage`.
- `DriverFactory` owns WebDriver lifecycle and uses `ThreadLocal`.
- TestNG listener handles reporting and failure screenshots.
- Browser and wait settings live in `src/main/resources/config.properties`, with command-line overrides.

## Known limitations / next improvements

- Parallel execution is not enabled by default. Before enabling it, validate listener/report thread safety and keep each test isolated.
- The current report is an ExtentReports HTML report; TestNG Surefire output is also generated.
- REST Assured API testing is intentionally not included because SauceDemo is being used as a UI test target. Add API automation against a separate practice API if needed.
- Validate selectors and expected values against the live demo application before presenting the project in an interview. Demo applications can change.

## Interview preparation

Be ready to explain:
1. Why Page Object Model is used and what belongs in a Page Object.
2. Why explicit waits are used instead of fixed sleeps.
3. How TestNG DataProvider and groups work.
4. How WebDriver is initialized and cleaned up.
5. How the listener captures a screenshot before teardown.
6. How Maven profiles select browsers.
7. How the CI workflow runs the suite in headless Chrome.
