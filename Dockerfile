# See .dockerignore
# syntax=docker/dockerfile:experimental
FROM openjdk:8-jdk-alpine as build
WORKDIR /workspace/app

COPY gradle gradle
COPY build.gradle settings.gradle gradlew ./
COPY src src

RUN --mount=type=cache,target=/root/.gradle ./gradlew build -x test
RUN mkdir -p build/libs/dependency && (cd build/libs/dependency; jar -xf ../*.jar)

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/libs/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.example.Application"]

##### v4
# FROM gradle:4.6-jdk8-alpine as compile
# COPY . /home/source/java
# WORKDIR /home/source/java
# # Default gradle user is `gradle`. We need to add permission on working directory for gradle to build.
# USER root
# RUN chown -R gradle /home/source/java
# USER gradle
# RUN gradle clean build
#
# FROM openjdk:8-jre-alpine
# WORKDIR /home/application/java
# COPY --from=compile "/home/source/java/build/libs/app.jar" .
# EXPOSE 8080
# ENTRYPOINT [ "java", "-jar", "/home/application/java/app.jar"]

#### V3
# FROM openjdk:8-jdk-alpine as build
# WORKDIR /workspace/app
# RUN ls -l ${WORKDIR}
# COPY gradle gradle
# # COPY build.gradle settings.gradle gradlew ./
# RUN cp build.gradle settings.gradle gradlew ./
# COPY src src
#
# RUN ./gradlew build -x test
# RUN mkdir -p build/libs/dependency && (cd build/libs/dependency; jar -xf ../*.jar)
#
# FROM openjdk:8-jdk-alpine
# VOLUME /tmp
# ARG DEPENDENCY=/workspace/app/build/libs/dependency
# COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
# COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
# COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app
# ENTRYPOINT ["java","-cp","app:app/lib/*","com.hero.moodn.App"]

#### V1
# # Build stage
# FROM gradle:latest as build
# WORKDIR /usr/app
# COPY . .
# # Default gradle user is `gradle`. We need to add permission on working directory for gradle to build.
# # USER root
# # RUN chown -R gradle build
# # USER gradle
# RUN gradle clean build
#
# # Package stage
# FROM eclipse-temurin:17-jdk-alpine
# WORKDIR /usr/app
# COPY --from=build "/usr/app/build/libs/*.jar" .
# EXPOSE 8080
# ENTRYPOINT [ "java", "-jar", "/usr/app/app.jar"]

#### V2
# FROM eclipse-temurin:17-jdk-alpine
# WORKDIR /usr/app
# RUN ls -l
# RUN ls -l /usr/app
# COPY /usr/app/build/libs/*.jar app.jar
# #ARG JAR_FILE
# #COPY ${JAR_FILE} app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]