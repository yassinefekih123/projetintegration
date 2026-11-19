# Mobile Accessories Manager

Application web full-stack de gestion d'accessoires pour téléphones mobiles : catalogue, catégories, marques et comptes utilisateurs (administrateur / client).

## Fonctionnalités

- Gestion des accessoires, catégories et marques (CRUD)
- Authentification JWT avec Spring Security, rôles Admin / Client
- Pagination, tri, filtrage et recherche
- Cache Redis sur les endpoints en lecture
- Documentation de l'API avec OpenAPI (Swagger)
- Interface responsive en Next.js et TailwindCSS
- Environnement local Dockerisé (PostgreSQL + Redis)
- Intégration continue avec GitHub Actions (tests backend + tests E2E Cypress)

## Technologies

| Couche | Technologies |
|---|---|
| Backend | Java 17, Spring Boot 3, Spring Data JPA / Hibernate, Spring Security, MapStruct, Lombok, Maven |
| Base de données | PostgreSQL (schéma + jeu de données dans `deploy/sql`) |
| Cache | Redis |
| Frontend | Next.js, TypeScript, TailwindCSS |
| Tests | JUnit 5, Mockito, Spring Boot Test, Cypress |
| DevOps | Docker, Docker Compose, GitHub Actions |

## Lancer le projet

Prérequis : Java 17, Maven, Node 18+ et, pour l'option Docker, Docker avec Docker Compose.

### Avec Docker

```bash
docker compose up --build
```

Frontend : http://localhost:3000 · API : http://localhost:8080

### En local

```bash
# Backend
cd backend
mvn spring-boot:run

# Frontend (dans un autre terminal)
cd frontend
npm install
npm run dev
```

La base doit être initialisée avec `deploy/sql/seed.sql` (extension `pgcrypto` requise pour les UUID).

## Tests

```bash
# Tests backend
cd backend && mvn test

# Tests E2E Cypress (frontend lancé)
cd frontend && npm run cypress:run -- --headless
```

## Documentation

- Schéma de données : [`docs/ERD.md`](docs/ERD.md)
- Scripts de données : [`deploy/`](deploy/)

## Auteur

Yassine Fekih – ISET Tozeur, Technologies de l'Informatique (DSI)
