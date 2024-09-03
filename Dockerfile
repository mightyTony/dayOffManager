## 1.
#FROM amazoncorretto:17 as builder
#
##2.
#WORKDIR /workspace/app
#
##3.
#COPY gradlew .
#COPY gradle gradle
#COPY build.gradle .
#COPY settings.gradle .
#COPY src src
#
##5.
#RUN chmod +x ./gradlew
#
###6.
##RUN ./gradlew build --no-daemon || return 0
#
##7.
#RUN ./gradlew build --no-daemon -x test
#
##8
#FROM amazoncorretto:17
#
##9
#WORKDIR /home/ec2-user/app
#
##10
#COPY --from=builder /workspace/app/build/libs/*.jar ./app.jar
#
## 11.
#EXPOSE 8080
#
## 12.
#ENTRYPOINT ["java", "-jar", "app.jar"]
