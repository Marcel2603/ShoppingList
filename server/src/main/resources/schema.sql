CREATE TABLE IF NOT EXISTS item (
    id int NOT NULL AUTO_INCREMENT,
    name VARCHAR(255),
    amount int DEFAULT 1,
    PRIMARY KEY (id)
);
