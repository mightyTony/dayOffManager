# Dockerfile

# 기본 이미지로 Amazon Corretto JDK 17 사용
FROM amazoncorretto:17

# 애플리케이션이 실행될 작업 디렉토리 설정
WORKDIR /home/ec2-user/app

# GitHub Actions에서 빌드한 JAR 파일을 이미지 내에 복사
# JAR 파일의 이름은 GitHub Actions 설정에서 정의하거나 Dockerfile 내에서 명시적으로 지정해야 함
COPY build/libs/*.jar ./app.jar

# 8080 포트를 오픈하여 외부에서 접근 가능하게 설정
EXPOSE 8080

# 컨테이너가 시작될 때 Java 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
