CREATE TABLE supports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    typeDemande VARCHAR(20) NOT NULL,
    user_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id)
);