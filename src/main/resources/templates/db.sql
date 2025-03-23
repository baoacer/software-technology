-- carts table
CREATE TABLE carts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    userId VARCHAR(255) NOT NULL UNIQUE
);

-- cart_items table
CREATE TABLE cart_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    quantity SMALLINT UNSIGNED NOT NULL,
    cart_id INT NOT NULL,
    product_id SMALLINT UNSIGNED NOT NULL,
    FOREIGN KEY (cart_id) REFERENCES carts(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- categories table
CREATE TABLE categories (
    id TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL UNIQUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
);

-- images table
CREATE TABLE images (
    id INT AUTO_INCREMENT PRIMARY KEY,
    src VARCHAR(255) UNIQUE,
    alt VARCHAR(255),
    position TINYINT UNSIGNED,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    deleted_at DATETIME,
    product_id SMALLINT UNSIGNED NOT NULL,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- inventory table
CREATE TABLE inventory (
    id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    quantity SMALLINT UNSIGNED NOT NULL,
    status ENUM('IN_STOCK', 'OUT_OF_STOCK', 'LOW_STOCK') NOT NULL DEFAULT 'IN_STOCK',
    updated_at DATE ON UPDATE CURRENT_TIMESTAMP,
    product_id SMALLINT UNSIGNED UNIQUE,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- products table
CREATE TABLE products (
    id SMALLINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    slug VARCHAR(255),
    title VARCHAR(255),
    description LONGTEXT,
    tags TEXT,
    price DECIMAL(10, 2),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
    deleted_at DATETIME,
    category_id TINYINT UNSIGNED,
    vendor_id TINYINT UNSIGNED,
    FOREIGN KEY (category_id) REFERENCES categories(id),
    FOREIGN KEY (vendor_id) REFERENCES vendors(id)
);

-- roles table
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

-- users table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
);

-- vendors table
CREATE TABLE vendors (
    id TINYINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
);

-- orders table
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    order_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    shipping_address VARCHAR(255),
    payment_method VARCHAR(255),
    order_status ENUM('PENDING', 'PROCESSING', 'SHIPPED', 'DELIVERED', 'CANCELLED', 'REFUNDED') DEFAULT 'PENDING',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- order_items table
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id SMALLINT UNSIGNED NOT NULL,
    quantity SMALLINT UNSIGNED NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Adding foreign key constraints after table creation for inventory table
ALTER TABLE inventory
ADD CONSTRAINT FK_inventory_product_id
FOREIGN KEY (product_id) REFERENCES products(id);
