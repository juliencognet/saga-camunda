docker-compose stop orchestrator
docker-compose rm -f orchestrator
docker rmi orchestration-demo_orchestrator
cd orchestrator
call mvn clean install -DskipTests
cd ..
docker-compose up -d