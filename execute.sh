#Build with maven
mvn clean install

# Copy *.war to docker topology
cp target/filmratingapp.war docker/images_docker/filmapp/

# docker compose
cd docker/topologies/topology_film_app || exit
docker-compose down
docker-compose up --build

