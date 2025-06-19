CREATE DATABASE bd2_yupanqui;

USE bd2_yupanqui;

CREATE TABLE clientes (
    id_cliente INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE peliculas (
    id_pelicula INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(255) NOT NULL,
    genero VARCHAR(100) NOT NULL,
    stock INT NOT NULL CHECK (stock >= 0)
);

CREATE TABLE alquileres (
    id_alquiler INT PRIMARY KEY AUTO_INCREMENT,
    fecha DATE NOT NULL,
    id_cliente INT NOT NULL,
    total DECIMAL(10, 2) NOT NULL CHECK (total >= 0),
    estado ENUM('ACTIVO', 'DEVUELTO', 'RETRASADO') NOT NULL DEFAULT 'ACTIVO',
    FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
);

SELECT * FROM detalle_alquiler;

CREATE TABLE detalle_alquiler (
    id_alquiler INT NOT NULL,
    id_pelicula INT NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    PRIMARY KEY (id_alquiler, id_pelicula),
    FOREIGN KEY (id_alquiler) REFERENCES alquileres(id_alquiler),
    FOREIGN KEY (id_pelicula) REFERENCES peliculas(id_pelicula)
);

INSERT INTO clientes (nombre, apellido, email) VALUES
('Juan', 'Perez', 'juan.perez@example.com'),
('Maria', 'Gomez', 'maria.gomez@example.com'),
('Carlos', 'Lopez', 'carlos.lopez@example.com');

INSERT INTO peliculas (titulo, genero, stock) VALUES
('El Exorcista del Papa', 'Terror', 5),
('Beekeeper', 'Accion', 3),
('El Transportador', 'Accion', 7);

INSERT INTO alquileres (fecha, id_cliente, total, estado) VALUES
('2025-05-20', 1, 10.50, 'ACTIVO'),
('2025-05-22', 2, 8.00, 'DEVUELTO'),
('2025-05-25', 3, 12.00, 'ACTIVO');

INSERT INTO detalle_alquiler (id_alquiler, id_pelicula, cantidad) VALUES
(1, 1, 1),
(1, 2, 1),
(2, 3, 2),
(3, 1, 1);