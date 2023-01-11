FROM amazoncorretto:11-alpine-jdk
MAINTAINER haminoum.dev
COPY target/*.jar moodn-api.jar
ENTRYPOINT ["java","-jar","/moodn-api.jar"]