# Draft PR: feat(frontend): add global toasts + confirm modal

## Summary

This draft captures the intended pull request for the `feat/frontend-toasts` branch.


<!-- ci-trigger -->
Triggered CI on: 2026-05-15T00:00:00Z
Triggered CI rerun at: 2026-05-15T18:20:00Z
Triggered CI rerun at: 2026-05-15T18:22:00Z

## Branch
- Base: `main` (created from feature branch for PR flow)

## Key commits (recent)
- feat(frontend): add global toast provider, confirm modal, and toast UI
- chore: prepare feat/frontend-toasts for push
- chore: remove frontend build artifacts from repo
- ci: publish JUnit results to PR using dorny/test-reporter
- test(e2e): add orders list->detail Cypress spec

## Local verification

- Backend: `mvn -DskipTests=false clean test` — tests passed locally after `CacheConfig` fix.
- Frontend: `npm run build` and `npm run start` from `frontend/` — Next.js builds and serves locally.
- E2E: Cypress run (local) — `order_cancel.spec.ts` and `order_list_and_view.spec.ts` pass locally.

## Known issues / notes

- The `main` branch was created from the feature branch to allow PR compare; branches are currently identical.
- CI has not yet been observed for this PR — will monitor once PR is submitted and adjust workflow if checks fail.

## Files changed (high level)

- `frontend/components/Toast*.tsx`, `components/ConfirmModal.tsx`, `components/ToastProvider.tsx`
- `frontend/cypress/e2e/*.spec.ts`
- `backend/src/main/java/.../config/CacheConfig.java` (conditional bean)
- removed tracked `frontend/.next/` build artifacts
- added `package.json` at repo root with `frontend:*` and `e2e` scripts

## Testing instructions for reviewers

1. Start backend (if reviewing backend changes): `cd backend && mvn spring-boot:run`
2. Start frontend: `npm run frontend:install` then `npm run frontend:build` then `npm run frontend:start`
3. Run E2E locally: `npm run e2e`

## Checklist
- [ ] Create PR on GitHub (title/body below)
- [ ] Wait for CI to run and address any failures
- [ ] Request reviewers and merge when green

## Suggested PR title & body

Title: feat(frontend): add global toasts + confirm modal

Body:
Adds a global toast provider, a confirm modal used for order cancellation, and E2E tests for the cancellation flow. Also fixes a backend cache config issue and removes tracked frontend build artifacts.

---
Generated draft by automation. Update as needed before submitting.
