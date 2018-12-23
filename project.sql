-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: project
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `project`
--

/*!40000 DROP DATABASE IF EXISTS `project`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `project` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */;

USE `project`;

--
-- Table structure for table `clients`
--

DROP TABLE IF EXISTS `clients`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `clients` (
  `idClient` int(11) NOT NULL AUTO_INCREMENT,
  `surnameClient` varchar(45) NOT NULL,
  `nameClient` varchar(45) NOT NULL,
  `numberClient` varchar(45) DEFAULT NULL,
  `countryClient` varchar(45) DEFAULT NULL,
  `townClient` varchar(45) DEFAULT NULL,
  `addressClient` varchar(45) DEFAULT NULL,
  `noteClient` varchar(45) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`idClient`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clients`
--

LOCK TABLES `clients` WRITE;
/*!40000 ALTER TABLE `clients` DISABLE KEYS */;
INSERT INTO `clients` VALUES (12,'Ивлеев','Максим','+37544123465','Украина','Киев','ул. Пушкина, 12,47','','ua','ua'),(14,'Пятер','Иван','+375668900','Беларусь','Брест','ул.Юбилейная 7б,22',NULL,'mm','mm'),(20,'Кириленко','Егор','+375445686574','Беларусь','Минск','ул.Петра Глебки 8,69','','egor','egor');
/*!40000 ALTER TABLE `clients` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `orders` (
  `idOrder` int(11) NOT NULL AUTO_INCREMENT,
  `nameFurniture` varchar(45) NOT NULL,
  `noteFurniture` varchar(45) NOT NULL,
  `quantityFurniture` int(11) NOT NULL,
  `priceFurniture` float NOT NULL,
  `priceDelivery` float DEFAULT NULL,
  `idClient` int(11) DEFAULT NULL,
  `idStaff` int(11) DEFAULT NULL,
  `dateOrder` date DEFAULT NULL,
  PRIMARY KEY (`idOrder`),
  KEY `nameProduct_idx` (`nameFurniture`),
  KEY `idClient_idx` (`idClient`),
  KEY `idStaff_idx` (`idStaff`),
  CONSTRAINT `idClientOrder` FOREIGN KEY (`idClient`) REFERENCES `clients` (`idclient`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `idStaffOrder` FOREIGN KEY (`idStaff`) REFERENCES `staffs` (`idstaff`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `nameProductOrder` FOREIGN KEY (`nameFurniture`) REFERENCES `products` (`nameproduct`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'Стол детский модульный регулируемый','материал: дуб',1,7500,296.333,12,8,'2018-07-06'),(40,'Аида Диван 2-х местный','Ткань/кожзаменитель 3 кат.',1,10000,800,14,8,'2017-12-01'),(41,'Берта Кресло','Ткань/кожзаменитель 3 кат.',1,5000,40,12,8,'2018-11-07'),(42,'Стул детскиц Chairman 250','Материал: ткань-сетка / ткань TW',2,1561,12.5,20,8,'2018-12-11'),(43,'Берта Диван 3-х местный','Ткань/кожзаменитель 3 кат.',1,1360,12.5,20,8,'2018-12-11'),(44,'Стол детский модульный регулируемый','материал: дуб',1,9900,12.5,20,8,'2018-12-11'),(45,'Берта Диван 3-х местный','Ткань/кожзаменитель 3 кат.',1,1360,90,14,8,'2018-12-10'),(46,'Аида Диван 3-х местный','Ткань/кожзаменитель 3 кат.',2,300,NULL,14,NULL,NULL),(47,'Аида Диван 3-х местный','Ткань/кожзаменитель 3 кат.',1,150,0,NULL,8,'2018-12-11');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `products` (
  `idProducts` int(11) NOT NULL AUTO_INCREMENT,
  `nameProduct` varchar(45) NOT NULL,
  `brand` varchar(45) NOT NULL,
  `quantity` int(11) NOT NULL,
  `purchasePrice` float NOT NULL,
  `price` float NOT NULL,
  `note` varchar(45) DEFAULT NULL,
  `nameProvider` varchar(45) NOT NULL,
  PRIMARY KEY (`idProducts`),
  UNIQUE KEY `nameProduct_UNIQUE` (`nameProduct`),
  KEY `nameProvider_idx` (`nameProvider`),
  CONSTRAINT `nameProviderProducts` FOREIGN KEY (`nameProvider`) REFERENCES `providers` (`nameprovider`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (5,'Берта Диван 3-х местный','фабрика “Лером”',1,1043.22,1360,'Ткань/кожзаменитель 3 кат.','фабрика “Лером”'),(6,'Аида Диван 3-х местный','фабрика “Лером”',6,110,150,'Ткань/кожзаменитель 3 кат.','фабрика “Лером”'),(7,'Аида Диван 2-х местный','ООО \"ММК\"',1,7680,10000,'Ткань/кожзаменитель 3 кат.','ПинскДрива'),(11,'Берта Кресло','ООО \"ММК\"',1,7536,9800,'Ткань/кожзаменитель 3 кат.','ОАО \"Бринт\"'),(12,'Шкаф детский','фабрика \"Аинт\"',1,5117,6730,'2 секционный','ОАО \"Бринт\"'),(13,'Стол детский модульный регулируемый','ООО \"ММК\"',5,7792,9900,'материал: дуб','ПинскДрива'),(14,'Волна Угловой сегмент','фабрика “Лером”',3,7400,9600,'Ткань/кожзаменитель 5 кат.','фабрика “Лером”'),(22,'Стул детскиц Chairman 250','фабрика \"РосСтул\"',3,540.5,780.5,'Материал: ткань-сетка / ткань TW','ОАО \"Бринт\"');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `providers`
--

DROP TABLE IF EXISTS `providers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `providers` (
  `idProviders` int(11) NOT NULL AUTO_INCREMENT,
  `nameProvider` varchar(45) NOT NULL,
  `country` varchar(45) NOT NULL,
  `town` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `contactPerson` varchar(45) NOT NULL,
  `contactNumber` varchar(45) NOT NULL,
  `note` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idProviders`),
  UNIQUE KEY `nameProvider_UNIQUE` (`nameProvider`),
  KEY `nameProvider_idx` (`nameProvider`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `providers`
--

LOCK TABLES `providers` WRITE;
/*!40000 ALTER TABLE `providers` DISABLE KEYS */;
INSERT INTO `providers` VALUES (1,'ОАО \"Бринт\"','Россия','Брянск','ул.Петра Глебки, д. 66','Александр Первый','+375298585678',''),(2,'фабрика “Лером”','Беларсь','Брест','ул.Юбилейная, д.12','Иван Семенов','+375293895678',''),(4,'ПинскДрива','Украина','Львов','ул.Первомайска, д.12','Артем Алеконт','+375293812348','');
/*!40000 ALTER TABLE `providers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staffs`
--

DROP TABLE IF EXISTS `staffs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `staffs` (
  `idStaff` int(11) NOT NULL AUTO_INCREMENT,
  `surnameStaff` varchar(45) NOT NULL,
  `nameStaff` varchar(45) NOT NULL,
  `positionStaff` varchar(45) DEFAULT NULL,
  `birthDayStaff` date DEFAULT NULL,
  `employmentDayStaff` date DEFAULT NULL,
  `numberStaff` varchar(45) DEFAULT NULL,
  `noteStaff` varchar(45) DEFAULT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`idStaff`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staffs`
--

LOCK TABLES `staffs` WRITE;
/*!40000 ALTER TABLE `staffs` DISABLE KEYS */;
INSERT INTO `staffs` VALUES (8,'Синий','Ярослав','Администратор','1993-12-09','2018-10-09','+375445687123',NULL,'zx','zx'),(9,'Гладин','Сергей','Продавец-консультант','1989-07-07','2018-10-08','+375445127163',NULL,'сс','сс'),(11,'Вересовая','Екатерина','Кассир','1999-03-21','2018-11-14','+3754451271111',NULL,'katya99','katya99');
/*!40000 ALTER TABLE `staffs` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-13 17:49:12
