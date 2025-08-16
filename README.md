# 📚 Spring Boot + PostgreSQL + Docker – Local Setup

A minimal Spring Boot CRUD API for books, backed by PostgreSQL, managed via pgAdmin, all containerized with Docker Compose.
This README has everything you need in one place.

## 🛠 Tech Stack

- Spring Boot 3.5.4
- Java 17
- PostgreSQL 15
- pgAdmin 4
- Docker & Docker Compose
- Swagger/OpenAPI for API documentation

## ✨ What you get

- REST endpoints for basic CRUD on Book
- PostgreSQL database
- Optional seed data on first run
- pgAdmin web UI
- Environment variables kept in .env (safe to push the repo without secrets)

## 🧰 Prerequisites

- Docker & Docker Compose
- Git
- (Optional) cURL/Postman for testing

## � System Requirements

- Memory: At least 4GB free RAM
- Disk: At least 2GB free space
- Ports: Make sure these ports are available
  - 8080: Spring Boot application
  - 5432: PostgreSQL
  - 5050: pgAdmin

## �🚀 Quick Start

1) Clone the repo
```bash
git clone https://github.com/your-username/your-repo.git
cd your-repo
```

2) Create .env

Create a file named .env in the project root:
```bash
# --- Postgres ---
POSTGRES_DB=bookdb
POSTGRES_USER=bookuser
POSTGRES_PASSWORD=supersecret

# --- pgAdmin (web UI) ---
PGADMIN_DEFAULT_EMAIL=admin@admin.com
PGADMIN_DEFAULT_PASSWORD=admin123
```

⚠️ Do not commit .env. Ensure .gitignore contains:
```
.env
```

3) Start everything
```bash
docker compose up --build
```

App → http://localhost:8080
pgAdmin → http://localhost:5050

## 🗄️ Database & Seeding

Seed scripts (first run only)

Place SQL files in ./db-init/. They run only when the db_data volume is empty.

Example: db-init/init.sql
```sql
CREATE TABLE IF NOT EXISTS book (
  id SERIAL PRIMARY KEY,
  title TEXT NOT NULL,
  author TEXT NOT NULL
);

INSERT INTO book (title, author) VALUES
  ('Spring Boot in Action', 'Craig Walls'),
  ('Docker Made Easy', 'Alice Johnson'),
  ('Postgres Basics', 'Bob Smith')
ON CONFLICT DO NOTHING;
```

🔁 If you change seed scripts and want them to run again, you must reset the DB volume:
```bash
docker compose down -v
docker compose up --build
```

## 🐘 pgAdmin (DB UI)

1. Open http://localhost:5050
2. Log in with PGADMIN_DEFAULT_EMAIL and PGADMIN_DEFAULT_PASSWORD from .env
3. Register a new server:
   - Name: Local Postgres
   - Host: db
   - Port: 5432
   - Database: bookdb
   - Username: bookuser
   - Password: supersecret

💡 If you connect from a local client outside Docker (DBeaver, psql on your host), use:
- Host: localhost
- Port: 5432
- DB/User/Pass: same as above

## 🌐 API Endpoints

Available at http://localhost:8080/api/books

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET    | /api/books | List all books |
| GET    | /api/books/{id} | Get a book by ID |
| POST   | /api/books | Create a new book |
| PUT    | /api/books/{id} | Update a book |
| DELETE | /api/books/{id} | Delete a book |

## 📖 API Documentation

- Swagger UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI Spec: http://localhost:8080/v3/api-docs

## 🔧 Troubleshooting

1. **Container Startup Issues**
   ```bash
   # Stop all containers and remove volumes
   docker compose down -v
   # Remove all containers and images
   docker system prune -a
   # Start fresh
   docker compose up --build
   ```

2. **Port Conflicts**
   - If any service fails to start, check if the ports (8080, 5432, 5050) are already in use
   - To kill a process using a port (on Unix/Mac):
     ```bash
     sudo lsof -i :8080  # Replace with the conflicting port
     kill -9 <PID>
     ```

3. **Database Connection Issues**
   - Ensure the .env file has correct database credentials
   - Wait a few seconds after containers start for the database to initialize
   - Check logs: `docker compose logs db`

## 📝 Development Notes

- The application uses Spring Boot's dev tools for hot reloading
- Database changes require container rebuild: `docker compose up --build`
- API changes are reflected immediately due to dev tools
- Database data persists in a Docker volume named `db_data`

## 🏗️ Project Structure
```
demo/
├── src/                    # Source code
├── db-init/               # Database initialization scripts
├── docker-compose.yml     # Docker compose configuration
├── Dockerfile             # Spring Boot app container config
└── README.md             # This file
```

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /books | Get all books |
| GET | /books/{id} | Get one book |
| POST | /books | Create a book |
| PUT | /books/{id} | Update a book |
| DELETE | /books/{id} | Delete a book |

### Examples (cURL)

Create:
```bash
curl -X POST http://localhost:8080/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Clean Architecture","author":"Robert C. Martin"}'
```

List:
```bash
curl http://localhost:8080/books
```

Get by ID:
```bash
curl http://localhost:8080/books/1
```

Update:
```bash
curl -X PUT http://localhost:8080/books/1 \
  -H "Content-Type: application/json" \
  -d '{"title":"Clean Architecture (Updated)","author":"Robert C. Martin"}'
```

Delete:
```bash
curl -X DELETE http://localhost:8080/books/1
```

## 🧩 Project Structure

```
your-repo/
│
├─ src/             # Spring Boot code (entities, repos, controllers)
├─ db-init/         # SQL files run once on first DB creation
│    └─ init.sql    # (example) create table + seed data
├─ docker-compose.yml  # Compose file (app, db, pgAdmin)
├─ Dockerfile       # App image build
├─ .env             # Environment variables (NOT committed)
├─ .gitignore       # Includes .env
└─ README.md        # This file
```

## 🛑 Stop & Clean

Stop (keep DB data):
```bash
docker compose down
```

Stop and erase DB data (fresh start):
```bash
docker compose down -v
```

Rebuild after code changes:
```bash
docker compose up --build
```

Show logs:
```bash
docker compose logs -f
```

Open psql shell inside DB container:
```bash
docker compose exec db psql -U "$POSTGRES_USER" -d "$POSTGRES_DB"
```

## 🧯 Troubleshooting

### ❌ db-1 exited with code 3 or relation "book" does not exist

Cause: Seed script tried to INSERT before CREATE TABLE, or you changed db-init after first run.
Fix:
```bash
docker compose down -v
docker compose up --build
```

Ensure your init.sql creates the table before inserting (see example above).

### ❌ App logs: UnknownHostException: db or The connection attempt failed

Cause: DB container crashed/exited; service name db won't resolve.
Fix:
Check DB logs:
```bash
docker compose logs db
```

If DB failed on init, reset volume:
```bash
docker compose down -v
docker compose up --build
```

### ❌ pgAdmin cannot connect using db

Remember: db works inside Docker (pgAdmin container).
If you use a local client on your host, use localhost instead.

## 🔒 Security Notes

- Secrets live in .env and are not committed.
- For real deployments, prefer a secret manager (Docker/Swarm/K8s/Cloud) over plain env files.
