-- PostgreSQL schema for Accessories Manager
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE brands (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    country VARCHAR(100),
    logo_url TEXT
);

CREATE TABLE categories (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    phone_number VARCHAR(50),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

-- Orders
CREATE TABLE orders (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    total_price NUMERIC(12,2),
    status TEXT,
    created_at TIMESTAMPTZ,
    user_id UUID REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE order_items (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID REFERENCES orders(id) ON DELETE CASCADE,
    accessory_id UUID REFERENCES accessories(id) ON DELETE RESTRICT,
    quantity INTEGER,
    price_snapshot NUMERIC(12,2)
);

CREATE TABLE accessories (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(500) NOT NULL,
    description TEXT,
    price NUMERIC(12,2) NOT NULL,
    stock_quantity INTEGER NOT NULL DEFAULT 0,
    image_url TEXT,
    accessory_type VARCHAR(200),
    compatibility VARCHAR(500),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE,
    category_id UUID REFERENCES categories(id) ON DELETE SET NULL,
    brand_id UUID REFERENCES brands(id) ON DELETE SET NULL
);

-- Indexes
CREATE INDEX idx_accessory_name ON accessories USING gin (to_tsvector('english', name));
CREATE INDEX idx_accessory_description ON accessories USING gin (to_tsvector('english', description));
