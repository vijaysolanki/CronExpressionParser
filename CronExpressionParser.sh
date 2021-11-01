#!/bin/sh
# script is executing cron expression parser and display parsed expression
exp=${1}
chmod 777 ./gradlew
#./gradlew build
./gradlew run --args="'${exp}'"
