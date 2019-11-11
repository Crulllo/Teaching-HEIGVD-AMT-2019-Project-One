USE `amt_project_one`;

INSERT INTO amt_films(TITLE, RUNNING_TIME, PATH_TO_POSTER, DIRECTOR) VALUES ('The Butler', 132, 'the_butler.jpg', 'Lee Daniels');
INSERT INTO amt_films(TITLE, RUNNING_TIME, PATH_TO_POSTER, DIRECTOR) VALUES ('Scarface', 170, 'scarface.jpg', 'Brian De Palma');
INSERT INTO amt_films(TITLE, RUNNING_TIME, PATH_TO_POSTER, DIRECTOR) VALUES ('Forrest Gump', 142, 'forest_gump.jpg', 'Robert Zemeckis');
INSERT INTO amt_films(TITLE, RUNNING_TIME, PATH_TO_POSTER, DIRECTOR) VALUES ('Fight Club', 119, 'fight_club.jpg', 'David Fincher');
INSERT INTO amt_films(TITLE, RUNNING_TIME, PATH_TO_POSTER, DIRECTOR) VALUES ('The Lion King', 118, 'the_lion_king.jpg', 'Jon Favreau');
INSERT INTO amt_films(TITLE, RUNNING_TIME, PATH_TO_POSTER, DIRECTOR) VALUES ('Spider-Man: Into the Spider-Verse', 117, 'spider_man.jpg', 'Bob Persichetti');
INSERT INTO amt_films(TITLE, RUNNING_TIME, PATH_TO_POSTER, DIRECTOR) VALUES ('Avengers: Endgame', 181, 'avengers_endgame.jpg', 'Anthony Russo');
INSERT INTO amt_films(TITLE, RUNNING_TIME, PATH_TO_POSTER, DIRECTOR) VALUES ('Joker', 122, 'joker.jpg', 'Todd Phillips');
INSERT INTO amt_films(TITLE, RUNNING_TIME, PATH_TO_POSTER, DIRECTOR) VALUES ('Star Wars: The Rise of Skywalker', 155, 'star_wars.jpg', 'J.J. Abrams');
INSERT INTO amt_films(TITLE, RUNNING_TIME, PATH_TO_POSTER, DIRECTOR) VALUES ('You''ve Got mail', 119, 'you''ve_got_mail.jpg', 'Nora Ephron');

INSERT INTO amt_users(USERNAME, FIRST_NAME, LAST_NAME, EMAIL, HASHED_PW, IS_ADMIN) VALUES ('admin', 'admin', 'admin', 'admin@email.com', 'password', true);
INSERT INTO amt_users(USERNAME, FIRST_NAME, LAST_NAME, EMAIL, HASHED_PW) VALUES ('user1', 'Bob', 'Dylan', 'dylan@email.com', 'asd');
INSERT INTO amt_users(USERNAME, FIRST_NAME, LAST_NAME, EMAIL, HASHED_PW) VALUES ('user2', 'Bob', 'Hope', 'hope@email.com', 'asd');
INSERT INTO amt_users(USERNAME, FIRST_NAME, LAST_NAME, EMAIL, HASHED_PW) VALUES ('user3', 'Mickey', 'Mouse', 'mickey@email.com', 'asd');

INSERT INTO amt_preferences(FILM_ID, USERNAME) VALUES (5, 'user1');
INSERT INTO amt_preferences(FILM_ID, USERNAME) VALUES (9, 'user1');


