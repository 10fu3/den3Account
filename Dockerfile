FROM ubuntu:18.04

COPY . .
RUN apt-get update && apt-get install -y \
    maven \
    openjdk-8-jre \
  && mvn clean package
CMD ["java","-jar","target/den3Account-1.0-jar-with-dependencies.jar"]
