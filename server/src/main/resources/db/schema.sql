CREATE TABLE IF NOT EXISTS item (
    id SERIAL,
    name VARCHAR(255),
    amount VARCHAR(255) DEFAULT '1x',
    PRIMARY KEY (id)
);
