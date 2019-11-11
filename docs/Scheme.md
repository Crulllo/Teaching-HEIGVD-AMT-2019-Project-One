# Model

Our model involves three entities: namely the user, movies and preferences. <br> <br>
Preference is a relation N to N between users and movies. A user can like many movies and a movie can be liked by many users. So naturally we implement as a relation table with its primary key defined as two foreign keys: the username of the user who liked the movie, and the id of the film that it is liked. Whenever a user likes a film, a preference is added.