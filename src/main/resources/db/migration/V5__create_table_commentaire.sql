CREATE TABLE commentaires (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nom_complet VARCHAR(255),
    note INT NOT NULL,
    description TEXT NOT NULL,
    user_id BIGINT,
    logement_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (logement_id) REFERENCES logements(id) ON DELETE CASCADE
);
