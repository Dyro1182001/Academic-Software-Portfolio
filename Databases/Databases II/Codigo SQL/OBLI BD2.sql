Create DataBase ObligatorioBD
go

go
SET DATEFORMAT DMY
go

go
use ObligatorioBD
go


--Creación de la tabla Locacion
CREATE TABLE Locacion (
	locID INT NOT NULL,
	pais VARCHAR(100) NOT NULL,
	ciudad VARCHAR(100)
);

--Creación de la tabla Torneo
CREATE TABLE Torneo (
    torneoID INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    fecha DATE,
	locID INT,
    tipo VARCHAR(50),
    estado VARCHAR(50),
	Nivel INT
);

--Creación de la tabla Participante
CREATE TABLE Participante (
    participanteID INT NOT NULL,
    nombre VARCHAR(100),
    locID INT,
    fecha_nacimiento DATE,
    experiencia INT  
);

--Creación de la tabla Inscripcion
CREATE TABLE Inscripcion (
    inscripcionID INT IDENTITY,
    torneoID INT,
    participanteID INT,
	posicion INT,
);

--Creación de la tabla auxiliar puesto_puntos (asumiendo dos columnas, puesto y puntos)
CREATE TABLE puesto_puntos (
    puesto INT,
    puntos INT
);

--Creación de la tabla auditoria
CREATE TABLE AuditInscripcion(AuditID int identity not null,
     AuditFecha datetime,
     AuditHost varchar(30),
     posAnterior decimal,
     posActual decimal)



--TABLA LOCACION

GO
ALTER TABLE Locacion
ADD CONSTRAINT LocacionClave PRIMARY KEY (LocID) 
GO

ALTER TABLE Locacion
ADD UNIQUE (pais, ciudad)



--TABLA TORNEO

GO
ALTER TABLE Torneo DROP COLUMN torneoID 
ALTER TABLE Torneo ADD torneoID int IDENTITY (1,1) NOT NULL
GO

ALTER TABLE Torneo
ADD CHECK (nombre NOT LIKE '%[^A-Za-z]%')

ALTER TABLE Torneo
ADD CHECK (year(fecha) > 2016)

GO
ALTER TABLE Torneo 
ADD CONSTRAINT TorneoClave PRIMARY KEY (torneoID)
GO

GO
ALTER TABLE Torneo
ADD CONSTRAINT TorneoLocacion FOREIGN KEY (locID) REFERENCES Locacion (locID) 
GO

ALTER TABLE Torneo 
ADD CHECK (tipo in ('freestyle', 'Kite-Surf', 'Parkstyle', 'Racing'))

ALTER TABLE Torneo 
ADD CHECK (estado in ('planificado', 'en competencia', 'finalizado', 'cancelado'))

ALTER TABLE Torneo
ADD CHECK (nivel in ('1','2','3','4','5','6','7','8','9', '10'))



--TABLA PARTICIPANTE

GO
ALTER TABLE Participante DROP COLUMN participanteID 
ALTER TABLE Participante ADD participanteID int IDENTITY (1,1) NOT NULL
GO

GO
ALTER TABLE Participante
ADD CONSTRAINT ParticipanteClave PRIMARY KEY (participanteID)
GO

ALTER TABLE Participante
ADD UNIQUE (nombre)

GO
ALTER TABLE Participante
ADD CONSTRAINT ParticipanteLocacion FOREIGN KEY (locID) REFERENCES Locacion (locID) 
GO

GO
ALTER TABLE Participante
ADD edad int NOT NULL
GO

ALTER TABLE Participante
ADD CHECK (edad >= 18)

ALTER TABLE Participante
ADD CHECK (experiencia in ('0','1','2','3','4','5','6','7','8','9', '10'))

GO
ALTER TABLE Participante
ADD PuntosAcumulados INT DEFAULT 0; 
GO


GO
ALTER TABLE Participante
ADD UltimaFechaActualizado DATETIME;

GO



--TABLA INSCRIPCION

GO
ALTER TABLE Inscripcion
ADD PRIMARY KEY(inscripcionID)
GO

ALTER TABLE Inscripcion
ADD CONSTRAINT RangoPosicion CHECK (posicion BETWEEN 1 AND 14);

GO
ALTER TABLE Inscripcion
ADD CONSTRAINT InscripcionTorneo FOREIGN KEY (torneoID) REFERENCES Torneo(torneoID);
GO

GO
ALTER TABLE Inscripcion
ADD CONSTRAINT InscripcionParticipante FOREIGN KEY (participanteID) REFERENCES Participante(participanteID);
GO



-- TABLA PUESTO_PUNTOS

ALTER TABLE puesto_puntos
ALTER COLUMN puesto int NOT NULL

ALTER TABLE puesto_puntos
ALTER COLUMN puntos int NOT NULL


GO
ALTER TABLE puesto_puntos
ADD CONSTRAINT puesto_puntosClave PRIMARY KEY (puesto, puntos);
GO

ALTER TABLE puesto_puntos
ADD CONSTRAINT PuestoRango CHECK (puesto >= 0);

ALTER TABLE puesto_puntos
ADD CONSTRAINT PuntosRango CHECK (puntos >= 0);



-- TABLA AUDITINSCRIPCION

GO
ALTER TABLE AuditInscripcion
ADD CONSTRAINT AuditInscripcionClave PRIMARY KEY (AuditID);
GO

ALTER TABLE AuditInscripcion
ADD CONSTRAINT PosicionAnteriorRango CHECK (posAnterior >= 0);

ALTER TABLE AuditInscripcion
ADD CONSTRAINT PosicionActualRango CHECK (posActual >= 0);



-- 1. INDICES

CREATE INDEX FechaTorneo ON Torneo(fecha);

CREATE INDEX LocacionTorneo ON Torneo(locID);

CREATE INDEX LocacionParticipante ON Participante(locID);

CREATE INDEX TorneoInscripcion ON Inscripcion(torneoID);

CREATE INDEX ParticipanteInscripcion ON Inscripcion(participanteID);

CREATE INDEX ParticipantePuntosAcumulados ON Participante(PuntosAcumulados);

CREATE INDEX ParticipanteExperiencia ON Participante(experiencia);

CREATE INDEX TorneoNivel ON Torneo(Nivel);

CREATE INDEX TorneoEstado ON Torneo(estado);

CREATE INDEX TorneoTipo ON Torneo(tipo);

CREATE INDEX PuestoPuntosPuntos ON puesto_puntos(puntos);

CREATE INDEX LocacionPais ON Locacion(pais);



-- TRIGGERS 


-- TRIGGER 5.A

GO

CREATE OR ALTER TRIGGER TriggerMantenimientoPuntos ON Inscripcion AFTER INSERT
AS
BEGIN
    DECLARE @YearActual INT;
    SET @YearActual = YEAR(GETDATE());

    UPDATE P
    SET P.PuntosAcumulados = P.PuntosAcumulados + PP.puntos,
        P.UltimaFechaActualizado = GETDATE()
    FROM Participante P
    INNER JOIN Inserted I ON P.participanteID = I.participanteID
    INNER JOIN puesto_puntos PP ON I.posicion = PP.puesto
    INNER JOIN Torneo T ON I.torneoID = T.torneoID 
    WHERE YEAR(T.fecha) = @YearActual;

    UPDATE P
    SET P.PuntosAcumulados = 0,
        P.UltimaFechaActualizado = GETDATE()
    FROM Participante P
    WHERE P.UltimaFechaActualizado IS NULL OR YEAR(P.UltimaFechaActualizado) != @YearActual;

END;

GO



-- TRIGGER 5.B

GO

CREATE OR ALTER TRIGGER TriggerAuditTorneo
ON Inscripcion
AFTER UPDATE
AS
BEGIN
    DECLARE @FechaActual DATETIME;
    SET @FechaActual = GETDATE();

    INSERT INTO AuditInscripcion(AuditFecha, AuditHost, posAnterior, posActual)
    SELECT 
        @FechaActual AS AuditFecha,
        HOST_NAME() AS AuditHost,
        CASE 
            WHEN UPDATE(posicion) THEN deleted.posicion
            ELSE NULL
        END AS posAnterior,
        CASE 
            WHEN UPDATE(posicion) THEN inserted.posicion
            ELSE NULL
        END AS posActual
    FROM inserted
    INNER JOIN deleted ON inserted.inscripcionID = deleted.inscripcionID;
END;

GO


-- TRIGGER 5.C

GO

CREATE OR ALTER TRIGGER TriggerValidarNivelKiter
ON Inscripcion
AFTER INSERT
AS
BEGIN
    DECLARE @FechaActual DATETIME;
    SET @FechaActual = GETDATE();

    CREATE TABLE #KitersNoCumplen (
        participanteID INT,
        torneoID INT
    );

    INSERT INTO #KitersNoCumplen (participanteID, torneoID)
    SELECT 
        I.participanteID,
        I.torneoID
    FROM Inserted I
    INNER JOIN Participante P ON I.participanteID = P.participanteID
    INNER JOIN Torneo T ON I.torneoID = T.torneoID
    WHERE P.experiencia < T.Nivel;

    IF EXISTS (SELECT 1 FROM #KitersNoCumplen)
    BEGIN
        RAISERROR('Algunos kiters no cumplen con el nivel del torneo. Lista de kiters no inscritos:', 16, 1);
		ROLLBACK; -- metimos el rollback
        
        SELECT 
            KNC.participanteID,
            KNC.torneoID
        FROM #KitersNoCumplen KNC;
    END

    DROP TABLE #KitersNoCumplen;
END;

GO



--2. JUEGO DE PRUEBAS

-- Locacion

INSERT INTO Locacion(locID, pais, ciudad) VALUES (1, 'Uruguay', 'Montevideo');

INSERT INTO Locacion(locID, pais, ciudad) VALUES (2, 'Argentina', 'Buenos Aires');

INSERT INTO Locacion(locID, pais, ciudad) VALUES (3, 'Chile', 'Santiago de Chile');

INSERT INTO Locacion(locID, pais, ciudad) VALUES (4, 'Brasil', 'Brasilia');

INSERT INTO Locacion(locID, pais, ciudad) VALUES (5, 'Colombia', 'Bogotá');



-- TORNEO

INSERT INTO Torneo(nombre, fecha, locID, tipo, estado, Nivel) VALUES ('TorneoDeFreestyle','27-12-2021' , 1, 'freestyle', 'planificado', 5);

INSERT INTO Torneo(nombre, fecha, locID, tipo, estado, Nivel) VALUES ('TorneoDeKiteSurf', '12-11-2022', 2, 'Kite-Surf', 'en competencia', 7);

INSERT INTO Torneo(nombre, fecha, locID, tipo, estado, Nivel) VALUES ('TorneoDeKiteSurf', '11-06-2022', 2, 'Kite-Surf', 'en competencia', 5);

INSERT INTO Torneo(nombre, fecha, locID, tipo, estado, Nivel) VALUES ('TorneoDeKiteSurf', '17-07-2022', 2, 'Kite-Surf', 'finalizado', 5);

INSERT INTO Torneo(nombre, fecha, locID, tipo, estado, Nivel) VALUES ('TorneoDeParkstyle', '31-12-2021', 2, 'Parkstyle', 'finalizado', 10);

INSERT INTO Torneo(nombre, fecha, locID, tipo, estado, Nivel) VALUES ('TorneoDeRacing', '27-2-2020', 2, 'Racing', 'cancelado', 3);

INSERT INTO Torneo(nombre, fecha, locID, tipo, estado, Nivel)VALUES ('TorneoDeFreestyle', '19-05-2022', 1, 'freestyle', 'finalizado', 5);



-- PARTICIPANTES

INSERT INTO Participante(nombre, locID, fecha_nacimiento, experiencia, edad) VALUES ('Tomás Alcarráz', 1, '05-07-2002', 5, 21);

INSERT INTO Participante(nombre, locID, fecha_nacimiento, experiencia, edad) VALUES ('Rodrigo Delgado', 1, '11-08-2001', 8, 22);

INSERT INTO Participante(nombre, locID, fecha_nacimiento, experiencia, edad) VALUES ('David Martinez', 5, '23-03-2005', 10, 18);

INSERT INTO Participante(nombre, locID, fecha_nacimiento, experiencia, edad) VALUES ('Alphonse Elric', 4, '13-12-1999', 3, 24);

INSERT INTO Participante(nombre, locID, fecha_nacimiento, experiencia, edad) VALUES ('Jorge Joestar', 3, '02-01-2002', 7, 23);

INSERT INTO Participante(nombre, locID, fecha_nacimiento, experiencia, edad) VALUES ('Johnny Manoplata', 3, '11-02-1990', 10, 30);

INSERT INTO Participante (nombre, locID, fecha_nacimiento, experiencia, edad) VALUES ('Michael Jackson', 1, '01-04-1990', 10, 30);

INSERT INTO Participante (nombre, locID, fecha_nacimiento, experiencia, edad) VALUES ('Miguel Buena Fuente', 2, '12-02-1985', 10, 36);

INSERT INTO Participante (nombre, locID, fecha_nacimiento, experiencia, edad) VALUES ('Rodolfo McGyver', 3, '09-04-1995', 10, 26);

INSERT INTO Participante (nombre, locID, fecha_nacimiento, experiencia, edad) VALUES ('Antonio Abanderado', 4, '04-03-1988', 10, 33);

INSERT INTO Participante (nombre, locID, fecha_nacimiento, experiencia, edad) VALUES ('Graciela Armstrong', 5, '07-06-1992', 10, 29);



-- PUESTO PUNTOS

INSERT INTO puesto_puntos(puesto, puntos) VALUES (1, 1000);

INSERT INTO puesto_puntos(puesto, puntos) VALUES (2, 870);

INSERT INTO puesto_puntos(puesto, puntos) VALUES (3, 770);

INSERT INTO puesto_puntos(puesto, puntos) VALUES (4, 700);

INSERT INTO puesto_puntos(puesto, puntos) VALUES (5, 580);

INSERT INTO puesto_puntos(puesto, puntos) VALUES (6, 420);

INSERT INTO puesto_puntos(puesto, puntos) VALUES (7, 420);

INSERT INTO puesto_puntos(puesto, puntos) VALUES (8, 420);

INSERT INTO puesto_puntos(puesto, puntos) VALUES (9, 420);

INSERT INTO puesto_puntos(puesto, puntos) VALUES (10, 280);

INSERT INTO puesto_puntos(puesto, puntos) VALUES (11, 280);

INSERT INTO puesto_puntos(puesto, puntos) VALUES (12, 280);

INSERT INTO puesto_puntos(puesto, puntos) VALUES (13, 280);

INSERT INTO puesto_puntos(puesto, puntos) VALUES (14, 245);



-- INSCRIPCION

INSERT INTO Inscripcion(torneoID, participanteID, posicion) VALUES (4, 2, 12);

INSERT INTO Inscripcion(torneoID, participanteID, posicion) VALUES (4, 3, 5);

INSERT INTO Inscripcion(torneoID, participanteID, posicion) VALUES (3, 5, 10);

INSERT INTO Inscripcion(torneoID, participanteID, posicion) VALUES (1, 1, 3);

INSERT INTO Inscripcion(torneoID, participanteID, posicion) VALUES (2, 2, 6);

INSERT INTO Inscripcion(torneoID, participanteID, posicion) VALUES (3, 3, 9);

INSERT INTO Inscripcion (torneoID, participanteID, posicion) VALUES (2, 8, 2);

INSERT INTO Inscripcion (torneoID, participanteID, posicion) VALUES (1, 8, 1);

INSERT INTO Inscripcion (torneoID, participanteID, posicion) VALUES (6, 7, 1);

INSERT INTO Inscripcion (torneoID, participanteID, posicion) VALUES (2, 9, 3);

INSERT INTO Inscripcion (torneoID, participanteID, posicion) VALUES (3, 7, 2);

INSERT INTO Inscripcion (torneoID, participanteID, posicion) VALUES (1, 7, 2);

INSERT INTO Inscripcion (torneoID, participanteID, posicion) VALUES (3, 9, 1);

INSERT INTO Inscripcion (torneoID, participanteID, posicion) VALUES (6, 8, 1);

INSERT INTO Inscripcion (torneoID, participanteID, posicion) VALUES (4, 1, 1);

INSERT INTO Inscripcion (torneoID, participanteID, posicion) VALUES (4, 2, 2);

INSERT INTO Inscripcion (torneoID, participanteID, posicion) VALUES (4, 3, 3);

INSERT INTO Inscripcion (torneoID, participanteID, posicion) VALUES (1, 9, 3);

INSERT INTO Inscripcion (torneoID, participanteID, posicion) VALUES (2, 9, 1);

INSERT INTO Inscripcion (torneoID, participanteID, posicion) VALUES (3, 9, 1);



-- AUDITINSCRIPCION

INSERT INTO AuditInscripcion(AuditFecha, AuditHost, posAnterior, posActual) VALUES ('04-11-2023', 'Primer Host', 1,2);

INSERT INTO AuditInscripcion(AuditFecha, AuditHost, posAnterior, posActual) VALUES ('12-08-2023', 'Segundo Host', 2,4);

INSERT INTO AuditInscripcion(AuditFecha, AuditHost, posAnterior, posActual) VALUES ('05-04-2023', 'Tercer Host', 4,6);

INSERT INTO AuditInscripcion(AuditFecha, AuditHost, posAnterior, posActual) VALUES ('10-03-2023', 'Cuarto Host', 6,8);

INSERT INTO AuditInscripcion(AuditFecha, AuditHost, posAnterior, posActual) VALUES ('11-01-2023', 'Quinto Host', 8,10);



-- 3. CONSULTAS 


-- CONSULTA A

SELECT P.nombre AS NombreParticipante, T.tipo AS TipoTorneo, PP.puntos AS Puntaje
FROM Participante P
INNER JOIN Inscripcion I ON P.participanteID = I.participanteID
INNER JOIN puesto_puntos PP ON I.posicion = PP.puesto
INNER JOIN Torneo T ON I.torneoID = T.torneoID
WHERE YEAR(T.fecha) = 2021;


-- CONSULTA B

SELECT
    P.nombre AS NombreParticipante,
    T.tipo AS TipoTorneo,
    MAX(PP.puntos) AS MayorPuntaje,
    AVG(PP.puntos) AS PuntajePromedio,
    MIN(PP.puntos) AS MenorPuntaje
FROM Participante P
INNER JOIN Inscripcion I ON P.participanteID = I.participanteID
INNER JOIN Torneo T ON I.torneoID = T.torneoID
INNER JOIN puesto_puntos PP ON I.posicion = PP.puesto
GROUP BY P.nombre, T.tipo;


-- CONSULTA C

SELECT
    T.nombre AS NombreTorneo,
    L.pais AS Pais,
    L.ciudad AS Ciudad,
    T.Nivel,
    COUNT(I.participanteID) AS CantidadParticipantes
FROM Torneo AS T
LEFT JOIN Inscripcion AS I ON T.torneoID = I.torneoID
INNER JOIN Locacion AS L ON T.locID = L.locID
WHERE T.estado = 'finalizado' OR T.estado IN ('planificado', 'en competencia')
GROUP BY T.nombre, L.pais, L.ciudad, T.Nivel;


-- CONSULTA D


SELECT P.nombre AS NombreParticipante
FROM Participante AS P
WHERE P.participanteID NOT IN (
    SELECT I.participanteID
    FROM Inscripcion AS I
    INNER JOIN Torneo AS T ON I.torneoID = T.torneoID
    WHERE YEAR(T.fecha) >= YEAR(GETDATE()) - 2 AND T.tipo = 'Racing'
);



-- CONSULTA E

	SELECT P.nombre AS Kiter, P.experiencia, SUM(PP.puntos) AS PuntosAcumulados, COUNT(T.torneoID) AS TorneosInscritos
	FROM Participante P
	JOIN Inscripcion I ON P.participanteID = I.participanteID
	JOIN Torneo T ON I.torneoID = T.torneoID
	JOIN puesto_puntos PP ON I.posicion = PP.puesto
	WHERE (T.tipo = 'freestyle' OR T.tipo = 'Kite-Surf')
	GROUP BY P.nombre, P.experiencia
	HAVING COUNT(T.torneoID) >= 3  AND SUM(PP.puntos) > 1200;
    

-- CONSULTAS F

SELECT TOP 1 T.nombre AS NombreTorneo, L.pais AS País, L.ciudad AS Ciudad, T.Nivel AS Nivel, COUNT(IP.participanteID) AS CantidadInscripciones
FROM Torneo T
LEFT JOIN Inscripcion IP ON T.torneoID = IP.torneoID
INNER JOIN Locacion L ON T.locID = L.locID
WHERE T.estado = 'finalizado'
AND T.fecha >= DATEADD(YEAR, -5, GETDATE())
AND L.pais != 'Brasil'
GROUP BY T.nombre, L.pais, L.ciudad, T.Nivel
HAVING SUM(CASE WHEN L.pais = 'Brasil' THEN 1 ELSE 0 END) = 0
ORDER BY CantidadInscripciones DESC;



-- T-SQL


-- 1

GO

CREATE OR ALTER PROCEDURE Procedure1(
    @FechaInicio DATE,
    @FechaFin DATE,
    @TipoTorneo VARCHAR(50),
    @GanadorNombre VARCHAR(100) OUTPUT
)
AS
BEGIN
    SELECT TOP 1 @GanadorNombre = P.nombre
    FROM Participante P
    INNER JOIN Inscripcion I ON P.participanteID = I.participanteID
    INNER JOIN Torneo T ON I.torneoID = T.torneoID
    WHERE T.fecha BETWEEN @FechaInicio AND @FechaFin
    AND T.tipo = @TipoTorneo
    ORDER BY I.posicion ASC;
END 

GO

DECLARE @Ganador VARCHAR(100);
EXEC Procedure1 '2020-01-01', '2023-12-31', 'freestyle', @Ganador OUTPUT;
SELECT @Ganador AS NombreGanador;



-- 2

GO

CREATE OR ALTER PROCEDURE Procedure2
    @TorneoID INT
AS
BEGIN
    CREATE TABLE #KitersOtrasCiudades (NombreKiter VARCHAR(100));

    IF EXISTS (
        SELECT 1
        FROM Torneo
        WHERE torneoID = @TorneoID
            AND estado = 'finalizado'
    )
    BEGIN

        INSERT INTO #KitersOtrasCiudades (NombreKiter)
        SELECT DISTINCT P.nombre
        FROM Inscripcion I
        INNER JOIN Participante P ON I.participanteID = P.participanteID
        INNER JOIN Torneo T ON I.torneoID = T.torneoID
        INNER JOIN Locacion L ON P.locID = L.locID
        WHERE T.torneoID = @TorneoID
            AND L.ciudad NOT IN (SELECT L.ciudad FROM Locacion L WHERE T.locID = L.locID);
    END
    SELECT * FROM #KitersOtrasCiudades;
END;

GO

DECLARE @KitersOtrasCiudades TABLE (NombreKiter VARCHAR(100));
DECLARE @TorneoID INT;
SET @TorneoID = 4; 
INSERT INTO @KitersOtrasCiudades
EXEC Procedure2 @TorneoID;
SELECT * FROM @KitersOtrasCiudades;



-- 3

GO

CREATE OR ALTER FUNCTION Function1
    (@TorneoID INT)
RETURNS INT
AS
BEGIN
    DECLARE @PaisTorneo VARCHAR(100);

    SELECT @PaisTorneo = L.pais
    FROM Torneo T
    INNER JOIN Locacion L ON T.locID = L.locID
    WHERE T.torneoID = @TorneoID;

    DECLARE @Contador INT;
    SELECT @Contador = COUNT(P.participanteID)
    FROM Inscripcion I
    INNER JOIN Participante P ON I.participanteID = P.participanteID
    INNER JOIN Torneo T ON I.torneoID = T.torneoID
    INNER JOIN Locacion L ON T.locID = L.locID  
    WHERE L.pais = @PaisTorneo;

    RETURN @Contador;
END;


GO


DECLARE @CantidadParticipantes INT;
SET @CantidadParticipantes = dbo.Function1(1);
PRINT 'La cantidad de participantes en torneos del mismo país es: ' + CAST(@CantidadParticipantes AS VARCHAR(10));



-- 4

GO

CREATE OR ALTER FUNCTION Function2
    (@ParticipanteID INT)
RETURNS INT
AS
BEGIN
    DECLARE @Contador INT;

    SELECT @Contador = COUNT(*)
    FROM Inscripcion
    WHERE participanteID = @ParticipanteID AND posicion = 1;

    RETURN @Contador;
END;

GO

DECLARE @PrimerosPuestosParticipante INT;
SET @PrimerosPuestosParticipante = dbo.Function2(1);
PRINT 'La cantidad de primeros puestos que ha obtenido el participante ingresado es de: ' + CAST(@PrimerosPuestosParticipante AS VARCHAR(10));



-- CASOS DE PRUEBA - INSERT DE ERRORES 

-- Locacion ERRORES

INSERT INTO Locacion(locID, pais, ciudad) VALUES (1, 'Chile', 'Montevideo'); -- No se pueden ingresar IDs ya registrados.


-- TORNEO ERRORES

INSERT INTO Torneo(nombre, fecha, locID, tipo, estado, Nivel) VALUES ('TorneoDeKiteSurf', '06-04-2015', 1, 'Kite-Surf', 'en finalizado', 10); 

-- La fecha NO puede ser menor  a 2016.

INSERT INTO Torneo(nombre, fecha, locID, tipo, estado, Nivel) VALUES ('TorneoDeParkstyle', '28-12-2023', 2, 'Parkstyle','por ver', 1);

-- El estado debe elegirse de entre las 3 opciones predefinidas.

INSERT INTO Torneo(nombre, fecha, locID, tipo, estado, Nivel) VALUES ('TorneoDeRacing', '06-12-2023', 2, 'Racing', 'finalizado', 20);

-- El nivel no puede ser mayor a 10.

INSERT INTO Torneo(nombre, fecha, locID, tipo, estado, Nivel) VALUES ('TorneoDeFreestyle 02', '17-12-2023', 3, 'freestyle','planificado', 5);

-- El nombre no puede contener espacios y/o números.

INSERT INTO Torneo(nombre, fecha, locID, tipo, estado, Nivel)VALUES ('Torneo de Freestyle', '19-05-2021', 1, 'freestyle', 'finalizado', 6);

-- El nombre no puede contener espacios.

INSERT INTO Torneo(nombre, fecha, locID, tipo, estado, Nivel) VALUES ('¡TorneoDeParkstyle!', '28-11-2022', 2, 'Parkstyle','por ver', 1);

-- El nombre no puede contener caracteres especiales.


-- PARTICIPANTE ERRORES


INSERT INTO Participante(nombre, locID, fecha_nacimiento, experiencia, edad) VALUES ('Dennis de la Fuente', 1, '15-05-2010', 10, 13);

-- El participante debe ser mayor de edad.

INSERT INTO Participante(participanteID, nombre, locID, fecha_nacimiento, experiencia, edad) VALUES (7, 'Margarita Nicole', 1, '08-03-2000', 15, 23);

-- No puedo insertar un ID manualmente en una tabla con identity (es decir, que asigna los IDs de forma automática).

INSERT INTO Participante(nombre, locID, fecha_nacimiento, experiencia, edad) VALUES ('Margarita Nicole', 1, '08-03-2000', 15, 23);

-- La experiencia no puede ser superior a 10.


-- PUESTO PUNTOS ERROR

INSERT INTO puesto_puntos(puesto, puntos) VALUES (0, -150); -- Los valores ingresados en ambos casos no pueden ser negativos.

INSERT INTO puesto_puntos(puesto, puntos) VALUES (-150, 0); -- Los valores ingresados en ambos casos no pueden ser negativos.


-- INSCRIPCION ERROR

INSERT INTO Inscripcion(inscripcionID, torneoID, participanteID, posicion) VALUES (6, 5, 3, 20); 

-- No puedo insertar un ID manualmente en una tabla con identity (es decir, que asigna los IDs de forma automática).



-- AUDITINSCRIPCION ERROR

INSERT INTO AuditInscripcion(AuditFecha, AuditHost, posAnterior, posActual) VALUES ('24-11-2023', 'Primer Host', -1,2); 

-- No se permite el ingreso de valores negativos.

INSERT INTO AuditInscripcion(AuditFecha, AuditHost, posAnterior, posActual) VALUES ('24-11-2023', 'Primer Host', 2,-4);

-- No se permite el ingreso de valores negativos.


















