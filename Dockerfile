FROM adoptopenjdk/openjdk11:alpine-slim

COPY . .
CMD ["java","-jar","target/den3Account-1.0-all.jar"]
