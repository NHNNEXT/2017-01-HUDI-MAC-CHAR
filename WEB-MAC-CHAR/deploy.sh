#!/bin/bash

docker-compose -p mafia up -d

./gradlew -PspringProfiles=dev clean sonarqube build

pkill -9 -f mafia-0.0.1.jar

sleep 5

# java -Dspring.profiles.active=dev -jar ./build/libs/mafia-0.0.1.jar > mafia.log 2>&1 &

nohup java -Dspring.profiles.active=dev -jar ./build/libs/mafia-0.0.1.jar &

