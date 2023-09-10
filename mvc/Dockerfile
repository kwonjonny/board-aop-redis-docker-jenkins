# 기본 Java 이미지를 사용합니다.
FROM tomcat:9-jdk11

# WAR 파일을 복사합니다.
ARG WAR_FILE=build/libs/mvc-0.0.1-SNAPSHOT.war
COPY ${WAR_FILE} /usr/local/tomcat/webapps/ROOT.war

# 8084 포트를 노출합니다.
EXPOSE 8084

# Tomcat 실행
CMD ["catalina.sh", "run"]
