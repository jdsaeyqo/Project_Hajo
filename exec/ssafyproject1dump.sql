-- MySQL dump 10.13  Distrib 8.0.30, for Linux (x86_64)
--
-- Host: localhost    Database: ssafyproject1
-- ------------------------------------------------------
-- Server version	8.0.30-0ubuntu0.20.04.2

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
-- Table structure for table `BOAST_BLOCK_TB`
--

DROP TABLE IF EXISTS `BOAST_BLOCK_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BOAST_BLOCK_TB` (
  `BOASTBLOCK_SEQ` int NOT NULL AUTO_INCREMENT,
  `BOAST_SEQ` int NOT NULL,
  `USER_UID` varchar(45) NOT NULL,
  PRIMARY KEY (`BOASTBLOCK_SEQ`),
  KEY `BOAST_BLOCK_FK_idx` (`BOAST_SEQ`),
  KEY `USER_BOAST_BLOCK_FK_idx` (`USER_UID`),
  CONSTRAINT `BOAST_BLOCK_FK` FOREIGN KEY (`BOAST_SEQ`) REFERENCES `BOAST_TB` (`BOAST_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `USER_BOAST_BLOCK_FK` FOREIGN KEY (`USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BOAST_BLOCK_TB`
--

LOCK TABLES `BOAST_BLOCK_TB` WRITE;
/*!40000 ALTER TABLE `BOAST_BLOCK_TB` DISABLE KEYS */;
/*!40000 ALTER TABLE `BOAST_BLOCK_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BOAST_LIKE_TB`
--

DROP TABLE IF EXISTS `BOAST_LIKE_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BOAST_LIKE_TB` (
  `BOASTLIKE_SEQ` int NOT NULL AUTO_INCREMENT,
  `BOAST_SEQ` int NOT NULL,
  `USER_UID` varchar(45) NOT NULL,
  `BOASTLIKE_TIME` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`BOASTLIKE_SEQ`),
  UNIQUE KEY `USER_UID` (`USER_UID`,`BOAST_SEQ`),
  KEY `feed_id_idx` (`BOAST_SEQ`),
  KEY `user_uid_idx` (`USER_UID`),
  CONSTRAINT `BOAST_LIKE_FK` FOREIGN KEY (`BOAST_SEQ`) REFERENCES `BOAST_TB` (`BOAST_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `USER_BOAST_LIKE_FK` FOREIGN KEY (`USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BOAST_LIKE_TB`
--

LOCK TABLES `BOAST_LIKE_TB` WRITE;
/*!40000 ALTER TABLE `BOAST_LIKE_TB` DISABLE KEYS */;
INSERT INTO `BOAST_LIKE_TB` VALUES (1,2,'2352393061','2022-08-18 14:10:39'),(2,4,'2393336713','2022-08-18 18:07:20'),(3,3,'2374360412','2022-08-19 01:59:45');
/*!40000 ALTER TABLE `BOAST_LIKE_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `BOAST_TB`
--

DROP TABLE IF EXISTS `BOAST_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BOAST_TB` (
  `BOAST_SEQ` int NOT NULL AUTO_INCREMENT,
  `USER_UID` varchar(45) DEFAULT NULL,
  `BOAST_TITLE` varchar(45) NOT NULL,
  `BOAST_CONTENT` text NOT NULL,
  `BOAST_IMG` varchar(2100) DEFAULT NULL,
  `BOAST_LIKECOUNT` int NOT NULL,
  `BOAST_AGRCOUNT` int NOT NULL,
  `BOAST_WRTTIME` timestamp NOT NULL,
  `BOAST_RPTCOUNT` int DEFAULT NULL,
  PRIMARY KEY (`BOAST_SEQ`),
  KEY `user_uid_idx` (`USER_UID`),
  CONSTRAINT `USER_BOAST_FK` FOREIGN KEY (`USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BOAST_TB`
--

LOCK TABLES `BOAST_TB` WRITE;
/*!40000 ALTER TABLE `BOAST_TB` DISABLE KEYS */;
INSERT INTO `BOAST_TB` VALUES (2,'2374360412','Í∞úÎ∞ú ÍøÄ??,'Í∞úÎ∞ú ?àÎ¨¥ ÍøÄ?ºÏù¥?§Ïöî\n\n?ºÎ•∏ Í≥†Ïàò Í∞úÎ∞ú?êÍ? ?òÍ≥† ?∂Ïñ¥??\n\n?∞Î¶¨ Î™®Îëê ?îÏù¥??!','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/BoardImage%2F2374360412%201660830794455?alt=media&token=37688fdb-5fdf-4a14-92f9-0047d81ec6c0',1,0,'2022-08-18 13:53:29',0),(3,'2374360412','ÏπòÏã§ Ï∂îÏ≤ú?©Îãà??,'?¥Î≤à????ÏπòÏã§?∏Îç∞ ?àÎ¨¥ Ï¢ãÏïÑ??Ï∂îÏ≤ú?¥Ïöî\n\nÏπòÏã§???êÍ∫º?åÏÑú ?ºÌäº?òÍ≥† Ï¢ãÎÑ§??n\n?∞Î¶¨ Î™®Îëê Í±¥Í∞ï?¥Ï†∏??','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/BoardImage%2F2374360412%201660830899642?alt=media&token=9021b138-7e33-400a-b4c2-ff1f46822c64',1,0,'2022-08-18 13:55:09',0),(4,'2352393061','?§Ïö¥??!','?§ÎûúÎßåÏóê ?±ÏÇ∞???àÏäµ?àÎã§! ?îÏ¶ò ?àÎ¨¥ ?¥Îèô??Î™ªÌï¥?? \n\n?¥ÏïºÍ≤†Îã§Í≥??ùÍ∞Å?òÎçò Ï∞∞ÎÇò?Ä?îÎç∞ Î™®Ï≤ò???¥Îèô????n\n?µÎãà???§ÎûúÎßåÏóê ?òÎãà ?àÎ¨¥ ?òÎìú?§Ïöî.. ?¨Îü¨Î∂ÑÎì§??\n\nÍ∞Ä?îÏî© ?¥Îèô?òÏãúÎ©¥ÏÑú ?®Ï? ?∏Ìîº ?ùÌôú ??ÎßàÎ¨¥Î¶??òÏãúÍ∏?\n\nÎ∞îÎûç?àÎã§!!','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/BoardImage%2F2352393061%201660831817957?alt=media&token=996875bc-4864-48dd-b870-a621bec4e442',1,0,'2022-08-18 14:10:25',0),(5,'2393336713','Í≥µÌÜµ1Î∞??òÍ≥†?àÏñ¥!!','?§ÎäòÎ°?Í≥µÌÜµ ?ÑÎ°ú?ùÌä∏Í∞Ä ?ùÎÇ¨?§Ïöî!\n?¨ÏßÑ?Ä ÎßàÏ?Îß??§ÌîÑ?ºÏù∏ ?òÏóÖ?êÏÑú Ï∞çÏóà?µÎãà??\n?îÏæå???¨ÏßÑ Í∞ôÏù¥ Ï∞çÏñ¥Ï£ºÏÖî??Í∞êÏÇ¨?¥Ïöî Ïª®Îãò?é„Öé\nÍ∑∏Í∞Ñ ?òÎì§?àÏ?Îß??ôÏãú???ïÎßê ?¨Î∞å?àÏñ¥??\nÏΩîÏπò?òÍ≥º ?Ä??Î∂ÑÎì§, ?πÌôî?êÏÑú????Î∂Ä?ÅÌï©?àÎã§!','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/BoardImage%2F2393336713%201660871261813?alt=media&token=5b018d7b-2bcf-4b2c-a9f1-45892877b278',0,0,'2022-08-19 01:07:48',0),(6,'2374360412','Î¨¥ÏÑ† ÎßàÏö∞??Ï∂îÏ≤ú?©Îãà??,'Î°úÏ???M331 Î¨¥ÏÑ† Î¨¥ÏÜå??ÎßàÏö∞?§Ïù∏??n?¥Î¶≠ ?åÎ¶¨Í∞Ä ?ïÎßê Ï°∞Ïö©?òÍ≥† Ï¢ãÏïÑ??\n?êÏù¥ ???¨Îûå?êÍ≤å??Ï°∞Í∏à Î∂àÌé∏???òÎèÑ ?àÏñ¥?î„Ö†','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/BoardImage%2F2374360412%201660874338741?alt=media&token=d20001b5-439b-419b-9d13-129a7281042f',0,0,'2022-08-19 01:59:07',0);
/*!40000 ALTER TABLE `BOAST_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DIARY_OBJSTICKER_TB`
--

DROP TABLE IF EXISTS `DIARY_OBJSTICKER_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DIARY_OBJSTICKER_TB` (
  `DIARYOBJ_SEQ` int NOT NULL,
  `STICKER_SEQ` int NOT NULL,
  KEY `diaryobj_id_idx` (`DIARYOBJ_SEQ`),
  KEY `stickers_id_idx` (`STICKER_SEQ`),
  CONSTRAINT `DIARY_OBJ_STICKER_FK` FOREIGN KEY (`DIARYOBJ_SEQ`) REFERENCES `DIARY_OBJ_TB` (`DIARYOBJ_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `LIST_OBJ_STICKER_FK` FOREIGN KEY (`STICKER_SEQ`) REFERENCES `LIST_STICKER_TB` (`STICKER_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DIARY_OBJSTICKER_TB`
--

LOCK TABLES `DIARY_OBJSTICKER_TB` WRITE;
/*!40000 ALTER TABLE `DIARY_OBJSTICKER_TB` DISABLE KEYS */;
/*!40000 ALTER TABLE `DIARY_OBJSTICKER_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DIARY_OBJTEXT_TB`
--

DROP TABLE IF EXISTS `DIARY_OBJTEXT_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DIARY_OBJTEXT_TB` (
  `OBJTEXT_SEQ` int NOT NULL AUTO_INCREMENT,
  `DIARYOBJ_SEQ` int NOT NULL,
  `OBJTEXT_CONTENT` text NOT NULL,
  `OBJTEXT_FONT` varchar(45) NOT NULL,
  `OBJTEXT_SIZE` int NOT NULL,
  `OBJTEXT_COLOR` varchar(20) NOT NULL COMMENT '''R.color.primary''',
  `OBJTEXT_ISBOLD` tinyint(1) NOT NULL,
  `OBJTEXT_ISITALIC` tinyint(1) NOT NULL,
  PRIMARY KEY (`OBJTEXT_SEQ`),
  KEY `DIARY_OBJ_TEXT_FK_idx` (`DIARYOBJ_SEQ`),
  CONSTRAINT `DIARY_OBJ_TEXT_FK` FOREIGN KEY (`DIARYOBJ_SEQ`) REFERENCES `DIARY_OBJ_TB` (`DIARYOBJ_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DIARY_OBJTEXT_TB`
--

LOCK TABLES `DIARY_OBJTEXT_TB` WRITE;
/*!40000 ALTER TABLE `DIARY_OBJTEXT_TB` DISABLE KEYS */;
INSERT INTO `DIARY_OBJTEXT_TB` VALUES (14,20,'?òÏùò ?§Ïù¥?¥Î¶¨ ?¥ÏïºÍ∏∞Î? ?®Î≥¥?∏Ïöî!','2',20,'#FFF5B51D',0,0),(17,23,'?òÏùò ?§Ïù¥?¥Î¶¨ ?¥ÏïºÍ∏∞Î? ?®Î≥¥?∏Ïöî!','0',30,'#FFFFFFFF',0,0),(18,24,'?òÏùò ?§Ïù¥?¥Î¶¨ ?¥ÏïºÍ∏∞Î? ?®Î≥¥?∏Ïöî!','0',30,'#FFFFFFFF',0,0),(19,26,'6??Î∂àÍΩÉ?Ä??,'',25,'#F0D64531',0,0),(26,36,'?òÍ≥† ÎßéÏúº?®Ïñ¥??!','0',35,'#FF28BC45',1,0),(28,38,'?òÍ≥†?àÏñ¥ ?§Îäò??,'0',30,'#FF374EBF',1,0);
/*!40000 ALTER TABLE `DIARY_OBJTEXT_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DIARY_OBJ_TB`
--

DROP TABLE IF EXISTS `DIARY_OBJ_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DIARY_OBJ_TB` (
  `DIARYOBJ_SEQ` int NOT NULL AUTO_INCREMENT,
  `DIARYPAGE_SEQ` int NOT NULL,
  `DIARYOBJ_TYPE` varchar(45) NOT NULL,
  `DIARYOBJ_XLOC` double NOT NULL,
  `DIARYOBJ_YLOC` double NOT NULL,
  `OBJTEXT_SEQ` int DEFAULT NULL,
  `OBJPIC_IMG` varchar(2100) DEFAULT NULL,
  PRIMARY KEY (`DIARYOBJ_SEQ`),
  KEY `DIARY_PAGE_OBJECT_FK_idx` (`DIARYPAGE_SEQ`),
  CONSTRAINT `DIARY_PAGE_OBJECT_FK` FOREIGN KEY (`DIARYPAGE_SEQ`) REFERENCES `DIARY_PAGE_TB` (`DIARYPAGE_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DIARY_OBJ_TB`
--

LOCK TABLES `DIARY_OBJ_TB` WRITE;
/*!40000 ALTER TABLE `DIARY_OBJ_TB` DISABLE KEYS */;
INSERT INTO `DIARY_OBJ_TB` VALUES (19,181,'pic',222,271,NULL,'https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/DiaryImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3862?alt=media&token=54207756-de8c-420f-9e64-b10acf042664'),(20,181,'text',500,863,14,NULL),(23,181,'text',283,935,17,NULL),(24,181,'text',174,1475,18,NULL),(25,241,'pic',44,127,NULL,'https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/DiaryImage%2Fcontent%3A%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F29828?alt=media&token=ac56ba71-624c-4ab0-8ce7-cd2e0e1f12d4'),(26,241,'text',62,572,19,NULL),(29,241,'pic',510,734,NULL,'https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/DiaryImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A31474?alt=media&token=ef619e9e-a5c8-4a04-b6f8-fc809a964977'),(36,241,'text',457,1206,26,NULL),(38,241,'text',447,1361,28,NULL);
/*!40000 ALTER TABLE `DIARY_OBJ_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DIARY_PAGE_TB`
--

DROP TABLE IF EXISTS `DIARY_PAGE_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DIARY_PAGE_TB` (
  `DIARYPAGE_SEQ` int NOT NULL AUTO_INCREMENT,
  `DIARYPAGE_NUM` int NOT NULL,
  `DIARY_SEQ` int NOT NULL,
  PRIMARY KEY (`DIARYPAGE_SEQ`),
  KEY `DIARY_PAGE_FK_idx` (`DIARY_SEQ`),
  CONSTRAINT `DIARY_PAGE_FK` FOREIGN KEY (`DIARY_SEQ`) REFERENCES `DIARY_TB` (`DIARY_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=271 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DIARY_PAGE_TB`
--

LOCK TABLES `DIARY_PAGE_TB` WRITE;
/*!40000 ALTER TABLE `DIARY_PAGE_TB` DISABLE KEYS */;
INSERT INTO `DIARY_PAGE_TB` VALUES (41,1,5),(42,2,5),(43,3,5),(44,4,5),(45,5,5),(46,6,5),(47,7,5),(48,8,5),(49,9,5),(50,10,5),(51,1,6),(52,2,6),(53,3,6),(54,4,6),(55,5,6),(56,6,6),(57,7,6),(58,8,6),(59,9,6),(60,10,6),(181,1,19),(182,2,19),(183,3,19),(184,4,19),(185,5,19),(186,6,19),(187,7,19),(188,8,19),(189,9,19),(190,10,19),(201,1,21),(202,2,21),(203,3,21),(204,4,21),(205,5,21),(206,6,21),(207,7,21),(208,8,21),(209,9,21),(210,10,21),(211,1,22),(212,2,22),(213,3,22),(214,4,22),(215,5,22),(216,6,22),(217,7,22),(218,8,22),(219,9,22),(220,10,22),(241,1,25),(242,2,25),(243,3,25),(244,4,25),(245,5,25),(246,6,25),(247,7,25),(248,8,25),(249,9,25),(250,10,25),(251,1,26),(252,2,26),(253,3,26),(254,4,26),(255,5,26),(256,6,26),(257,7,26),(258,8,26),(259,9,26),(260,10,26),(261,1,27),(262,2,27),(263,3,27),(264,4,27),(265,5,27),(266,6,27),(267,7,27),(268,8,27),(269,9,27),(270,10,27);
/*!40000 ALTER TABLE `DIARY_PAGE_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `DIARY_TB`
--

DROP TABLE IF EXISTS `DIARY_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `DIARY_TB` (
  `DIARY_SEQ` int NOT NULL AUTO_INCREMENT,
  `USER_UID` varchar(45) NOT NULL,
  `DIARY_TITLE` varchar(45) NOT NULL,
  `DIARY_TYPE` varchar(45) NOT NULL,
  PRIMARY KEY (`DIARY_SEQ`),
  KEY `user_uid_idx` (`USER_UID`),
  CONSTRAINT `USER_DIARY_FK` FOREIGN KEY (`USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `DIARY_TB`
--

LOCK TABLES `DIARY_TB` WRITE;
/*!40000 ALTER TABLE `DIARY_TB` DISABLE KEYS */;
INSERT INTO `DIARY_TB` VALUES (5,'2352393061','?¥ÏÉÅ???§Ïù¥?¥Î¶¨1','0'),(6,'2352393061','?¥ÏÉÅ???§Î¶¨?¥Î¶¨ 2','1'),(19,'_BbjgCc-vkNW6jnQ76U2FheKeGqMb69Wy2FQbQRpoHE','Î∞§Ìïò???§Ïù¥?¥Î¶¨','1'),(21,'2352393061','?§Ïù¥?¥Î¶¨?§Ïù¥?¥Î¶¨','0'),(22,'2352393061','Í∞Ä?òÎã§??,'1'),(25,'2393336713','?òÏùò Ï≤´Î≤àÏß??ºÍ∏∞','3'),(26,'2393336713','?òÏùò ?êÎ≤àÏß??ºÍ∏∞','1'),(27,'2393336713','?òÏùò ?∏Î≤àÏß??ºÍ∏∞','1');
/*!40000 ALTER TABLE `DIARY_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LIST_ARCHIVE_TB`
--

DROP TABLE IF EXISTS `LIST_ARCHIVE_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LIST_ARCHIVE_TB` (
  `ARCHIVE_SEQ` int NOT NULL,
  `ARCHIVE_NAME` varchar(45) DEFAULT NULL,
  `ARCHIVE_CONDPARAM` varchar(45) DEFAULT NULL,
  `ARCHIVE_COND` int DEFAULT NULL,
  `ARCHIVE_REWARDTYPE` varchar(10) DEFAULT NULL,
  `ARCHIVE_REWARDSEQ` int NOT NULL,
  PRIMARY KEY (`ARCHIVE_SEQ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LIST_ARCHIVE_TB`
--

LOCK TABLES `LIST_ARCHIVE_TB` WRITE;
/*!40000 ALTER TABLE `LIST_ARCHIVE_TB` DISABLE KEYS */;
INSERT INTO `LIST_ARCHIVE_TB` VALUES (1,'Ï≤??¨Ïù∏???çÎìù','point',1,'exp',100),(2,'?àÎ≤® 5 ?¨ÏÑ±','exp',500,'title',10),(3,'Ï≤??πÎ¶¨','win',1,'point',1000),(4,'?πÎ¶¨Î≥¥Îã§ Í∞íÏßÑ','winwin',1,'title',11),(5,'?¨Ïù∏??Î∂Ä???òÍ∏∞','point',1000,'exp',100),(6,'?¨Ïù∏???ºÎ????òÍ∏∞','point',10000,'exp',100),(7,'?πÎ???,'win',10,'exp',1000);
/*!40000 ALTER TABLE `LIST_ARCHIVE_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LIST_LEVEL_TB`
--

DROP TABLE IF EXISTS `LIST_LEVEL_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LIST_LEVEL_TB` (
  `LEVEL_SEQ` int NOT NULL AUTO_INCREMENT,
  `LEVEL_EXP` int DEFAULT NULL COMMENT '?àÎ≤®???§Î•¥??Í∏∞Ï??êÏù¥ ?òÎäî Í≤ΩÌóòÏπ?nLEVEL_SEQÍ∞Ä 1?¥Í≥† LEVEL_EXPÍ∞Ä 100?¥Î©¥, USER_EXPÍ∞Ä 100???òÎäî ?úÏ†ê?êÏÑú USER_LEVEL?Ä 2Í∞Ä ??,
  PRIMARY KEY (`LEVEL_SEQ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LIST_LEVEL_TB`
--

LOCK TABLES `LIST_LEVEL_TB` WRITE;
/*!40000 ALTER TABLE `LIST_LEVEL_TB` DISABLE KEYS */;
/*!40000 ALTER TABLE `LIST_LEVEL_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LIST_SKIN_TB`
--

DROP TABLE IF EXISTS `LIST_SKIN_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LIST_SKIN_TB` (
  `SKIN_SEQ` int NOT NULL AUTO_INCREMENT,
  `SKIN_NAME` varchar(45) NOT NULL,
  `SKIN_IMG` varchar(2100) DEFAULT NULL,
  `SKIN_DESC` varchar(200) NOT NULL,
  `SKIN_PRICE` int NOT NULL,
  `SKIN_HIT` int NOT NULL,
  `SKIN_ISONSALE` tinyint(1) NOT NULL,
  `SKIN_PERIOD` int NOT NULL,
  PRIMARY KEY (`SKIN_SEQ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LIST_SKIN_TB`
--

LOCK TABLES `LIST_SKIN_TB` WRITE;
/*!40000 ALTER TABLE `LIST_SKIN_TB` DISABLE KEYS */;
/*!40000 ALTER TABLE `LIST_SKIN_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LIST_STICKER_TB`
--

DROP TABLE IF EXISTS `LIST_STICKER_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LIST_STICKER_TB` (
  `STICKER_SEQ` int NOT NULL,
  `STICKER_NAME` varchar(45) NOT NULL,
  `STICKER_IMG` varchar(2100) DEFAULT NULL,
  `STICKER_DESC` varchar(200) NOT NULL,
  `STICKER_PRICE` int NOT NULL,
  `STICKER_HIT` int NOT NULL,
  `STICKER_ISONSALE` tinyint(1) NOT NULL,
  PRIMARY KEY (`STICKER_SEQ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LIST_STICKER_TB`
--

LOCK TABLES `LIST_STICKER_TB` WRITE;
/*!40000 ALTER TABLE `LIST_STICKER_TB` DISABLE KEYS */;
/*!40000 ALTER TABLE `LIST_STICKER_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LIST_TITLE_TB`
--

DROP TABLE IF EXISTS `LIST_TITLE_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `LIST_TITLE_TB` (
  `TITLE_SEQ` int NOT NULL AUTO_INCREMENT,
  `TITLE_NAME` varchar(45) NOT NULL,
  PRIMARY KEY (`TITLE_SEQ`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LIST_TITLE_TB`
--

LOCK TABLES `LIST_TITLE_TB` WRITE;
/*!40000 ALTER TABLE `LIST_TITLE_TB` DISABLE KEYS */;
INSERT INTO `LIST_TITLE_TB` VALUES (1,'?àÎÇ¥Í∏?),(2,'?ºÍ¥ÑÎ≥ÄÍ≤?),(3,'?ºÍ¥ÑÎ≥ÄÍ≤?),(4,'?ºÍ¥ÑÎ≥ÄÍ≤?),(5,'?ºÍ¥ÑÎ≥ÄÍ≤?),(6,'?ºÍ¥ÑÎ≥ÄÍ≤?),(7,'?ºÍ¥ÑÎ≥ÄÍ≤?),(8,'?ºÍ¥ÑÎ≥ÄÍ≤?),(9,'?ºÍ¥ÑÎ≥ÄÍ≤?),(10,'Í±∏ÏùåÎßàÎ? ?Ä'),(11,'Í∞ôÏù¥ ?òÏïÑÍ∞Ä??),(12,'?ºÍ¥ÑÎ≥ÄÍ≤?),(13,'?ºÍ¥ÑÎ≥ÄÍ≤?),(14,'?ºÍ¥ÑÎ≥ÄÍ≤?),(15,'?ºÍ¥ÑÎ≥ÄÍ≤?),(16,'?ºÍ¥ÑÎ≥ÄÍ≤?),(17,'?ºÍ¥ÑÎ≥ÄÍ≤?),(18,'?ºÍ¥ÑÎ≥ÄÍ≤?),(19,'?ºÍ¥ÑÎ≥ÄÍ≤?),(20,'?ºÍ¥ÑÎ≥ÄÍ≤?),(21,'?ºÍ¥ÑÎ≥ÄÍ≤?),(22,'Ï¥àÍ∏∞Ïπ?ò∏');
/*!40000 ALTER TABLE `LIST_TITLE_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MATCH_NOW_TB`
--

DROP TABLE IF EXISTS `MATCH_NOW_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MATCH_NOW_TB` (
  `NOW_SEQ` int NOT NULL AUTO_INCREMENT,
  `USER_UID` varchar(45) NOT NULL,
  `GRANDPLAN_SEQ` int NOT NULL,
  `PLAN_STATUS` varchar(2) DEFAULT NULL,
  `MATCH_USER_UID` varchar(45) NOT NULL,
  `MATCH_GRANDPLAN_SEQ` int NOT NULL,
  `MATCH_PLAN_STATUS` varchar(2) DEFAULT NULL,
  `MATCH_STARTDATE` date NOT NULL,
  `MATCH_ENDDATE` date NOT NULL,
  `MATCH_RESULT` varchar(2) NOT NULL COMMENT 'WI:?πÎ¶¨\nLO:?®Î∞∞\nWW:?àÏúà\nDR:Î¨¥ÏäπÎ∂Ä',
  `USER_PROGRESS` varchar(10) DEFAULT '',
  `MATCH_USER_PROGRESS` varchar(10) DEFAULT '',
  PRIMARY KEY (`NOW_SEQ`),
  KEY `USER_MATCH_NOW_FK_idx` (`USER_UID`),
  KEY `GRANDPLAN_SEQ` (`GRANDPLAN_SEQ`),
  KEY `MATCH_GRANDPLAN_SEQ` (`MATCH_GRANDPLAN_SEQ`),
  KEY `MATCH_USER_UID` (`MATCH_USER_UID`),
  CONSTRAINT `MATCH_NOW_TB_ibfk_1` FOREIGN KEY (`GRANDPLAN_SEQ`) REFERENCES `PLAN_GRAND_TB` (`GRANDPLAN_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `MATCH_NOW_TB_ibfk_2` FOREIGN KEY (`MATCH_GRANDPLAN_SEQ`) REFERENCES `PLAN_GRAND_TB` (`GRANDPLAN_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `MATCH_NOW_TB_ibfk_3` FOREIGN KEY (`MATCH_USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `USER_MATCH_NOW_FK` FOREIGN KEY (`USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MATCH_NOW_TB`
--

LOCK TABLES `MATCH_NOW_TB` WRITE;
/*!40000 ALTER TABLE `MATCH_NOW_TB` DISABLE KEYS */;
INSERT INTO `MATCH_NOW_TB` VALUES (9,'_BbjgCc-vkNW6jnQ76U2FheKeGqMb69Wy2FQbQRpoHE',14,'-','2374360412',13,'-','2022-08-20','2022-08-23','-','',''),(13,'2393336713',18,'-','_BbjgCc-vkNW6jnQ76U2FheKeGqMb69Wy2FQbQRpoHE',26,'-','2022-08-20','2022-08-21','L','',''),(14,'_BbjgCc-vkNW6jnQ76U2FheKeGqMb69Wy2FQbQRpoHE',26,'-','2393336713',18,'-','2022-08-20','2022-08-21','-','',''),(15,'2393336713',21,'-','2352393061',12,'-','2022-08-20','2022-08-22','WW','',''),(16,'2352393061',12,'-','2393336713',21,'-','2022-08-20','2022-08-22','-','','');
/*!40000 ALTER TABLE `MATCH_NOW_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MATCH_PBCHALL_TB`
--

DROP TABLE IF EXISTS `MATCH_PBCHALL_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MATCH_PBCHALL_TB` (
  `PBCHALL_SEQ` int NOT NULL AUTO_INCREMENT,
  `USER_UID` varchar(45) NOT NULL,
  `GRANDPLAN_SEQ` int NOT NULL,
  `MATCH_PERIOD` int NOT NULL,
  PRIMARY KEY (`PBCHALL_SEQ`),
  KEY `GRANDPLAN_SEQ` (`GRANDPLAN_SEQ`),
  KEY `USER_UID` (`USER_UID`),
  CONSTRAINT `MATCH_PBCHALL_TB_ibfk_1` FOREIGN KEY (`GRANDPLAN_SEQ`) REFERENCES `PLAN_GRAND_TB` (`GRANDPLAN_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `MATCH_PBCHALL_TB_ibfk_2` FOREIGN KEY (`USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MATCH_PBCHALL_TB`
--

LOCK TABLES `MATCH_PBCHALL_TB` WRITE;
/*!40000 ALTER TABLE `MATCH_PBCHALL_TB` DISABLE KEYS */;
INSERT INTO `MATCH_PBCHALL_TB` VALUES (5,'2393336713',22,5);
/*!40000 ALTER TABLE `MATCH_PBCHALL_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MATCH_PSCHALL_TB`
--

DROP TABLE IF EXISTS `MATCH_PSCHALL_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `MATCH_PSCHALL_TB` (
  `PSCHALL_SEQ` int NOT NULL AUTO_INCREMENT,
  `USER_UID` varchar(45) NOT NULL COMMENT '?ëÏÑ±??Í∏∞Ï??ºÎ°ú ?∞Ïó¨Ï°åÏúºÎØÄÎ°?\nUSER_UID : ?ëÏÑ±?êÏùò UID\nMATCH_USER_UID : Î∞õÎäî ?¨Îûå??UID\nMATCH_PBCHALL_TB Ï∞∏Í≥† Í∞Ä??,
  `GRANDPLAN_SEQ` int NOT NULL,
  `MATCH_USER_UID` varchar(45) NOT NULL,
  `MATCH_GRANDPLAN_SEQ` int NOT NULL,
  `MATCH_PERIOD` int DEFAULT NULL,
  PRIMARY KEY (`PSCHALL_SEQ`),
  KEY `USER_MATCH_PSCHALL_FK_idx` (`USER_UID`),
  KEY `GRANDPLAN_SEQ` (`GRANDPLAN_SEQ`),
  KEY `MATCH_USER_UID` (`MATCH_USER_UID`),
  KEY `MATCH_GRANDPLAN_SEQ` (`MATCH_GRANDPLAN_SEQ`),
  CONSTRAINT `MATCH_PSCHALL_TB_ibfk_1` FOREIGN KEY (`GRANDPLAN_SEQ`) REFERENCES `PLAN_GRAND_TB` (`GRANDPLAN_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `MATCH_PSCHALL_TB_ibfk_2` FOREIGN KEY (`MATCH_USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `MATCH_PSCHALL_TB_ibfk_3` FOREIGN KEY (`USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `MATCH_PSCHALL_TB_ibfk_4` FOREIGN KEY (`MATCH_GRANDPLAN_SEQ`) REFERENCES `PLAN_GRAND_TB` (`GRANDPLAN_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `USER_MATCH_PSCHALL_FK` FOREIGN KEY (`USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MATCH_PSCHALL_TB`
--

LOCK TABLES `MATCH_PSCHALL_TB` WRITE;
/*!40000 ALTER TABLE `MATCH_PSCHALL_TB` DISABLE KEYS */;
INSERT INTO `MATCH_PSCHALL_TB` VALUES (13,'2393336713',23,'2374360412',13,3);
/*!40000 ALTER TABLE `MATCH_PSCHALL_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PLAN_GRAND_TB`
--

DROP TABLE IF EXISTS `PLAN_GRAND_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PLAN_GRAND_TB` (
  `GRANDPLAN_SEQ` int NOT NULL AUTO_INCREMENT,
  `USER_UID` varchar(45) DEFAULT NULL,
  `GRANDPLAN_TITLE` varchar(45) NOT NULL,
  `GRANDPLAN_DESC` varchar(200) DEFAULT NULL,
  `GRANDPLAN_STARTDATE` date NOT NULL,
  `GRANDPLAN_ENDDATE` date NOT NULL,
  `GRANDPLAN_ISDONE` tinyint(1) NOT NULL,
  `GRANDPLAN_ISMATCH` tinyint(1) NOT NULL,
  `GRANDPLAN_TTMPLAN` int NOT NULL COMMENT 'TOTAL MID PLAN',
  `GRANDPLAN_TDMPLAN` int NOT NULL COMMENT 'TOTAL DONE MID PLAN',
  `GRANDPLAN_COLOR` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`GRANDPLAN_SEQ`),
  KEY `user_uid_idx` (`USER_UID`),
  CONSTRAINT `USER_PLAN_GRAND_FK` FOREIGN KEY (`USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PLAN_GRAND_TB`
--

LOCK TABLES `PLAN_GRAND_TB` WRITE;
/*!40000 ALTER TABLE `PLAN_GRAND_TB` DISABLE KEYS */;
INSERT INTO `PLAN_GRAND_TB` VALUES (9,'_BbjgCc-vkNW6jnQ76U2FheKeGqMb69Wy2FQbQRpoHE','Í≥µÌÜµ ?ÑÎ°ú?ùÌä∏','?∏Ìîº Í≥µÌÜµ','2022-08-18','2022-08-24',0,1,1,0,'#ffffff'),(10,'2352393061','?§Ïù¥?¥Ìä∏','?∞Îßê ÍπåÏ? 10kg Í∞êÎüâ!','2022-08-18','2022-12-31',0,1,1,0,'#ffffff'),(12,'2352393061','?åÏïÖ?åÎûú','?åÏïÖ??Î∞∞Ïö∞Í∏??ÑÌïú ?åÎûú','2022-08-18','2022-12-18',0,1,1,0,'#ffffff'),(13,'2374360412','Î°??§Î≤Ñ ?¨ÏÑ±','?§Î≤Ñ Í∞ÄÏ¶àÏïÑ','2022-08-18','2022-12-22',0,0,1,0,'#ffffff'),(14,'_BbjgCc-vkNW6jnQ76U2FheKeGqMb69Wy2FQbQRpoHE','?∏Ìîº 2?ôÍ∏∞','2?ôÍ∏∞??','2022-08-19','2022-08-23',0,1,1,0,'#ffffff'),(16,'_BbjgCc-vkNW6jnQ76U2FheKeGqMb69Wy2FQbQRpoHE','Í≥µÌÜµ?åÍ≥†?òÍ∏∞','Í≥µÌÜµ?ÑÎ°ú?ùÌä∏ ?åÍ≥†','2022-08-19','2022-08-26',0,1,1,0,'#ffffff'),(18,'2393336713','Í≥µÌÜµ ?ÑÎ°ú?ùÌä∏ Ï§ÄÎπÑÌïòÍ∏?,'7Ï°??åÏù¥??!','2022-05-19','2022-08-19',0,1,1,0,'#ffffff'),(21,'2393336713','CS Î∞ïÏÇ¨?òÍ∏∞','Ï∑®ÏóÖ?òÏûê...','2022-08-19','2022-09-13',0,1,2,0,'#ffffff'),(22,'2393336713','1??1?§Ìä∏?àÏπ≠','ÏΩîÏñ¥Î•?ÎßåÎì§?¥Ïïº ?òÎùºÍ∞Ä ?∞Îã§','2022-08-17','2023-08-19',0,1,2,0,'#ffffff'),(23,'2393336713','Ïß??ÑÏ≤¥ ?ÄÏ≤?Üå','?¥Í±∏ Î¥ÑÏóê ?àÏñ¥???òÎäî??,'2022-08-19','2022-09-19',0,0,1,0,'#ffffff'),(24,'2393336713','??Î™ÖÏûë?åÏÑ§ ?§Ïãú?ΩÍ∏∞','?òÎ£® Î∞òÍ∂åÎß??ΩÍ∏∞','2022-07-19','2022-07-20',1,0,1,1,'#ffffff'),(26,'_BbjgCc-vkNW6jnQ76U2FheKeGqMb69Wy2FQbQRpoHE','Í≥µÌÜµ?ÑÎ°ú?ùÌä∏ ?åÍ≥†','?åÍ≥†?òÍ∏∞','2022-08-19','2022-08-26',0,1,1,0,'#ffffff');
/*!40000 ALTER TABLE `PLAN_GRAND_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PLAN_MID_TB`
--

DROP TABLE IF EXISTS `PLAN_MID_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PLAN_MID_TB` (
  `MIDPLAN_SEQ` int NOT NULL AUTO_INCREMENT,
  `GRANDPLAN_SEQ` int NOT NULL,
  `MIDPLAN_TITLE` varchar(45) NOT NULL,
  `MIDPLAN_DESC` varchar(200) DEFAULT NULL,
  `MIDPLAN_STARTDATE` date NOT NULL,
  `MIDPLAN_ENDDATE` date NOT NULL,
  `MIDPLAN_ISDONE` tinyint(1) NOT NULL,
  `MIDPLAN_TTSPLAN` int NOT NULL COMMENT 'TOTAL SMALL PLAN',
  `MIDPLAN_TDSPLAN` int NOT NULL COMMENT 'TOTAL DONE SMALL PLAN',
  `MIDPLAN_COLOR` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`MIDPLAN_SEQ`),
  KEY `grandplan_id_idx` (`GRANDPLAN_SEQ`),
  CONSTRAINT `PLAN_GRAND_MID_FK` FOREIGN KEY (`GRANDPLAN_SEQ`) REFERENCES `PLAN_GRAND_TB` (`GRANDPLAN_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PLAN_MID_TB`
--

LOCK TABLES `PLAN_MID_TB` WRITE;
/*!40000 ALTER TABLE `PLAN_MID_TB` DISABLE KEYS */;
INSERT INTO `PLAN_MID_TB` VALUES (9,9,'ÎßàÎ¨¥Î¶??òÍ∏∞','?ÑÎ°ú?ùÌä∏ ÎßàÎ¨¥Î¶?,'2022-08-18','2022-08-20',0,3,0,'#FFFFBD1F'),(11,10,'Îß§Ïùº Ï°∞ÍπÖ?òÍ∏∞','????Í∞?Îß§Ïùº Ï°∞ÍπÖ?òÍ∏∞','2022-08-18','2022-09-18',0,32,0,'#74C1FD'),(13,12,'?ºÏïÑ??Î∞∞Ïö∞Í∏?,'?ºÏïÑ???ÑÍ≥°?òÍ∏∞','2022-08-18','2022-09-25',0,39,0,'#FFFFBD1F'),(14,13,'Î∏åÎ°†Ï¶? ?¨ÏÑ±','Î∏åÎ°†Ï¶? Í∞ÄÏ¶àÏïÑ','2022-08-18','2022-10-18',0,62,0,'#FFFFBD1F'),(15,14,'?ºÏñ¥','??,'2022-08-19','2022-08-21',0,3,0,'#FFFFBD1F'),(17,16,'Í≥µÌÜµ ?åÍ≥†?òÍ∏∞','?åÍ≥†?òÍ∏∞','2022-08-19','2022-08-22',0,4,0,'#FF5722'),(21,18,'?§Îäò??Í≥ÑÌöç','Îß§ÏùºÎß§Ïùº ?†Ïùº','2022-05-19','2022-08-19',0,93,2,'#FFFFBD1F'),(26,21,'?¥ÏÇ∞?òÌïô','?òÎ£®???ÅÏÉÅ ?úÍ∞ú??Î≥¥Í∏∞','2022-07-19','2022-08-18',0,31,0,'#FFFFBD1F'),(27,21,'Ïª¥Ìì®??Íµ¨Ï°∞?§Ô∏è?çüî?,'?õÎÇ† ?ùÍ∞Å?úÎã§...','2022-08-19','2022-09-19',0,32,0,'#F0D8887D'),(29,22,'8???¥Îèô?ºÍ∏∞','?¥Î≤à???¥Í±∞??nhttps://youtu.be/5fnEEzi_ev0','2022-08-01','2022-08-31',0,31,0,'#74C1FD'),(30,23,'??Î∞?ÏπòÏö∞Í∏?,'Ï∂îÏÑù ?ÑÏóê ?¨Í∏¥ ÏπòÏõå?êÍ∏∞','2022-08-19','2022-08-27',0,9,0,'#FF7783BF'),(31,24,'Ï≤úÎ°ú??†ï','Ï≤úÎ°ú??†ï ???ΩÍ∏∞','2022-07-19','2022-07-20',1,2,2,'#FFF5B51D'),(33,26,'Í≥µÌÜµ ?åÍ≥† ?ëÏÑ±','?ëÏÑ±','2022-08-19','2022-08-23',0,5,0,'#FFFFBD1F');
/*!40000 ALTER TABLE `PLAN_MID_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PLAN_SMALL_TB`
--

DROP TABLE IF EXISTS `PLAN_SMALL_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PLAN_SMALL_TB` (
  `SMALLPLAN_SEQ` int NOT NULL AUTO_INCREMENT,
  `MIDPLAN_SEQ` int NOT NULL,
  `SMALLPLAN_DATE` date NOT NULL,
  `SMALLPLAN_ISDONE` tinyint(1) NOT NULL,
  `SMALLPLAN_TTTASK` int NOT NULL COMMENT 'TOTAL TASK',
  `SMALLPLAN_TDTASK` int NOT NULL COMMENT 'TOTAL DAILY TASK',
  PRIMARY KEY (`SMALLPLAN_SEQ`),
  KEY `midplan_id_idx` (`MIDPLAN_SEQ`),
  CONSTRAINT `PLAN_MID_SMALL_FK` FOREIGN KEY (`MIDPLAN_SEQ`) REFERENCES `PLAN_MID_TB` (`MIDPLAN_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=649 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PLAN_SMALL_TB`
--

LOCK TABLES `PLAN_SMALL_TB` WRITE;
/*!40000 ALTER TABLE `PLAN_SMALL_TB` DISABLE KEYS */;
INSERT INTO `PLAN_SMALL_TB` VALUES (92,9,'2022-08-18',0,5,0),(93,9,'2022-08-19',0,1,0),(94,9,'2022-08-20',0,0,0),(100,11,'2022-08-18',0,2,0),(101,11,'2022-08-19',0,0,0),(102,11,'2022-08-20',0,0,0),(103,11,'2022-08-21',0,0,0),(104,11,'2022-08-22',0,0,0),(105,11,'2022-08-23',0,0,0),(106,11,'2022-08-24',0,0,0),(107,11,'2022-08-25',0,0,0),(108,11,'2022-08-26',0,0,0),(109,11,'2022-08-27',0,0,0),(110,11,'2022-08-28',0,0,0),(111,11,'2022-08-29',0,0,0),(112,11,'2022-08-30',0,0,0),(113,11,'2022-08-31',0,0,0),(114,11,'2022-09-01',0,0,0),(115,11,'2022-09-02',0,0,0),(116,11,'2022-09-03',0,0,0),(117,11,'2022-09-04',0,0,0),(118,11,'2022-09-05',0,0,0),(119,11,'2022-09-06',0,0,0),(120,11,'2022-09-07',0,0,0),(121,11,'2022-09-08',0,0,0),(122,11,'2022-09-09',0,0,0),(123,11,'2022-09-10',0,0,0),(124,11,'2022-09-11',0,0,0),(125,11,'2022-09-12',0,0,0),(126,11,'2022-09-13',0,0,0),(127,11,'2022-09-14',0,0,0),(128,11,'2022-09-15',0,0,0),(129,11,'2022-09-16',0,0,0),(130,11,'2022-09-17',0,0,0),(131,11,'2022-09-18',0,0,0),(164,13,'2022-08-18',0,1,0),(165,13,'2022-08-19',0,0,0),(166,13,'2022-08-20',0,0,0),(167,13,'2022-08-21',0,0,0),(168,13,'2022-08-22',0,0,0),(169,13,'2022-08-23',0,0,0),(170,13,'2022-08-24',0,0,0),(171,13,'2022-08-25',0,0,0),(172,13,'2022-08-26',0,0,0),(173,13,'2022-08-27',0,0,0),(174,13,'2022-08-28',0,0,0),(175,13,'2022-08-29',0,0,0),(176,13,'2022-08-30',0,0,0),(177,13,'2022-08-31',0,0,0),(178,13,'2022-09-01',0,0,0),(179,13,'2022-09-02',0,0,0),(180,13,'2022-09-03',0,0,0),(181,13,'2022-09-04',0,0,0),(182,13,'2022-09-05',0,0,0),(183,13,'2022-09-06',0,0,0),(184,13,'2022-09-07',0,0,0),(185,13,'2022-09-08',0,0,0),(186,13,'2022-09-09',0,0,0),(187,13,'2022-09-10',0,0,0),(188,13,'2022-09-11',0,0,0),(189,13,'2022-09-12',0,0,0),(190,13,'2022-09-13',0,0,0),(191,13,'2022-09-14',0,0,0),(192,13,'2022-09-15',0,0,0),(193,13,'2022-09-16',0,0,0),(194,13,'2022-09-17',0,0,0),(195,13,'2022-09-18',0,0,0),(196,13,'2022-09-19',0,0,0),(197,13,'2022-09-20',0,0,0),(198,13,'2022-09-21',0,0,0),(199,13,'2022-09-22',0,0,0),(200,13,'2022-09-23',0,0,0),(201,13,'2022-09-24',0,0,0),(202,13,'2022-09-25',0,0,0),(203,14,'2022-08-18',0,1,0),(204,14,'2022-08-19',0,1,0),(205,14,'2022-08-20',0,0,0),(206,14,'2022-08-21',0,0,0),(207,14,'2022-08-22',0,0,0),(208,14,'2022-08-23',0,0,0),(209,14,'2022-08-24',0,0,0),(210,14,'2022-08-25',0,0,0),(211,14,'2022-08-26',0,0,0),(212,14,'2022-08-27',0,0,0),(213,14,'2022-08-28',0,0,0),(214,14,'2022-08-29',0,0,0),(215,14,'2022-08-30',0,0,0),(216,14,'2022-08-31',0,0,0),(217,14,'2022-09-01',0,0,0),(218,14,'2022-09-02',0,0,0),(219,14,'2022-09-03',0,0,0),(220,14,'2022-09-04',0,0,0),(221,14,'2022-09-05',0,0,0),(222,14,'2022-09-06',0,0,0),(223,14,'2022-09-07',0,0,0),(224,14,'2022-09-08',0,0,0),(225,14,'2022-09-09',0,0,0),(226,14,'2022-09-10',0,0,0),(227,14,'2022-09-11',0,0,0),(228,14,'2022-09-12',0,0,0),(229,14,'2022-09-13',0,0,0),(230,14,'2022-09-14',0,0,0),(231,14,'2022-09-15',0,0,0),(232,14,'2022-09-16',0,0,0),(233,14,'2022-09-17',0,0,0),(234,14,'2022-09-18',0,0,0),(235,14,'2022-09-19',0,0,0),(236,14,'2022-09-20',0,0,0),(237,14,'2022-09-21',0,0,0),(238,14,'2022-09-22',0,0,0),(239,14,'2022-09-23',0,0,0),(240,14,'2022-09-24',0,0,0),(241,14,'2022-09-25',0,0,0),(242,14,'2022-09-26',0,0,0),(243,14,'2022-09-27',0,0,0),(244,14,'2022-09-28',0,0,0),(245,14,'2022-09-29',0,0,0),(246,14,'2022-09-30',0,0,0),(247,14,'2022-10-01',0,0,0),(248,14,'2022-10-02',0,0,0),(249,14,'2022-10-03',0,0,0),(250,14,'2022-10-04',0,0,0),(251,14,'2022-10-05',0,0,0),(252,14,'2022-10-06',0,0,0),(253,14,'2022-10-07',0,0,0),(254,14,'2022-10-08',0,0,0),(255,14,'2022-10-09',0,0,0),(256,14,'2022-10-10',0,0,0),(257,14,'2022-10-11',0,0,0),(258,14,'2022-10-12',0,0,0),(259,14,'2022-10-13',0,0,0),(260,14,'2022-10-14',0,0,0),(261,14,'2022-10-15',0,0,0),(262,14,'2022-10-16',0,0,0),(263,14,'2022-10-17',0,0,0),(264,14,'2022-10-18',0,0,0),(265,15,'2022-08-19',0,1,0),(266,15,'2022-08-20',0,1,0),(267,15,'2022-08-21',0,0,0),(320,17,'2022-08-19',0,2,0),(321,17,'2022-08-20',0,0,0),(322,17,'2022-08-21',0,0,0),(323,17,'2022-08-22',0,0,0),(431,21,'2022-05-19',0,0,1),(432,21,'2022-05-20',0,0,3),(433,21,'2022-05-21',0,0,5),(434,21,'2022-05-22',0,0,0),(435,21,'2022-05-23',0,0,3),(436,21,'2022-05-24',0,0,1),(437,21,'2022-05-25',0,0,1),(438,21,'2022-05-26',0,0,1),(439,21,'2022-05-27',0,0,1),(440,21,'2022-05-28',0,0,1),(441,21,'2022-05-29',0,0,1),(442,21,'2022-05-30',0,0,1),(443,21,'2022-05-31',0,0,1),(444,21,'2022-06-01',0,0,2),(445,21,'2022-06-02',0,0,5),(446,21,'2022-06-03',0,0,1),(447,21,'2022-06-04',0,0,1),(448,21,'2022-06-05',0,0,1),(449,21,'2022-06-06',0,0,3),(450,21,'2022-06-07',0,0,1),(451,21,'2022-06-08',0,0,5),(452,21,'2022-06-09',0,0,3),(453,21,'2022-06-10',0,0,1),(454,21,'2022-06-11',0,0,5),(455,21,'2022-06-12',0,0,1),(456,21,'2022-06-13',0,0,1),(457,21,'2022-06-14',0,0,1),(458,21,'2022-06-15',0,0,1),(459,21,'2022-06-16',0,0,3),(460,21,'2022-06-17',0,0,1),(461,21,'2022-06-18',0,0,1),(462,21,'2022-06-19',0,0,2),(463,21,'2022-06-20',0,0,1),(464,21,'2022-06-21',0,0,1),(465,21,'2022-06-22',0,0,1),(466,21,'2022-06-23',0,0,5),(467,21,'2022-06-24',0,0,1),(468,21,'2022-06-25',0,0,1),(469,21,'2022-06-26',0,0,1),(470,21,'2022-06-27',0,0,3),(471,21,'2022-06-28',0,0,1),(472,21,'2022-06-29',0,0,3),(473,21,'2022-06-30',0,0,1),(474,21,'2022-07-01',0,0,1),(475,21,'2022-07-02',0,0,1),(476,21,'2022-07-03',0,0,1),(477,21,'2022-07-04',0,0,1),(478,21,'2022-07-05',0,0,1),(479,21,'2022-07-06',0,0,1),(480,21,'2022-07-07',0,0,1),(481,21,'2022-07-08',0,0,3),(482,21,'2022-07-09',0,0,1),(483,21,'2022-07-10',0,0,1),(484,21,'2022-07-11',0,0,1),(485,21,'2022-07-12',0,0,1),(486,21,'2022-07-13',0,0,1),(487,21,'2022-07-14',0,0,5),(488,21,'2022-07-15',0,0,3),(489,21,'2022-07-16',0,0,1),(490,21,'2022-07-17',0,0,3),(491,21,'2022-07-18',0,0,3),(492,21,'2022-07-19',0,0,3),(493,21,'2022-07-20',0,0,3),(494,21,'2022-07-21',0,0,1),(495,21,'2022-07-22',0,0,1),(496,21,'2022-07-23',0,0,1),(497,21,'2022-07-24',0,0,1),(498,21,'2022-07-25',0,0,1),(499,21,'2022-07-26',0,0,1),(500,21,'2022-07-27',0,0,1),(501,21,'2022-07-28',0,0,1),(502,21,'2022-07-29',0,0,1),(503,21,'2022-07-30',0,0,1),(504,21,'2022-07-31',0,0,1),(505,21,'2022-08-01',0,0,1),(506,21,'2022-08-02',0,0,1),(507,21,'2022-08-03',0,0,1),(508,21,'2022-08-04',0,0,1),(509,21,'2022-08-05',0,0,1),(510,21,'2022-08-06',0,0,3),(511,21,'2022-08-07',0,0,5),(512,21,'2022-08-08',0,0,5),(513,21,'2022-08-09',0,0,1),(514,21,'2022-08-10',0,0,1),(515,21,'2022-08-11',0,0,1),(516,21,'2022-08-12',0,0,5),(517,21,'2022-08-13',0,0,1),(518,21,'2022-08-14',0,0,1),(519,21,'2022-08-15',0,0,1),(520,21,'2022-08-16',0,0,5),(521,21,'2022-08-17',0,0,1),(522,21,'2022-08-18',0,0,1),(523,21,'2022-08-19',1,24,3),(534,26,'2022-07-19',0,0,0),(535,26,'2022-07-20',0,0,0),(536,26,'2022-07-21',0,0,0),(537,26,'2022-07-22',0,0,0),(538,26,'2022-07-23',0,0,0),(539,26,'2022-07-24',0,0,0),(540,26,'2022-07-25',0,0,0),(541,26,'2022-07-26',0,0,0),(542,26,'2022-07-27',0,0,0),(543,26,'2022-07-28',0,0,0),(544,26,'2022-07-29',0,0,0),(545,26,'2022-07-30',0,0,0),(546,26,'2022-07-31',0,0,0),(547,26,'2022-08-01',0,0,0),(548,26,'2022-08-02',0,0,0),(549,26,'2022-08-03',0,0,0),(550,26,'2022-08-04',0,0,0),(551,26,'2022-08-05',0,0,0),(552,26,'2022-08-06',0,0,0),(553,26,'2022-08-07',0,0,0),(554,26,'2022-08-08',0,0,0),(555,26,'2022-08-09',0,0,0),(556,26,'2022-08-10',0,0,0),(557,26,'2022-08-11',0,0,0),(558,26,'2022-08-12',0,0,0),(559,26,'2022-08-13',0,0,0),(560,26,'2022-08-14',0,0,0),(561,26,'2022-08-15',0,0,0),(562,26,'2022-08-16',0,0,0),(563,26,'2022-08-17',0,0,0),(564,26,'2022-08-18',0,3,0),(565,27,'2022-08-19',0,3,1),(566,27,'2022-08-20',0,0,0),(567,27,'2022-08-21',0,0,0),(568,27,'2022-08-22',0,0,0),(569,27,'2022-08-23',0,0,0),(570,27,'2022-08-24',0,0,0),(571,27,'2022-08-25',0,0,0),(572,27,'2022-08-26',0,0,0),(573,27,'2022-08-27',0,0,0),(574,27,'2022-08-28',0,0,0),(575,27,'2022-08-29',0,0,0),(576,27,'2022-08-30',0,0,0),(577,27,'2022-08-31',0,0,0),(578,27,'2022-09-01',0,0,0),(579,27,'2022-09-02',0,0,0),(580,27,'2022-09-03',0,0,0),(581,27,'2022-09-04',0,0,0),(582,27,'2022-09-05',0,0,0),(583,27,'2022-09-06',0,0,0),(584,27,'2022-09-07',0,0,0),(585,27,'2022-09-08',0,0,0),(586,27,'2022-09-09',0,0,0),(587,27,'2022-09-10',0,0,0),(588,27,'2022-09-11',0,0,0),(589,27,'2022-09-12',0,0,0),(590,27,'2022-09-13',0,0,0),(591,27,'2022-09-14',0,0,0),(592,27,'2022-09-15',0,0,0),(593,27,'2022-09-16',0,0,0),(594,27,'2022-09-17',0,0,0),(595,27,'2022-09-18',0,0,0),(596,27,'2022-09-19',0,0,0),(598,29,'2022-08-01',0,0,1),(599,29,'2022-08-02',0,0,1),(600,29,'2022-08-03',0,0,1),(601,29,'2022-08-04',0,0,1),(602,29,'2022-08-05',0,0,1),(603,29,'2022-08-06',0,0,1),(604,29,'2022-08-07',0,0,1),(605,29,'2022-08-08',0,0,1),(606,29,'2022-08-09',0,0,1),(607,29,'2022-08-10',0,0,1),(608,29,'2022-08-11',0,0,1),(609,29,'2022-08-12',0,0,1),(610,29,'2022-08-13',0,0,1),(611,29,'2022-08-14',0,0,1),(612,29,'2022-08-15',0,0,1),(613,29,'2022-08-16',0,0,1),(614,29,'2022-08-17',0,0,1),(615,29,'2022-08-18',0,0,1),(616,29,'2022-08-19',0,1,1),(617,29,'2022-08-20',0,1,1),(618,29,'2022-08-21',0,1,1),(619,29,'2022-08-22',0,0,1),(620,29,'2022-08-23',0,0,1),(621,29,'2022-08-24',0,0,1),(622,29,'2022-08-25',0,0,1),(623,29,'2022-08-26',0,0,1),(624,29,'2022-08-27',0,0,1),(625,29,'2022-08-28',0,0,1),(626,29,'2022-08-29',0,0,1),(627,29,'2022-08-30',0,0,1),(628,29,'2022-08-31',0,0,1),(629,30,'2022-08-19',0,3,0),(630,30,'2022-08-20',0,3,0),(631,30,'2022-08-21',0,1,0),(632,30,'2022-08-22',0,0,0),(633,30,'2022-08-23',0,0,0),(634,30,'2022-08-24',0,0,0),(635,30,'2022-08-25',0,0,0),(636,30,'2022-08-26',0,0,0),(637,30,'2022-08-27',0,0,0),(638,31,'2022-07-19',1,1,1),(639,31,'2022-07-20',1,1,1),(644,33,'2022-08-19',0,2,0),(645,33,'2022-08-20',0,1,0),(646,33,'2022-08-21',0,0,0),(647,33,'2022-08-22',0,0,0),(648,33,'2022-08-23',0,0,0);
/*!40000 ALTER TABLE `PLAN_SMALL_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `PLAN_TASK_TB`
--

DROP TABLE IF EXISTS `PLAN_TASK_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PLAN_TASK_TB` (
  `TASK_SEQ` int NOT NULL AUTO_INCREMENT,
  `SMALLPLAN_SEQ` int NOT NULL,
  `TASK_TITLE` varchar(45) NOT NULL,
  `TASK_ISDONE` tinyint(1) NOT NULL,
  `TASK_MEMO` varchar(200) DEFAULT NULL,
  `TASK_IMG` varchar(2100) DEFAULT NULL,
  `TASK_ALARMTIME` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`TASK_SEQ`),
  KEY `smallplan_id_idx` (`SMALLPLAN_SEQ`),
  CONSTRAINT `PLAN_SMALL_TASK_FK` FOREIGN KEY (`SMALLPLAN_SEQ`) REFERENCES `PLAN_SMALL_TB` (`SMALLPLAN_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PLAN_TASK_TB`
--

LOCK TABLES `PLAN_TASK_TB` WRITE;
/*!40000 ALTER TABLE `PLAN_TASK_TB` DISABLE KEYS */;
INSERT INTO `PLAN_TASK_TB` VALUES (13,92,'ucc ÎßåÎì§Í∏?,0,'ucc ÎßåÎì§Í∏?,'https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(14,92,'ppt ?ëÏÑ±',0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(15,92,'?ÄÎ≥?ÎßåÎì§Í∏?,0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(16,92,'?∞Ï∂úÎ¨??ëÏÑ±',0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(17,92,'??Î∞∞Ìè¨ ?òÍ∏∞',0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(18,100,'Ï°∞ÍπÖ?òÍ∏∞',0,'Ï°∞ÍπÖ?òÍ∏∞','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(19,100,'?Ä???êÎü¨??Î®πÍ∏∞',0,'?êÎü¨??Î®πÍ∏∞','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(20,93,'Î∞úÌëú?òÍ∏∞',0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(24,164,'?ÖÎ≥¥ 1?òÏù¥ÏßÄ ?∞Ïäµ?òÍ∏∞',0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(25,203,'??Å¨ Í≤åÏûÑ 8???òÍ∏∞',0,'123','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(26,265,'?ä„Ñπ??,0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(27,266,'?Ö„Ñπ',0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(30,204,'??Å¨ 8???òÍ∏∞',0,'?êÎë•','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(34,320,'?åÍ≥†Î°?Î∞ÄÎ¶∞Í±∞ ?ëÏÑ±',0,'?åÍ≥† Î∞Ä?∏Îã§...','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(36,320,'?åÍ≥† Í≥µÏú†?òÍ∏∞',0,'8??ÏØ?','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(42,523,'Î∞úÌëú Ï§ÄÎπÑÌïòÍ∏?,1,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(48,565,'?†ÌäúÎ∏??ÅÏÉÅ ?úÏ≤≠?òÍ∏∞',1,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(49,565,'Í≥µÎ???Í±??ÑÍ∏∞?òÍ∏∞',0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(50,565,'?§ÌÑ∞?îÏóê??Í≥µÏú†?òÍ∏∞',0,'?§Îäò?Ä ?úÏãúÍ∞ÑÎßå???ùÎÇ¥Í∏?','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(51,616,'?§ÎäòÏπ??àÎãà?',0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(52,617,'?§ÎäòÏπ??àÎãà?',0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(53,618,'?§ÎäòÏπ??àÎãà?',0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(54,629,'Î¨ºÍ±¥ Î®ºÏ? ?®Í∏∞',0,'?àÏ™Ω ?•Ïãù??Ï°∞Ïã¨??,'https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(55,629,'Ï≤?ÜåÍ∏??åÎ¶¨Í∏?,0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(56,629,'?∞Î°ú ?ïÎ¶¨?¥Ïïº??Î¨ºÍ±¥ ÎπºÎëêÍ∏?,0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(57,630,'ÎπºÎëî Î¨ºÍ±¥ Î≤ÑÎ¶¨Í∏?,0,'ÎπºÎëî Î¨ºÍ±¥ Î≤ÑÎ¶¨Í∏?,'https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(58,630,'?•Ïãù??Îπ°Îπ° ??∏∞',0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(59,630,'Ï∞ΩÌ? ??∏∞',0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(60,631,'Î∞îÎã• ??∏∞',0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(61,638,'?ûÌåå?????ΩÍ∏∞',1,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(62,639,'?∑Ìåå???ΩÍ∏∞',1,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(64,644,'Î∞úÌëú ?åÍ≥†',0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(65,644,'Í∞úÎ∞ú ?åÍ≥†',0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï'),(66,645,'Î∞úÌëú?åÍ≥†',0,'','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/TaskImage%2Fcontent%3A%2Fcom.android.providers.media.documents%2Fdocument%2Fimage%253A3813?alt=media&token=202e7d14-874d-4fce-a491-bcf70979e35b','?åÎûå ?§Ï†ï');
/*!40000 ALTER TABLE `PLAN_TASK_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SHRPLAN_BLOCK_TB`
--

DROP TABLE IF EXISTS `SHRPLAN_BLOCK_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SHRPLAN_BLOCK_TB` (
  `SHRPLANBLOCK_SEQ` int NOT NULL AUTO_INCREMENT,
  `SHRPLAN_SEQ` int NOT NULL,
  `USER_UID` varchar(45) NOT NULL,
  PRIMARY KEY (`SHRPLANBLOCK_SEQ`),
  KEY `SHRPLAN_BLOCK_FK_idx` (`SHRPLAN_SEQ`),
  KEY `USER_SHRPLAN_BLOCK_FK_idx` (`USER_UID`),
  CONSTRAINT `SHRPLAN_BLOCK_FK` FOREIGN KEY (`SHRPLAN_SEQ`) REFERENCES `SHRPLAN_TB` (`SHRPLAN_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `USER_SHRPLAN_BLOCK_FK` FOREIGN KEY (`USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SHRPLAN_BLOCK_TB`
--

LOCK TABLES `SHRPLAN_BLOCK_TB` WRITE;
/*!40000 ALTER TABLE `SHRPLAN_BLOCK_TB` DISABLE KEYS */;
/*!40000 ALTER TABLE `SHRPLAN_BLOCK_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SHRPLAN_BMK_TB`
--

DROP TABLE IF EXISTS `SHRPLAN_BMK_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SHRPLAN_BMK_TB` (
  `SHRPLANBMK_SEQ` int NOT NULL AUTO_INCREMENT,
  `SHRPLAN_SEQ` int NOT NULL,
  `USER_UID` varchar(45) NOT NULL,
  `SHRPLANBMK_TIME` timestamp NULL DEFAULT NULL,
  `SHRPLANLBMK_TIME` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`SHRPLANBMK_SEQ`),
  UNIQUE KEY `USER_UID` (`USER_UID`,`SHRPLAN_SEQ`),
  KEY `user_uid_idx` (`USER_UID`),
  KEY `SHRPLAN_BMK_FK_idx` (`SHRPLAN_SEQ`),
  CONSTRAINT `SHRPLAN_BMK_FK` FOREIGN KEY (`SHRPLAN_SEQ`) REFERENCES `SHRPLAN_TB` (`SHRPLAN_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `USER_SHRPLAN_BMK_FK` FOREIGN KEY (`USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SHRPLAN_BMK_TB`
--

LOCK TABLES `SHRPLAN_BMK_TB` WRITE;
/*!40000 ALTER TABLE `SHRPLAN_BMK_TB` DISABLE KEYS */;
INSERT INTO `SHRPLAN_BMK_TB` VALUES (1,1,'2374360412',NULL,'2022-08-18 23:00:43.486000'),(3,1,'2352393061',NULL,'2022-08-18 23:16:08.258000'),(4,3,'2352393061',NULL,'2022-08-18 23:33:05.648000'),(5,3,'2393336713',NULL,'2022-08-19 01:48:11.216000');
/*!40000 ALTER TABLE `SHRPLAN_BMK_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SHRPLAN_LIKE_TB`
--

DROP TABLE IF EXISTS `SHRPLAN_LIKE_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SHRPLAN_LIKE_TB` (
  `SHRPLANLIKE_SEQ` int NOT NULL AUTO_INCREMENT,
  `SHRPLAN_SEQ` int NOT NULL,
  `USER_UID` varchar(45) NOT NULL,
  `SHRPLANLIKE_TIME` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`SHRPLANLIKE_SEQ`),
  UNIQUE KEY `USER_UID` (`USER_UID`,`SHRPLAN_SEQ`),
  KEY `user_uid_idx` (`USER_UID`),
  KEY `SHRPLAN_LIKE_FK_idx` (`SHRPLAN_SEQ`),
  CONSTRAINT `SHRPLAN_LIKE_FK` FOREIGN KEY (`SHRPLAN_SEQ`) REFERENCES `SHRPLAN_TB` (`SHRPLAN_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `USER_SHRPLAN_LIKE_FK` FOREIGN KEY (`USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SHRPLAN_LIKE_TB`
--

LOCK TABLES `SHRPLAN_LIKE_TB` WRITE;
/*!40000 ALTER TABLE `SHRPLAN_LIKE_TB` DISABLE KEYS */;
INSERT INTO `SHRPLAN_LIKE_TB` VALUES (1,1,'2374360412','2022-08-18 14:00:37'),(3,1,'2352393061','2022-08-18 14:15:58'),(4,3,'2352393061','2022-08-18 14:33:05'),(8,5,'2352393061','2022-08-19 01:54:26'),(10,1,'2393336713','2022-08-19 01:55:02');
/*!40000 ALTER TABLE `SHRPLAN_LIKE_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SHRPLAN_TB`
--

DROP TABLE IF EXISTS `SHRPLAN_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SHRPLAN_TB` (
  `SHRPLAN_SEQ` int NOT NULL AUTO_INCREMENT,
  `USER_UID` varchar(45) DEFAULT NULL,
  `SHRPLAN_TITLE` varchar(45) NOT NULL,
  `SHRPLAN_CONTENT` text NOT NULL,
  `SHRPLAN_CATEGORY` varchar(45) NOT NULL,
  `GRANDPLAN_SEQ` int NOT NULL,
  `SHRPLAN_LIKECOUNT` int NOT NULL,
  `SHRPLAN_BMKCOUNT` int NOT NULL,
  `SHRPLAN_WRTTIME` timestamp NOT NULL,
  `SHRPLAN_PERIOD` int DEFAULT NULL,
  `SHRPLAN_RPTCOUNT` int DEFAULT NULL,
  `SHRPLAN_SUMMARY` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`SHRPLAN_SEQ`),
  KEY `user_uid_idx` (`USER_UID`),
  KEY `PLAN_GRAND_SHRPLAN_FK_idx` (`GRANDPLAN_SEQ`),
  CONSTRAINT `PLAN_GRAND_SHRPLAN_FK` FOREIGN KEY (`GRANDPLAN_SEQ`) REFERENCES `PLAN_GRAND_TB` (`GRANDPLAN_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `USER_SHRPLAN_FK` FOREIGN KEY (`USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SHRPLAN_TB`
--

LOCK TABLES `SHRPLAN_TB` WRITE;
/*!40000 ALTER TABLE `SHRPLAN_TB` DISABLE KEYS */;
INSERT INTO `SHRPLAN_TB` VALUES (1,'2352393061','????Í∞?Îß§Ïùº Ï°∞ÍπÖ?òÍ∏∞!','Îß§Ïùº ?ïÌï¥Ïß??úÍ∞Ñ???ïÌï¥ ?¥Îèô???©Îãà??n\n?àÏãú ) ?Ä??Î®πÏ? ??7??8???ôÏïà Ï°∞ÍπÖ?òÍ∏∞\n\n?òÏ?Í∞Ä ?ΩÌï¥Ïß????àÏúºÎØÄÎ°?Ï£ºÏúÑ??Îß§Ïùº ?¥Îèô\n\n?òÍ∏∞Î°??àÎã§Í≥??åÎ¶¨Î©??îÏö± Ï¢ãÏäµ?àÎã§','?¥Îèô',10,3,2,'2022-08-17 15:00:00',30,0,'?∞ÎßêÍπåÏ? 10kg ÎπºÍ∏∞'),(3,'2352393061','?ºÏïÑ?∏Î°ú ?ºÎùº?úÎìú OSTÎ•??∞Ïäµ?©Îãà??,'1Ï£ºÏ∞® : ?ÖÎ≥¥ 1?òÏù¥ÏßÄ ?∞Ïäµ?òÍ∏∞\n\n2Ï£ºÏ∞® : ?ÖÎ≥¥ 1?òÏù¥ÏßÄ Î≥µÏäµ Î∞?2?òÏù¥ÏßÄ ?∞Ïäµ\n\n3Ï£ºÏ∞® : ?ÖÎ≥¥ 2?òÏù¥ÏßÄ Î≥µÏäµ Î∞?3?òÏù¥ÏßÄ ?∞Ïäµ\n\n4Ï£ºÏ∞® : ?ÖÎ≥¥ 3?òÏù¥ÏßÄ ?∞Ïäµ Î∞?ÎßàÎ¨¥Î¶?,'?åÏïÖ',12,1,2,'2022-08-17 15:00:00',30,0,'?ºÏïÑ?∏Î°ú ?ºÎùº?úÎìú OST?∞Ï£º?òÍ∏∞'),(5,'2393336713','???úÎπÑ???ÑÎ°ú?ùÌä∏Î•??ÑÏàò?òÍ∏∞ÍπåÏ???Í≥ºÏ†ï?ÖÎãà??,'?ÑÏ?????Í±∞Ïòà??','?ôÏäµ',18,1,0,'2022-08-18 15:00:00',30,0,'ÏΩîÎî© Í≥µÎ?');
/*!40000 ALTER TABLE `SHRPLAN_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_ARCHIVE_TB`
--

DROP TABLE IF EXISTS `USER_ARCHIVE_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USER_ARCHIVE_TB` (
  `USER_ARCHIVE_SEQ` int NOT NULL AUTO_INCREMENT,
  `ARCHIVE_SEQ` int NOT NULL,
  `USER_UID` varchar(45) NOT NULL,
  `USER_ARCHIVE_ISDONE` tinyint(1) DEFAULT NULL,
  `USER_ARCHIVE_ISRECEIVED` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`USER_ARCHIVE_SEQ`),
  KEY `ARCHIVEments_id_idx` (`ARCHIVE_SEQ`),
  KEY `user_uid_idx` (`USER_UID`),
  CONSTRAINT `LIST_ARCHIVE_USER_FK` FOREIGN KEY (`ARCHIVE_SEQ`) REFERENCES `LIST_ARCHIVE_TB` (`ARCHIVE_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `USER_ARCHIVE_FK` FOREIGN KEY (`USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_ARCHIVE_TB`
--

LOCK TABLES `USER_ARCHIVE_TB` WRITE;
/*!40000 ALTER TABLE `USER_ARCHIVE_TB` DISABLE KEYS */;
INSERT INTO `USER_ARCHIVE_TB` VALUES (1,1,'2393336713',0,0),(2,2,'2393336713',0,0),(3,3,'2393336713',0,0),(4,4,'2393336713',0,0),(5,5,'2393336713',0,0),(6,6,'2393336713',0,0),(7,7,'2393336713',0,0),(8,1,'2352393061',0,0),(9,2,'2352393061',0,0),(10,3,'2352393061',0,0),(11,4,'2352393061',0,0),(12,5,'2352393061',0,0),(13,6,'2352393061',0,0),(14,7,'2352393061',0,0),(15,1,'_BbjgCc-vkNW6jnQ76U2FheKeGqMb69Wy2FQbQRpoHE',0,0),(16,2,'_BbjgCc-vkNW6jnQ76U2FheKeGqMb69Wy2FQbQRpoHE',0,0),(17,3,'_BbjgCc-vkNW6jnQ76U2FheKeGqMb69Wy2FQbQRpoHE',0,0),(18,4,'_BbjgCc-vkNW6jnQ76U2FheKeGqMb69Wy2FQbQRpoHE',0,0),(19,5,'_BbjgCc-vkNW6jnQ76U2FheKeGqMb69Wy2FQbQRpoHE',0,0),(20,6,'_BbjgCc-vkNW6jnQ76U2FheKeGqMb69Wy2FQbQRpoHE',0,0),(21,7,'_BbjgCc-vkNW6jnQ76U2FheKeGqMb69Wy2FQbQRpoHE',0,0),(22,1,'2374360412',0,0),(23,2,'2374360412',0,0),(24,3,'2374360412',0,0),(25,4,'2374360412',0,0),(26,5,'2374360412',0,0),(27,6,'2374360412',0,0),(28,7,'2374360412',0,0),(29,1,'108245288156623437159',0,0),(30,2,'108245288156623437159',0,0),(31,3,'108245288156623437159',0,0),(32,4,'108245288156623437159',0,0),(33,5,'108245288156623437159',0,0),(34,6,'108245288156623437159',0,0),(35,7,'108245288156623437159',0,0);
/*!40000 ALTER TABLE `USER_ARCHIVE_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_BLOCK_TB`
--

DROP TABLE IF EXISTS `USER_BLOCK_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USER_BLOCK_TB` (
  `BLOCK_SEQ` int NOT NULL AUTO_INCREMENT,
  `USER_UID` varchar(45) NOT NULL,
  `BLOCK_UID` varchar(45) NOT NULL,
  PRIMARY KEY (`BLOCK_SEQ`),
  KEY `USER_UID_idx` (`USER_UID`),
  KEY `BLOCK_UID_idx` (`BLOCK_UID`),
  CONSTRAINT `BLOCK_UID` FOREIGN KEY (`BLOCK_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `USER_UID` FOREIGN KEY (`USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_BLOCK_TB`
--

LOCK TABLES `USER_BLOCK_TB` WRITE;
/*!40000 ALTER TABLE `USER_BLOCK_TB` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_BLOCK_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_HISTORY_TB`
--

DROP TABLE IF EXISTS `USER_HISTORY_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USER_HISTORY_TB` (
  `HISTORY_SEQ` int NOT NULL AUTO_INCREMENT,
  `USER_UID` varchar(45) NOT NULL,
  `MATCH_USER_NICKNAME` varchar(45) NOT NULL,
  `MATCH_USER_EXP` int DEFAULT NULL,
  `MATCH_USER_TITLE` int DEFAULT NULL,
  `MATCH_RESULT` varchar(2) NOT NULL COMMENT 'WI:?πÎ¶¨\nLO:?®Î∞∞\nWW:?àÏúà\nDR:Î¨¥ÏäπÎ∂Ä',
  `MATCH_USER_LEVEL` int DEFAULT NULL,
  PRIMARY KEY (`HISTORY_SEQ`),
  KEY `USER_HISTORY_FK_idx` (`USER_UID`),
  CONSTRAINT `USER_HISTORY_FK` FOREIGN KEY (`USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_HISTORY_TB`
--

LOCK TABLES `USER_HISTORY_TB` WRITE;
/*!40000 ALTER TABLE `USER_HISTORY_TB` DISABLE KEYS */;
INSERT INTO `USER_HISTORY_TB` VALUES (1,'2374360412','asdf',0,1,'WW',NULL),(2,'2374360412','ssssjh',0,1,'L',NULL);
/*!40000 ALTER TABLE `USER_HISTORY_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_SKIN_TB`
--

DROP TABLE IF EXISTS `USER_SKIN_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USER_SKIN_TB` (
  `SKIN_SEQ` int NOT NULL AUTO_INCREMENT,
  `USER_UID` varchar(45) NOT NULL,
  `SKIN_NAME` varchar(45) NOT NULL,
  `USERSKIN_EXPIREDATE` date NOT NULL,
  KEY `skins_id_idx` (`SKIN_SEQ`),
  KEY `user_uid_idx` (`USER_UID`),
  CONSTRAINT `LIST_SKIN_USER_FK` FOREIGN KEY (`SKIN_SEQ`) REFERENCES `LIST_SKIN_TB` (`SKIN_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `USER_SKIN_FK` FOREIGN KEY (`USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_SKIN_TB`
--

LOCK TABLES `USER_SKIN_TB` WRITE;
/*!40000 ALTER TABLE `USER_SKIN_TB` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_SKIN_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_STICKER_TB`
--

DROP TABLE IF EXISTS `USER_STICKER_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USER_STICKER_TB` (
  `STICKER_SEQ` int NOT NULL,
  `USER_UID` varchar(45) NOT NULL,
  `STICKER_NAME` varchar(45) NOT NULL,
  KEY `user_uid_idx` (`USER_UID`),
  KEY `LIST_STICKER_USER_FK` (`STICKER_SEQ`),
  CONSTRAINT `LIST_STICKER_USER_FK` FOREIGN KEY (`STICKER_SEQ`) REFERENCES `LIST_STICKER_TB` (`STICKER_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `USER_STICKER_FK` FOREIGN KEY (`USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_STICKER_TB`
--

LOCK TABLES `USER_STICKER_TB` WRITE;
/*!40000 ALTER TABLE `USER_STICKER_TB` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_STICKER_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_TB`
--

DROP TABLE IF EXISTS `USER_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USER_TB` (
  `USER_UID` varchar(45) NOT NULL,
  `USER_EMAIL` varchar(45) NOT NULL,
  `USER_LOGINTYPE` char(1) NOT NULL,
  `USER_NICKNAME` varchar(45) NOT NULL,
  `USER_IMG` varchar(2100) DEFAULT NULL,
  `TITLE_SEQ` int NOT NULL,
  `USER_POINT` int NOT NULL,
  `USER_CPOINT` int NOT NULL COMMENT 'MONTHLY POINT',
  `SKIN_SEQ` int NOT NULL,
  `USER_WIN` int NOT NULL,
  `USER_LOSE` int NOT NULL,
  `USER_DRAW` int NOT NULL,
  `USER_WINWIN` int NOT NULL,
  `USER_WINSTREAK` int NOT NULL COMMENT '?∞Ïäπ ?üÏàò',
  `USER_ONMATCH` tinyint(1) DEFAULT NULL,
  `USER_ATTEND` int NOT NULL COMMENT '?ÑÏ†Å Ï∂úÏÑù??,
  `USER_ATTENDSTREAK` int NOT NULL COMMENT '?∞ÏÜç Ï∂úÏÑù ?üÏàò',
  `USER_ATTENDTODAY` tinyint(1) NOT NULL COMMENT '?§Îäò??Ï∂úÏÑù?¨Î?',
  `USER_EXP` int NOT NULL,
  `USER_TTTASK` int NOT NULL COMMENT 'TOTAL TASK',
  `USER_TDTASK` int NOT NULL COMMENT 'TOTAL DONE TASK',
  `USER_DLTASK` int NOT NULL COMMENT 'DAILY TASK',
  `USER_DDTASK` int NOT NULL COMMENT 'DAILY DONE TASK',
  `USER_COMMENT` varchar(200) DEFAULT NULL COMMENT '?ÅÌÉúÎ©îÏÑ∏ÏßÄ',
  PRIMARY KEY (`USER_UID`),
  UNIQUE KEY `user_email_UNIQUE` (`USER_EMAIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_TB`
--

LOCK TABLES `USER_TB` WRITE;
/*!40000 ALTER TABLE `USER_TB` DISABLE KEYS */;
INSERT INTO `USER_TB` VALUES ('_BbjgCc-vkNW6jnQ76U2FheKeGqMb69Wy2FQbQRpoHE','Woway2488@naver.com','N','ssssjh','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/hajo_icon.png?alt=media&token=5e8f5069-805b-4c3e-9930-30d4a7e44893',1,-4200,0,1,0,0,0,0,0,1,0,0,0,0,15,0,0,0,''),('108245288156623437159','hosan130@naver.com','G','Íµ¨Í??úÎê®','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/hajo_icon.png?alt=media&token=5e8f5069-805b-4c3e-9930-30d4a7e44893',1,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,''),('2352393061','?ì„Öì@.na','K','asdf','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/hajo_icon.png?alt=media&token=5e8f5069-805b-4c3e-9930-30d4a7e44893',1,-1400,0,1,0,0,0,0,0,1,0,0,0,0,8,0,0,0,''),('2374360412','toy9910@naver.com','K','??èÑÎ¶¨ÌÉï','https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/hajo_icon.png?alt=media&token=5e8f5069-805b-4c3e-9930-30d4a7e44893',1,100,100,1,0,1,0,1,0,1,0,0,0,160,20,6,0,6,''),('2393336713','?á„Ñπ?á„Ñπ?á„Ñπ@.?ä„Öá?π„ÖÅ','K','Î∞ïÏûê??,'https://firebasestorage.googleapis.com/v0/b/hajo-82aa3.appspot.com/o/hajo_icon.png?alt=media&token=5e8f5069-805b-4c3e-9930-30d4a7e44893',1,1000,0,1,0,0,0,0,0,1,0,0,0,0,20,8,0,8,'');
/*!40000 ALTER TABLE `USER_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_TITLE_TB`
--

DROP TABLE IF EXISTS `USER_TITLE_TB`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USER_TITLE_TB` (
  `USER_TITLE_SEQ` int NOT NULL AUTO_INCREMENT,
  `USER_UID` varchar(45) DEFAULT NULL,
  `TITLE_SEQ` int NOT NULL,
  PRIMARY KEY (`USER_TITLE_SEQ`),
  KEY `titles_id_idx` (`TITLE_SEQ`),
  KEY `user_uid_idx` (`USER_UID`),
  CONSTRAINT `LIST_TITLE_USER_FK` FOREIGN KEY (`TITLE_SEQ`) REFERENCES `LIST_TITLE_TB` (`TITLE_SEQ`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `USER_TITLE_FK` FOREIGN KEY (`USER_UID`) REFERENCES `USER_TB` (`USER_UID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_TITLE_TB`
--

LOCK TABLES `USER_TITLE_TB` WRITE;
/*!40000 ALTER TABLE `USER_TITLE_TB` DISABLE KEYS */;
/*!40000 ALTER TABLE `USER_TITLE_TB` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (4);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-19 11:15:03
