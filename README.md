# 🚀 Reseller Wallet & Recharge Management System

A secure Spring Boot backend system for managing resellers, wallet operations, transactions, and mobile recharge services with JWT-based authentication and role-based authorization.

## 📌 Overview

The **Reseller Wallet & Recharge Management System** provides backend APIs for managing reseller accounts, wallet balances, financial transactions, and mobile recharges.

The application follows a **layered architecture** to keep business logic, API handling, and database operations separated and maintainable.

### Key Highlights

* JWT-based authentication and authorization
* Role-based access control for ADMIN and RESELLER
* Wallet credit and debit operations
* Transaction history and audit tracking
* Mobile recharge functionality
* Real-time wallet balance updates
* RESTful APIs
* Swagger/OpenAPI API documentation
* Unit testing with JUnit 5
* Dockerized application setup

---

# ✨ Features

### 🔐 Authentication & Authorization

* User registration
* User login
* JWT token generation
* Protected APIs using Bearer authentication
* Role-based access control

### 👥 User Management

* Create reseller accounts
* Retrieve user details
* ADMIN and RESELLER roles

### 💰 Wallet Management

* Credit money to reseller wallet
* Debit money from reseller wallet
* Check current wallet balance
* Maintain transaction records
* Prevent inconsistent wallet operations through service-layer validation

### 💳 Transaction Management

* Record wallet credits
* Record wallet debits
* Retrieve transaction history
* Maintain transaction information for auditing

### 📱 Mobile Recharge

* Process mobile recharge requests
* Validate wallet balance
* Deduct recharge amount from wallet
* Store the corresponding transaction

---

# 🏗️ Architecture

The application follows a **Layered Architecture**:

```text
Client
   │
   ▼
Controller Layer
   │
   ▼
Service Layer
   │
   ▼
Repository Layer
   │
   ▼
MySQL Database
```

### Layers

**Controller Layer**

* Handles HTTP requests and responses
* Exposes REST APIs

**Service Layer**

* Contains business logic
* Handles wallet operations
* Processes recharge operations
* Validates business rules

**Repository Layer**

* Handles database operations
* Uses Spring Data JPA

---

# 🧰 Tech Stack

| Category          | Technology                  |
| ----------------- | --------------------------- |
| Language          | Java                        |
| Framework         | Spring Boot                 |
| API               | REST APIs                   |
| Database          | MySQL                       |
| ORM               | Spring Data JPA / Hibernate |
| Security          | JWT                         |
| Build Tool        | Maven                       |
| API Documentation | Swagger / OpenAPI           |
| Testing           | JUnit 5                     |
| Containerization  | Docker                      |
| API Testing       | Postman                     |

---

# 📂 Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── ...
│   │       ├── controller/
│   │       ├── service/
│   │       ├── repository/
│   │       ├── entity/
│   │       ├── dto/
│   │       ├── security/
│   │       └── exception/
│   │
│   └── resources/
│       └── application.properties
│
└── test/
    └── java/
        └── ...
```

---

# 📡 API Documentation

## 🔐 Authentication APIs

### 1. Create User/Admin

```http
POST /api/auth/users
```

Example request:

```json
{
  "username": "Admin",
  "password": "admin123",
  "role": "ADMIN"
}
```

### 2. Login

```http
POST /api/auth/login
```

Example request:

```json
{
  "username": "Admin",
  "password": "admin123"
}
```

Returns a JWT token that can be used to access protected APIs.

---

# ⚙️ Admin APIs

### 3. Create Reseller

```http
POST /api/admin/users
```

🔒 Bearer Token Required

### 4. Get User

```http
GET /api/admin/users/{id}
```

🔒 Bearer Token Required

### 5. Credit Wallet

```http
POST /api/admin/credit?walletId={id}
```

### 6. Debit Wallet

```http
POST /api/admin/debit?walletId={id}
```

---

# 💰 Wallet APIs

### 7. Get User Details

```http
GET /api/reseller/{id}/details
```

Example response:

```json
{
  "data": {
    "balance": 1601.0,
    "transactions": [
      {
        "id": 1,
        "amount": 2000,
        "type": "CREDIT"
      }
    ]
  }
}
```

---

# 💳 Transaction APIs

### 8. Get Transactions

```http
GET /api/reseller/transactions/{userId}
```

Example response:

```json
{
  "data": [
    {
      "amount": 2000,
      "type": "CREDIT"
    },
    {
      "amount": 200,
      "type": "DEBIT"
    }
  ]
}
```

---

# 📱 Recharge API

### 9. Mobile Recharge

```http
POST /api/admin/recharge?walletId={id}
```

Example request:

```json
{
  "mobileNumber": "8080028914",
  "operator": "Jio",
  "amount": 199
}
```

The recharge operation deducts the recharge amount from the wallet and records the transaction.

---

# 🧠 Business Logic

The system implements the following core business rules:

* Wallet balance is updated after every financial transaction.
* Credit operations increase wallet balance.
* Debit operations decrease wallet balance.
* Recharge operations deduct the recharge amount from the wallet.
* Transactions are stored for audit and tracking.
* Protected operations require authentication.
* Administrative operations are restricted using role-based authorization.
* Business logic is handled in the service layer.

---

# 🧪 Testing

The project uses **JUnit 5** for unit testing.

Example areas covered by tests:

* Wallet credit operations
* Wallet debit operations
* Recharge validation
* Balance calculation
* Transaction creation
* Service-layer business logic

Run tests using Maven:

```bash
mvn test
```

---

# 🐳 Docker

The application can be containerized using Docker to provide a consistent runtime environment.

Build the application:

```bash
mvn clean package
```

Build the Docker image:

```bash
docker build -t reseller-wallet-system .
```

Run the container:

```bash
docker run -p 8080:8080 reseller-wallet-system
```

> If you later add Docker Compose for Spring Boot + MySQL, add the `docker-compose.yml` setup here as well.

---

# 📖 Swagger API Documentation

Once the application is running, Swagger UI can be used to explore and test the REST APIs.

Typical Swagger URL:

```text
http://localhost:8080/swagger-ui/index.html
```

---

# 🔐 Security

The application uses:

* JWT authentication
* Bearer token authorization
* Role-based access control
* Protected REST endpoints

Example authorization header:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

# 🚀 Running the Project Locally

### 1. Clone the repository

```bash
git clone <YOUR_GITHUB_REPOSITORY_URL>
```

### 2. Configure MySQL

Create a MySQL database and update the database configuration in:

```text
src/main/resources/application.properties
```

### 3. Build the project

```bash
mvn clean install
```

### 4. Run the application

```bash
mvn spring-boot:run
```

The application will start on:

```text
http://localhost:8080
```

---

# 🔮 Future Improvements

* Payment gateway integration
* Redis caching
* Kafka-based asynchronous transaction processing
* Microservices architecture
* Admin dashboard
* Centralized logging and monitoring
* Docker Compose deployment
* Integration testing
* CI/CD pipeline

---

# 👨‍💻 Author

**Ankush Dhavale**

Java Backend Developer | Spring Boot | REST APIs | MySQL
