echo "RUN THE PROJECT IN THE SERVER/LOCAL"

echo "Compiling while skipping tests ..."
./mvnw clean install -DskipTests
echo "Compilation finished"

echo "Kill the process on port 8080, to undeploy the former version if existing"
sudo kill $(sudo lsof -t -i:8080)

echo "Let's deploy the new version silently"
nohup ./mvnw spring-boot:run &
