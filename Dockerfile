FROM openjdk:16-jdk-alpine
ARG JAR_FILE=target/yuzee-institute.jar
COPY ${JAR_FILE} yuzee-institute.jar
ENTRYPOINT ["java","-Dspring.profiles.active=kube","-jar","/yuzee-institute.jar"]
EXPOSE 8905