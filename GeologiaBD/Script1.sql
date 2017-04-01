use DICT;

Insert into horario values (1,1);
insert into horario values (2,1);

Select * 
from horario;

Select * 
from horarioReal;

Select * 
from horarioComp;

Insert into horarioReal values (1, 12.00, 14.99, 135);
Insert into horarioComp values (1, 10.00, 13.99, 6);
Insert into horarioReal values (2, 10.00, 14.99, 24);


select * from horario 
left outer join horarioReal on horario.idHorario = horarioReal.idHorario
left outer join horarioComp on horario.idHorario = horarioComp.idHorario;