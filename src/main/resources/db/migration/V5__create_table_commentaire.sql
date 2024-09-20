CREATE TABLE commentaires (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nomComplet VARCHAR(255),
    note INT NOT NULL,
    description TEXT NOT NULL,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);
