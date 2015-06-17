SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";

CREATE TABLE IF NOT EXISTS `usuarios` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL,
  `fullname` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `pwd` varchar(1024) NOT NULL,
  `salt` varchar(512) NOT NULL,
  `role` varchar(30) NOT NULL,
  `dtcreate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `enable` int(11) NOT NULL DEFAULT '1',
   PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `propriedades` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `chave` varchar(255) DEFAULT NULL,
  `valor` varchar(255) DEFAULT NULL,
  `dtcreate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `enabled` int(11) DEFAULT NULL,
  PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `logcommands` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `idusuario` bigint(20) NOT NULL,
  `dtcreate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `command` varchar(80) NOT NULL,
  `info` varchar(1000),
  `status` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY(`id`),
  KEY `fk_logcommand_usuario` (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;




/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
