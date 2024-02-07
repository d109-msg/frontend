drop database if exists `msg`;

CREATE DATABASE `msg` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

use msg;

SET FOREIGN_KEY_CHECKS=0;

-- msg.first_nicknames definition

CREATE TABLE `first_nicknames` (
  `id` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.first_room_names definition

CREATE TABLE `first_room_names` (
  `id` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.jobs definition

CREATE TABLE `jobs` (
  `id` varchar(50) NOT NULL,
  `info` varchar(500) NOT NULL,
  `image_url` varchar(300) DEFAULT NULL,
  `image_uuid` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.last_nicknames definition

CREATE TABLE `last_nicknames` (
  `id` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.last_room_names definition

CREATE TABLE `last_room_names` (
  `id` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.missions definition

CREATE TABLE `missions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `normal` varchar(300) DEFAULT NULL,
  `mafia` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.nickname_images definition

CREATE TABLE `nickname_images` (
  `uuid` varchar(100) NOT NULL,
  `url` varchar(300) NOT NULL,
  PRIMARY KEY (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.room_images definition

CREATE TABLE `room_images` (
  `url` varchar(300) NOT NULL,
  `uuid` varchar(100) NOT NULL,
  PRIMARY KEY (`url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.users definition

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email_id` varchar(100) NOT NULL,
  `email_password` varchar(100) DEFAULT NULL,
  `nickname` varchar(50) NOT NULL,
  `bio` varchar(100) DEFAULT NULL,
  `provider` varchar(10) DEFAULT NULL,
  `identifier` varchar(50) DEFAULT NULL,
  `flag_identifier` int(11) DEFAULT 0,
  `flag_admin` int(11) NOT NULL DEFAULT 0,
  `firebase_token` varchar(300) DEFAULT NULL,
  `image_url` varchar(300) DEFAULT NULL DEFAULT "https://team109testbucket.s3.ap-northeast-2.amazonaws.com/2c5954d7-2aec-4cac-9c67-9ada52a1eafb",
  `image_uuid` varchar(100) DEFAULT NULL DEFAULT "2c5954d7-2aec-4cac-9c67-9ada52a1eafb",
  `flag_private` int(11) NOT NULL DEFAULT 0,
  `sign_up_time` datetime NOT NULL DEFAULT current_timestamp(),
  `sign_in_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_id` (`email_id`),
  UNIQUE KEY `users_unique` (`identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;



-- msg.follows definition

CREATE TABLE `follows` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `from_user_id` int(11) NOT NULL,
  `to_user_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `follows_users_FK` (`from_user_id`),
  KEY `follows_users_FK_1` (`to_user_id`),
  CONSTRAINT `follows_users_FK` FOREIGN KEY (`from_user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `follows_users_FK_1` FOREIGN KEY (`to_user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.notifications definition

CREATE TABLE `notifications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `content` varchar(300) NOT NULL,
  `create_time` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `notifications_users_FK` (`user_id`),
  CONSTRAINT `notifications_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.article_images definition

CREATE TABLE `article_images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `url` varchar(300) NOT NULL,
  `uuid` varchar(100) NOT NULL,
  `flag_mission` int(11) DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `article_images_articles_FK` (`article_id`),
  CONSTRAINT `article_images_articles_FK` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`) ON DELETE CASCADE 
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.article_likes definition

CREATE TABLE `article_likes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `article_likes_articles_FK` (`article_id`),
  KEY `article_likes_users_FK` (`user_id`),
  CONSTRAINT `article_likes_articles_FK` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`) ON DELETE CASCADE,
  CONSTRAINT `article_likes_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.article_reports definition

CREATE TABLE `article_reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `from_user_id` int(11) NOT NULL,
  `content` varchar(100),
  `create_time` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `article_reports_articles_FK` (`article_id`),
  KEY `article_reports_users_FK` (`from_user_id`),
  CONSTRAINT `article_reports_articles_FK` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`) ON DELETE CASCADE,
  CONSTRAINT `article_reports_users_FK` FOREIGN KEY (`from_user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.articles definition

CREATE TABLE `articles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `content` varchar(50) DEFAULT NULL,
  `create_time` datetime NOT NULL DEFAULT current_timestamp(),
  `flag_private` int(11) DEFAULT NULL,
  `modify_time` datetime NOT NULL DEFAULT current_timestamp(),
  `room_id` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `articles_users_FK` (`user_id`),
  KEY `articles_rooms_FK` (`room_id`),
  CONSTRAINT `articles_rooms_FK` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`),
  CONSTRAINT `articles_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.comment_likes definition

CREATE TABLE `comment_likes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `comment_likes_comments_FK` (`comment_id`),
  KEY `comment_likes_users_FK` (`user_id`),
  CONSTRAINT `comment_likes_comments_FK` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`) ON DELETE CASCADE,
  CONSTRAINT `comment_likes_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.comments definition

CREATE TABLE `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `parent_comment_id` int(11) DEFAULT NULL,
  `content` varchar(500) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT current_timestamp(),
  `modify_time` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `comments_articles_FK` (`article_id`),
  KEY `comments_users_FK` (`user_id`),
  KEY `comments_comments_FK_1` (`parent_comment_id`),
  CONSTRAINT `comments_articles_FK` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`) ON DELETE CASCADE,
  CONSTRAINT `comments_comments_FK_1` FOREIGN KEY (`parent_comment_id`) REFERENCES `comments` (`id`) ON DELETE CASCADE,
  CONSTRAINT `comments_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.daily_missions definition

CREATE TABLE `daily_missions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `participant_id` int(11) NOT NULL,
  `mission_id` int(11) NOT NULL,
  `day` int(11) DEFAULT NULL,
  `try` int(11) DEFAULT NULL,
  `flag_success` int(11) DEFAULT NULL,
  `normal_vote` int(11) DEFAULT NULL,
  `mafia_vote` int(11) DEFAULT NULL,
  `doctor_vote` int(11) DEFAULT NULL,
  `reporter_vote` INT(11) DEFAULT NULL,
  `article_id` int(11) DEFAULT NULL,
--   
--   `police_vote` INT(11) DEFAULT NULL,
--   `cleaner_vote` INT(11) DEFAULT NULL,
--   `idiot_vote` INT(11) DEFAULT NULL,
--   `hunter_vote` INT(11) DEFAULT NULL,
--   `ganster_vote` INT(11) DEFAULT NULL,
--   `spy_vote` INT(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `daily_missions_users_FK` (`normal_vote`),
  KEY `daily_missions_users_FK_1` (`mafia_vote`),
  KEY `daily_missions_users_FK_2` (`doctor_vote`),
  KEY `daily_missions_missions_FK` (`mission_id`),
  KEY `daily_missions_participants_FK` (`participant_id`),
  KEY `daily_missions_articles_FK` (`article_id`),
  CONSTRAINT `daily_missions_articles_FK` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`),
  CONSTRAINT `daily_missions_missions_FK` FOREIGN KEY (`mission_id`) REFERENCES `missions` (`id`),
  CONSTRAINT `daily_missions_participants_FK` FOREIGN KEY (`participant_id`) REFERENCES `participants` (`id`) ON DELETE CASCADE,
  CONSTRAINT `daily_missions_users_FK` FOREIGN KEY (`normal_vote`) REFERENCES `participants` (`id`) ON DELETE CASCADE,
  CONSTRAINT `daily_missions_users_FK_1` FOREIGN KEY (`mafia_vote`) REFERENCES `participants` (`id`) ON DELETE CASCADE,
  CONSTRAINT `daily_missions_users_FK_2` FOREIGN KEY (`doctor_vote`) REFERENCES `participants` (`id`) ON DELETE CASCADE,
  CONSTRAINT `daily_missions_users_FK_3` FOREIGN KEY (`reporter_vote`) REFERENCES `participants` (`id`) ON DELETE CASCADE
--   CONSTRAINT `daily_missions_users_FK_4` FOREIGN KEY (`police_vote`) REFERENCES `participants` (`id`) ON DELETE CASCADE,
--   CONSTRAINT `daily_missions_users_FK_5` FOREIGN KEY (`cleaner_vote`) REFERENCES `participants` (`id`) ON DELETE CASCADE,
--   CONSTRAINT `daily_missions_users_FK_6` FOREIGN KEY (`idiot_vote`) REFERENCES `participants` (`id`) ON DELETE CASCADE,
--   CONSTRAINT `daily_missions_users_FK_7` FOREIGN KEY (`hunter_vote`) REFERENCES `participants` (`id`) ON DELETE CASCADE,
--   CONSTRAINT `daily_missions_users_FK_8` FOREIGN KEY (`ganster_vote`) REFERENCES `participants` (`id`) ON DELETE CASCADE,
--   CONSTRAINT `daily_missions_users_FK_9` FOREIGN KEY (`spy_vote`) REFERENCES `participants` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.message_images definition

CREATE TABLE `message_images` (
  `url` varchar(300) NOT NULL,
  `message_id` int(11) NOT NULL,
  `uuid` varchar(100) NOT NULL,
  PRIMARY KEY (`url`),
  KEY `message_images_messages_FK` (`message_id`),
  CONSTRAINT `message_images_messages_FK` FOREIGN KEY (`message_id`) REFERENCES `messages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.message_texts definition

CREATE TABLE `message_texts` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(500) NOT NULL,
  PRIMARY KEY (`message_id`),
  CONSTRAINT `message_texts_messages_FK` FOREIGN KEY (`message_id`) REFERENCES `messages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.messages definition

CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_id` varchar(100) NOT NULL,
  `user_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT current_timestamp(),
  `data_type` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `messages_users_FK` (`user_id`),
  KEY `messages_rooms_FK` (`room_id`),
  CONSTRAINT `messages_rooms_FK` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`),
  CONSTRAINT `messages_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.participants definition

CREATE TABLE `participants` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_id` varchar(100) NOT NULL,
  `user_id` int(11) NOT NULL,
  `last_message_id` int(11) DEFAULT NULL,
  `flag_die` int(11) NOT NULL DEFAULT 0,
  `flag_win` int(11) NOT NULL DEFAULT 1,
  `job_id` varchar(50) DEFAULT NULL,
  `image_url` varchar(300) DEFAULT NULL,
  `nickname` varchar(100) NOT NULL,
  `ability` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `participants_users_FK` (`user_id`),
  KEY `participants_rooms_FK` (`room_id`),
  KEY `participants_jobs_FK` (`job_id`),
  KEY `participants_nickname_images_FK` (`image_url`),
  CONSTRAINT `participants_jobs_FK` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`),
  CONSTRAINT `participants_rooms_FK` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`),
  CONSTRAINT `participants_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.rooms definition

CREATE TABLE `rooms` (
  `id` varchar(100) NOT NULL,
  `last_message_id` int(11) DEFAULT NULL,
  `data_type` varchar(100) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT current_timestamp(),
  `end_time` datetime DEFAULT NULL,
  `start_time` datetime DEFAULT NULL,
  `title` varchar(100) NOT NULL,
  `image_url` varchar(300) DEFAULT NULL,
  `flag_available` int(11) NOT NULL DEFAULT 1,
  `flag_night` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  KEY `rooms_messages_FK` (`last_message_id`),
  KEY `rooms_room_images_FK` (`image_url`),
  CONSTRAINT `rooms_messages_FK` FOREIGN KEY (`last_message_id`) REFERENCES `messages` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.user_reports definition

CREATE TABLE `user_reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `participant_id` int(11) NOT NULL,
  `to_user_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `reports_participants_FK` (`participant_id`),
  KEY `reports_users_FK` (`to_user_id`),
  CONSTRAINT `reports_participants_FK` FOREIGN KEY (`participant_id`) REFERENCES `participants` (`id`),
  CONSTRAINT `reports_users_FK` FOREIGN KEY (`to_user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `static_flag` (
	`id` varchar(100) NOT NULL DEFAULT 'flag_night',
	`value` int(11) NOT NULL DEFAULT 1,
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS=1;
