use msg;

INSERT INTO `first_nicknames` (`id`) VALUES
('명석한'), ('사악한'), ('정의로운'), ('우울한'), ('냉철한'), ('기발한'), ('재치 있는'), ('재능 있는'),
('즐거운'), ('미스터리한'), ('불가사의한'), ('치밀한'), ('냉정한'), ('능숙한'), ('영악한'), ('음흉한'),
('치열한'), ('은밀한'), ('논리적인'), ('비밀스러운'), ('사려 깊은'), ('재빠른'), ('기교 있는'),
('명민한'), ('빈틈없는'), ('기만하는'), ('천재적인'), ('계산적인'), ('조용한'), ('거짓말쟁이'), ('정직한');


INSERT INTO `last_nicknames` (`id`) VALUES
('콜레오네'), ('홈즈'), ('진'), ('블레이크'), ('커닝햄'), ('데모네'), ('에버렛'), ('파울러'),
('그리핀'), ('할로웨이'), ('잉그램'), ('젠킨스'), ('켄달'), ('레이'), ('맥코이'), ('니콜슨'),
('오닐'), ('페인'), ('퀸'), ('리들'), ('스틸'), ('터너'), ('윈터스');

INSERT INTO first_room_names  (`id`) VALUES
('축축한'), ('음산한'), ('비내리는'), ('어두운'), ('밝은'),
('쓸쓸한'), ('고요한'), ('비밀스러운'), ('신비로운'), ('고독한');

INSERT INTO last_room_names  (`id`) VALUES
('런던'), ('둔산동'), ('인의동'), ('애버내시'),
('파리'), ('서울'), ('뉴욕'), ('베를린'), ('도쿄');

insert into jobs (`id`, `info`, `image_url`) values
('마피아', '마피아입니다.', 'tmpurl'),
('시민', '시민입니다.', 'tmpurl'),
('의사', '의사입니다.', 'tmpurl');

INSERT INTO users (email_id, nickname, sign_up_time) VALUES 
('test1@naver.com', 'test1', NOW()),
('test2@naver.com', 'test2', NOW()),
('test3@naver.com', 'test3', NOW()),
('test4@naver.com', 'test4', NOW()),
('test5@naver.com', 'test5', NOW()),
('test6@naver.com', 'test6', NOW()),
('test7@naver.com', 'test7', NOW()),
('test8@naver.com', 'test8', NOW()),
('test9@naver.com', 'test9', NOW()),
('test10@naver.com', 'test10', NOW());

insert into nickname_images (url, uuid) values ('tmp1', 'uuid'), ('tmp2', 'uuid'), ('tmp3', 'uuid'), ('tmp4', 'uuid'), ('tmp5', 'uuid'), ('tmp6', 'uuid'), ('tmp7', 'uuid'), ('tmp8', 'uuid'), ('tmp9', 'uuid'), ('tmp10', 'uuid');
insert into room_images  (url, uuid) values ('tmp1', 'uuid'), ('tmp2', 'uuid'), ('tmp3', 'uuid'), ('tmp4', 'uuid'), ('tmp5', 'uuid'), ('tmp6', 'uuid'), ('tmp7', 'uuid'), ('tmp8', 'uuid'), ('tmp9', 'uuid'), ('tmp10', 'uuid');

INSERT INTO rooms (id, data_type, create_time, title, image_url) 
VALUES ('room1', '랜덤', NOW(), 'Sample Room1', 'tmp1'), ('room2', '그룹', NOW(), 'Sample Room2', 'tmp2'), ('room3', '개인', NOW(), 'Sample Room3', 'tmp3');

INSERT INTO `participants` (`id`, `room_id`, `user_id`, `last_message_id`, `flag_die`, `flag_win`, `job_id`, `image_url`, `nickname`) VALUES
(1, 'room1', 1, NULL, 0, 0, '시민', 'tmp3', '거짓말쟁이 니콜슨'),
(2, 'room1', 2, NULL, 0, 0, '마피아', 'tmp4', '재능 있는 젠킨스'),
(3, 'room1', 3, NULL, 0, 0, '마피아', 'tmp5', '능숙한 스틸'),
(4, 'room1', 4, NULL, 0, 0, '의사', 'tmp9', '우울한 터너'),
(5, 'room1', 5, NULL, 0, 0, '시민', 'tmp10', '은밀한 데모네'),
(6, 'room1', 6, NULL, 0, 0, '시민', 'tmp6', '비밀스러운 콜레오네'),
(7, 'room1', 7, NULL, 0, 0, '시민', 'tmp1', '기발한 윈터스');

INSERT INTO `missions` (`normal`, `mafia`) VALUES
('자연 사진', '나무 사진'),
('자연 사진', '하늘 사진'),
('자연 사진', '식물 사진'),
('자연 사진', '꽃 사진'),
('자연 사진', '나뭇잎 사진'),
('실내 사진', '책상 사진'),
('실내 사진', '필기구 사진'),
('실내 사진', '화장실 사진'),
('실내 사진', '책상 위의 책 사진'),
('실내 사진', '컴퓨터 사진'),
('실내 사진', '창문 사진'),
('음식 사진', '쌀밥 사진'),
('음식 사진', '면 요리 사진'),
('음식 사진', '숟가락이 포함된 사진'),
('음식 사진', '젓가락이 포함된 사진'),
('음료 사진', '탄산 음료 사진'),
('음료 사진', '커피 사진'),
('음료 사진', '물 사진'),
('음료 사진', '주스 사진'),
('도시 사진', '건물 사진'),
('도시 사진', '도로 사진'),
('도시 사진', '자동차 사진'),
('도시 사진', '가게 사진');

INSERT INTO daily_missions (participant_id, mission_id, day, try, flag_success, normal_vote, mafia_vote, doctor_vote) VALUES
(1, 1, 1, 1, 1, 1, 1, 1),
(2, 1, 1, 1, 0, 1, 1, 1),
(3, 1, 1, 1, 1, 1, 1, 1),
(4, 1, 1, 1, 0, 1, 1, 1),
(5, 1, 1, 1, 1, 1, 1, 1),
(6, 1, 1, 1, 0, 1, 1, 1),
(7, 1, 1, 1, 0, 2, 3, 4);

SELECT * FROM daily_missions dm ;
SELECT * FROM participants p ;
SELECT * FROM users;


<<<<<<< HEAD
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
  `provider` varchar(10) DEFAULT NULL,
  `identifier` varchar(50) DEFAULT NULL,
  `flag_identifier` int(11) DEFAULT 0,
  `flag_admin` int(11) NOT NULL DEFAULT 0,
  `refresh_token` varchar(300) DEFAULT NULL,
  `image_url` varchar(300) DEFAULT NULL,
  `image_uuid` varchar(100) DEFAULT NULL,
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
  CONSTRAINT `follows_users_FK` FOREIGN KEY (`from_user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `follows_users_FK_1` FOREIGN KEY (`to_user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.notifications definition

CREATE TABLE `notifications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `content` varchar(300) NOT NULL,
  `create_time` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `notifications_users_FK` (`user_id`),
  CONSTRAINT `notifications_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
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
  CONSTRAINT `article_images_articles_FK` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`)
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
  CONSTRAINT `article_likes_articles_FK` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`),
  CONSTRAINT `article_likes_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.article_reports definition

CREATE TABLE `article_reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `from_user_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `article_reports_articles_FK` (`article_id`),
  KEY `article_reports_users_FK` (`from_user_id`),
  CONSTRAINT `article_reports_articles_FK` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`),
  CONSTRAINT `article_reports_users_FK` FOREIGN KEY (`from_user_id`) REFERENCES `users` (`id`)
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
  CONSTRAINT `articles_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
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
  CONSTRAINT `comment_likes_comments_FK` FOREIGN KEY (`comment_id`) REFERENCES `comments` (`id`),
  CONSTRAINT `comment_likes_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.comments definition

CREATE TABLE `comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `parent_comment_id` int(11) DEFAULT NULL,
  `content` varchar(500) NOT NULL,
  `create_time` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `comments_articles_FK` (`article_id`),
  KEY `comments_users_FK` (`user_id`),
  KEY `comments_comments_FK_1` (`parent_comment_id`),
  CONSTRAINT `comments_articles_FK` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`),
  CONSTRAINT `comments_comments_FK_1` FOREIGN KEY (`parent_comment_id`) REFERENCES `comments` (`id`),
  CONSTRAINT `comments_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
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
  `article_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `daily_missions_users_FK` (`normal_vote`),
  KEY `daily_missions_users_FK_1` (`mafia_vote`),
  KEY `daily_missions_users_FK_2` (`doctor_vote`),
  KEY `daily_missions_missions_FK` (`mission_id`),
  KEY `daily_missions_participants_FK` (`participant_id`),
  KEY `daily_missions_articles_FK` (`article_id`),
  CONSTRAINT `daily_missions_articles_FK` FOREIGN KEY (`article_id`) REFERENCES `articles` (`id`),
  CONSTRAINT `daily_missions_missions_FK` FOREIGN KEY (`mission_id`) REFERENCES `missions` (`id`),
  CONSTRAINT `daily_missions_participants_FK` FOREIGN KEY (`participant_id`) REFERENCES `participants` (`id`),
  CONSTRAINT `daily_missions_users_FK` FOREIGN KEY (`normal_vote`) REFERENCES `participants` (`id`),
  CONSTRAINT `daily_missions_users_FK_1` FOREIGN KEY (`mafia_vote`) REFERENCES `participants` (`id`),
  CONSTRAINT `daily_missions_users_FK_2` FOREIGN KEY (`doctor_vote`) REFERENCES `participants` (`id`)
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
  CONSTRAINT `messages_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


-- msg.participants definition

CREATE TABLE `participants` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `room_id` varchar(100) NOT NULL,
  `user_id` int(11) NOT NULL,
  `last_message_id` int(11) DEFAULT NULL,
  `flag_die` int(11) NOT NULL DEFAULT 0,
  `flag_win` int(11) NOT NULL DEFAULT 0,
  `job_id` varchar(50) DEFAULT NULL,
  `image_url` varchar(300) DEFAULT NULL,
  `nickname` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `participants_users_FK` (`user_id`),
  KEY `participants_rooms_FK` (`room_id`),
  KEY `participants_jobs_FK` (`job_id`),
  KEY `participants_nickname_images_FK` (`image_url`),
  CONSTRAINT `participants_jobs_FK` FOREIGN KEY (`job_id`) REFERENCES `jobs` (`id`),
  CONSTRAINT `participants_rooms_FK` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`id`),
  CONSTRAINT `participants_users_FK` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
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
  CONSTRAINT `reports_users_FK` FOREIGN KEY (`to_user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

SET FOREIGN_KEY_CHECKS=1;
=======
>>>>>>> cb4fd39a819d76beb9fbcd326e240676f721fefd
