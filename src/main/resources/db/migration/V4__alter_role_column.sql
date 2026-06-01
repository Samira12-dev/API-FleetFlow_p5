ALTER TABLE users
    MODIFY COLUMN role ENUM('admin','manager','chauffeur') NOT NULL;