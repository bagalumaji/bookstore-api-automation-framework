# 🚀 BOOKSTORE Api Automation Framework

A comprehensive, enterprise-grade REST API testing framework built with **REST Assured**, **TestNG**, and **Maven**, following industry best practices including **SOLID principles**, **DRY methodology**, and proven **design patterns**.

[![Java](https://img.shields.io/badge/Java-11+-orange.svg)](https://openjdk.java.net/)
[![Maven](https://img.shields.io/badge/Maven-3.6+-blue.svg)](https://maven.apache.org/)
[![REST Assured](https://img.shields.io/badge/REST%20Assured-5.3.2-green.svg)](https://rest-assured.io/)
[![TestNG](https://img.shields.io/badge/TestNG-7.11.0-red.svg)](https://testng.org/)
[![Build Status](https://img.shields.io/badge/Build-Passing-brightgreen.svg)](#)

## 📋 Table of Contents

- [🏗️ Framework Architecture](#️-framework-architecture)
- [✨ Key Features](#-key-features)
- [🛠️ Prerequisites](#️-prerequisites)
- [⚙️ Setup Instructions](#️-setup-instructions)
- [📊 Configuration](#-configuration)
- [🧪 Test Execution](#-test-execution)
- [📈 Reporting](#-reporting)
- [🔄 CI/CD Integration](#-cicd-integration)
- [📝 API Endpoints Covered](#-api-endpoints-covered)
- [🗂️ Project Structure](#️-project-structure)
- [🎯 Test Scenarios](#-test-scenarios)
- [📚 Framework Components](#-framework-components)
- [🚦 Best Practices](#-best-practices)
- [🤝 Contributing](#-contributing)
- [📞 Support](#-support)

## 🏗️ Framework Architecture

### Design Principles Applied:
- **🎯 SOLID Principles**: Single Responsibility, Open/Closed, Liskov Substitution, Interface Segregation, Dependency Inversion
- **🔄 DRY (Don't Repeat Yourself)**: Reusable components and utilities
- **🎨 Design Patterns**: Singleton, Builder, Factory, Template Method

### Layer Structure:
```
📁 src/main/java/com/api/framework/
├── 📁 client/          # API Client layer (HTTP operations)
├── 📁 services/        # Service layer (Business logic)
├── 📁 pojo/           # POJO classes (Data models)
├── 📁 utils/          # Utility classes
├── 📁 data/           # Test data providers
├── 📁 reporting/      # Reporting utilities
└── 📁 listeners/      # TestNG listeners

📁 src/test/java/com/api/framework/
└── 📁 tests/          # Test classes

📁 src/test/resources/
├── config.properties  # Configuration file
├── testng.xml         # TestNG suite configuration
└── log4j2.xml         # Logging configuration
```

## ✨ Key Features

| Feature | Description |
|---------|-------------|
| 🌐 **REST Assured Integration** | Powerful HTTP client for API testing |
| 🧪 **TestNG Framework** | Advanced test execution with parallel support |
| 📊 **ExtentReports** | Rich HTML reports with detailed logging |
| 🔧 **Maven Build System** | Dependency management and build automation |
| 🚀 **GitHub Actions CI/CD** | Automated testing pipeline |
| 📋 **Property-based Configuration** | External configuration management |
| 🏗️ **POJO Data Models** | Clean object-oriented request/response handling |
| 📏 **Request/Response Specifications** | Reusable REST Assured specifications |
| 📊 **Data-driven Testing** | TestNG DataProviders for comprehensive coverage |
| ⚡ **Parallel Execution** | Multi-threaded test execution |
| 🔐 **Bearer Token Authentication** | Secure API authentication handling |
| ✅ **Comprehensive Validation** | Custom validation utilities |
| 📈 **Performance Testing** | Response time validation |
| 🎯 **Multiple Test Categories** | Smoke, Regression, Performance tests |

## 🛠️ Prerequisites

Before setting up the framework, ensure you have:

- ☕ **Java 11** or higher ([Download](https://openjdk.java.net/))
- 🔧 **Maven 3.6+** ([Download](https://maven.apache.org/download.cgi))
- 💻 **IDE** (IntelliJ IDEA, Eclipse, VS Code)
- 🌐 **API Server** running on `http://127.0.0.1:8000` (or update config)
- 📝 **Git** for version control

## ⚙️ Setup Instructions

### 1️⃣ Clone the Repository
```bash
git clone <repository-url>
cd rest-api-framework
```

### 2️⃣ Install Dependencies
```bash
mvn clean install
```

### 3️⃣ Configure the Framework
Update `src/test/resources/config.properties` with your API details:

```properties
# API Configuration
base.url=http://127.0.0.1:8000
request.timeout=30000

# Test Credentials
test.username=testuser@example.com
test.password=Test123!
```

### 4️⃣ Verify Setup
```bash
mvn clean test -Dgroups=smoke
```

## 📊 Configuration

### Main Configuration File: `config.properties`

```properties
# 🌐 API Configuration
base.url=http://127.0.0.1:8000
request.timeout=30000

# 🔗 Endpoints
endpoint.health=/health
endpoint.signup=/signup
endpoint.login=/login
endpoint.books=/books/

# 👤 Test Data
test.username=umaji.bagal@bookstore.com
test.password=bookstore@123
test.invalid.username=invalid@example.com
test.invalid.password=wrongpassword

# 📊 Reporting
extent.report.name=API Test Report
extent.report.path=target/extent-reports/

# ⚡ Parallel Execution
thread.count=3
data.provider.thread.count=3

# 📝 Logging
log.level=INFO
```

### TestNG Configuration: `testng.xml`

The framework includes pre-configured test suites:
- **Smoke Tests**: Critical path validation
- **Regression Tests**: Comprehensive feature testing
- **Full Test Suite**: Complete test coverage

## 🧪 Test Execution

### 🚀 Quick Start Commands

```bash
# Run all tests
mvn clean test

# Run smoke tests only
mvn clean test -Psmoke

# Run regression tests
mvn clean test -Pregression

# Run specific test groups
mvn clean test -Dgroups=smoke,health
mvn clean test -Dgroups=auth
mvn clean test -Dgroups=books

# Parallel execution with custom thread count
mvn clean test -DthreadCount=5 -DdataProviderThreadCount=3

# Run with specific environment
mvn clean test -Dbase.url=https://api.staging.example.com
```

### 📋 Available Test Groups

| Group | Description | Test Count |
|-------|-------------|------------|
| `smoke` | Critical functionality tests | 8 |
| `regression` | Comprehensive feature tests | 15 |
| `health` | Health endpoint tests | 3 |
| `auth` | Authentication tests | 7 |
| `books` | Book management tests | 18 |
| `performance` | Response time validation | 3 |

## 📈 Reporting

### 🎨 ExtentReports (Primary)
- **Location**: `target/extent-reports/APITestReport_*.html`
- **Features**:
    - Interactive dashboard
    - Test execution timeline
    - Detailed step-by-step logs
    - Test categorization
    - System information
    - Pass/Fail statistics

### 📊 TestNG Reports (Secondary)
- **Location**: `target/surefire-reports/`
- **Features**:
    - XML format for CI/CD integration
    - Detailed test results
    - Exception stack traces

### 📝 Logging
- **Console Logs**: Real-time test execution logs
- **File Logs**: `target/logs/api-test.log`
- **Rolling Logs**: Automatic log rotation

## 🔄 CI/CD Integration

### GitHub Actions Workflow Features:

```yaml
🏃‍♂️ Parallel Job Execution:
├── 🟢 Smoke Tests (Always run first)
├── 🔵 Regression Tests (After smoke pass)
├── 🟡 Full Test Suite (Scheduled/Main branch)
└── 📊 Test Result Publishing

🎯 Trigger Conditions:
├── Push to main/develop branches
├── Pull requests
└── Daily scheduled runs (2 AM UTC)

📦 Artifacts Generated:
├── HTML Test Reports
├── XML Test Results
└── Execution Logs
```

### Pipeline Stages:

1. **🔍 Code Checkout**: Get latest code
2. **☕ Java Setup**: Configure JDK 11
3. **📦 Cache Dependencies**: Speed up builds
4. **🧪 Test Execution**: Run test suites
5. **📊 Report Generation**: Create test reports
6. **📤 Artifact Upload**: Store results
7. **📋 Result Publishing**: GitHub integration

## 📝 API Endpoints Covered

### 🏥 Health Endpoint
```http
GET /health
```
**Tests**: Health check, response time, content type validation

### 🔐 Authentication Endpoints

```http
POST /signup
Content-Type: application/json

{
  "username": "user@example.com",
  "password": "Password123!",
  "email": "user@example.com",
  "firstName": "John",
  "lastName": "Doe"
}
```

```http
POST /login
Content-Type: application/json

{
  "username": "user@example.com",
  "password": "Password123!"
}
```

### 📚 Book Management Endpoints

```http
# Get all books
GET /books
Authorization: Bearer {token}

# Create book
POST /books
Authorization: Bearer {token}
Content-Type: application/json

{
  "id": 0,
  "name": "The Great Gatsby",
  "author": "F. Scott Fitzgerald",
  "published_year": 1925,
  "book_summary": "A classic American novel"
}

# Update book
PUT /books/{id}
Authorization: Bearer {token}
Content-Type: application/json

# Delete book
DELETE /books/{id}
Authorization: Bearer {token}

# Get book by ID
GET /books/{id}
Authorization: Bearer {token}
```

## 🗂️ Project Structure

```
rest-api-framework/
│
├── 📄 pom.xml                          # Maven configuration
├── 📋 README.md                        # This file
├── 🔧 .github/workflows/
│   └── github-actions.yml              # CI/CD pipeline
│
├── 📁 src/main/java/com/api/framework/
│   ├── 🌐 client/
│   │   └── ApiClient.java              # HTTP operations
│   ├── 🎯 services/
│   │   ├── BaseService.java            # Base service class
│   │   ├── AuthService.java            # Authentication operations
│   │   ├── BookService.java            # Book CRUD operations
│   │   └── HealthService.java          # Health check operations
│   ├── 🗃️ pojo/
│   │   ├── User.java                   # User data model
│   │   ├── LoginRequest.java           # Login request model
│   │   ├── LoginResponse.java          # Login response model
│   │   ├── Book.java                   # Book data model
│   │   ├── ApiResponse.java            # Generic API response
│   │   └── ErrorResponse.java          # Error response model
│   ├── 🛠️ utils/
│   │   ├── ConfigReader.java           # Configuration management
│   │   ├── SpecBuilder.java            # REST Assured specifications
│   │   └── ValidationUtility.java      # Validation methods
│   ├── 📊 data/
│   │   └── TestDataProvider.java       # Test data factory
│   ├── 📈 reporting/
│   │   └── ExtentReportManager.java    # Report management
│   └── 👂 listeners/
│       └── TestListener.java           # TestNG listeners
│
├── 📁 src/test/java/com/api/framework/
│   └── 🧪 tests/
│       ├── BaseTest.java               # Base test class
│       ├── HealthTests.java            # Health endpoint tests
│       ├── AuthTests.java              # Authentication tests
│       └── BookTests.java              # Book management tests
│
└── 📁 src/test/resources/
    ├── ⚙️ config.properties            # Configuration file
    ├── 📋 testng.xml                   # TestNG suite configuration
    └── 📝 log4j2.xml                   # Logging configuration
```

## 🎯 Test Scenarios

### ✅ Positive Test Scenarios

| Category | Test Scenario | Validation |
|----------|---------------|------------|
| 🏥 **Health** | Health endpoint check | Status 200, JSON response |
| 🔐 **Auth** | Valid login credentials | Token generation, success flag |
| 🔐 **Auth** | User registration | Account creation, status 201 |
| 📚 **Books** | Create valid book | Book creation, data validation |
| 📚 **Books** | Get all books | List retrieval, authentication |
| 📚 **Books** | Update existing book | Data modification, ID preservation |
| 📚 **Books** | Delete book | Removal confirmation, 404 on re-access |
| 📚 **Books** | CRUD complete flow | Full lifecycle validation |

### ❌ Negative Test Scenarios

| Category | Test Scenario | Expected Result |
|----------|---------------|-----------------|
| 🔐 **Auth** | Invalid credentials | Status 401, error message |
| 🔐 **Auth** | Empty login payload | Status 400, validation error |
| 🔐 **Auth** | Malformed email | Status 400, format error |
| 📚 **Books** | Create without auth | Status 401, unauthorized |
| 📚 **Books** | Invalid book data | Status 400, validation error |
| 📚 **Books** | Update non-existent book | Status 404, not found |
| 📚 **Books** | Delete with invalid ID | Status 404, not found |
| 📚 **Books** | Access with invalid token | Status 401, invalid token |

### 📊 Data-Driven Test Examples

```java
// Book creation with multiple datasets
@DataProvider(name = "validBooks")
public static Object[][] validBooksData() {
    return new Object[][] {
        {createValidBook("The Great Gatsby", "F. Scott Fitzgerald", 1925, "Classic novel")},
        {createValidBook("1984", "George Orwell", 1949, "Dystopian fiction")},
        {createValidBook("To Kill a Mockingbird", "Harper Lee", 1960, "Social justice")},
        // ... more test data
    };
}
```

## 📚 Framework Components

### 🌐 API Client Layer (`ApiClient.java`)
**Responsibility**: Low-level HTTP operations
**Features**:
- GET, POST, PUT, DELETE operations
- Authentication header management
- Request/Response logging
- Error handling

### 🎯 Service Layer
**Responsibility**: Business logic abstraction

#### 🔐 AuthService
- User registration (`signup`)
- User authentication (`login`)
- Token management
- Credential validation

#### 📚 BookService
- Complete CRUD operations
- List retrieval with filtering
- Authentication integration
- Error handling

#### 🏥 HealthService
- Health status monitoring
- Uptime validation
- Performance metrics

### 🗃️ POJO Classes
**Responsibility**: Data model representation
**Features**:
- JSON serialization/deserialization
- Lombok integration for reduced boilerplate
- Validation annotations
- Builder pattern implementation

### 🛠️ Utility Classes

#### ⚙️ ConfigReader (Singleton Pattern)
```java
ConfigReader config = ConfigReader.getInstance();
String baseUrl = config.getBaseUrl();
```

#### 📏 SpecBuilder (Builder Pattern)
```java
RequestSpecification spec = SpecBuilder.getRequestSpecWithAuth(token);
```

#### ✅ ValidationUtility (Static Methods)
```java
ValidationUtility.validateStatusCode(response, 200);
ValidationUtility.validateResponseTime(response, 5000);
```

### 📊 Test Data Management

#### 🏭 TestDataProvider (Factory Pattern)
```java
@DataProvider(name = "validBooks", parallel = true)
public static Object[][] validBooksData() {
    // Returns multiple test datasets
}
```

### 📈 Reporting System

#### 📊 ExtentReportManager (Singleton Pattern)
```java
ExtentReportManager.createTest("Test Name");
ExtentReportManager.logPass("Test passed successfully");
```

## 🚦 Best Practices

### 🏗️ Architecture Principles

1. **🎯 Single Responsibility**: Each class has one clear purpose
2. **🔄 DRY Principle**: No code duplication, maximum reusability
3. **📦 Separation of Concerns**: Clear layer boundaries
4. **🔌 Dependency Injection**: Loose coupling between components
5. **🛡️ Error Handling**: Graceful failure management

### 🧪 Testing Principles

1. **🎯 Test Independence**: Each test can run in isolation
2. **📊 Data-Driven**: Parameterized tests for better coverage
3. **⚡ Parallel Execution**: Optimal resource utilization
4. **📈 Comprehensive Reporting**: Detailed test documentation
5. **🔄 CI/CD Integration**: Automated test execution

### 📝 Code Quality

1. **📋 Clear Naming**: Self-documenting code
2. **💬 Comprehensive Comments**: Detailed documentation
3. **🔍 Code Reviews**: Peer validation process
4. **📊 Test Coverage**: High coverage metrics
5. **🛡️ Exception Handling**: Robust error management

## 🤝 Contributing

We welcome contributions! Here's how to get started:

### 🚀 Getting Started

1. **🍴 Fork the repository**
   ```bash
   git fork <repository-url>
   ```

2. **📥 Clone your fork**
   ```bash
   git clone <your-fork-url>
   cd rest-api-framework
   ```

3. **🌿 Create a feature branch**
   ```bash
   git checkout -b feature/amazing-new-feature
   ```

4. **💻 Make your changes**
    - Follow existing code patterns
    - Add appropriate tests
    - Update documentation

5. **🧪 Test your changes**
   ```bash
   mvn clean test
   ```

6. **📝 Commit and push**
   ```bash
   git add .
   git commit -m "Add amazing new feature"
   git push origin feature/amazing-new-feature
   ```

7. **🔄 Create Pull Request**
    - Use descriptive title and description
    - Reference any related issues
    - Ensure all tests pass

### 📋 Contribution Guidelines

- ✅ Follow existing code style and patterns
- ✅ Write comprehensive tests for new features
- ✅ Update documentation as needed
- ✅ Ensure all tests pass before submitting
- ✅ Use meaningful commit messages

## 📞 Support

### 🆘 Getting Help

- **📋 Issues**: [Open an issue](https://github.com/your-repo/issues) for bugs or feature requests
- **💬 Discussions**: Use GitHub Discussions for questions
- **📧 Email**: Contact the development team
- **📚 Documentation**: Check this README and code comments

### 🐛 Bug Reports

When reporting bugs, please include:
- 🖥️ Operating system and version
- ☕ Java version
- 🔧 Maven version
- 📝 Steps to reproduce
- 🔍 Expected vs actual behavior
- 📊 Log files and screenshots

### 💡 Feature Requests

For new features, please provide:
- 📝 Detailed description
- 🎯 Use case and benefits
- 💭 Proposed implementation approach
- 🔄 Backward compatibility considerations

## 📄 License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **REST Assured Team** - For the excellent API testing library
- **TestNG Contributors** - For the robust testing framework
- **ExtentReports Team** - For beautiful reporting capabilities
- **Open Source Community** - For continuous inspiration and support

---

## 🚀 Quick Start Checklist

- [ ] ☕ Java 11+ installed
- [ ] 🔧 Maven 3.6+ configured
- [ ] 📥 Repository cloned
- [ ] 📦 Dependencies installed (`mvn clean install`)
- [ ] ⚙️ Configuration updated (`config.properties`)
- [ ] 🧪 Smoke tests executed (`mvn clean test -Psmoke`)
- [ ] 📊 Reports generated and reviewed
- [ ] 🔄 CI/CD pipeline configured (if using GitHub)

**🎉 Happy Testing!**

*Built with ❤️ for the API testing community*

---

*Last updated: $(date) | Version: 1.0.0 | Maintainers: API Test Framework Team*