# Deploy helpers

This folder contains helper scripts for running and seeding the local development environment.

Run the Postgres seed SQL after `docker-compose up -d` (from this folder):

PowerShell:

```powershell
./run_seed.ps1
```

Bash (Linux / WSL / Git Bash):

```bash
./run_seed.sh
```

Both scripts will start the `postgres` service (if not running), copy `sql/seed.sql` into the container, and execute it.
