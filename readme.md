# 🚨 Rescue Management System

A fully functional, real-time **Disaster Rescue Management System** built with a **Spring Boot & MongoDB** backend, and a modern, high-fidelity **HTML/CSS/JS** frontend featuring a premium dark-mode glassmorphism design.

---

## 📂 Project Structure

The project has been restructured into clear directories for backend, frontend, and database configurations:

```text
rescue-management-system/
├── backend/                   # Spring Boot Java Backend
│   ├── pom.xml                # Maven Dependencies
│   └── src/                   # Java Source Files & Configurations
│
├── frontend/                  # Standalone Frontend Application
│   ├── css/
│   │   └── style.css          # Premium Dark-Mode Glassmorphism Design System
│   ├── js/
│   │   └── api.js             # Standalone REST API Client
│   ├── index.html             # Landing Page
│   ├── login.html             # Role-Based Login
│   ├── report.html            # Public Emergency Reporting Panel
│   ├── admin.html             # Admin Dashboard & Dispatch Center
│   └── rescue.html            # Mobile-first Rescue Team Portal
│
└── database/                  # Database Configuration
    ├── docker-compose.yml     # Spin up MongoDB with Docker Compose
    └── readme.md              # MongoDB Connection & Setup Instructions
```

---

## 🚀 How to Run the Project

### Step 1: Start the Database (MongoDB)
Ensure MongoDB is running on `localhost:27017`. You can spin up MongoDB instantly using Docker Compose:
```bash
cd database
docker-compose up -d
```
Alternatively, ensure your local MongoDB Community Server service is running.

### Step 2: Start the Backend (Spring Boot)
1. Navigate to the `backend/` directory:
   ```bash
   cd backend
   ```
2. Build and run the backend using Maven:
   ```bash
   mvn spring-boot:run
   ```
   *The backend will automatically connect to MongoDB, create the database, and seed it with demo users, teams, and reports.*

### Step 3: Run the Frontend
Since the frontend is built using pure HTML, CSS, and JS:
1. Simply open `frontend/index.html` directly in your browser.
2. Or serve it using any simple local server (e.g., Live Server in VS Code, or python `-m http.server`).

---

## 🔑 Demo Credentials

| Role | Email | Password |
| :--- | :--- | :--- |
| **Admin** | `admin@rescue.gov` | `admin123` |
| **Citizen** | `john@email.com` | `user123` |
| **Rescue Team** | `alpha@rescue.gov` | `team123` |

---

## 🛠️ API Endpoint Specifications

| Endpoint | Method | Description |
| :--- | :--- | :--- |
| `/api/users/login` | `POST` | Authenticate user & retrieve profile role |
| `/api/emergencies` | `POST` | Report a new emergency (Citizen) |
| `/api/emergencies` | `GET` | Get all emergencies (Admin) |
| `/api/emergencies/{id}` | `GET` | Get details of a specific emergency |
| `/api/emergencies/{id}/status`| `PATCH`| Update emergency status |
| `/api/teams` | `POST` | Create a new rescue team |
| `/api/teams` | `GET` | Get all rescue teams |
| `/api/teams/available` | `GET` | Get all available rescue teams |
| `/api/teams/dispatch` | `POST` | Dispatch a team to an emergency |
| `/api/teams/user/{userId}` | `GET` | Get team assigned to a specific user (team leader) |