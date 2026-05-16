Local docker-compose run (ports, secrets, smoke tests)

Quick start

1. Build and start the stack (from project root):

```powershell
cd C:/Users/dell/Desktop/project
docker-compose build --no-cache
docker-compose up -d
```

2. Services and host ports

- Backend: container listening on 8080; mapped to host `8081` (use `http://localhost:8081`).
- Frontend: container listening on 3000; mapped to host `3001` (use `http://localhost:3001`).
- Postgres: host `5432` (mapped), DB `accessories` user `postgres`/`postgres`.
- Redis: host `6379` (mapped).

Important notes

- The repository `docker-compose.yml` sets a `JWT_SECRET` environment variable for the backend. This must be a sufficiently long random secret (at least 256 bits) for HMAC-SHA signing. Rotate or inject securely for production.

- If your host already has services bound on ports 8080 or 3000 (common for other apps), the compose file maps the backend and frontend to host ports 8081 and 3001 respectively to avoid conflicts.

Smoke test

- Run the provided smoke test script to verify auth and accessories endpoints:

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\api_check.ps1
```

What I changed

- `backend/Dockerfile`: ensure Spring Boot repackage runs during the build so the runtime image contains an executable jar.
- `docker-compose.yml`: remapped backend/frontend host ports (`8081:8080`, `3001:3000`) and added a `JWT_SECRET` for local testing.

Next steps

- Commit the Dockerfile and `docker-compose.yml` fixes and push to your branch.
- Replace the test `JWT_SECRET` with a secure secret management solution for deployments.
