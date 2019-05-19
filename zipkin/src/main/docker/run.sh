#!/bin/sh

echo "*******************************************"
echo "ZIpkin has started ************************"
java -Dserver.port=$SERVER_PORT -Dspring.profiles.active=$PROFILE -Dspring.zipkin.base-url=$ZIPKIN_URI -jar /usr/local/zipkin/@project.build.finalName@.jar
