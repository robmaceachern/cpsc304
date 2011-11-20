-- MySQL dump 10.13  Distrib 5.5.17, for Win64 (x86)
--
-- Host: localhost    Database: crazycoollibrary
-- ------------------------------------------------------
-- Server version	5.5.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `book` (
  `callNumber` int(9) NOT NULL,
  `isbn` int(9) DEFAULT NULL,
  `title` varchar(45) DEFAULT NULL,
  `mainAuthor` varchar(45) DEFAULT NULL,
  `publisher` varchar(45) DEFAULT NULL,
  `year` int(4) NOT NULL,
  PRIMARY KEY (`callNumber`),
  CONSTRAINT unique_isbn UNIQUE (isbn)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookcopy`
--

DROP TABLE IF EXISTS `bookcopy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookcopy` (
  `callNumber` int(9) NOT NULL,
  `copyNo` int(5) NOT NULL AUTO_INCREMENT,
  `status` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`callNumber`,`copyNo`),
  KEY (`copyNo`),
  CONSTRAINT `bookcopy_ibfk_1` FOREIGN KEY (`callNumber`) REFERENCES `book` (`callNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookcopy`
--

LOCK TABLES `bookcopy` WRITE;
/*!40000 ALTER TABLE `bookcopy` DISABLE KEYS */;
/*!40000 ALTER TABLE `bookcopy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrower`
--

DROP TABLE IF EXISTS `borrower`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `borrower` (
  `bid` int(10) NOT NULL AUTO_INCREMENT,
  `password` varchar(20) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `phone` int(7) DEFAULT NULL,
  `emailAddress` varchar(20) DEFAULT NULL,
  `sinOrStNo` int(10) NOT NULL,
  `expiryDate` date NOT NULL,
  `btype` ENUM ('student', 'faculty', 'staff'),
  PRIMARY KEY (`bid`),
  CONSTRAINT `borrower_ibfk_1` FOREIGN KEY (`btype`) REFERENCES `borrowertype` (`btype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrower`
--

LOCK TABLES `borrower` WRITE;
/*!40000 ALTER TABLE `borrower` DISABLE KEYS */;
/*!40000 ALTER TABLE `borrower` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrowertype`
--

DROP TABLE IF EXISTS `borrowertype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `borrowertype` (
  `btype` ENUM ('student', 'faculty', 'staff'),
  `bookTimeLimit` ENUM ('2', '6', '12'),  
  PRIMARY KEY (`btype`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrowertype`
--

LOCK TABLES `borrowertype` WRITE;
/*!40000 ALTER TABLE `borrowertype` DISABLE KEYS */;
/*!40000 ALTER TABLE `borrowertype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrowing`
--

DROP TABLE IF EXISTS `borrowing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `borrowing` (
  `borid` int(9) NOT NULL AUTO_INCREMENT,
  `bid` int(9) NOT NULL,
  `callNumber` int(9) NOT NULL,
  `copyNo` int(5) NOT NULL,
  `outDate` datetime DEFAULT NULL,
  `inDate` datetime DEFAULT NULL,
  PRIMARY KEY (`borid`),
  KEY `bid` (`bid`),
  CONSTRAINT `borrowing_ibfk_1` FOREIGN KEY (`bid`) REFERENCES `borrower` (`bid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `borrowing_ibfk_2` FOREIGN KEY (`callNumber`, `copyNo`) REFERENCES `bookcopy` (`callNumber`, `copyNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrowing`
--

LOCK TABLES `borrowing` WRITE;
/*!40000 ALTER TABLE `borrowing` DISABLE KEYS */;
/*!40000 ALTER TABLE `borrowing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fine`
--

DROP TABLE IF EXISTS `fine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fine` (
  `fid` int(9) NOT NULL AUTO_INCREMENT,
  `amount` int(9) NOT NULL,
  `issuedDate` datetime NOT NULL,
  `paidDate` datetime DEFAULT NULL,
  `borid` varchar(45) NOT NULL,
  PRIMARY KEY (`fid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fine`
--

LOCK TABLES `fine` WRITE;
/*!40000 ALTER TABLE `fine` DISABLE KEYS */;
/*!40000 ALTER TABLE `fine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hasauthor`
--

DROP TABLE IF EXISTS `hasauthor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hasauthor` (
  `author` varchar(30) NOT NULL,
  `callNumber` int(9) NOT NULL,
  PRIMARY KEY (`author`,`callNumber`),
  CONSTRAINT `callNumber` FOREIGN KEY (`callNumber`) REFERENCES `book` (`callNumber`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hasauthor`
--

LOCK TABLES `hasauthor` WRITE;
/*!40000 ALTER TABLE `hasauthor` DISABLE KEYS */;
/*!40000 ALTER TABLE `hasauthor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hassubject`
--

DROP TABLE IF EXISTS `hassubject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hassubject` (
  `callNumber` int(9) NOT NULL,
  `subject` varchar(20) NOT NULL,
  PRIMARY KEY (`callNumber`,`subject`),
  CONSTRAINT `hassubject_ibfk_1` FOREIGN KEY (`callNumber`) REFERENCES `book` (`callNumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hassubject`
--

LOCK TABLES `hassubject` WRITE;
/*!40000 ALTER TABLE `hassubject` DISABLE KEYS */;
/*!40000 ALTER TABLE `hassubject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `holdrequest`
--

DROP TABLE IF EXISTS `holdrequest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `holdrequest` (
  `hid` int(9) NOT NULL AUTO_INCREMENT,
  `bid` int(9) NOT NULL,
  `callNumber` int(9) NOT NULL,
  `issuedDate` datetime DEFAULT NULL,
  PRIMARY KEY (`hid`),
  KEY `bid` (`bid`),
  KEY `callNumber` (`callNumber`),
  CONSTRAINT `holdrequest_ibfk_1` FOREIGN KEY (`bid`) REFERENCES `borrower` (`bid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `holdrequest_ibfk_2` FOREIGN KEY (`callNumber`) REFERENCES `book` (`callNumber`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `holdrequest`
--

LOCK TABLES `holdrequest` WRITE;
/*!40000 ALTER TABLE `holdrequest` DISABLE KEYS */;
/*!40000 ALTER TABLE `holdrequest` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2011-11-13 14:45:07
