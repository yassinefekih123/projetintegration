# Sprint 1 — Duration: 2 weeks

Goal: Deliver core backend authentication, accessories CRUD, and frontend auth pages.

Planned User Stories:
- US-001: As a user I can register and login so I can access protected features. (AuthController, JWT)
- US-002: As an admin I can create/update/delete accessories so I can manage inventory. (AccessoryService + API)
- US-003: As a client I can browse accessories and view details. (frontend list & details)

Tasks:
- Implement registration and login endpoints (AuthController, AuthService)
- Implement `User` entity and `UserRepository`
- Implement `Accessory` entity, repository, service, controller
- Implement MapStruct mappers and DTOs
- Implement frontend login/register pages and dashboard placeholder
- Add unit tests for AuthService and AccessoryService
- Create Docker Compose for local development

Acceptance Criteria:
- Users can register and receive a JWT token
- Admin-only endpoints protected by roles
- Accessories can be created via API and visible in frontend list
- Unit tests covering auth and accessory services pass
