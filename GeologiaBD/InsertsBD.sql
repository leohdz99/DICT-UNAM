INSERT INTO Asignatura
VALUES(1, 1676, 'Cartografia Geologica',6.00); 
INSERT INTO Asignatura
VALUES(2, 1870, 'Estancias Profesionales',4.50);
INSERT INTO Asignatura
VALUES(3, 1215, 'Fundamentos de Geologia', 6.00);
INSERT INTO Asignatura
VALUES(4, 1431, 'Geodinamica Interna',3.00);
INSERT INTO Asignatura
VALUES(5, 312, 'Geologia Aplicada a la Ingeniria Civil',4.50);
INSERT INTO Asignatura
VALUES(6, 1674, 'Geologia de Campo',4.50);
INSERT INTO Asignatura
VALUES(7, 252, 'Geologia Estructural',4.50);
INSERT INTO Asignatura
VALUES(8, 1327, 'Geologia Fisica',9.00); 
INSERT INTO Asignatura
VALUES(9, 318, 'Geologia General',4.50); 
INSERT INTO Asignatura
VALUES(10, 424, 'Geologia General (2016)',5.00);

INSERT INTO asignaturaxhorario
VALUES(1, 1, '2017-2');
INSERT INTO asignaturaxhorario
VALUES(2, 2, '2017-2');
INSERT INTO asignaturaxhorario
VALUES(3, 3, '2017-2');
INSERT INTO asignaturaxhorario
VALUES(5, 4, '2017-2');
INSERT INTO asignaturaxhorario
VALUES(6, 6, '2017-2');
INSERT INTO asignaturaxhorario
VALUES(8, 5, '2017-2');

INSERT INTO horario
VALUES(1, '1', 1,1);
INSERT INTO horario
VALUES(2, '3', 4,11);
INSERT INTO horario
VALUES(3, '1', 2,4);
INSERT INTO horario
VALUES(4, '2', 10,2);
INSERT INTO horario
VALUES(5, '3', 13,3);
INSERT INTO horario
VALUES(6, '1', 8,5);
INSERT INTO horario
VALUES(7, '1', 9,7);
INSERT INTO horario
VALUES(8, '2', 12,8);
INSERT INTO horario
VALUES(9, '3', 6,9);
INSERT INTO horario
VALUES(10, '2', 10,10);


INSERT INTO horarioreal
VALUES(1, 7.00, 9.00,135);
INSERT INTO horarioreal
VALUES(2, 11.00, 12.50,24);
INSERT INTO horarioreal
VALUES(3, 9.00, 11.00,135);
INSERT INTO horarioreal
VALUES(4, 13.00, 14.00,24);
INSERT INTO horarioreal
VALUES(5, 11.00, 13.00,1236);
INSERT INTO horarioreal
VALUES(6, 15.00, 16.50,24);

INSERT INTO horariocomp
VALUES(2, 11.00, 12.50,6);
INSERT INTO horariocomp
VALUES(6, 15.00, 16.50,6);



(select * from horario 
	left outer join 
	horarioReal on horario.idHorario = horarioReal.idHorario)
    union
	(select * from horario 
    left outer join 
    horarioComp on horario.idHorario = horarioComp.idHorario
	order by grupo);


select horario.idHorario, claveAsig as clave, nombreAsig as asignatura, grupo, salon,  Profesor.folio as folio,
       concat(Profesor.titulo,' ',Profesor.nombre,' ',Profesor.apPatern,' ',Profesor.apMatern) as profesor,
       concat(horaEntrada,' - ',horaSalida) as horario, dias
from asignaturaxhorario
	left outer join 
	asignatura on asignaturaxhorario.idAsignatura = asignatura.idAsignatura
    left outer join
    horario on asignaturaxhorario.idHorario = horario.idHorario
	left outer join
    profesor on profesor.idProfesor = horario.idHorario
    left outer join
    salon on salon.idSalon = horario.idSalon
	left outer join
    horarioreal on horario.idHorario = horarioreal.idHorario
    where dias like '%%' 
union
select horario.idHorario, claveAsig as clave, nombreAsig as asignatura, grupo, salon,  Profesor.folio as folio,
       concat(Profesor.titulo,' ',Profesor.nombre,' ',Profesor.apPatern,' ',Profesor.apMatern) as profesor,
       concat(horaEntrada,' - ',horaSalida) as horario, dias
from asignaturaxhorario
	left outer join 
	asignatura on asignaturaxhorario.idAsignatura = asignatura.idAsignatura
    left outer join
    horario on asignaturaxhorario.idHorario = horario.idHorario
	left outer join
    profesor on profesor.idProfesor = horario.idHorario
    left outer join
    salon on salon.idSalon = horario.idSalon
	right outer join
    horariocomp on horario.idHorario = horariocomp.idHorario
where dias like '%%' /*para Buscar Dias repetidos, hay que verificar la parte de los traslapes*/
order by asignatura;

    
    
    
    
    
    
    