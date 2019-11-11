USE `amt_project_one`

LOAD DATA LOCAL INFILE '/docker-entrypoint-initdb.d/films_generated.csv'
INTO TABLE 	amt_films
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(ID, TITLE, RUNNING_TIME, PATH_TO_POSTER, DIRECTOR);