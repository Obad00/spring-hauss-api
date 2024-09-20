CREATE TABLE paiements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    montant DECIMAL(10, 2) NOT NULL,
    type VARCHAR(20) NOT NULL,
    commission DECIMAL(10, 2) NOT NULL,
    reservation_id BIGINT,
    FOREIGN KEY (reservation_id) REFERENCES reservations(id)
);