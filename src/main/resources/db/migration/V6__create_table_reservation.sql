CREATE TABLE reservations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    statut VARCHAR(20) NOT NULL,
    logement_id BIGINT,
    user_id BIGINT,
    FOREIGN KEY (logement_id) REFERENCES logements(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);