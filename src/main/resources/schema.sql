DROP TABLE IF EXISTS Users;

CREATE TABLE Users (
    User_Id BIGINT AUTO_INCREMENT PRIMARY KEY,
    User_Name VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL,
    CONSTRAINT uq_email UNIQUE (Email),
    CONSTRAINT chk_email_format CHECK (Email LIKE '%_@_%._%')
);
