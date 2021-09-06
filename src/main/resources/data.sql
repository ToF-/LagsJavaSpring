DROP TABLE orders IF EXISTS;
DROP TABLE Customers IF EXISTS;
CREATE TABLE customers (
    id VARCHAR(8) NOT NULL PRIMARY KEY,
    name VARCHAR(80) NOT NULL
);
CREATE TABLE orders (
    id VARCHAR(8) NOT NULL PRIMARY KEY,
    customerid VARCHAR(8) NOT NULL,
    start INT NULL,
    duration INT NOT NULL,
    price DECIMAL(10,2) NOT NULL
);
ALTER TABLE orders
    ADD FOREIGN KEY (customerId)
    REFERENCES Customers(Id)
