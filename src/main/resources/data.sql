-- Insertar autores
INSERT INTO autor (nombre, apellido) VALUES ('Gabriel', 'Garcia Marquez');
INSERT INTO autor (nombre, apellido) VALUES ('J.K.', 'Rowling');
INSERT INTO autor (nombre, apellido) VALUES ('George', 'Orwell');
INSERT INTO autor (nombre, apellido) VALUES ('Manolo', 'Garcia');



-- Insertar biografías de los autores
-- Gabriel García Márquez
INSERT INTO biografia (id_autor, fecha_nac, nacionalidad, premios, obras_destacadas)VALUES (1, '06-03-1927', 'Colombiana', 'Premio Romulo Gallegos 1972', 'El amor en los tiempos de la muerte');

-- J.K. Rowling
INSERT INTO biografia (id_autor, fecha_nac, nacionalidad, premios, obras_destacadas)VALUES (2, '31-07-1965', 'Britanica', 'Legion de Honor 2009', 'Harry Potter y las reliquias de la muerte');

-- George Orwell
INSERT INTO biografia (id_autor, fecha_nac, nacionalidad, premios, obras_destacadas)VALUES (3, '25-06-1903', 'Britanica', 'Retro Hugo Award 1996', 'Sin blanca en Paris y Londres');

-- Manolo Garcia
INSERT INTO biografia (id_autor, fecha_nac, nacionalidad, premios, obras_destacadas)VALUES (4, '27-06-1903', 'Español', 'Retro Hugo Award 1997', 'Pajaros de Barro');


-- Insertar libros y asociarlos con los autores
INSERT INTO libro (titulo, precio, id_autor) VALUES ('Cien anios de soledad', 19.99, 1);
INSERT INTO libro (titulo, precio, id_autor) VALUES ('El amor en los tiempos del colera', 17.99, 1);
INSERT INTO libro (titulo, precio, id_autor) VALUES ('Harry Potter y la piedra filosofal', 15.99, 2);
INSERT INTO libro (titulo, precio, id_autor) VALUES ('1984', 13.99, 3);
INSERT INTO libro (titulo, precio, id_autor) VALUES ('Rebelion en la granja', 14.99, 3);


INSERT INTO usuario(usuario, password, rol) VALUES ('mateoir95', '$2a$12$sFN.GK/dlqOEx7XLzkFhPeXkJga5c9PDnav/LFi9iDzh8TzD3VoRe', 'ADMIN');
INSERT INTO usuario(usuario, password, rol) VALUES ('adri86', '$2a$12$u1f/u/D8QKENhLP1jq0bTuWhu7VwT8zQUiZW2HrrXKlGymd5Wk8kK', 'USER');

INSERT INTO compra(usuario_id, libro_id, fecha) VALUES (1, 3, '06-09-2024');
INSERT INTO compra(usuario_id, libro_id, fecha) VALUES (1, 5, '06-09-2024');
INSERT INTO compra(usuario_id, libro_id, fecha) VALUES (2, 1, '06-09-2024');


