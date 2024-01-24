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


