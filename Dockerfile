FROM openjdk:8-alpine
COPY target/java_apps*.jar /usr/local/app/twitterApp/lib/twitterApp.jar
ENTRYPOINT ["java","-jar","/usr/local/app/twitterApp/lib/twitterApp.jar"]