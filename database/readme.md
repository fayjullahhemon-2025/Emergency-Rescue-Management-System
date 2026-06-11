# Database Setup & Configuration

This project uses **MongoDB** as its database.

## Database Details
- **Database Name**: `rescue_management_db`
- **Default Connection URI**: `mongodb://localhost:27017/rescue_management_db`

## How to Run MongoDB

### Option 1: Using Docker (Recommended)
You can run MongoDB inside a Docker container using the provided `docker-compose.yml` file.

1. Navigate to this `database/` directory.
2. Run the following command:
   ```bash
   docker-compose up -d
   ```

### Option 2: Local MongoDB Installation
If you have MongoDB Community Server installed locally on your system:
1. Make sure the MongoDB service is running (default port is `27017`).
2. The Spring Boot backend will automatically connect to `mongodb://localhost:27017` and initialize the database.

## Database Seeding
The backend contains a data initializer class (`com.rescue.management.config.DataInitializer`) that will automatically run on startup and seed the database with initial admin, user, and rescue team accounts, as well as demo emergency reports if the database is empty.
