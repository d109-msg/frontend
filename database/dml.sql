use msg;

INSERT INTO `first_nicknames` (`id`) VALUES
('명석한'), ('사악한'), ('정의로운'), ('우울한'), ('냉철한'), ('기발한'), ('재치 있는'), ('재능 있는'),
('즐거운'), ('미스터리한'), ('불가사의한'), ('치밀한'), ('냉정한'), ('능숙한'), ('영악한'), ('음흉한'),
('치열한'), ('은밀한'), ('논리적인'), ('비밀스러운'), ('사려 깊은'), ('재빠른'), ('기교 있는'),
('명민한'), ('빈틈없는'), ('기만하는'), ('천재적인'), ('계산적인'), ('조용한'), ('거짓말쟁이'), ('정직한'), ('황금빛');


INSERT INTO `last_nicknames` (`id`) VALUES
('콜레오네'), ('홈즈'), ('진'), ('블레이크'), ('커닝햄'), ('데모네'), ('에버렛'), ('파울러'),
('그리핀'), ('할로웨이'), ('잉그램'), ('젠킨스'), ('켄달'), ('레이'), ('맥코이'), ('니콜슨'),
('오닐'), ('페인'), ('퀸'), ('리들'), ('스틸'), ('터너'), ('윈터스'), ('드미트리'), ('예인'), ('규환시치'), ('호저');

INSERT INTO first_room_names  (`id`) VALUES
('축축한'), ('음산한'), ('비내리는'), ('어두운'), ('밝은'),
('쓸쓸한'), ('고요한'), ('비밀스러운'), ('신비로운'), ('고독한');

INSERT INTO last_room_names  (`id`) VALUES
('런던'), ('둔산동'), ('인의동'), ('애버내시'),
('파리'), ('서울'), ('뉴욕'), ('베를린'), ('도쿄'), ('브루클린');

insert into jobs (`id`, `info`, `image_url`) values
('마피아', '마피아입니다.', 'tmpurl'),
('시민', '시민입니다.', 'tmpurl'),
('의사', '의사입니다.', 'tmpurl');

INSERT INTO users (email_id, email_password, nickname, image_url, sign_up_time) VALUES 
('hong@ssafy.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', '홍길동', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player1.png', NOW()),
('test1@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test1', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player1.png', NOW()),
('test2@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test2', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player1.png', NOW()),
('test3@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test3', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player1.png', NOW()),
('test4@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test4', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player1.png', NOW()),
('test5@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test5', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player1.png', NOW()),
('test6@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test6', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player1.png', NOW()),
('test7@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test7', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player1.png', NOW()),
('test8@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test8', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player1.png', NOW()),
('test9@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test9', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player1.png', NOW()),
('test10@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test10', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player1.png', NOW()); 

-- follow
INSERT INTO follows (from_user_id, to_user_id) VALUES (1, 2);
INSERT INTO follows (from_user_id, to_user_id) VALUES (1, 3);
INSERT INTO follows (from_user_id, to_user_id) VALUES (1, 5);
INSERT INTO follows (from_user_id, to_user_id) VALUES (7, 1);
INSERT INTO follows (from_user_id, to_user_id) VALUES (2, 1);
INSERT INTO follows (from_user_id, to_user_id) VALUES (2, 3);
INSERT INTO follows (from_user_id, to_user_id) VALUES (3, 1);
INSERT INTO follows (from_user_id, to_user_id) VALUES (3, 2);
INSERT INTO follows (from_user_id, to_user_id) VALUES (4, 5);
INSERT INTO follows (from_user_id, to_user_id) VALUES (5, 4);
INSERT INTO follows (from_user_id, to_user_id) VALUES (6, 7);
INSERT INTO follows (from_user_id, to_user_id) VALUES (7, 6);

INSERT INTO users (email_id, email_password, nickname, sign_up_time) VALUES 
('hong@ssafy.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', '홍길동', NOW()),
('test1@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test1', NOW()),
('test2@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test2', NOW()),
('test3@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test3', NOW()),
('test4@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test4', NOW()),
('test5@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test5', NOW()),
('test6@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test6', NOW()),
('test7@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test7', NOW()),
('test8@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test8', NOW()),
('test9@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test9', NOW()),
('test10@naver.com', '$2a$10$8C8PCUov//Edd6tWzdvvTupTLkAXiIOPn/OZMioTWNx5NOtgHl6rq', 'test10', NOW());
>>>>>>> be/dev

-- 게임 유저 아이콘
INSERT INTO nickname_images (url, uuid) VALUES
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player1.png', 'player1.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player2.png', 'player2.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player3.png', 'player3.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player4.png', 'player4.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player5.png', 'player5.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player6.png', 'player6.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player7.png', 'player7.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player8.png', 'player8.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player9.png', 'player9.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player10.png', 'player10.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player11.png', 'player11.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player12.png', 'player12.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player13.png', 'player13.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player14.png', 'player14.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player15.png', 'player15.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player16.png', 'player16.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player17.png', 'player17.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player18.png', 'player18.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player19.png', 'player19.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player20.png', 'player20.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player21.png', 'player21.png');

INSERT INTO room_images (url, uuid) VALUES 
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/room_img1.png', 'room_img1.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/room_img2.png', 'room_img2.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/room_img3.png', 'room_img3.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/room_img4.png', 'room_img4.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/room_img5.png', 'room_img5.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/room_img6.png', 'room_img6.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/room_img7.png', 'room_img7.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/room_img8.png', 'room_img8.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/room_img9.png', 'room_img9.png'),
('https://team109testbucket.s3.ap-northeast-2.amazonaws.com/room_img10.png', 'room_img10.png');


INSERT INTO rooms (id, data_type, create_time, title, image_url) 
VALUES 
('room1', '대기방', NOW(), 'Sample Room1', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/room_img1.png'), 
('room2', '그룹', NOW(), 'Sample Room2', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/room_img2.png'), 
('room3', '개인', NOW(), 'Sample Room3', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/room_img9.png'),
('room4', '랜덤', NOW(), 'Sample Room4', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/room_img10.png');

INSERT INTO `participants` (`id`, `room_id`, `user_id`, `last_message_id`, `flag_die`, `job_id`, `image_url`, `nickname`) VALUES
(1, 'room4', 1, NULL, 0, '시민', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player21.png', '거짓말쟁이 니콜슨'),
(2, 'room4', 2, NULL, 0, '마피아', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player18.png', '재능 있는 젠킨스'),
(3, 'room4', 3, NULL, 0, '마피아', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player10.png', '능숙한 스틸'),
(4, 'room4', 4, NULL, 0, '의사', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player9.png', '우울한 터너'),
(5, 'room4', 5, NULL, 0, '시민', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player11.png', '은밀한 데모네'),
(6, 'room4', 6, NULL, 0, '시민', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player6.png', '비밀스러운 콜레오네'),
(7, 'room4', 7, NULL, 0, '시민', 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player1.png', '기발한 윈터스');

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

-- articles 테이블에 데이터 삽입
INSERT INTO articles (user_id, content, create_time, flag_private, room_id)
VALUES
('1', 'test1', NOW(), 0, 'room4'),
('2', 'test2', NOW(), 0, 'room4'),
('3', 'test3', NOW(), 0, 'room4'),
('4', 'test4', NOW(), 0, 'room4'),
('5', 'test5', NOW(), 0, NULL),
('6', 'test6', NOW(), 0, NULL),
('7', 'test7', NOW(), 0, NULL),
('8', 'test8', NOW(), 0, NULL),
('9', 'test9', NOW(), 0, NULL),
('11', 'test10', NOW(), 0, NULL);

-- article_images 테이블에 데이터 삽입
-- 1번 article_id에 대한 이미지 삽입
INSERT INTO article_images (article_id, url, uuid, flag_mission)
VALUES
(1, 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player1.png', 'player1.png', 1),
(1, 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player2.png', 'player2.png', 0),
(1, 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player3.png', 'player3.png', 0);

-- 이하, 2번부터 10번 article_id에 대한 삽입을 반복합니다.
INSERT INTO article_images (article_id, url, uuid, flag_mission)
VALUES
(2, 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player4.png', 'player4.png', 1),
(2, 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player5.png', 'player5.png', 0),
(2, 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player6.png', 'player6.png', 0);


INSERT INTO article_images (article_id, url, uuid, flag_mission)
VALUES
(5, 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player10.png', 'player10.png', 0),
(5, 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player11.png', 'player11.png', 0),
(5, 'https://team109testbucket.s3.ap-northeast-2.amazonaws.com/player12.png', 'player12.png', 0);

SELECT * FROM follows;
SELECT * FROM articles ;
SELECT * FROM article_images ai ;
SELECT * FROM daily_missions dm ;
SELECT * FROM participants p ;
SELECT * FROM users;
