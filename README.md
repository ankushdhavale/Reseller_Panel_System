# 🚀 Reseller Wallet & Recharge Management System

---

## 📌 Overview

A secure and scalable backend system designed to manage multi-level resellers with wallet operations, transaction tracking, and mobile recharge functionality. The system ensures secure authentication, financial consistency, and role-based access control.

---

## ✨ Key Features

* 🔐 JWT Authentication & Authorization
* 👥 Role-Based Access Control (ADMIN / RESELLER)
* 💰 Wallet Credit & Debit System
* 💳 Transaction History Tracking
* 📱 Mobile Recharge Module
* ⚙️ Admin Control APIs
* 📊 Real-Time Balance Updates

---

## 🏗️ Architecture

* Spring Boot (MVC Pattern)
* Layered Architecture:

  * Controller Layer
  * Service Layer
  * Repository Layer

---

## 🧰 Tech Stack

* **Backend:** Java, Spring Boot
* **Database:** MySQL
* **Security:** JWT
* **Build Tool:** Maven

---

# 📡 API DOCUMENTATION

---

## 🔐 AUTH APIs

### 1️⃣ Create User/Admin

**POST** `/api/auth/users`

```json
{
  "username": "Admin",
  "password": "admin123",
  "role": "ADMIN"
}
```

---

### 2️⃣ Login

**POST** `/api/auth/login`

```json
{
  "username": "Admin",
  "password": "admin123"
}
```

✅ Returns JWT Token

---

## ⚙️ ADMIN APIs

### 3️⃣ Create Reseller

**POST** `/api/admin/users`
🔒 Bearer Token Required

---

### 4️⃣ Get User

**GET** `/api/admin/users/{id}`
🔒 Bearer Token Required

---

### 5️⃣ Credit Amount

**POST** `/api/admin/credit?walletId={id}`

---

### 6️⃣ Debit Amount

**POST** `/api/admin/debit?walletId={id}`

---

## 💰 WALLET APIs

### 7️⃣ Get User Details (Balance + Transactions)

**GET** `/api/reseller/{id}/details`

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

## 💳 TRANSACTION APIs

### 8️⃣ Get All Transactions

**GET** `/api/reseller/transactions/{userId}`

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

## 📱 RECHARGE API

### 9️⃣ Recharge Mobile

**POST** `/api/admin/recharge?walletId={id}`

```json
{
  "mobileNumber": "8080028914",
  "operator": "Jio",
  "amount": 199
}
```

---

## 🧠 Business Logic

* Wallet updates after every transaction
* Recharge deducts amount from wallet
* Transactions stored for audit
* Admin controls financial operations
* Secure role-based access

---

## 🔐 Security

* JWT-based authentication
* Role-based authorization

---

## 🚀 Future Improvements

* Payment Gateway Integration
* Microservices Architecture
* Kafka for async processing
* Redis caching
* Admin Dashboard UI

---

## 👨‍💻 Author

Ankush Dhavale
