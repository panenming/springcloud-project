CREATE database if NOT EXISTS `user` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `user`;

CREATE TABLE `user_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `user_age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin