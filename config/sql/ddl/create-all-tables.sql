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
  `command` varchar(80) NOT NULL,
  `info` varchar(1000),
  `dtcreate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `enable` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY(`id`),
  KEY `fk_logcommand_usuario` (`idusuario`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;


CREATE TABLE IF NOT EXISTS `plugins` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `plugin` varchar(60) NOT NULL,
  `command` varchar(100) NOT NULL,
  `dtcreate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `enable` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `scripts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `idplugin` bigint(20) NOT NULL,
  `script` varchar(60) NOT NULL,
  `path` varchar(255) NOT NULL,
  `dtcreate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `enable` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY(`id`),
  KEY `fk_script_plugin` (`idplugin`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `projetos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dtcreate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `projeto` varchar(80) NOT NULL,
  `enable` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY(`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `planos` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `idprojeto` bigint(20) NOT NULL,
  `plano` varchar(80) NOT NULL,
  `dtcreate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `enable` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY(`id`),
  KEY `fk_plano_projeto` (`idprojeto`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `atividades` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `idplano` bigint(20) NOT NULL,
  `idscript` bigint(20) NOT NULL,
  `idusuario` bigint(20) NOT NULL,
  `atividade` varchar(80) NOT NULL,
  `dtcreate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `enable` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY(`id`),
  KEY `fk_atividade_plano` (`idplano`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=latin1;



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
