-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 52.78.53.155    Database: ssafy
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'ROLE_USER','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyRW1haWwiOiJnaXllb25AZ21haWwuY29tIiwic3ViIjoiZ2l5ZW9uQGdtYWlsLmNvbSIsImV4cCI6MTY2MTEzNDI3MH0.hSPjmwp2UK4B-AcBKOny0BduOm8jgt3kTj9njVN_iFI','졸려요!','안녕하세요!','giyeon@gmail.com',4,'giyeon','01011111111','$2a$10$wdjJ3cV./liFoigCCKLWPObt6oYeAhuRnmIAW9swwtLyqK0VYeqyK','2022-08-19 07:44:13','2022-08-19 10:29:59'),(2,'ROLE_USER','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyRW1haWwiOiJybGF4b2drMDA2QG5hdmVyLmNvbSIsInN1YiI6InJsYXhvZ2swMDZAbmF2ZXIuY29tIiwiZXhwIjoxNjYxMTIxOTA0fQ.nkFJk0L-cvK9e6G40wJUrnouR8FXEhmvNicm9-xZhZ8',NULL,NULL,'rlaxogk006@naver.com',4,'tttkim','01022222222','$2a$10$lczVEKKN2Ilw8pXZFBpdru7OQWxeIIXw7ggObKSonrd875t45W6Q2','2022-08-19 07:45:00',NULL),(3,'ROLE_USER','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyRW1haWwiOiJndXM0ODA1QG5hdmVyLmNvbSIsInN1YiI6Imd1czQ4MDVAbmF2ZXIuY29tIiwiZXhwIjoxNjYxMTM0NjU5fQ.ojcM5rObOeFg-WKS38fmsaqo4J_hYiWUXDx_A7CJPmg','죽...여줘.....','안녕하세요 개발자 꿈나무랍니다~','gus4805@naver.com',4,'김싸피','01099999999','$2a$10$6giHVzJfquGUu.goy824uuJvvTwvnyB6rRzOhQ84OOcdTjmx8DLnG','2022-08-19 07:46:33','2022-08-19 07:47:17'),(4,'ROLE_USER','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyRW1haWwiOiJhZG1pbkBzc2FmeS5jb20iLCJzdWIiOiJhZG1pbkBzc2FmeS5jb20iLCJleHAiOjE2NjExMzE4ODB9.ChUspHztyJuUTO1_EAmBVxMn1LZIHzG4Mf133XjnLi4',NULL,NULL,'admin@ssafy.com',4,'admin','01033333333','$2a$10$Qaw0Pb7QB5EoRxw9CkQOJe4GQ8tgblUD1wiZDwr/Hphs9tu3SauaS','2022-08-19 08:56:55',NULL),(5,'ROLE_USER',NULL,NULL,NULL,'hayoung@ssafy.com',4,'hayoung','01044444444','$2a$10$.Ru5fHIo4wAyF4SK8XMUkOjFCnI4pLnDSPterrbo4zfIBJXKdF0zO','2022-08-19 09:02:43',NULL),(6,'ROLE_USER',NULL,NULL,NULL,'youngjin@ssafy.com',4,'youngjin','01055555555','$2a$10$/7e5mYimAPLqrwe/vgleJ.VbqsKkUEvzc9a4VY2rH2KRvMePrTNqG','2022-08-19 09:03:47',NULL),(7,'ROLE_USER','eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyRW1haWwiOiJzdXdoYW4xQHNzYWZ5LmNvbSIsInN1YiI6InN1d2hhbjFAc3NhZnkuY29tIiwiZXhwIjoxNjYxMTMwMzEyfQ.WMc3fe9QrE8x-EtdJhJ0pUVrmp7GGxwzjQUlF-G2sEY',NULL,NULL,'suwhan1@ssafy.com',4,'suwhan1','01066666666','$2a$10$R2IeDENAjhYytVypuBBT0uAL8ehTzO1XbWSsclp7KNnbRLL6pgCRK','2022-08-19 09:56:18',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-08-19 11:21:29
