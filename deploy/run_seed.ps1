param(
  [string]$ComposeFile = "docker-compose.yml",
  [string]$Service = "postgres",
  [string]$SqlPath = "sql/seed.sql"
)

Write-Host "Starting postgres service (if not already running)..."
docker-compose -f (Join-Path $PSScriptRoot $ComposeFile) up -d $Service

Write-Host "Locating postgres container..."
$container = docker ps --filter "label=com.docker.compose.service=$Service" --format "{{.ID}}"
if (-not $container) {
  Write-Error "Postgres container not found. Ensure docker-compose is running in deploy/ and the service name is '$Service'."
  exit 1
}

$dest = "/tmp/seed.sql"
Write-Host "Copying seed file to container ($container): $SqlPath -> $dest"
docker cp (Join-Path $PSScriptRoot $SqlPath) $container:$dest

Write-Host "Executing seed SQL inside container..."
docker exec -i $container psql -U postgres -d accessoriesdb -f $dest

Write-Host "Seed script executed."
