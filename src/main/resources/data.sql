-- Insert sample products (motorcycles)
INSERT INTO products (name, description, price, stock, image_url, is_discounted) VALUES
('Ducati Panigale V4', 'The Ducati Panigale V4 is the first Ducati production bike to use a 4-cylinder engine. This 1103 cc Desmosedici Stradale engine produces 214 hp and 91.5 lb-ft of torque.', 24999.99, 5, '/images/gallery1.jpg', false),
('Ducati Streetfighter V4', 'The Streetfighter V4 is the naked version of the Panigale V4, featuring the same powerful engine but with a more upright riding position and aggressive styling.', 19999.99, 8, '/images/gallery2.jpg', true),
('Ducati Multistrada V4', 'The Multistrada V4 is Ducati''s flagship adventure bike, featuring a 1158 cc V4 Granturismo engine, advanced electronics, and long-distance comfort.', 22999.99, 6, '/images/gallery3.jpg', false),
('Ducati Monster', 'The new Monster features a 937 cc Testastretta 11° engine, producing 111 hp and 69 lb-ft of torque, wrapped in a lightweight and agile chassis.', 15999.99, 10, '/images/gallery4.jpg', true),
('Ducati Diavel', 'The Diavel combines the power of a muscle cruiser with the handling of a sportbike, featuring a 1262 cc Testastretta DVT engine producing 162 hp.', 21999.99, 4, '/images/gallery5.jpg', false),
('Ducati Scrambler', 'The Scrambler is a modern interpretation of the 1970s Ducati Scrambler, featuring a 803 cc L-twin engine and retro styling with modern technology.', 12999.99, 12, '/images/gallery6.jpg', true),
('Ducati Hypermotard', 'The Hypermotard is a supermoto-style motorcycle featuring a 937 cc Testastretta 11° engine, perfect for urban riding and track days.', 17999.99, 7, '/images/gallery7.jpg', false);

-- Insert sample users (customers)
INSERT INTO users (fullname, email, password, is_admin) VALUES
('John Smith', 'john@example.com', 'password123', false),
('Sarah Johnson', 'sarah@example.com', 'password123', false),
('Mike Wilson', 'mike@example.com', 'password123', false);

-- Insert sample discount codes
INSERT INTO discounts (code, percentage, is_active) VALUES
('MOTO20', 20.0, true),
('DUCATI15', 15.0, true),
('SPRING10', 10.0, true);

-- Insert sample orders
INSERT INTO orders (order_date, total_amount, status, discount_code) VALUES
(NOW(), 22499.99, 'COMPLETED', 'MOTO20'),
(NOW(), 16999.99, 'PENDING', NULL),
(NOW(), 19499.99, 'COMPLETED', 'DUCATI15');