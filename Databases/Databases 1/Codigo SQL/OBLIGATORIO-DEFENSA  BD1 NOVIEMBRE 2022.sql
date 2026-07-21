/*
DEFENSA OBLIGATORIO:
--------------------------------------------------------------------------------------------------
NUMERO DE ESTUDIANTE  1 : 299328
NOMBRE DE ESSTUDIANTE 1 : Rodrigo Delgado

NUMERO DE ESTUDIANTE  2 : 299685
NOMBRE DE ESSTUDIANTE 2 : Agustin Onetti

NUMERO DE ESTUDIANTE  3:
NOMBRE DE ESSTUDIANTE 3 :
---------------------------------------------------------------------------------------------------
*/

/*
CREACION DE LA BASE DE DATOS UTILIZANDO EL SIGUIENTE ESQUEMA RELACIONAL
---------------------------------------------------------------------------------------------------
BARRIO(codBarrio, nomBarrio)
ESTACION(codEst, dscEst, codBarrio, TipoEst )
LINEA(numLinea, dscLinea, colorLinea, longLinea codEstOrigen, codEstDestino)
MOLINETE(codEst, numMol, acumMol, fchMol)
TREN(numTren, dscTren, letraTren)
VAGON(numTren, numVagon, capVagon)
ES-VECINO(codBarrio1, codBarrio2)
LIN_EST(numLinea, codEst)
PASA_POR(numTren, numLinea, codEst, fechahora_pasa)
---------------------------------------------------------------------------------------------------
*/


CREATE DATABASE OBLBD1
GO
USE OBLBD1
GO
CREATE TABLE BARRIO(codBarrio numeric(6), 
                    nomBarrio varchar(30),
					CONSTRAINT PK_Barrio PRIMARY KEY(codBarrio))
GO
CREATE TABLE ES_VECINO(codBarrio1 numeric(6),
                       codBarrio2 numeric(6),
					   CONSTRAINT PK_Vecino PRIMARY KEY(codBarrio1,codBarrio2),
					   CONSTRAINT FK_Barrio1 FOREIGN KEY(codBarrio1) REFERENCES Barrio(codBarrio),
					   CONSTRAINT FK_Barrio2 FOREIGN KEY(codBarrio2) REFERENCES Barrio(codBarrio))
GO
CREATE TABLE ESTACION(codEst numeric(6), 
                      dscEst varchar(30),
					  codBarrio numeric(6),
					  tipoEst character(1),
					  CONSTRAINT PK_Estacion PRIMARY KEY(codEst),
					  CONSTRAINT FK_BarrioEstacion FOREIGN KEY(codBarrio) REFERENCES Barrio(codBarrio))
GO
CREATE TABLE LINEA(numLinea numeric(6),
                   dscLinea varchar(30),
				   colorLinea varchar(20), 
				   longLinea numeric(12,2),
				   codEstOrigen numeric(6), 
				   codEstDestino numeric(6),
				   CONSTRAINT PK_Linea PRIMARY KEY(numLinea),
				   CONSTRAINT FK_Origen FOREIGN KEY(codEstOrigen) REFERENCES Estacion(codEst),
				   CONSTRAINT FK_Destino FOREIGN KEY(codEstDestino) REFERENCES Estacion(codEst))
GO
CREATE TABLE MOLINETE(codEst numeric(6), 
                      numMol numeric(3),
					  acumMol numeric(10),
					  fchMol datetime,
					  CONSTRAINT PK_Molinete PRIMARY KEY(codEst,numMol),
					  CONSTRAINT FK_EstMol FOREIGN KEY(codEst) REFERENCES Estacion(codEst))
GO
CREATE TABLE TREN(numTren numeric(6),
                  dscTren varchar(30),
				  letraTren char(1),
				  CONSTRAINT PK_Tren PRIMARY KEY(numTren))
GO
CREATE TABLE VAGON(numTren numeric(6),
                   numVagon numeric(2),
				   capVagon numeric(10),
				   CONSTRAINT PK_Vagon PRIMARY KEY(numTren,numVagon),
				   CONSTRAINT FK_TrenVagon FOREIGN KEY(numTren) REFERENCES Tren(numTren))
GO

CREATE TABLE LIN_EST(numLinea numeric(6), 
                     codEst numeric(6),
					 CONSTRAINT PK_Lin_Est PRIMARY KEY(numLinea,codEst),
					 CONSTRAINT FK_Linea_LinEst FOREIGN KEY(numLinea) REFERENCES Linea(numLinea),
					 CONSTRAINT FK_Estac_LinEst FOREIGN KEY(codEst) REFERENCES Estacion(codEst))
GO
CREATE TABLE PASA_POR(numTren numeric(6), 
                      numLinea numeric(6), 
					  codEst numeric(6), 
					  fechahora_pasa datetime,
					  CONSTRAINT PK_PasaPor PRIMARY KEY(numTren,numLinea,codEst,fechahora_pasa),
					  CONSTRAINT FK_TrenPasaPor FOREIGN KEY(numTren) REFERENCES Tren(numTren),
					  CONSTRAINT FK_LineaEstacion FOREIGN KEY(numLinea,codEst) REFERENCES LIN_EST(numLinea,codEst))
GO



SET DATEFORMAT DMY
USE OBLBD1
GO
INSERT INTO BARRIO VALUES(1,'Centenario'),
                         (2,'Constitucion'),
						 (3,'Artigas'),
						 (4,'Centro'),
						 (5,'Obrero'),
						 (6,'Playa'),
						 (7,'Norte'),
						 (8,'Sur'),
						 (9,'Este'),
						 (10,'Oeste'),
						 (20,'Suburbanos')
GO
INSERT INTO ES_VECINO VALUES(1,2),
                            (1,10),
							(2,3),
							(2,4),
							(2,9),
							(3,8),
							(4,7),
							(5,1),
							(6,3),
							(6,10),
							(6,8),
							(7,2),
							(7,5),
							(8,1),
							(8,2),
							(8,5),
							(8,6),
							(8,10),
							(9,6)
GO
INSERT INTO ESTACION VALUES(100,'Central',2,'U'),
                           (101,'Norte Rambla',6,'U'),
						   (102,'Villa Nueva',2,'U'),
						   (103,'Shopping Center',10,'U'),
						   (104,'Las Palmeras',1,'U'),
						   (105,'Los Ombues',1,'U'),
						   (106,'Puente Chico',5,'U'),
						   (107,'Campo Grande',3,'U'),
						   (108,'Intendencia',10,'U'),
						   (109,'Banco Republica',7,'U'),
						   (110,'BPS',10,'U'),
						   (200,'Pueblo Centenario',20,'S'),
						   (201,'Paso de los Toros',20,'S'),
						   (202,'Pasaje Carpincho',20,'S')
GO
INSERT INTO MOLINETE VALUES(100,1,20,'18/07/2022'),
                           (100,2,15,'20/07/2022'),
						   (100,3,50,'20/07/2022'),
						   (101,1,15,'16/06/2022'),
						   (101,2,50,'01/08/2022'),
						   (102,1,100,'26/07/2022'),
						   (103,1,14,'16/07/2022'),
						   (103,2,4,'16/07/2022'),
						   (103,3,11,'16/07/2022'),
						   (103,4,10,'16/07/2022'),
						   (103,5,19,'16/07/2022'),
						   (103,6,5,'16/07/2022'),
						   (103,7,2,'16/07/2022'),
						   (104,1,160,'20/07/2022'),
						   (104,2,6,'20/07/2022'),
						   (104,3,10,'20/07/2022'),
						   (104,4,70,'20/07/2022'),
						   (105,1,100,'30/07/2022'),
						   (106,1,2,'23/07/2022'),
						   (106,2,10,'16/07/2022'),
						   (107,1,100,'01/08/2022'),
						   (107,2,10,'01/08/2022'),
						   (107,3,100,'01/08/2022'),
						   (107,4,103,'01/08/2022'),
						   (107,5,110,'01/08/2022'),
						   (107,6,120,'01/08/2022'),
						   (107,7,10,'01/08/2022'),
						   (107,8,50,'01/08/2022'),
						   (107,9,48,'01/08/2022'),
						   (108,1,100,'01/08/2022'),
						   (108,2,129,'01/08/2022'),
						   (109,1,30,'29/07/2022'),
						   (109,2,30,'29/07/2022'),
						   (109,3,40,'29/07/2022'),
						   (109,4,10,'29/07/2022'),
						   (109,5,80,'29/07/2022'),
						   (109,6,90,'29/07/2022'),
						   (109,7,120,'29/07/2022'),
						   (109,8,110,'29/07/2022'),
						   (109,9,30,'29/07/2022'),
						   (109,10,35,'29/07/2022'),
						   (110,1,111,'02/08/2022'),
						   (110,2,60,'02/08/2022'),
						   (200,1,60,'02/08/2022'),
						   (201,1,60,'02/08/2022'),
						   (202,1,60,'02/08/2022')
GO
INSERT INTO LINEA VALUES(1,'Palermo','ROJO',8,100,110),
                        (2,'Circular','VERDE',15,105,110),
						(3,'Azul 3','AZUL',3,102,109),
						(4,'Naranja 4','NARANJA',6,100,104),
						(5,'Paseo Matriz','ROJO',20,107,110),
						(6,'Puntas de Sayago','NEGRO',11,107,101),
						(7,'Linea Corta Norte','VERDE',7,104,110),
						(8,'Aduana Vieja','VERDE',3,103,105),
						(9,'Puerto','ROJO',12,110,105),
						(10,'Transversal','AZUL',20,107,104),
						(20,'Ruta 5','AMARILLO',20,100,202),
						(21,'Camino Tropas','CELESTE',24,103,200)
GO
INSERT INTO LIN_EST VALUES(1,100),
						  (1,102),
                          (1,105),
						  (1,107),
						  (1,109),
						  (1,110),
						  (2,105),
						  (2,101),
						  (2,103),
						  (2,110),
						  (3,102),
						  (3,101),
						  (3,110),
						  (3,109),
						  (4,100),
						  (4,104),
						  (5,107),
						  (5,101),
						  (5,109),
						  (5,110),
						  (6,107),
						  (6,105),
						  (6,106),
						  (6,109),
						  (6,101),
						  (7,104),
						  (7,102),
						  (7,110),
						  (8,103),
						  (8,102),
						  (8,109),
						  (8,105),
						  (9,110),
						  (9,105),
						  (10,107),
						  (10,101),
						  (10,200),
						  (10,108),
						  (10,104)
GO
INSERT INTO TREN VALUES(1,'El Oriental','A'),
                       (2,'Rayo','C'),
					   (3,'Nube Blanca','B'),
					   (4,'El Tricolor','D'),
					   (5,'Expreso del Oriente','R'),
					   (6,'El Seis','M'),
					   (7,'Suertudo','X'),
					   (8,'Dominante','Q'),
					   (9,'Luna','Z'),
					   (10,'Estrella del Norte','K')
GO
INSERT INTO VAGON VALUES(1,1,30),
                        (1,2,30),
						(1,3,30),
						(1,4,30),
						(1,5,30),
						(1,6,30),
						(1,7,25),
						(2,1,40),
						(3,1,35),
						(3,2,35),
						(3,3,35),
						(3,4,35),
						(3,5,30),
						(4,1,40),
						(4,2,40),
						(4,3,40),
						(4,4,40),
						(4,5,40),
						(4,6,40),
						(4,7,40),
						(4,8,40),
						(4,9,40),
						(4,10,40),
						(5,1,38),
						(5,2,38),
						(5,3,38),
						(5,4,38),
						(6,1,36),
						(6,2,40),
						(6,3,40),
						(6,4,40),
						(6,5,40),
						(6,6,25),
						(7,1,40),
						(8,1,25),
						(9,1,40),
						(9,2,40),
						(10,1,25),
						(10,2,25),
						(10,3,25),
						(10,4,25),
						(10,5,25),
						(10,6,25),
						(10,7,25)
GO
SET DATEFORMAT DMY

INSERT INTO PASA_POR VALUES(1,10,101,'02/08/2021')
INSERT INTO PASA_POR VALUES(1,10,104,'01/08/2021')
INSERT INTO PASA_POR VALUES(1,10,200,'31/07/2021')
INSERT INTO PASA_POR VALUES(1,10,107,'30/07/2021')
INSERT INTO PASA_POR VALUES(1,10,108,'29/07/2021')
INSERT INTO PASA_POR VALUES(2,10,101,'02/08/2021')
INSERT INTO PASA_POR VALUES(2,10,104,'20/06/2021')
INSERT INTO PASA_POR VALUES(2,10,200,'24/06/2021')
INSERT INTO PASA_POR VALUES(3,10,107,'24/06/2021')
INSERT INTO PASA_POR VALUES(3,10,108,'25/06/2021')
INSERT INTO PASA_POR VALUES(10,1,102,'02/08/2021')
INSERT INTO PASA_POR VALUES(10,1,105,'01/08/2021')
INSERT INTO PASA_POR VALUES(10,1,107,'31/07/2021')
INSERT INTO PASA_POR VALUES(10,1,109,'30/07/2021')
INSERT INTO PASA_POR VALUES(1,10,101,'01/08/2022')
INSERT INTO PASA_POR VALUES(1,10,104,'31/07/2022')
INSERT INTO PASA_POR VALUES(1,10,107,'28/07/2022')
INSERT INTO PASA_POR VALUES(1,10,108,'27/07/2022')
INSERT INTO PASA_POR VALUES(2,10,101,'26/07/2022')
INSERT INTO PASA_POR VALUES(2,10,104,'24/07/2022')
INSERT INTO PASA_POR VALUES(3,10,107,'26/06/2022')
INSERT INTO PASA_POR VALUES(3,10,108,'01/08/2022')
INSERT INTO PASA_POR VALUES(10,1,102,'01/08/2022')
INSERT INTO PASA_POR VALUES(10,1,105,'01/08/2022')
INSERT INTO PASA_POR VALUES(10,1,107,'01/08/2022')
INSERT INTO PASA_POR VALUES(10,1,109,'01/08/2022')
INSERT INTO PASA_POR VALUES(8,3,102,'02/08/2021')
INSERT INTO PASA_POR VALUES(8,3,101,'01/08/2021')
INSERT INTO PASA_POR VALUES(8,3,109,'31/07/2021')
INSERT INTO PASA_POR VALUES(8,3,110,'30/07/2021')
INSERT INTO PASA_POR VALUES(8,3,109,'30/07/2022')
INSERT INTO PASA_POR VALUES(8,3,101,'28/07/2022')
INSERT INTO PASA_POR VALUES(8,3,102,'27/07/2022')
INSERT INTO PASA_POR VALUES(8,3,110,'26/07/2022')
INSERT INTO PASA_POR VALUES(1,9,110,'02/08/2022')
INSERT INTO PASA_POR VALUES(1,9,105,'02/08/2022')
INSERT INTO PASA_POR VALUES(5,5,107,'02/08/2022')
INSERT INTO PASA_POR VALUES(6,3,102,'02/08/2022')
INSERT INTO PASA_POR VALUES(6,3,101,'02/08/2022')
INSERT INTO PASA_POR VALUES(6,3,110,'02/08/2022')
INSERT INTO PASA_POR VALUES(8,9,105,'02/08/2021')
INSERT INTO PASA_POR VALUES(1,9,105,'02/08/2021')
INSERT INTO PASA_POR VALUES(4,9,105,'02/08/2022')
GO

--------------------------------------------------------------------------------------------------
/*
PRESENTAR LAS SOLUCIONES EN EL ESPACIO CORRESPONDIENTE
--------------------------------------------------------------------------------------------------
*/

/*
CONSULTA 1
1. Listar código de Barrio y descripción de Barrio para todos aquellos Barrios de Estaciones por donde
pasaron Trenes este año, filtrar resultados repetidos.
*/

select Distinct (b.codBarrio) codBarrio, nomBarrio
from (BARRIO b join ESTACION e on b.codBarrio = e.codBarrio join PASA_POR p on p.codEst = e.codEst)
where YEAR(fechahora_pasa) = YEAR(GETDATE())




/*
CONSULTA 2
2. Listar las 5 Estaciones por las que pasaron mas trenes el año pasado.
*/

SELECT TOP 5 p.codEst as Codigo, COUNT(p.fechahora_pasa) as Viajes 
FROM PASA_POR p, ESTACION e 
WHERE YEAR(p.fechahora_pasa) < YEAR(GETDATE()) 
GROUP BY (p.codEst) 
ORDER BY COUNT(p.fechahora_pasa) desc, p.codEst asc




/*
CONSULTA 3
3. Para cada Estación mostrar sus datos y el total acumulado de todos sus Molinetes.
*/

select distinct E.codEst, E.dscEst, E.tipoEst, sum(M.acumMol) as TotalAcumulado
from ESTACION E join MOLINETE M on E.codEst=M.codEst
where E.tipoEst='U'
group by E.codEst, E.dscEst, E.tipoEst





/*
CONSULTA 4
4. Para las Líneas que tengan más de 5 kmts de longitud y tengan como destino Estaciones del mismo
Barrio mostrar su número, descripción y nombre del Barrio, ordenar los resultados por número de
Línea.
*/

select numLinea,dscLinea, nomBarrio from LINEA L JOIN ESTACION E ON L.codEstOrigen=E.codEst
									  JOIN ESTACION D ON L.codEstDestino=D.codEst
									  JOIN BARRIO B ON D.codBarrio=B.codBarrio
WHERE L.longLinea>5 AND D.codEst!=E.codEst
ORDER BY numLinea




/*
CONSULTA 5
5. Mostrar para cada Tren sus datos, la cantidad de Vagones que tiene y su capacidad total de
pasajeros, solo debe mostrarse aquellos Trenes que tengan más de 1 Vagón (ordenar el resultado
por cantidad de vagones descendente).
*/

SELECT T.numTren,T.dscTren,T.letraTren,COUNT(V.numTren) AS Vagones,SUM(V.capVagon) AS TotalAcumulado
FROM TREN T
JOIN VAGON V ON T.numTren=V.numTren
GROUP BY T.numTren,T.dscTren,T.letraTren
HAVING COUNT(V.numTren)>1
ORDER BY Vagones desc



/*
CONSULTA 6
6. Mostrar los datos de las Estaciones por donde aún no pasaron Trenes.
*/

SELECT E.codEst,  E.dscEst, E.codBarrio, E.tipoEst
FROM ESTACION E JOIN LIN_EST L ON E.codEst!=L.codEst
Where E.codEst NOT IN (SELECT codEst FROM PASA_POR)
GROUP BY E.codEst, E.dscEst, E.codBarrio, E.tipoEst	




/*
CONSULTA 7
7. Para las Líneas de color ROJO, mostrar sus datos y los datos de las Estaciones Origen y Destino.
*/


SELECT numLinea,dscLinea,colorLinea,longLinea, codEstOrigen,codEstDestino,E.dscEst AS ORIGEN, D.dscEst AS DESTINO
FROM LINEA L JOIN ESTACION E ON L.codEstOrigen=E.codEst
					  JOIN ESTACION D ON L.codEstDestino=D.codEst
					  WHERE colorLinea='ROJO'



/*
CONSULTA 8
8. Mostrar número y descripción de Línea, código y descripción de Estación, número y descripción de
Tren para todos los trenes que pasaron por esas estaciones y líneas en el año corriente.
*/

SELECT L.numLinea,L.dscLinea,E.codEst,E.dscEst,T.numTren,T.dscTren
FROM LINEA L JOIN ESTACION E ON L.codEstOrigen=E.codEst
             JOIN ESTACION D ON L.codEstDestino=D.codEst
             JOIN PASA_POR P ON L.numLinea=P.numLinea AND YEAR(P.fechahora_pasa)=YEAR(GETDATE())
             JOIN TREN T ON P.numTren=T.numTren
ORDER BY numTren



/*
CONSULTA 9
9. Mostrar la cantidad de líneas Urbanas diferentes que tienen Estaciones donde el nombre de sus
Barrios comienzan con la letra “C”
*/

select COUNT(distinct numLinea) AS CantLineas
from LIN_EST L, ESTACION E, BARRIO B
where e.codEst=L.codEst AND E.codBarrio=B.codBarrio AND E.tipoEst ='U' AND    B.nomBarrio LIKE 'C%'






/*------------------------------------------------------------------------------------------------
--------------------------------------------------------------------------------------------------
RESOLVER LA SIGUIENTE CONSULTA AGREGADA>
--------------------------------------------------------------------------------------------------
*/------------------------------------------------------------------------------------------------




/*
LISTAR EL NUMERO DE LINEA Y LA CANTIDAD DE ESTACIONES PARA LAS LINEAS 
QUE PASAN POR LA MAYOR CANTIDAD DE ESTACIONES DISTINTAS
*/

select distinct numLinea, COUNT(distinct codEst) AS CantidadEstaciones from PASA_POR 
group by numLinea
having COUNT(codEst)>= all (select COUNT(codEst) from PASA_POR
group by numLinea)


--MOSTRAR LOS NUMEROS DE LINEA Y LA CANTIDAD DE ESTACIONES DISTINTAS POR LAS QUE PASAN QUITANDO LAS 
--LINEAS QUE PASAN POR LA MAYOR CANTIDAD DE ESTACIONES DISTINTAS

select distinct numLinea, COUNT(distinct codEst) AS CantidadEstaciones from PASA_POR 
group by numLinea
having not COUNT(codEst) >= all (select COUNT(codEst) from PASA_POR
group by numLinea)