DROP TABLE IF EXISTS users;
CREATE TABLE users (
     user_id INT AUTO_INCREMENT PRIMARY KEY,
     first_name VARCHAR(50) NOT NULL,
     last_name VARCHAR(50) NOT NULL,
     email VARCHAR(100) NOT NULL UNIQUE,
     password VARCHAR(255) NOT NULL
);

INSERT INTO users (first_name, last_name, email, password) VALUES
    ('Anders', 'Nielsen', 'anders.nielsen@email.dk', 'hashed_password123'),
    ('Mette', 'Jensen', 'mette.jensen@email.dk', 'hashed_password456'),
    ('Lars', 'Pedersen', 'lars.pedersen@email.dk', 'hashed_password789'),
    ('Sofie', 'Hansen', 'sofie.hansen@email.dk', 'hashed_password101'),
    ('Mikkel', 'Andersen', 'mikkel.andersen@email.dk', 'hashed_password202');






DROP TABLE IF EXISTS wishlists;
CREATE TABLE wishlists (
    wishlist_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);


INSERT INTO wishlists (user_id, title) VALUES
    (1, 'Fødselsdag'),
    (1, 'Jul'),
    (2, 'Bryllup'),
    (3, 'Indflytterfest'),
    (4, 'Fødselsdag 30 år'),
    (5, 'Ønsker til hjemmet');






DROP TABLE IF EXISTS wishes;
CREATE TABLE wishes (
    wish_id INT AUTO_INCREMENT PRIMARY KEY,
    wishlist_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2),
    url VARCHAR(255),
    image_url VARCHAR(255),
    FOREIGN KEY (wishlist_id) REFERENCES wishlists(wishlist_id) ON DELETE CASCADE
);


INSERT INTO wishes (wishlist_id, title, description, price, url, image_url) VALUES
    (1, 'Kaffemaskine', 'Philips kaffemaskine med kværn', 899.00, 'https://www.elgiganten.dk/product/kaffemaskine', 'https://images.example.com/kaffemaskine.jpg'),
    (1, 'Kogebog', 'Meyers nordisk mad', 349.95, 'https://www.saxo.com/dk/kogebog', 'https://images.example.com/kogebog.jpg'),
    (2, 'Skitøj', 'Jakke og bukser fra Helly Hansen, str. L', 2499.00, 'https://www.sportmaster.dk/skitoj', 'https://images.example.com/skitoj.jpg'),
    (2, 'Trådløse høretelefoner', 'Sony WH-1000XM4', 2799.00, 'https://www.power.dk/horetelefoner', 'https://images.example.com/horetelefoner.jpg'),
    (3, 'Espressomaskine', 'Sage Barista Express', 4999.00, 'https://www.coolshop.dk/espresso', 'https://images.example.com/espresso.jpg'),
    (3, 'Sæt med vinglas', '6 stk. Spiegelau rødvinsglas', 599.00, 'https://www.imerco.dk/vinglas', 'https://images.example.com/vinglas.jpg'),
    (4, 'Plantebord', 'Træbord til udendørs planter', 450.00, 'https://www.plantorama.dk/plantebord', 'https://images.example.com/plantebord.jpg'),
    (4, 'Køkkenmaskine', 'KitchenAid Artisan i rød', 3699.00, 'https://www.bahne.dk/kitchenaid', 'https://images.example.com/kitchenaid.jpg'),
    (5, 'Gavekort til spa', 'Dagspakke på Comwell Spa', 1200.00, 'https://www.comwellspa.dk/gavekort', 'https://images.example.com/spa.jpg'),
    (5, 'Tablet', 'iPad Air 64GB', 4999.00, 'https://www.apple.com/dk/ipad', 'https://images.example.com/ipad.jpg'),
    (6, 'Sofabord', 'Rundt sofabord i egetræ', 1499.00, 'https://www.ilva.dk/sofabord', 'https://images.example.com/sofabord.jpg'),
    (6, 'Sengetøj', 'Georg Jensen Damask sengesæt', 899.00, 'https://www.gjdamask.dk/sengetoj', 'https://images.example.com/sengetoj.jpg');