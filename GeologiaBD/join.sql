SELECT  salon.salon as Salon, 
salon.cupo as Cupo, salon.vacante as Vacante, horario.grupo,
		Profesor.folio as FolioProfesor,
        concat(Profesor.titulo,' ',Profesor.nombre,' ',Profesor.apPatern,' ',Profesor.apMatern) as Profesor,
        asignatura.nombreAsig AS Asignatura
                                FROM asignaturaxhorario
                                inner join horario ON horario.idHorario = horarioreal.idHorario
                                right OUTER JOIN horario ON  asignaturaxhorario.idHorario = horario.idHorario
								LEFT OUTER join profesor ON horario.idProfesor = profesor.idProfesor
								left outer join salon ON horario.idSalon = salon.idSalon
                                left outer join asignatura on asignaturaxhorario.idAsignatura = asignatura.idAsignatura
								
             
             
             
             union             
SELECT  concat(horarioreal.horaEntrada, ' - ',horarioreal.horaSalida) AS Horario, horarioreal.dias AS Dias,
		 AS Grupo, asignatura. claveAsig AS Clave, asignatura.nombreAsig AS Asignatura 
							FROM horarioreal 
                            INNER JOIN horario ON horario.idHorario = horarioreal.idHorario
                            left outer join horario ON horario.idHorario = asignaturaxhorario.idHorario
                            left outer join asignaturaxhorario ON asignaturaxhorario.idAsignatura = asignatura.idAsignatura
                            
                               
