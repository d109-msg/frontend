drop database if exists `msg`;

CREATE DATABASE `msg` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

use msg;
-- msg.users definition

SET FOREIGN_KEY_CHECKS=0;

CREATE TABLE `users` (
  `email_id` varchar(100) NOT NULL,
  `email_password` varchar(100) DEFAULT NULL,
  `nickname` varchar(50) NOT NULL,
  `provider` varchar(10) DEFAULT NULL,
  `identifier` varchar(50) DEFAULT NULL,
  `flag_admin` int(11) NOT NULL DEFAULT 0,
  `refresh_token` varchar(300) DEFAULT NULL,
  `sign_in_time` datetime DEFAULT NULL,
  `sign_up_time` datetime NOT NULL DEFAULT current_timestamp(),
  `flag_private` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`email_id`),
  UNIQUE KEY `users_unique` (`identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- msg.articles definition

CREATE TABLE `articles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_email_id` varchar(100) NOT NULL,
  `content` varchar(50) DEFAULT NULL,
  `upload_time` datetime NOT NULL DEFAULT current_timestamp(),
  `flag_private` int(11) DEFAULT NULL,
  `modify_time` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `articles_users_FK` (`user_email_id`),
  CONSTRAINT `articles_users_FK` FOREIGN KEY (`user_email_id`) REFERENCES `users` (`email_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- msg.article_images definition

CREATE TABLE `article_images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `url` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `article_images_articles_FK` (`article_id`),
  CONSTRAINT `article_images_articles_FK` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- msg.article_likes definition

CREATE TABLE `article_likes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `user_email_id` varchar(100) NOT NULL,
  `like_time` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `article_likes_articles_FK` (`article_id`),
  KEY `article_likes_users_FK` (`user_email_id`),
  CONSTRAINT `article_likes_articles_FK` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`),
  CONSTRAINT `article_likes_users_FK` FOREIGN KEY (`user_email_id`) REFERENCES `users` (`email_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- msg.comments definition

CREATE TABLE `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `user_email_id` varchar(100) NOT NULL,
  `comment_id` int(11) DEFAULT NULL,
  `parent_comment_id` int(11) DEFAULT NULL,
  `content` varchar(500) NOT NULL,
  `upload_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `comments_articles_FK` (`article_id`),
  KEY `comments_users_FK` (`user_email_id`),
  KEY `comments_comments_FK` (`comment_id`),
  KEY `comments_comments_FK_1` (`parent_comment_id`),
  CONSTRAINT `comments_articles_FK` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`),
  CONSTRAINT `comments_comments_FK` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`),
  CONSTRAINT `comments_comments_FK_1` FOREIGN KEY (`parent_comment_id`) REFERENCES `comments` (`id`),
  CONSTRAINT `comments_users_FK` FOREIGN KEY (`user_email_id`) REFERENCES `users` (`email_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- msg.comment_likes definition

CREATE TABLE `comment_likes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_id` int(11) NOT NULL,
  `user_email_id` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `comment_likes_comments_FK` (`comment_id`),
  KEY `comment_likes_users_FK` (`user_email_id`),
  CONSTRAINT `comment_likes_comments_FK` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`),
  CONSTRAINT `comment_likes_users_FK` FOREIGN KEY (`user_email_id`) REFERENCES `users` (`email_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- msg.follows definition

CREATE TABLE `follows` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_user_email_id` varchar(100) NOT NULL,
  `to_user_email_id` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `follows_users_FK` (`from_user_email_id`),
  KEY `follows_users_FK_1` (`to_user_email_id`),
  CONSTRAINT `follows_users_FK` FOREIGN KEY (`from_user_email_id`) REFERENCES `users` (`email_id`),
  CONSTRAINT `follows_users_FK_1` FOREIGN KEY (`to_user_email_id`) REFERENCES `users` (`email_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- msg.rooms definition

CREATE TABLE `rooms` (
  `id` varchar(100) NOT NULL,
  `create_time` datetime NOT NULL,
  `last_message_id` int(11) DEFAULT NULL,
  `
  PRIMARY KEY (`id`),
  KEY `rooms_messages_FK` (`last_message_id`),
  CONSTRAINT `rooms_messages_FK` FOREIGN KEY (`last_message_id`) REFERENCES `messages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- msg.jobs definition

CREATE TABLE `jobs` (
  `id` varchar(50) NOT NULL,
  `info` varchar(500) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- msg.room_users definition

CREATE TABLE `room_users` (
  `room_id` varchar(100) NOT NULL,
  `user_email_id` varchar(100) NOT NULL,
  `last_message_id` int(11) DEFAULT NULL,
  `job_id` varchar(50) DEFAULT NULL,
  `data_type` varchar(100) NOT NULL, -- 개인, 그룹, 랜덤, 대기
  PRIMARY KEY (`room_id`,`user_email_id`),
  KEY `room_users_jobs_FK` (`job_id`),
  CONSTRAINT `room_users_jobs_FK` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`),
  CONSTRAINT `room_users_rooms_FK` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- msg.messages definition

CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_id` varchar(100) NOT NULL,
  `user_email_id` varchar(100) NOT NULL,
  `send_time` datetime NOT NULL,
  `data_type` varchar(100) NOT NULL, -- 텍스트, 이미지
  PRIMARY KEY (`id`),
  KEY `messages_users_FK` (`user_email_id`),
  KEY `messages_rooms_FK` (`room_id`),
  CONSTRAINT `messages_rooms_FK` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`),
  CONSTRAINT `messages_users_FK` FOREIGN KEY (`user_email_id`) REFERENCES `users` (`email_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- msg.message_texts definition

CREATE TABLE `message_texts` (
  `id` int(11) NOT NULL,
  `content` varchar(500) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `message_texts_messages_FK` FOREIGN KEY (`id`) REFERENCES `messages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- msg.message_images definition

CREATE TABLE `message_images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(300) NOT NULL,
  `message_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `message_images_messages_FK` (`message_id`),
  CONSTRAINT `message_images_messages_FK` FOREIGN KEY (`message_id`) REFERENCES `messages` (`id`)
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


SET FOREIGN_KEY_CHECKS=1;