CREATE TABLE logements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(255) NOT NULL,
    adresse VARCHAR(255) NOT NULL,
    type VARCHAR(20) NOT NULL,
    statut VARCHAR(20) NOT NULL,
    image VARCHAR(255),
    ville VARCHAR(255) NOT NULL,
    region VARCHAR(255) NOT NULL,
    quartier VARCHAR(255) NOT NULL,
    nombre_chambre INT NOT NULL,
    nombre_toilette INT NOT NULL,
    nombre_etage INT,
    surface INT NOT NULL,
    description TEXT NOT NULL,
    prix DECIMAL(10, 2) NOT NULL,
    user_id BIGINT,
    categorie_id BIGINT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (categorie_id) REFERENCES categories(id)
);
