# TIC Empleo

Aplicación con cliente de consola para publicar ofertas de trabajo.

Aplicación creada con Spring Boot y Spring Data JPA.

En el repositorio **Leer-Datos-Teclado** está la librería utilizada para leer los datos por teclado.

El motivo de utilizar esta librería para leer los datos por teclado es porque en ocasiones la clase Scanner no funciona correctamente y hay que limpiar el buffer.

# Creación de la base de datos

Script para la creación de la base de datos.

```
-- Script para crear la base de datos empleos

DROP SCHEMA IF EXISTS empleos;
CREATE SCHEMA IF NOT EXISTS empleos;
USE empleos;

DROP TABLE IF EXISTS `categorias`;
CREATE TABLE `categorias` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `perfiles`;
CREATE TABLE `perfiles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `perfil` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `usuarios`;
CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `email` varchar(100) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(100) NOT NULL,
  `estatus` int(11) NOT NULL DEFAULT '1',
  `fecha_registro` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `vacantes`;
CREATE TABLE `vacantes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text NOT NULL,
  `fecha` date NOT NULL,
  `salario` double NOT NULL,
  `estatus` enum('Creada','Aprobada','Eliminada') NOT NULL,
  `destacada` int(11) NOT NULL,
  `imagen` varchar(250) NOT NULL,
  `detalles` text,
  `idCategoria` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_vacantes_categorias1_idx` (`idCategoria`),
  CONSTRAINT `fk_vacantes_categorias1` FOREIGN KEY (`idCategoria`) REFERENCES `Categorias` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `solicitudes`;
CREATE TABLE `solicitudes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fecha` date NOT NULL,
  `archivo` varchar(250) NOT NULL,
  `comentarios` text,
  `idVacante` int(11) NOT NULL,
  `idUsuario` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `vacante_usuario_UNIQUE` (`idVacante`,`idUsuario`),
  KEY `fk_solicitudes_vacantes1_idx` (`idVacante`),
  KEY `fk_solicitudes_usuarios1_idx` (`idUsuario`),
  CONSTRAINT `fk_solicitudes_usuarios1` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `fk_solicitudes_vacantes1` FOREIGN KEY (`idVacante`) REFERENCES `vacantes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `perfiles_usuarios`;
CREATE TABLE `perfiles_usuarios` (
  `idUsuario` int(11) NOT NULL,
  `idPerfil` int(11) NOT NULL,
  UNIQUE KEY `usuario_perfil_UNIQUE` (`idUsuario`,`idPerfil`),
  KEY `fk_usuarios1_idx` (`idUsuario`),
  KEY `fk_perfiles1_idx` (`idPerfil`),
  CONSTRAINT `fk_usuarios1` FOREIGN KEY (`idUsuario`) REFERENCES `usuarios` (`id`),
  CONSTRAINT `fk_perfiles1` FOREIGN KEY (`idPerfil`) REFERENCES `perfiles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```

Script para insertar datos

```
-- Insertar Categorías

INSERT INTO `categorias` (`id`,`nombre`,`descripcion`) VALUES (1,'Sistemas','Ofertas de trabajo relacionadas con sistemas');
INSERT INTO `categorias` (`id`,`nombre`,`descripcion`) VALUES (2,'Desarrollo Web','Ofertas de trabajo relacionadas con desarrollo Web');
INSERT INTO `categorias` (`id`,`nombre`,`descripcion`) VALUES (3,'Desarrollo Back End','Ofertas de trabajo relacionadas con desarrollo Back End');
INSERT INTO `categorias` (`id`,`nombre`,`descripcion`) VALUES (4,'Desarrollo Front End','Ofertas de trabajo relacionadas con desarrollo Front End');
INSERT INTO `categorias` (`id`,`nombre`,`descripcion`) VALUES (5,'Desarrollo Full Stack','Ofertas de trabajo relacionadas con desarrollo Full Stack');
INSERT INTO `categorias` (`id`,`nombre`,`descripcion`) VALUES (6,'Desarrollo MERN Stack','Ofertas de trabajo relacionadas con desarrollo MERN Stack');
INSERT INTO `categorias` (`id`,`nombre`,`descripcion`) VALUES (7,'Desarrollo MEAN Stack','Ofertas de trabajo relacionadas con desarrollo MEAN Stack');
INSERT INTO `categorias` (`id`,`nombre`,`descripcion`) VALUES (8,'Desarrollo Android/IOS','Ofertas de trabajo relacionadas con Android/IOS');
INSERT INTO `categorias` (`id`,`nombre`,`descripcion`) VALUES (9,'Big Data','Ofertas de trabajo relacionadas con Big Data');
INSERT INTO `categorias` (`id`,`nombre`,`descripcion`) VALUES (10,'Negocio','Ofertas de trabajo relacionadas con sistemas de gestion empresarial');
INSERT INTO `categorias` (`id`,`nombre`,`descripcion`) VALUES (11,'Diseño Gráfico','Ofertas de trabajo relacionadas con diseño gráfico');
INSERT INTO `categorias` (`id`,`nombre`,`descripcion`) VALUES (12,'Comunicaciones','Ofertas de trabajo relacionadas con diseño de redes, administración de redes, telecomunicaciones, etc...');
INSERT INTO `categorias` (`id`,`nombre`,`descripcion`) VALUES (13,'Base de Datos','Ofertas de trabajo relacionadas con diseño, integración y administración de bases de datos');
INSERT INTO `categorias` (`id`,`nombre`,`descripcion`) VALUES (14,'Blockchain/Bitcoin','Ofertas de trabajo relacionadas con blockchain y bitcoin.');

-- Insertar Vacantes


INSERT INTO `vacantes` (`id`,`nombre`,`descripcion`,`fecha`,`salario`,`estatus`,`destacada`,`imagen`,`detalles`,`idCategoria`) 
VALUES (1,'Administrador de Sistemas Linux','Buscamos administrador de sistemas Linux','2021-01-28',23000,'Aprobada',1,'no-image.png','Experiencia en administración de sistemas operativos Linux. Red Hat Enterprise, virtualización VMWare, administración de contenedores Docker',1);

INSERT INTO `vacantes` (`id`,`nombre`,`descripcion`,`fecha`,`salario`,`estatus`,`destacada`,`imagen`,`detalles`,`idCategoria`) 
VALUES (2,'Desarrollador MEAN Stack','Buscamos desarrollador MEAN Stack','2021-01-28',34000,'Aprobada',1,'no-image.png','Experiencia como desarrollador MEAN Stack. MongoDB, Express, Angular, Node.js',7);

INSERT INTO `vacantes` (`id`,`nombre`,`descripcion`,`fecha`,`salario`,`estatus`,`destacada`,`imagen`,`detalles`,`idCategoria`) 
VALUES (3,'Desarrollador MERN Stack','Buscamos desarrollador MERN Stack','2021-01-28',23000,'Aprobada',1,'no-image.png','Experiencia como desarrollador MERN Stack. MongoDB, Express, React, Angular y NodeJS',6);

INSERT INTO `vacantes` (`id`,`nombre`,`descripcion`,`fecha`,`salario`,`estatus`,`destacada`,`imagen`,`detalles`,`idCategoria`) 
VALUES (4,'Desarrollador Senior Front End, Angular','Buscamos programador con experiencia en desarrollo Front End con JavaScript, TypeScript, Angular, NodeJS, Express','2021-01-29',27000,'Aprobada',1,'no-image.png','Experiencia como desarrollador front end. JavaScript/TypeScript, Angular, Node.js, HTML5, CSS3',4);

INSERT INTO `vacantes` (`id`,`nombre`,`descripcion`,`fecha`,`salario`,`estatus`,`destacada`,`imagen`,`detalles`,`idCategoria`) 
VALUES (5,'Desarrollador Back End','Buscamos programador con experiencia en desarrollo Back End','2021-01-29',24000,'Aprobada',1,'no-image.png','Experiencia como desarrollador Back End. JavaScript/TypeScript, Node.js, Express',3);

INSERT INTO `vacantes` (`id`,`nombre`,`descripcion`,`fecha`,`salario`,`estatus`,`destacada`,`imagen`,`detalles`,`idCategoria`)
VALUES (6,'Analista Programador/a Java J2EE','Buscamos analista programador/a Java','2021-01-29',24000,'Aprobada',1,'no-image.png','Experiencia como analista programador Java2EE. Java en entornos web, Api Rest, Spring MVC, Thymeleaf, Oracle',5);

INSERT INTO `vacantes` (`id`,`nombre`,`descripcion`,`fecha`,`salario`,`estatus`,`destacada`,`imagen`,`detalles`,`idCategoria`)
VALUES (7,'Analista Programador/a Big Data','Buscamos analista programador/a Big Data con experiencia en las tecnologías MongoDB, Cassandra Hadoop, Spark, Kafka, Apache Flink','2021-01-29',30000,'Aprobada',1,'no-image.png','Experiencia como analista programador Big Data. BigData, BigData/Apache, Hadoop, Kafka, Spark, Apache Flink',6);

INSERT INTO `vacantes` (`id`,`nombre`,`descripcion`,`fecha`,`salario`,`estatus`,`destacada`,`imagen`,`detalles`,`idCategoria`)
VALUES (8,'Desarrollador Web','Buscamos programador con experiencia en desarrollo web con JavaScript, TypeScript, Angular, NodeJS, Express, Symphony, Laravel','2021-01-29',26000,'Aprobada',1,'no-image.png','Experiencia como desarrollador web. JavaScript/TypeSript, Angular, HTML5, CSS3, Node.js, Express',2);

INSERT INTO `vacantes` (`id`,`nombre`,`descripcion`,`fecha`,`salario`,`estatus`,`destacada`,`imagen`,`detalles`,`idCategoria`) 
VALUES (9,'Analista Programador/a web Full Stack','Buscamos analista programador con experiencia en desarrollo web, Java, JavaScript, TypeScript','2021-01-29',29000,'Aprobada',1,'no-image.png','Experiencia como desarrollador Full-Stack. JavaScript/TypeSript, Python, PHP, Angular, Bootstrap, HTML5, CSS3, Node.js, Express, Symfony, Laravel, MySQL, MongoDB, Cassandra',5);

INSERT INTO `vacantes` (`id`,`nombre`,`descripcion`,`fecha`,`salario`,`estatus`,`destacada`,`imagen`,`detalles`,`idCategoria`)
VALUES (10,'Programador Android/IOS','Buscamos programador con experiencia en desarrollo Android/IOS','2021-01-29',28000,'Aprobada',1,'no-image.png','Experiencia como desarrollador Android/IOS. Java, Kotlin, API, Test unitarios, HTML5, CSS3, Aplicaciones móviles',8);

INSERT INTO `vacantes` (`id`,`nombre`,`descripcion`,`fecha`,`salario`,`estatus`,`destacada`,`imagen`,`detalles`,`idCategoria`)
VALUES (11,'Arquitecto/a Big Data','Buscamos arquitecto/a Big Data con experiencia en las tecnologías MongoDB, Cassandra Hadoop, Spark, Kafka, Apache Flink','2021-01-29',31000,'Aprobada',1,'no-image.png','Experiencia como arquitecto Big Data. Python, Scala, Hadoop, Spark, HortonWorks, Git, Jira, Bases de Datos SQL y NoSQL, Oracle, MongoDB, Cassandra',9);

INSERT INTO `vacantes` (`id`,`nombre`,`descripcion`,`fecha`,`salario`,`estatus`,`destacada`,`imagen`,`detalles`,`idCategoria`) 
VALUES (12,'Consultor Sap','Buscamos consultor Sap MM','2021-01-30',33000,'Creada',1,'no-image.png','Experiencia como consultor SAP. Integración de sistemas SAP, ERP, modulos (MM, FICO, SD), S/4 Hana Cloud',10);

INSERT INTO `vacantes` (`id`,`nombre`,`descripcion`,`fecha`,`salario`,`estatus`,`destacada`,`imagen`,`detalles`,`idCategoria`) 
VALUES (13,'Administrador de Redes','Buscamos administrador de redes para monitorizar y administrar servidores Linux','2021-01-29',23000,'Aprobada',1,'no-image.png','Experiencia como administrador de Redes y sistemas Linux Red Hat. Sistemas Linux Red Hat, configuración, integración y administración de redes, LAN, VLAN, seguridad, routing, DNS, DHCP, VPN, monitorización',12);

INSERT INTO `vacantes` (`id`,`nombre`,`descripcion`,`fecha`,`salario`,`estatus`,`destacada`,`imagen`,`detalles`,`idCategoria`)
VALUES (14,'Administrador de Bases de Datos','Buscamos administrador de bases de datos SQL, NoSQL, MongoDB, Cassandra, Oracle, MySQL, PostgreSQL','2021-01-29',26000,'Aprobada',1,'no-image.png','Experiencia como administrador de base de datos. Configuración, integración y administración de bases de datos, Bases de Datos SQL y NoSQL, Oracle, MySQL, PostgreSQL, MongoDB, Cassandra',13);

-- Insertar Perfiles

INSERT INTO `perfiles` (`id`,`perfil`) VALUES (1,'Supervisor');
INSERT INTO `perfiles` (`id`,`perfil`) VALUES (2,'Administrador');
INSERT INTO `perfiles` (`id`,`perfil`) VALUES (3,'Usuario');

-- Insertar Usuarios

-- Insertar usuarios
INSERT INTO `usuarios` (`nombre`, `email`, `estatus`, `username`, `password`, `fecha_registro`) values ('Daniel', 'daniel.pompa@gmail.com', 1, 'daniel', '$2a$10$Wmt8KHBUMquT3tRWalV3JuDVtG.e/bPMP4QFyvI0QXhmaaN/t6Vne', '2021-01-29');
INSERT INTO `usuarios` (`nombre`, `email`, `estatus`, `username`, `password`, `fecha_registro`) values ('Emma', 'emma.ciambrino@gmail.com', 1, 'emma', '$2a$10$TltuPExdlriVD1AAOtMkdOGBU4CJ.0tPP5FhjGYUyvcJ2OJ20NqOW', '2021-01-29');
INSERT INTO `usuarios` (`nombre`, `email`, `estatus`, `username`, `password`, `fecha_registro`) values ('Oscar', 'oscar.pompa@gmail.com', 1, 'oscar', '$2a$10$TltuPExdlriVD1AAOtMkdOGBU4CJ.0tPP5FhjGYUyvcJ2OJ20NqOW', '2021-01-29');

-- Insertar/asignar roles a los usuarios.
INSERT INTO `perfiles_usuarios` VALUES (1,2);
INSERT INTO `perfiles_usuarios` VALUES (2,3);
INSERT INTO `perfiles_usuarios` VALUES (3,3);

```