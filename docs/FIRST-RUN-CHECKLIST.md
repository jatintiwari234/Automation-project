# First Run Checklist

1. Install JDK 17 and Maven.
2. Confirm `java -version` and `mvn -version`.
3. Open this folder as a Maven project in IntelliJ IDEA or Eclipse.
4. Allow Maven to download dependencies.
5. Confirm Chrome is installed.
6. Run `mvn clean test`.
7. Open `target/extent-reports/index.html`.
8. If a test fails, inspect the assertion, screenshot and application state before changing the test.
9. Do not hide failures with broad exception handling or unconditional retries.
10. Before an interview, run the suite from a clean checkout and explain each framework layer.
