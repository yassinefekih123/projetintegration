-- Seed data for Accessories Manager
-- Enable uuid generation if not available
CREATE EXTENSION IF NOT EXISTS pgcrypto;

INSERT INTO brands (id, name, country, logo_url) VALUES
    (gen_random_uuid(), 'Acme Audio', 'USA', ''),
    (gen_random_uuid(), 'Vertex Accessories', 'China', '');

INSERT INTO categories (id, name, description) VALUES
    (gen_random_uuid(), 'Chargers', 'Wall and car chargers'),
    (gen_random_uuid(), 'Cables', 'USB, Lightning cables');

-- Example accessories
INSERT INTO accessories (id, name, description, price, stock_quantity, accessory_type, compatibility, category_id, brand_id)
SELECT gen_random_uuid(), 'Fast Charger 30W', 'USB-C PD fast charger', 29.99, 150, 'Charger', 'Most USB-C phones', c.id, b.id
FROM categories c, brands b
WHERE c.name = 'Chargers' LIMIT 1;

INSERT INTO accessories (id, name, description, price, stock_quantity, accessory_type, compatibility, category_id, brand_id)
SELECT gen_random_uuid(), 'Durable USB-C Cable', '2m braided USB-C to USB-C cable', 9.99, 300, 'Cable', 'USB-C devices', c.id, b.id
FROM categories c, brands b
WHERE c.name = 'Cables' LIMIT 1;

INSERT INTO accessories (id, name, description, price, stock_quantity, accessory_type, compatibility, category_id, brand_id)
SELECT gen_random_uuid(), 'Wireless Earbuds', 'Bluetooth 5.2 earbuds with charging case', 59.99, 80, 'Audio', 'Most smartphones', c.id, b.id
FROM categories c, brands b
WHERE c.name = 'Chargers' LIMIT 1;
