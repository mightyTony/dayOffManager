#FROM ubuntu:latest
#LABEL authors="tony"
#
#ENTRYPOINT ["top", "-b"]

#Dockerfile

# jdk 17 Image Start
FROM openjdk:17

# 인자 설정 - JAR_File
#ARG JAR_FILE=build/libs/*.jar

#jar 파일 복제
#COPY ${JAR_FILE} app.jar

COPY dayOffManager-0.0.1-SNAPSHOT.jar app.jar

# 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]

