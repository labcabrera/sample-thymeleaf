#!/bin/bash

./gradlew clean build -x test

docker stop sample-api

docker rm sample-api

docker rmi labcabrera/sample-api:latest

docker build -t labcabrera/sample-api:latest .

#TODO update config variables as needed
docker run -d --name sample-api -p 8082:8082 \
  -e JAVA_OPTS="-Xms512m -Xmx1024m" \
  -e LOG_LEVEL="DEBUG" \
  labcabrera/sample-api:latest

docker logs -f sample-api
