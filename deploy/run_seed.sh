#!/usr/bin/env bash
set -euo pipefail
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
COMPOSE_FILE="$SCRIPT_DIR/docker-compose.yml"
SERVICE="postgres"
SQL_PATH="$SCRIPT_DIR/sql/seed.sql"

echo "Starting postgres service (if not already running)..."
docker-compose -f "$COMPOSE_FILE" up -d "$SERVICE"

echo "Locating postgres container..."
container=$(docker ps --filter "label=com.docker.compose.service=$SERVICE" --format "{{.ID}}")
if [ -z "$container" ]; then
  echo "Postgres container not found. Ensure docker-compose is running in deploy/ and the service name is '$SERVICE'." >&2
  exit 1
fi

dest="/tmp/seed.sql"
echo "Copying seed file to container ($container): $SQL_PATH -> $dest"
docker cp "$SQL_PATH" "$container":"$dest"

echo "Executing seed SQL inside container..."
docker exec -i "$container" psql -U postgres -d accessoriesdb -f "$dest"

echo "Seed script executed."
