/*
	Nombre: 		usuarios.sql
	Autor:			Josué Hernández
	Fecha:			24 de enero de 2017
	Objetivo:		Archivo que contiene la gestión de la autenticación 
						de los usuarios
*/



-- Cargar la cadena llave en una variable;
SET @psswd:='B893EEA';

-- Insertar usuarios cifrados
INSERT INTO Usuarios(nombreUsuario, contrasenaUsuario, tipoUsuario) 
VALUES(AES_ENCRYPT('leo.hdz',@psswd),AES_ENCRYPT('123',@psswd), 'A');

INSERT INTO Usuarios(nombreUsuario, contrasenaUsuario, tipoUsuario) 
VALUES(AES_ENCRYPT('j.genico',@psswd),AES_ENCRYPT('123',@psswd), 'A');

INSERT INTO Usuarios(nombreUsuario, contrasenaUsuario, tipoUsuario) 
VALUES(AES_ENCRYPT('angelin',@psswd),AES_ENCRYPT('perro123',@psswd), 'U');



-- Consultar Contraseñas Decifradas
select CONVERT(aes_decrypt(Usuarios.nombreUsuario, @psswd) using utf8) as Usuario, CONVERT(aes_decrypt(Usuarios.contrasenaUsuario, @psswd) using utf8) as Contraseña, tipoUsuario from Usuarios
WHERE  nombreUsuario = AES_ENCRYPT('angelin', @psswd);

select * FROM Usuarios
WHERE  nombreUsuario = AES_ENCRYPT('leo.hdz', @psswd) and contrasenaUsuario = AES_ENCRYPT('123', @psswd);
        
select count(*) > 0 as match_found FROM Usuarios
WHERE  nombreUsuario = AES_ENCRYPT('leo.hdz', 'B893EEA') and contrasenaUsuario = AES_ENCRYPT('123', 'B893EEA');


-- Eliminar Procediniento almacenado si existe
DROP PROCEDURE IF EXISTS `validarUsuario`;
-- Procedimeinto almacenado
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `validarUsuario`(in usr VARCHAR(12),in pass VARCHAR(12))
BEGIN
	DECLARE llave VARCHAR(10);
	SET llave = 'B893EEA';
	SELECT count(*) > 0 AS match_found FROM Usuarios
	WHERE  nombreUsuario = AES_ENCRYPT(usr, llave) 
	AND contrasenaUsuario = AES_ENCRYPT(pass, llave);
END$$
DELIMITER ;

-- Llamamos al procedimiento
CALL validarUsuario('angelin','perro123');


-- Cambiar Contraseñas
UPDATE Usuarios SET contrasenaUsuario = AES_ENCRYPT('josh', 'B893EEA') WHERE nombreUsuario = AES_ENCRYPT('leo.hdz', 'B893EEA');
