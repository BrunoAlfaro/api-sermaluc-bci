-- Tabla Usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    created TIMESTAMP,
    modified TIMESTAMP,
    last_login TIMESTAMP,
    token VARCHAR(255),
    is_active BOOLEAN
);


-- Tabla Phone
CREATE TABLE IF NOT EXISTS telefonos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    number VARCHAR(255),
    citycode VARCHAR(255),
    contrycode VARCHAR(255),
    user_id VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES usuarios(id) ON DELETE CASCADE
);
