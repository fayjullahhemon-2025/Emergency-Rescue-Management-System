# 🛠️ Setup and Run Guide

This guide will walk you through the step-by-step process of setting up and running the **Emergency Rescue Management System** on your local machine.

---

## 📋 Prerequisites

Before starting, ensure you have the following software installed:

1. **Docker / Docker Desktop**
   - **Why:** To run the MongoDB database instance easily without manual configuration.
   - **Download:** [Get Docker Desktop](https://www.docker.com/products/docker-desktop/)

2. **Java Development Kit (JDK 17 or higher)**
   - **Why:** Required to run the Spring Boot backend.
   - **Download:** [Get JDK 17 (Eclipse Temurin)](https://adoptium.net/)

3. **Apache Maven** (Optional if you run using a global installation, or if using wrapper)
   - **Download:** [Get Apache Maven](https://maven.apache.org/download.cgi)

---

## 🚀 Step-by-Step Execution

Follow these steps in order to start all components:

### 1️⃣ Step 1: Start the Database (MongoDB)

Launch Docker Desktop first to ensure the Docker daemon is running. Then open your terminal/command prompt and run:

```bash
# Navigate to the database configuration directory
cd database

# Start MongoDB container in detached mode
docker compose up -d
```

> [!NOTE]  
> This spins up a MongoDB instance listening on `localhost:27017` with the container name `rescue_mongodb`.

---

### 2️⃣ Step 2: Run the Backend Server (Spring Boot)

Once the database is up, open a new terminal window/tab and start the backend:

```bash
# Navigate to the backend directory
cd backend

# Compile and start the Spring Boot application
mvn spring-boot:run
```

> [!TIP]  
> The backend server runs on `http://localhost:8081`. 
> Upon starting, it will automatically connect to MongoDB and seed it with demo users, rescue teams, and active emergencies so you have data to test with.

---

### 3️⃣ Step 3: Open the Frontend Client

The frontend is a lightweight, high-fidelity client. You do not need to compile it or install npm dependencies:

1. Locate the file: `frontend/index.html`
2. Double-click the file to open it directly in your web browser, or use a local development server like **Live Server** (VS Code extension) or run:
   ```bash
   # From the project root, start a simple HTTP server (Python 3)
   python -m http.server 8000
   ```
3. Open `http://localhost:8000/frontend/` in your browser.

---

## 🔑 Demo Accounts

Use these pre-seeded accounts to log in and test different system roles:

| Role | Email / Username | Password | Access / Functionality |
| :--- | :--- | :--- | :--- |
| **Admin** | `admin@rescue.gov` | `admin123` | Full dispatch dashboard, team management, incident control |
| **Citizen** | `john@email.com` | `user123` | Report emergencies, upload attachments, view safety guides |
| **Rescue Team** | `alpha@rescue.gov` | `team123` | Access assigned rescue missions, update task status |

---

## 🔍 Troubleshooting

- **MongoDB connection issues:** Ensure Docker Desktop is active and container `rescue_mongodb` is running (`docker ps` to verify).
- **Port Conflict:** If port `8081` is already in use, you can change it in [backend/src/main/resources/application.properties](file:///C:/Users/User/Desktop/rescue%20management%20system/backend/src/main/resources/application.properties).
