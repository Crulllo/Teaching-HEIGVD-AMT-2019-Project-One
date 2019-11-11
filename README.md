# Teaching-HEIGVD-AMT-2019-Project-One
## Introduction
This is our first project with hands-on JavaEE to implement a multi-tiered application.

## Funcionality
Our application is a very simple film rating app. Users should be able to browse through a list of films and like them as they please.
Once they like a movie, it's added as a preference which they should be able to see on their profile.

## How
Our application is deployed using a docker topology on a payara server, using a payara image, mysql and phpmyadmin for the database. To facilitate the execution of our application we've setup a shell script execute.sh to build and deploy our application automatically.

## Work done
### Implement model
Here we implement the three entities we use in our project: User, Film and Rating. Rating is an N-to-N relationship
between Film and User.

### pom
We add all dependencies and we specify the packaging target as .war.

## Issues
- Bootstrap link and enable interaction
- Servers on docker compose
- Username and password
- Database connection and DAO

SQL server
- Init db
-Sql table three entities
- DAO with arquilian
Servlet authentification
-EJBs