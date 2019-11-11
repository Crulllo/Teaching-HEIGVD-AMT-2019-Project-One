#Build with maven
mvn clean install

# docker compose
cd docker/topologies/topology_test || exit
docker-compose down
docker-compose up --build

