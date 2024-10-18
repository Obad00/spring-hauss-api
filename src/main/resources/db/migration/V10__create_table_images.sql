CREATE TABLE images (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    url VARCHAR(255) NOT NULL,
    logement_id BIGINT NOT NULL,
    FOREIGN KEY (logement_id) REFERENCES logements(id) ON DELETE CASCADE
);