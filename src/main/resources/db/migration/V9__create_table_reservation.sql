CREATE TABLE reservations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    statut VARCHAR(20) NOT NULL,
    logement_id BIGINT,
    locataire_id BIGINT,
    deletedByOwner BOOLEAN NOT NULL,
    deletedByTenant BOOLEAN NOT NULL,
    archivedByTenantAt TIMESTAMP,
    archivedByOwnerAt TIMESTAMP,
    FOREIGN KEY (logement_id) REFERENCES logements(id),
    FOREIGN KEY (locataire_id) REFERENCES locataires(id)
);