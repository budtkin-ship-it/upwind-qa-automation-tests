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

1. Open the project root in File Explorer. This is the folder containing `Dockerfile` and `pom.xml`.
2. Type `powershell` in the File Explorer address bar and press Enter.
3. Build the Docker image.

   ```powershell
   docker build -t qa-automation-tests .
   ```

4. After the build succeeds, run the tests in the same PowerShell window.

   ```powershell
   docker run --rm qa-automation-tests
   ```

5. A successful run shows `BUILD SUCCESS` with all tests run, 0 failures, 0 errors, and 0 skipped.

## Test Approach

- **Locators** use SauceDemo's `data-test` attributes for stable element identification.
- Synchronization relies on Selenide's built-in condition-based waits. No fixed sleeps are used.
- **UI and API code structure** keeps page-specific locators and actions in UI Page Objects and scenarios in UI test classes. API tests live separately and use REST Assured.
- **Assumptions and limitations** 
- Only positive scenarios are currently covered. Negative scenarios are not included.
- Detailed step-by-step logging is not configured. Test results are available through Maven and TestNG reports.
