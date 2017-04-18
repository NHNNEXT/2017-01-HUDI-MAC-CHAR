#!/bin/bash

docker-compose -p mafia up -d

./gradlew -PspringProfiles=dev clean sonarqube build

pkill -9 -f mafia-0.0.1.jar

# java -Dspring.profiles.active=dev -jar ./build/libs/mafia-0.0.1.jar > /dev/null 2>&1 &

# java -Dspring.profiles.active=dev -jar ./build/libs/mafia-0.0.1.jar &

