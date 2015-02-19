-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Feb 19, 2015 at 01:02 PM
-- Server version: 5.6.17
-- PHP Version: 5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `javarpg`
--

-- --------------------------------------------------------

--
-- Table structure for table `characters`
--

CREATE TABLE IF NOT EXISTS `characters` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `saveName` varchar(20) NOT NULL,
  `level` int(11) NOT NULL,
  `currenthealth` int(11) NOT NULL,
  `strength` int(11) NOT NULL,
  `agility` int(11) NOT NULL,
  `intelligence` int(11) NOT NULL,
  `defensepower` int(11) NOT NULL,
  `experience` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 COMMENT='characters to save' AUTO_INCREMENT=7 ;

--
-- Dumping data for table `characters`
--

INSERT INTO `characters` (`id`, `saveName`, `level`, `currenthealth`, `strength`, `agility`, `intelligence`, `defensepower`, `experience`) VALUES
(2, '', 1, 100, 0, 0, 0, 0, 0),
(3, 'testsave', 1, 100, 0, 0, 0, 0, 0),
(4, 'testsave', 1, 100, 0, 0, 0, 0, 0),
(5, 'testsave', 1, 100, 0, 0, 0, 0, 0),
(6, 'testsave', 1, 100, 0, 0, 0, 0, 0);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
