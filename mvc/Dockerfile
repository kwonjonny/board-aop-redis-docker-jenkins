# Dockerfile

# 기본 Java 이미지를 사용합니다.
FROM openjdk:17-jdk-slim

WORKDIR /app

# 소스 코드를 복사합니다.
COPY . /app

# 어플리케이션을 빌드하되, 테스트를 건너뜁니다.
RUN ./gradlew build -x test

# WAR 파일을 복사합니다.
COPY ./build/libs/mvc-0.0.1-SNAPSHOT.war /app/mvc.war

# 8084 포트를 노출합니다.
EXPOSE 8084

# 어플리케이션을 실행합니다.
ENTRYPOINT ["java", "-jar", "/app/mvc.war"]
