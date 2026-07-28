CREATE TABLE books (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publisher VARCHAR(255) NOT NULL,
    total_copies INTEGER NOT NULL,
    available_copies INTEGER NOT NULL
);