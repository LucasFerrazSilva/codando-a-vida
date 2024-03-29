SELECT * FROM TB_USERS;
SELECT * FROM TB_CATEGORIES;
SELECT * FROM TB_POSTS ORDER BY PUBLISH_DATE DESC;
SELECT * FROM TB_FORGOTTEN_PASSWORD;

---

INSERT INTO TB_FORGOTTEN_PASSWORD VALUES (NULL, 16, '123', 'NEW');
DELETE FROM TB_FORGOTTEN_PASSWORD;

INSERT INTO TB_USERS VALUES (NULL, 'Admin', 'admin@teste.com', 'ADMIN', 'ADMIN', 'ACTIVE', NOW(), NULL);
INSERT INTO TB_USERS VALUES (NULL, 'Lucas', 'lucas@teste.com', '123', 'USER', 'ACTIVE', NOW(), NULL);
UPDATE TB_USERS SET NAME = 'Lucas2' WHERE ID = 10;
UPDATE TB_USERS SET PASSWORD = '$2a$10$bkwI7ZW5o0dCYkNQDVfEyOUwBAeUkKWQr.Ox0zfOKbx1l4AHCFJwe' WHERE NAME = 'Lucas Ferraz';
UPDATE TB_USERS SET ROLE = 'USER', PASSWORD = '$2a$10$eDSJuMLl8qYIpuKwAlk8tOqrwIjYIznd6/6QWtutWLmUg2Esb/dG2' WHERE NAME = 'Carina';
UPDATE TB_USERS SET EMAIL = 'lucasferrazsilva@hotmail.com' WHERE ID = 16;
DELETE FROM TB_USERS;

INSERT INTO TB_CATEGORIES (NAME, CREATION_USER_ID) VALUES ('CAT1', 15);
INSERT INTO TB_CATEGORIES (NAME, CREATION_USER_ID) VALUES ('Categoria 2', 16);
UPDATE TB_CATEGORIES SET NAME = 'CAT1-2' WHERE NAME = 'CAT1';
UPDATE TB_CATEGORIES SET CREATION_USER_ID = 16 WHERE NAME = 'CAT1';
DELETE FROM TB_CATEGORIES;

INSERT INTO TB_POSTS (TITLE, BODY, CATEGORY_ID, CREATION_USER_ID) VALUES ('Postagem 1', 'Corpo da postagem 1.', 3, 16);
INSERT INTO TB_POSTS (TITLE, BODY, CATEGORY_ID, CREATION_USER_ID) VALUES ('Postagem 2', 'Corpo da postagem 2.', 4, 15);
UPDATE TB_POSTS SET STATUS = 'PUBLISHED' WHERE ID = 2;