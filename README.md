# Teaching-HEIGVD-AMT-2019-Project-One
## Introduction
This is our first project with hands-on JavaEE to implement a multi-tiered application.

## Funcionality
Our application is a very simple film rating app. Users should be able to browse through a list of films and like them as they please.
Once they like a movie, it's added as a preference which they should be able to see on their profile.

## How
Our application is deployed using a docker topology on a payara server, using a payara image, mysql and phpmyadmin for the database. To facilitate the execution of our application we've setup a shell script execute.sh to build and deploy our application automatically. You can then go to your localhost:8080/filmratingapp on your favourite browser, preferably on 127.0.0.1:8080/filmratingapp/home (or the corresponding ip address on your system) and begin to use our application!

[Business Components](docs/Business%20Components.md)
[Database](docs/Database.md)
[Integration](docs/Integration.md)
[Performance](docs/Performance.md)
[Presentation](docs/Presentation.md)
[Scheme](docs/Scheme.md)
