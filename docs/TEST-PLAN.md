# SauceDemo Automation Test Plan

## 1. Objective
Validate core e-commerce user journeys on SauceDemo using browser-based automated tests.

## 2. Scope
Included: authentication, inventory, sorting, cart operations, checkout validation and order completion.

Excluded: payment gateway integration, backend/API validation, security penetration testing, load testing and production payment processing.

## 3. Test approach
- UI automation with Selenium WebDriver 4 and Java 17.
- TestNG for execution, assertions, data providers and suite grouping.
- Page Object Model for page-specific locators and actions.
- Maven for dependency and test execution management.
- ExtentReports and screenshots for execution evidence.

## 4. Test types
- Smoke: critical login, inventory, cart and purchase path.
- Regression: positive and negative functional scenarios.
- Data-driven: invalid login combinations.

## 5. Environment
- Application: https://www.saucedemo.com/
- Browser: Chrome by default; Firefox supported when installed.
- Runtime: Java 17.
- Build: Maven.

## 6. Entry / exit criteria
Entry: application reachable, JDK and Maven configured, browser installed.
Exit: planned tests executed, failures reviewed, reports and screenshots retained, known limitations documented.

## 7. Risks
- Demo application behaviour or selectors may change.
- External network or browser startup failures may affect runs.
- Shared demo accounts can have state or availability constraints.
- Browser versions and CI environment can differ.

## 8. Evidence
Retain TestNG/Surefire reports, Extent report, failure screenshots and relevant logs.
