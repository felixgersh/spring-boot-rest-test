FROM openjdk:8
COPY ./testtask.pccw-0.0.1-SNAPSHOT.jar testtask.pccw.jar
CMD ["java", "-jar", "testtask.pccw.jar"]
