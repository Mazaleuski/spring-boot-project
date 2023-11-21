FROM openjdk:19-alpine
ARG JAR_FILE
COPY target/spring-boot-project-0.0.1-SNAPSHOT.jar /spring-boot-project-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/spring-boot-project-0.0.1-SNAPSHOT.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]