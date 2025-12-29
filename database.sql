-- MySQL dump 10.13  Distrib 8.0.44, for Win64 (x86_64)
--
-- Host: localhost    Database: yurt_camasirhanesi
-- ------------------------------------------------------
-- Server version	8.0.44

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `makineler`
--

DROP TABLE IF EXISTS `makineler`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `makineler` (
  `id_makine` int NOT NULL AUTO_INCREMENT,
  `makine_tip` enum('camasir','kurutma') NOT NULL DEFAULT 'camasir',
  `makine_durum` enum('dolu','musait','arizali','hurda','bakimda') NOT NULL DEFAULT 'musait',
  `kullanim_sayisi` int DEFAULT '0',
  PRIMARY KEY (`id_makine`),
  UNIQUE KEY `id_makine_UNIQUE` (`id_makine`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `makineler`
--

LOCK TABLES `makineler` WRITE;
/*!40000 ALTER TABLE `makineler` DISABLE KEYS */;
INSERT INTO `makineler` VALUES (1,'camasir','musait',1),(2,'camasir','dolu',1),(3,'camasir','musait',2),(4,'camasir','musait',1),(5,'kurutma','musait',0),(6,'kurutma','musait',1),(7,'kurutma','bakimda',1),(8,'kurutma','hurda',1),(9,'camasir','musait',0);
/*!40000 ALTER TABLE `makineler` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `randevu`
--

DROP TABLE IF EXISTS `randevu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `randevu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ogrenci_id` varchar(45) NOT NULL,
  `makine_id` varchar(45) NOT NULL,
  `tarih` varchar(45) NOT NULL,
  `saat` varchar(45) NOT NULL,
  `aktif` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `randevu`
--

LOCK TABLES `randevu` WRITE;
/*!40000 ALTER TABLE `randevu` DISABLE KEYS */;
INSERT INTO `randevu` VALUES (5,'3','1','28.12.2025','09:30 - 10:00',1),(6,'1','2','29.12.2025','08:30 - 09:00',1);
/*!40000 ALTER TABLE `randevu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `userid` int unsigned NOT NULL AUTO_INCREMENT,
  `name_surname` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `userpassword` varchar(45) NOT NULL,
  `usertype` enum('ogrenci','gorevli','teknisyen','mudur') NOT NULL DEFAULT 'ogrenci',
  PRIMARY KEY (`userid`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `userid_UNIQUE` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Elif Yılmaz','elif_yilmaz','1234','ogrenci'),(2,'Zeynep Kaya','zeynep_kaya','1234','ogrenci'),(3,'Ayşe Demir','ayse_demir','1234','ogrenci'),(4,'Selin Yıldız','selin_yildiz','1234','ogrenci'),(5,'Deniz Arslan','deniz_arslan','1234','ogrenci'),(6,'Ceren Koç','ceren_koc','1234','gorevli'),(7,'Derya Tekin','derya_tekin','1234','teknisyen'),(8,'Asena Türk','asena_turk','1234','mudur');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-12-29 18:00:01
