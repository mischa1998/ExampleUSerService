FROM openjdk:19
RUN mkdir /app
COPY /target/users-0.0.1-SNAPSHOT.jar /app
WORKDIR /app
EXPOSE 9090
CMD java -jar users-0.0.1-SNAPSHOT.jar
