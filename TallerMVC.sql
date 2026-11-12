create database if not exists TallerSpringMVC;
use TallerSpringMVC;
CREATE TABLE Carrera (
    idCarrera   BIGINT NOT NULL AUTO_INCREMENT,
    siglas      VARCHAR(5) NOT NULL,
    nombre      VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255) DEFAULT NULL, 
    estatus     TINYINT(1) NOT NULL DEFAULT 1, -- 1=Activo, 0=Inactivo
    eliminado   TINYINT(1) NOT NULL DEFAULT 0, -- 0=No borrado, 1=Borrado
    PRIMARY KEY (idCarrera),
    UNIQUE KEY UK_Carrera_Siglas (siglas),
    UNIQUE KEY UK_Carrera_Nombre (nombre)
); 

CREATE TABLE Alumno (
    idAlumno      BIGINT NOT NULL AUTO_INCREMENT,
    nombreAlumno  VARCHAR(100) NOT NULL, 
    paternoAlumno VARCHAR(100) NOT NULL,
    maternoAlumno VARCHAR(100) DEFAULT NULL, 
    emailAlumno   VARCHAR(150) NOT NULL, 
    estatus       TINYINT(1) NOT NULL DEFAULT 1,
    eliminado     TINYINT(1) NOT NULL DEFAULT 0,
    idCarrera     BIGINT NOT NULL,
    PRIMARY KEY (idAlumno),
    UNIQUE KEY UK_Alumno_Email (emailAlumno),
    KEY IDX_Alumno_Carrera (idCarrera),
    CONSTRAINT FK_Alumno_Carrera  
        FOREIGN KEY (idCarrera) REFERENCES Carrera (idCarrera)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

