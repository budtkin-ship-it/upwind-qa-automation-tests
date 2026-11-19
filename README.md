## Prerequisites

- **Local** — JDK 17 or newer, Maven, and Google Chrome.
- **Docker** — A running Docker Engine with Linux amd64 container support. The image includes Java, Maven, and Chrome.
- Network access to the corresponding public test service is required.

## Local Execution

Run these commands from the repository root.

**UI tests**

```sh
mvn "-Dtest=LoginTest,ProductCartTest" test
```

**API tests**

```sh
mvn "-Dtest=BookingApiTest" test
```

**All tests**

```sh
mvn test
```

## Docker Execution

Run both commands from the repository root.

```sh
docker build -t qa-automation-tests .
docker run --rm qa-automation-tests
```

## Test Approach

- **Locators** use SauceDemo's `data-test` attributes for stable element identification.
- Synchronization relies on Selenide's built-in condition-based waits. No fixed sleeps are used.
- **UI and API code structure** keeps page-specific locators and actions in UI Page Objects and scenarios in UI test classes. API tests live separately and use REST Assured.
- **Assumptions and limitations** 
- Only positive scenarios are currently covered. Negative scenarios are not included.
- Detailed step-by-step logging is not configured. Test results are available through Maven and TestNG reports.
