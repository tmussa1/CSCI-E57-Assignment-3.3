#!/bin/sh

echo "********************************************************"
echo "eurekaserver starting on port $EUREKASERVER_PORT"
echo "********************************************************"
while ! `nc -z eurekaserver $EUREKASERVER_PORT `; do sleep 3; done
echo ">>>>>>>>>>>> Eureka Server has started"

echo "********************************************************"
echo "postgres starting on port $DATABASESERVER_PORT"
echo "********************************************************"
while ! `nc -z database $DATABASESERVER_PORT`; do sleep 3; done
echo ">>>>>>>>>>>> Database Server has started"

echo "********************************************************"
echo "confsvr starting on port $CONFIGSERVER_PORT"
echo "********************************************************"
while ! `nc -z confsvr $CONFIGSERVER_PORT `; do sleep 3; done
echo ">>>>>>>>>>>> Configuration Server has started"

echo "********************************************************"
echo "Waiting for the kafka server to start on port $KAFKASERVER_PORT"
echo "********************************************************"
while ! `nc -z kafkaserver $KAFKASERVER_PORT`; do sleep 10; done
echo "******* Kafka Server has started"

echo "********************************************************"
echo "Waiting for the ZIPKIN server to start  on port $ZIPKIN_PORT"
echo "********************************************************"
while ! `nc -z zipkin $ZIPKIN_PORT`; do sleep 10; done
echo "******* ZIPKIN has started"

echo "********************************************************"
echo "Company service starting with Configuration Service :  $CONFIGSERVER_URI";
echo "********************************************************"
java -Dserver.port=$SERVER_PORT -Deureka.client.serviceUrl.defaultZone=$EUREKASERVER_URI -Dspring.cloud.config.uri=$CONFIGSERVER_URI -Dspring.zipkin.baseUrl=$ZIPKIN_URI -Dspring.profiles.active=$PROFILE -Dspring.cloud.stream.kafka.binder.brokers=$KAFKASERVER_URI -jar /usr/local/company-service/@project.build.finalName@.jar
