# 🚀 Test Automation Framework: Java + Playwright **(API & UI)**

## 📌 Overview
This repository contains a modular automation test framework built using Java + Playwright, covering API testing (Swagger Petstore API v2) and UI testing (Sauce Demo). The framework is designed for efficiency, maintainability, and scalability, supporting cross-browser execution, parallel execution, and detailed reporting.
## 📁 Project Structure
```
playwright-test-automation/
│── api-tests/
│   ├── src/
│   │   ├── main/java/api.clients/              # HTTP Request Builders
│   │   ├── main/java/api.models/               # API Data Models
│   │   ├── main/java/api.utilities/            # API Helpers
│   │   ├── test/java/api.functional/           # Functional API Tests
│   │   ├── test/java/api.nonfunctional/        # Non-functional API Tests
│   │   ├── test/java/api.runners/              # API Test Runners
│   │   ├── test/java/api.stepdefinitions/      # API Step Definitions (Cucumber)
│── ui-tests/
│   ├── src/
│   │   ├── main/java/pages/                    # UI Page Objects
│   │   ├── main/java/utilities/                # PlaywrightManager (Browser setup)
│   │   ├── test/java/ui.functional/            # Functional UI Tests
│   │   ├── test/java/ui.nonfunctional/         # Non-functional UI Tests
│   │   ├── test/java/ui.runners/               # UI Test Runners
│   │   ├── test/java/ui.stepdefinitions/       # UI Step Definitions (Cucumber)
│── common-utils/                               # Shared utilities across API & UI tests
│── config/                                     # Configuration files
│── target/                                     # Test reports & logs
│── pom.xml                                     # Maven configuration
│── README.md                                   # Documentation
│── .gitignore                                  # Ignored files
```

---

## 🛠️ Installation & Setup

### **🔹 1. Clone the Repository**
```sh
git clone https://github.com/cepnif/playwright-test-automation.git
cd playwright-test-automation
```

### **🔹 2. Install Dependencies**

#### ✅ **Install Java 21**
```sh
java -version
```

#### ✅ **Install Maven**
```sh
mvn -version
```

#### ✅ **Install Node.js & Playwright Browsers**
```sh
node -v
npx playwright install
```

---

## 🚀 Running Tests

### **🔹 Run UI Tests**
```sh
mvn test -Pui-tests
```

### **🔹 Run API Tests**
```sh
mvn test -Papi-tests
```

### **🔹 Run UI & API Tests Together**
```sh
mvn test -Pall-tests
```

### **🔹 Run Tests on a Specific Browser**
```sh
mvn test -Pui-tests -Dbrowser=firefox
```

### **🔹 Run Cross-Browser Testing (Parallel Execution)**
```sh
mvn test -Pui-tests -Dbrowser=chrome,firefox,webkit
```


### 🏆 Key Features of This Framework

- ✅ Playwright Integration (Supports UI & API)

- ✅ Cross-browser testing (Chromium, Firefox, WebKit, Edge)

- ✅ Parallel execution with TestNG

- ✅ Data-driven & BDD testing with Cucumber

- ✅ Robust error handling & logging


### 🔍 API Testing (Swagger Petstore)
API Test Features

- ✅ Uses Playwright APIRequestContext for API interactions

- ✅ Covers positive & negative test scenarios

- ✅ Validates status codes, response bodies, schema compliance

- ✅ Handles authentication, invalid requests, error handling

### 🎭 UI Testing (Sauce Demo)
UI Test Features

- ✅ Built using Playwright Page Object Model (POM)

- ✅ Supports Cross-browser execution (Chromium, Firefox, WebKit, Edge)

- ✅ Tests Login, Product interactions, Cart, Checkout

- ✅ Implements parallel execution

### 🏁 Running TestNG XML for Parallel Execution
🛠️ Example testng.xml for Parallel Execution

```sh
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd" >
<suite name="Parallel UI & API Tests" parallel="methods" thread-count="4">

    <test name="UI Tests">
        <classes>
            <class name="tests.ui.LoginTests"/>
            <class name="tests.ui.CartTests"/>
            <class name="tests.ui.CheckoutTests"/>
        </classes>
    </test>

    <test name="API Tests">
        <classes>
            <class name="tests.api.GetStoreOrderTests"/>
            <class name="tests.api.PostStoreOrderTests"/>
        </classes>
    </test>

</suite>
```

### How to Run TestNG XML
1- Run using Maven

```sh
mvn test -Dsurefire.suiteXmlFiles=testng.xml
```

2- Run using Intellij IDEA

```sh
Right-click testng.xml ➡ Click Run
```

### 🔎 Reviewing Test Results
✅ Console Output
- ✅ Passed Tests: Green
- ✅ ❌ Failed Tests: Red ❌
- ⚠️ Skipped Tests: Yellow ⚠️

✅ Review Logs (logs/test-execution.log)

- INFO Logs: ✅ Test steps executed
- ERROR Logs: ❌ Assertion failures
- DEBUG Logs: 🛠 Troubleshooting info

✅ TestNG Reports (target/surefire-reports/)

- testng-results.xml → Detailed results
- index.html → HTML report

✅ Cucumber Reports (target/cucumber-reports/)

```sh
mvn test -Dcucumber.options="--plugin json:target/cucumber-reports/cucumber.json"
```
- Open target/cucumber-reports/index.html in a browser

## 📊 Test Reports & Debugging
- **Test reports & logs** are available in the `target/` directory.
- **Failed test screenshots** are saved in `target/`.
- **Configuration settings** can be updated in `config/config.properties`.
- **API logs** are available under the `logs/` directory.

---

## 🔧 Enhancements & Future Improvements

Below are ways to enhance and improve the test automation framework:

- ✅ Test Data Management
 Use Faker or an external data provider (CSV, JSON, DB) for dynamic test data.
Implement data-driven testing to run tests with multiple inputs.

- ✅ Parallel Execution & Performance
Optimize Playwright selectors for better stability and speed.
Use headless mode for UI tests to improve performance.
Configure grid execution to scale tests across multiple machines.

- ✅ Enhanced Reporting
Integrate TestNG HTML Reports for detailed execution insights.
Optionally, implement custom logging dashboards.

- ✅ API Testing Enhancements
Expand API test coverage with negative scenarios.
Implement contract testing to validate API request/response schemas.
Add OAuth, JWT authentication support for secured APIs.

- ✅ Retry Mechanism for Flaky Tests
Implement TestNG RetryAnalyzer to retry failed tests.
Configure Playwright timeouts and better wait strategies.

- ✅ CI/CD Integration
Integrate with GitHub Actions, Jenkins, or GitLab CI.
Automate scheduled test runs on every code commit.

- ✅ Containerization & Cloud Execution
Run tests in Dockerized environments.
Execute tests in cloud platforms like BrowserStack, Sauce Labs, or LambdaTest.


## ✨ Contributing

1. Fork the repository.
2. Create a new branch.
3. Commit changes.
4. Submit a pull request.

---

### 🚀 Happy Testing! 🎯
