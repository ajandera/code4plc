FROM openjdk:13
COPY ./out/production/com.code4plc.api/ /tmp
COPY ./lib/ /tmp

ENV CLASSPATH ${CLASSPATH}:/tmp/mysql-connector-java-8.0.21.jar
ARG CLASSPATH=${CLASSPATH}:/tmp/mysql-connector-java-8.0.21.jar

ENV CLASSPATH ${CLASSPATH}:/tmp/protobuf-java-3.11.4.jar
ARG CLASSPATH=${CLASSPATH}:/tmp/protobuf-java-3.11.4.jar

WORKDIR /tmp
ENTRYPOINT ["java", "App"]