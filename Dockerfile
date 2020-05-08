FROM openjdk:11.0.6
ADD target/companion.jar companion.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "companion.jar"]