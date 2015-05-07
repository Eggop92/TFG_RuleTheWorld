-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 06-05-2015 a las 20:36:41
-- Versión del servidor: 5.5.37-0ubuntu0.14.04.1
-- Versión de PHP: 5.5.9-1ubuntu4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `RuleTheWorld`
--
CREATE DATABASE IF NOT EXISTS `RuleTheWorld` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `RuleTheWorld`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Amigos`
--

CREATE TABLE IF NOT EXISTS `Amigos` (
  `idJugador` varchar(20) NOT NULL,
  `idAmigo` varchar(20) NOT NULL,
  PRIMARY KEY (`idJugador`,`idAmigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Amigos`
--

INSERT INTO `Amigos` (`idJugador`, `idAmigo`) VALUES('AkiShawol', 'eggop');
INSERT INTO `Amigos` (`idJugador`, `idAmigo`) VALUES('eggop', 'AkiShawol');
INSERT INTO `Amigos` (`idJugador`, `idAmigo`) VALUES('eggop', 'bego');
INSERT INTO `Amigos` (`idJugador`, `idAmigo`) VALUES('eggop', 'Defensa');
INSERT INTO `Amigos` (`idJugador`, `idAmigo`) VALUES('eggop', 'hpaz');
INSERT INTO `Amigos` (`idJugador`, `idAmigo`) VALUES('eggop', 'pepe');
INSERT INTO `Amigos` (`idJugador`, `idAmigo`) VALUES('Egoitz P', 'eggop');
INSERT INTO `Amigos` (`idJugador`, `idAmigo`) VALUES('Egoitz P', 'hpaz');
INSERT INTO `Amigos` (`idJugador`, `idAmigo`) VALUES('hpaz', 'eggop');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Combinaciones`
--

CREATE TABLE IF NOT EXISTS `Combinaciones` (
  `creacion` datetime NOT NULL,
  `objeto1` int(11) NOT NULL,
  `objeto2` int(11) NOT NULL,
  `resultado` int(11) NOT NULL,
  PRIMARY KEY (`objeto1`,`objeto2`),
  KEY `objeto2` (`objeto2`),
  KEY `resultado` (`resultado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Combinaciones`
--

INSERT INTO `Combinaciones` (`creacion`, `objeto1`, `objeto2`, `resultado`) VALUES('0000-00-00 00:00:00', 1, 3, 10);
INSERT INTO `Combinaciones` (`creacion`, `objeto1`, `objeto2`, `resultado`) VALUES('0000-00-00 00:00:00', 1, 12, 11);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Enemigos`
--

CREATE TABLE IF NOT EXISTS `Enemigos` (
  `creacion` datetime NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `vida` int(11) NOT NULL,
  `ataque` int(11) NOT NULL,
  `defensa` int(11) NOT NULL,
  `experiencia` int(11) NOT NULL,
  PRIMARY KEY (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Enemigos`
--

INSERT INTO `Enemigos` (`creacion`, `nombre`, `vida`, `ataque`, `defensa`, `experiencia`) VALUES('2014-08-08 00:00:00', 'esqueleto', 40, 20, 10, 30);
INSERT INTO `Enemigos` (`creacion`, `nombre`, `vida`, `ataque`, `defensa`, `experiencia`) VALUES('2014-09-03 13:00:00', 'indestructible', 1000, 100, 50, 1000);
INSERT INTO `Enemigos` (`creacion`, `nombre`, `vida`, `ataque`, `defensa`, `experiencia`) VALUES('2014-08-01 00:00:00', 'zombie', 30, 2, 0, 15);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Envios`
--

CREATE TABLE IF NOT EXISTS `Envios` (
  `idJugador` varchar(20) NOT NULL,
  `idObjeto` int(11) NOT NULL,
  `lat` double NOT NULL,
  `lon` double NOT NULL,
  `cuando` datetime NOT NULL,
  `recibido` int(1) NOT NULL,
  PRIMARY KEY (`idJugador`,`idObjeto`,`cuando`),
  KEY `idObjeto` (`idObjeto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Envios`
--

INSERT INTO `Envios` (`idJugador`, `idObjeto`, `lat`, `lon`, `cuando`, `recibido`) VALUES('Defensa', 6, 43.2359327, -2.8863452, '2014-10-17 19:51:31', 0);
INSERT INTO `Envios` (`idJugador`, `idObjeto`, `lat`, `lon`, `cuando`, `recibido`) VALUES('Defensa', 8, 43.2636928, -2.9509297, '2014-09-26 10:31:40', 0);
INSERT INTO `Envios` (`idJugador`, `idObjeto`, `lat`, `lon`, `cuando`, `recibido`) VALUES('eggop', 6, 43.277, -2.968, '2014-08-03 17:37:38', 1);
INSERT INTO `Envios` (`idJugador`, `idObjeto`, `lat`, `lon`, `cuando`, `recibido`) VALUES('eggop', 9, 43.2802685, -2.9591873, '2014-09-23 12:27:12', 1);
INSERT INTO `Envios` (`idJugador`, `idObjeto`, `lat`, `lon`, `cuando`, `recibido`) VALUES('eggop', 10, 42.8926224, -8.5462887, '2014-08-03 18:29:59', 1);
INSERT INTO `Envios` (`idJugador`, `idObjeto`, `lat`, `lon`, `cuando`, `recibido`) VALUES('pepe', 1, 43.2802797, -2.95921, '2014-08-03 17:40:23', 0);
INSERT INTO `Envios` (`idJugador`, `idObjeto`, `lat`, `lon`, `cuando`, `recibido`) VALUES('pepe', 7, 43.2802646, -2.9592117, '2014-08-03 17:43:34', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Niveles`
--

CREATE TABLE IF NOT EXISTS `Niveles` (
  `creacion` datetime NOT NULL,
  `Nivel` int(11) NOT NULL,
  `Experiencia` int(11) NOT NULL,
  PRIMARY KEY (`Nivel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Niveles`
--

INSERT INTO `Niveles` (`creacion`, `Nivel`, `Experiencia`) VALUES('0000-00-00 00:00:00', 1, 0);
INSERT INTO `Niveles` (`creacion`, `Nivel`, `Experiencia`) VALUES('0000-00-00 00:00:00', 2, 20);
INSERT INTO `Niveles` (`creacion`, `Nivel`, `Experiencia`) VALUES('0000-00-00 00:00:00', 3, 90);
INSERT INTO `Niveles` (`creacion`, `Nivel`, `Experiencia`) VALUES('0000-00-00 00:00:00', 4, 150);
INSERT INTO `Niveles` (`creacion`, `Nivel`, `Experiencia`) VALUES('0000-00-00 00:00:00', 5, 270);
INSERT INTO `Niveles` (`creacion`, `Nivel`, `Experiencia`) VALUES('0000-00-00 00:00:00', 6, 510);
INSERT INTO `Niveles` (`creacion`, `Nivel`, `Experiencia`) VALUES('2014-08-03 00:00:00', 7, 990);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Objetos`
--

CREATE TABLE IF NOT EXISTS `Objetos` (
  `id` int(11) NOT NULL,
  `creacion` datetime NOT NULL,
  `nombre` varchar(25) NOT NULL,
  `minLevel` int(11) NOT NULL,
  `vida` int(11) NOT NULL,
  `ataque` int(11) NOT NULL,
  `defensa` int(11) NOT NULL,
  `cabeza` int(1) NOT NULL,
  `torso` int(1) NOT NULL,
  `piernas` int(1) NOT NULL,
  `pies` int(1) NOT NULL,
  `arma` int(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`),
  KEY `minLevel` (`minLevel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Objetos`
--

INSERT INTO `Objetos` (`id`, `creacion`, `nombre`, `minLevel`, `vida`, `ataque`, `defensa`, `cabeza`, `torso`, `piernas`, `pies`, `arma`) VALUES(1, '0000-00-00 00:00:00', 'Palo', 1, 0, 3, 0, 0, 0, 0, 0, 1);
INSERT INTO `Objetos` (`id`, `creacion`, `nombre`, `minLevel`, `vida`, `ataque`, `defensa`, `cabeza`, `torso`, `piernas`, `pies`, `arma`) VALUES(2, '0000-00-00 00:00:00', 'Piedra', 1, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO `Objetos` (`id`, `creacion`, `nombre`, `minLevel`, `vida`, `ataque`, `defensa`, `cabeza`, `torso`, `piernas`, `pies`, `arma`) VALUES(3, '0000-00-00 00:00:00', 'Tela', 1, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO `Objetos` (`id`, `creacion`, `nombre`, `minLevel`, `vida`, `ataque`, `defensa`, `cabeza`, `torso`, `piernas`, `pies`, `arma`) VALUES(4, '0000-00-00 00:00:00', 'Tierra', 1, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO `Objetos` (`id`, `creacion`, `nombre`, `minLevel`, `vida`, `ataque`, `defensa`, `cabeza`, `torso`, `piernas`, `pies`, `arma`) VALUES(5, '0000-00-00 00:00:00', 'Casco', 1, 10, 0, 10, 1, 0, 0, 0, 0);
INSERT INTO `Objetos` (`id`, `creacion`, `nombre`, `minLevel`, `vida`, `ataque`, `defensa`, `cabeza`, `torso`, `piernas`, `pies`, `arma`) VALUES(6, '0000-00-00 00:00:00', 'Camiseta', 1, 10, 0, 10, 0, 1, 0, 0, 0);
INSERT INTO `Objetos` (`id`, `creacion`, `nombre`, `minLevel`, `vida`, `ataque`, `defensa`, `cabeza`, `torso`, `piernas`, `pies`, `arma`) VALUES(7, '0000-00-00 00:00:00', 'Pantalones de tela', 1, 0, 0, 10, 0, 0, 1, 0, 0);
INSERT INTO `Objetos` (`id`, `creacion`, `nombre`, `minLevel`, `vida`, `ataque`, `defensa`, `cabeza`, `torso`, `piernas`, `pies`, `arma`) VALUES(8, '0000-00-00 00:00:00', 'Botas de cuero', 1, 0, 0, 10, 0, 0, 0, 1, 0);
INSERT INTO `Objetos` (`id`, `creacion`, `nombre`, `minLevel`, `vida`, `ataque`, `defensa`, `cabeza`, `torso`, `piernas`, `pies`, `arma`) VALUES(9, '0000-00-00 00:00:00', 'Cuchillo', 1, 0, 10, 0, 0, 0, 0, 0, 1);
INSERT INTO `Objetos` (`id`, `creacion`, `nombre`, `minLevel`, `vida`, `ataque`, `defensa`, `cabeza`, `torso`, `piernas`, `pies`, `arma`) VALUES(10, '0000-00-00 00:00:00', 'Bandera', 1, 0, 0, 0, 0, 0, 0, 0, 0);
INSERT INTO `Objetos` (`id`, `creacion`, `nombre`, `minLevel`, `vida`, `ataque`, `defensa`, `cabeza`, `torso`, `piernas`, `pies`, `arma`) VALUES(11, '0000-00-00 00:00:00', 'Tirachinas', 1, 0, 5, 0, 0, 0, 0, 0, 1);
INSERT INTO `Objetos` (`id`, `creacion`, `nombre`, `minLevel`, `vida`, `ataque`, `defensa`, `cabeza`, `torso`, `piernas`, `pies`, `arma`) VALUES(12, '0000-00-00 00:00:00', 'Cuerda', 1, 0, 0, 0, 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Usuarios`
--

CREATE TABLE IF NOT EXISTS `Usuarios` (
  `id` varchar(20) NOT NULL,
  `email` varchar(50) NOT NULL,
  `contrasena` varchar(50) NOT NULL,
  `vida` int(11) NOT NULL,
  `ataque` int(11) NOT NULL,
  `defensa` int(11) NOT NULL,
  `experiencia` int(11) NOT NULL,
  `idCabeza` int(11) DEFAULT NULL,
  `idTorso` int(11) DEFAULT NULL,
  `idPiernas` int(11) DEFAULT NULL,
  `idPies` int(11) DEFAULT NULL,
  `idArma` int(11) DEFAULT NULL,
  PRIMARY KEY (`email`),
  UNIQUE KEY `id` (`id`),
  KEY `idTorso` (`idTorso`),
  KEY `idPies` (`idPies`),
  KEY `idArma` (`idArma`),
  KEY `idPiernas` (`idPiernas`),
  KEY `idCabeza` (`idCabeza`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Usuarios`
--

INSERT INTO `Usuarios` (`id`, `email`, `contrasena`, `vida`, `ataque`, `defensa`, `experiencia`, `idCabeza`, `idTorso`, `idPiernas`, `idPies`, `idArma`) VALUES('AkiShawol', 'AkiBD11@gmail.com', 'e82bb3f18a9f301e29bbbd6c382849b2', 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `Usuarios` (`id`, `email`, `contrasena`, `vida`, `ataque`, `defensa`, `experiencia`, `idCabeza`, `idTorso`, `idPiernas`, `idPies`, `idArma`) VALUES('bego', 'begobeld@gmail.com', '827ccb0eea8a706c4c34a16891f84e7b', 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `Usuarios` (`id`, `email`, `contrasena`, `vida`, `ataque`, `defensa`, `experiencia`, `idCabeza`, `idTorso`, `idPiernas`, `idPies`, `idArma`) VALUES('Egoitz P', 'egoitz@hotmail.es', 'fcea920f7412b5da7be0cf42b8c93759', 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `Usuarios` (`id`, `email`, `contrasena`, `vida`, `ataque`, `defensa`, `experiencia`, `idCabeza`, `idTorso`, `idPiernas`, `idPies`, `idArma`) VALUES('eggop', 'egoitzpuerta@hotmail.com', '827ccb0eea8a706c4c34a16891f84e7b', 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `Usuarios` (`id`, `email`, `contrasena`, `vida`, `ataque`, `defensa`, `experiencia`, `idCabeza`, `idTorso`, `idPiernas`, `idPies`, `idArma`) VALUES('Defensa', 'epuerta001@ehu.es', 'fcea920f7412b5da7be0cf42b8c93759', 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `Usuarios` (`id`, `email`, `contrasena`, `vida`, `ataque`, `defensa`, `experiencia`, `idCabeza`, `idTorso`, `idPiernas`, `idPies`, `idArma`) VALUES('hpaz', 'hpaz_1808@hotmail.com', '25d55ad283aa400af464c76d713c07ad', 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `Usuarios` (`id`, `email`, `contrasena`, `vida`, `ataque`, `defensa`, `experiencia`, `idCabeza`, `idTorso`, `idPiernas`, `idPies`, `idArma`) VALUES('nekoz', 'nekoz@nekoz.com', 'bf4ab447496f2d3d5a6c77c2cd12f996', 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `Usuarios` (`id`, `email`, `contrasena`, `vida`, `ataque`, `defensa`, `experiencia`, `idCabeza`, `idTorso`, `idPiernas`, `idPies`, `idArma`) VALUES('pepe', 'pepe@hit.com', '827ccb0eea8a706c4c34a16891f84e7b', 0, 0, 0, 0, NULL, NULL, NULL, NULL, NULL);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `Combinaciones`
--
ALTER TABLE `Combinaciones`
  ADD CONSTRAINT `Combinaciones_ibfk_3` FOREIGN KEY (`resultado`) REFERENCES `Objetos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Combinaciones_ibfk_1` FOREIGN KEY (`objeto1`) REFERENCES `Objetos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Combinaciones_ibfk_2` FOREIGN KEY (`objeto2`) REFERENCES `Objetos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `Envios`
--
ALTER TABLE `Envios`
  ADD CONSTRAINT `Envios_ibfk_2` FOREIGN KEY (`idObjeto`) REFERENCES `Objetos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `Envios_ibfk_1` FOREIGN KEY (`idJugador`) REFERENCES `Usuarios` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `Objetos`
--
ALTER TABLE `Objetos`
  ADD CONSTRAINT `Objetos_ibfk_1` FOREIGN KEY (`minLevel`) REFERENCES `Niveles` (`Nivel`);

--
-- Filtros para la tabla `Usuarios`
--
ALTER TABLE `Usuarios`
  ADD CONSTRAINT `Usuarios_ibfk_5` FOREIGN KEY (`idArma`) REFERENCES `Objetos` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `Usuarios_ibfk_1` FOREIGN KEY (`idCabeza`) REFERENCES `Objetos` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `Usuarios_ibfk_2` FOREIGN KEY (`idTorso`) REFERENCES `Objetos` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `Usuarios_ibfk_3` FOREIGN KEY (`idPiernas`) REFERENCES `Objetos` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  ADD CONSTRAINT `Usuarios_ibfk_4` FOREIGN KEY (`idPies`) REFERENCES `Objetos` (`id`) ON DELETE SET NULL ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
