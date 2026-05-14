# Mobile Accessories Manager

Professional full-stack application for managing mobile phone accessories — enterprise-grade, secure, and production-ready scaffolding suitable for university capstones or internship demos.

--

## Features
- Accessories, Categories, Brands, Users (Admin / Client)
- JWT authentication + Spring Security
- Redis caching for read-heavy endpoints
- Pagination, sorting, filtering, full-text search
- OpenAPI (Swagger) documentation
- Responsive Next.js dashboard UI (TailwindCSS)
- Dockerized local development (Postgres + Redis)

--

## Tech Stack
- Backend: Spring Boot 3, Java 21, Maven, JPA/Hibernate, MapStruct, Lombok
- Database: PostgreSQL (UUID PKs, indexes, schema + seed SQL)
- Cache: Redis (Spring Cache / RedisCacheManager)
- Security: Spring Security, JWT, BCrypt
- Frontend: Next.js 15, TypeScript, TailwindCSS, Framer Motion
- Testing: JUnit 5, Mockito, Spring Boot Test, (Cypress E2E planned)
- DevOps: Docker, Docker Compose

--

## Repository Layout
- `backend/` — Spring Boot application
- `frontend/` — Next.js app (app directory)
- `deploy/` — `docker-compose.yml`, SQL schema & seeds
- `docs/` — ERD and design notes

--

## Quickstart (local)

Prerequisites: Docker, Docker Compose, Java 21, Maven, Node 20

1) Start services (Postgres + Redis) and build containers:

```bash
cd deploy
docker compose up --build
```

2) Backend (if running locally without Docker):

```bash
cd backend
mvn clean package
mvn spring-boot:run
```

3) Frontend (if running locally):

```bash
cd frontend
npm install
npm run dev
```

Open the frontend at `http://localhost:3000` and the backend at `http://localhost:8080`.

--

## Environment variables
Configure these for production or development overrides (examples are in `deploy/docker-compose.yml`):
- `SPRING_DATASOURCE_URL` — JDBC URL for Postgres
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SPRING_REDIS_HOST`
- `JWT_SECRET` (strong secret)
- `APP_PORT` (optional backend port)

--

## Database: schema & seed
- Schema and seed SQL are available in `deploy/sql/schema.sql` and `deploy/sql/seed.sql`.
- Run them against the `accessoriesdb` database or let Docker Compose initialize Postgres and run manually for quick seeding.

--

## API documentation
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Swagger UI: `http://localhost:8080/swagger-ui.html` or `http://localhost:8080/swagger-ui/index.html`

All endpoints are versioned under `/api/v1/` (e.g. `/api/v1/accessories`). Responses are wrapped in a generic `ApiResponse<T>` structure for consistency.

--

## Testing
- Run backend unit tests:

```bash
cd backend
mvn test
```

- Frontend tests and E2E (Cypress) are planned; placeholder scaffolding exists in `frontend/`.

--

## Deployment
- Use the provided `deploy/docker-compose.yml` for a local multi-container setup (Postgres, Redis, backend, frontend).
- For production, build Docker images and run behind a reverse proxy (NGINX) with TLS certificates. Store secrets in a secure vault and rotate regularly.

--

## Architecture notes
- Clean architecture layers in the backend: `controller`, `service`, `repository`, `entity`, `dto`, `mapper`, `security`, `config`, `exception`.
- Redis caches read-heavy endpoints (`accessories`, `categories`, `brands`) with eviction on writes using `@Cacheable` and `@CacheEvict`.
- DB design uses UUID primary keys, FK constraints with `ON DELETE SET NULL`, GIN full-text indexes for search.

--

## Scrum & Project Management (short)
- Product backlog, sprint planning, and task breakdown are simulated in project docs. Use the `docs/` folder for backlog examples and sprint artifacts (to be added).

--

## Contributors
- Project scaffolded by developer; additional contributors welcome. See `CONTRIBUTING.md` (TBD).

--

If you'd like, I'll now:
- implement frontend auth pages and protected routes, or
- add controller integration tests using `@SpringBootTest` and `MockMvc`.

Which should I start next?