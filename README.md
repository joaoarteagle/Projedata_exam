
# Full Stack Application

This project is a full stack application composed of:

- **Backend:** Java Spring Boot
- **Frontend:** Vue.js
- **Containerization:** Docker & Docker Compose

---

# Prerequisites

Before running the project, make sure you have installed:

- Docker Desktop
- Java 17+
- Maven
- Node.js (LTS)
- npm

---

# Running the Application

Follow the steps below to start the application.

## 1. Open the project in your IDE

You can use any IDE such as:

- IntelliJ IDEA
- VS Code
- Eclipse

---

## 2. Start Docker Desktop

Make sure Docker Desktop is running before executing the containers.

---

## 3. Build the Backend

Open a terminal in the **backend** directory and run:

```apache
mvn package
```

## 4. Install Frontend Dependencies

Open a terminal in the **frontend** directory and run:

```apache
npm install
```

This will install all required frontend dependencies.

---

## 5. Start the Application with Docker

Navigate to the **project root directory** and run:

```apache
docker-compose up --build
```

Docker will build and start both the backend and frontend containers.

---

## 6. Access the Application

Once the containers are running, open your browser and navigate to:

```bash
http://localhost:5173
```
