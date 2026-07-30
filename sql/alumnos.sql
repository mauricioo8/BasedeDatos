-- Script de creación de la tabla ALUMNOS
-- Integrante 1: Gestión de alumnos
-- Ejecutar este script sobre la base de datos sistema_academico

USE sistema_academico;

CREATE TABLE IF NOT EXISTS alumnos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    correo VARCHAR(100),
    telefono VARCHAR(20),
    fecha_nacimiento DATE
);

-- Datos de prueba (opcional, para verificar que todo funciona)
INSERT INTO alumnos (codigo, nombre, apellido, correo, telefono, fecha_nacimiento)
VALUES
    ('2024-001', 'Ana', 'Torres', 'ana.torres@correo.com', '999111222', '2002-05-14'),
    ('2024-002', 'Luis', 'Pérez', 'luis.perez@correo.com', '999333444', '2001-11-02');

SELECT * FROM alumnos;
