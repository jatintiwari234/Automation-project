SauceDemo Automation
This is a small UI automation project I built to practise Selenium with Java. It tests SauceDemo, a public demo shop that a lot of people use for learning. I'm a QA engineer with about 10 months of manual testing behind me, and I'm moving into automation, so I wanted one project I could build properly, run end to end and explain line by line.
It uses Java, Selenium 4, TestNG and Maven, and follows the Page Object Model.
What it tests
There are 18 tests in total:
Login: a valid user, a locked-out user, and four invalid cases (wrong username, wrong password, empty username, empty password). The invalid ones run from a single TestNG `@DataProvider`.
Inventory: the product count, sorting by price and by name, the cart badge when you add and remove items, and logout.
Cart: adding one product, adding several, and removing one.
Checkout: first name and postal code validation, and a full purchase from cart to the thank-you page.
Four of them are tagged `smoke` so I can run a quick check without waiting for everything.
How it's put together
Each page of the site has its own class in `pages/`, holding the locators and the things a user can do on that page. The test classes only describe the flow and check the results. Any waiting is done in one place, `BasePage`, using `WebDriverWait` and `ExpectedConditions`. There is no `Thread.sleep` anywhere, and the implicit wait is set to zero so the two don't mix.
`DriverFactory` creates and closes the browser, and keeps it in a `ThreadLocal` so parallel runs would be possible later. I haven't turned parallel execution on, because I'd want to check the reporting for thread safety first.
A TestNG listener builds the Extent report and takes a screenshot when a test fails.
```
src/main/java/com/jatin/saucedemo/
    base/       BasePage with the shared actions and waits
    config/     reads config.properties
    driver/     DriverFactory
    pages/      LoginPage, InventoryPage, CartPage, CheckoutPage
    utils/      Extent report setup, screenshots
src/test/java/com/jatin/saucedemo/
    base/       BaseTest (open and close the browser)
    dataproviders/
    listeners/
    tests/
```
Running it
You need Java 17 or newer and Google Chrome. Selenium Manager downloads the right driver by itself, so you do need an internet connection the first time. I run it on Java 25 and it works.
From Eclipse (this is how I usually run it): import the folder with File > Import > Maven > Existing Maven Projects, then right-click the project and choose Run As > Maven build.... Put `clean test` in Goals. For the quick smoke run, type `smoke` in the Profiles box.
From the command line, if you have Maven installed:
```
mvn clean test                  # everything, in Chrome
mvn clean test -Psmoke          # only the smoke tests
mvn clean test -Pheadless       # Chrome without a window
mvn clean test -Pfirefox        # Firefox
mvn clean test -Psmoke,headless # profiles can be combined
```
You can also override the browser directly with `-Dbrowser=chrome -Dheadless=true`. The default settings and the timeouts are in `src/main/resources/config.properties`.
After a run
The HTML report is at `target/extent-reports/index.html`
Screenshots of any failed tests go in `target/screenshots/`
The log is at `target/logs/automation.log`
Plain Surefire output is in `target/surefire-reports/`
The `target` folder is wiped by `clean`, so each run starts fresh.
Things worth knowing
SauceDemo's login errors start with "Epic sadface:", so the expected messages in the tests include that prefix.
Chrome sometimes shows a "change your password" pop-up for the demo password, which blocks clicks. The driver setup switches the password manager off to avoid that.
It's a public demo site, so if they change a locator or a message, a test may need updating.
The login uses the demo accounts published on the site's own login page. Please don't put real credentials in this repo.
There's also a GitHub Actions workflow in `.github/workflows` that runs the tests in headless Chrome.
Possible next steps
API tests would be a good addition, but against a separate practice API rather than SauceDemo, since SauceDemo is just a front end. I'd also like to add more edge cases around checkout, and try parallel runs once I've checked the report handling.
