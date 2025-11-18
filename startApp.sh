#!/bin/bash

#source ~/.bashrc

cd /scratch/ociapp/arduinodata || exit
git pull

if [ "$(pgrep -f arduinodata | wc -l)" -lt 3 ]; then

export JAVA_HOME=/scratch/binaries/jdk-17.0.6
export PATH=$JAVA_HOME/bin:$PATH
export spring_profiles_active=prod

sh mvnw -Dspring.profiles.active=prod clean compile spring-boot:run
#sh mvnw clean compile package -DskipTests
#java -jar ./target/arduinodata-0.0.1-SNAPSHOT.jar

else
  echo "arduinodata application is already running."
fi
