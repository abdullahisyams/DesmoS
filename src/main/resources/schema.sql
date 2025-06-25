-- Create products table
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    price DOUBLE NOT NULL,
    stock INTEGER NOT NULL,
    image_url VARCHAR(255),
    is_discounted BOOLEAN DEFAULT FALSE
);

-- Add image_url column if it doesn't exist
ALTER TABLE products
ADD COLUMN IF NOT EXISTS image_url VARCHAR(255);

-- Create users table (for mock login)
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    is_admin BOOLEAN DEFAULT FALSE
);

-- Modify users table to match new structure
ALTER TABLE users 
    ADD COLUMN fullname VARCHAR(255) AFTER id,
    ADD COLUMN email VARCHAR(255) AFTER fullname,
    DROP COLUMN username;

-- Create cart_items table
CREATE TABLE IF NOT EXISTS cart_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Create orders table
CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_date DATETIME NOT NULL,
    total_amount DOUBLE NOT NULL,
    status VARCHAR(50) NOT NULL,
    discount_code VARCHAR(50)
);

-- Create discounts table
CREATE TABLE IF NOT EXISTS discounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(50) NOT NULL UNIQUE,
    percentage DOUBLE NOT NULL,
    is_active BOOLEAN DEFAULT TRUE
);

-- Insert default discount code
INSERT INTO discounts (code, percentage, is_active)
VALUES ('DISCOUNT10', 10.0, true)
ON DUPLICATE KEY UPDATE is_active = true; 