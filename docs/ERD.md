# ERD and Database Design Explanation

Entities:
- brands: stores brand meta (id, name, country, logoUrl)
- categories: normalized categories for accessories
- users: application users with roles and credentials
- accessories: main product table referencing brands and categories

Design choices:
- UUID primary keys for distributed-safe IDs and easier merges
- Foreign keys with `ON DELETE SET NULL` to preserve historical accessories when brands/categories are removed
- GIN full-text search indexes on `name` and `description` for fast keyword searches
- Timestamps (`created_at`, `updated_at`) for auditing

Normalization:
- Accessories reference brands and categories by foreign key, reducing duplication

Cache strategy:
- Read-heavy lists (accessories list, categories, brands) are cached in Redis with short TTLs (10 minutes) and evicted on writes.
