FROM openjdk:17
RUN mkdir /app
COPY ./build/libs/*.jar /app/lottery-draw-service.jar
EXPOSE 8080 9090
WORKDIR /app
CMD java -jar lottery-draw-service.jar