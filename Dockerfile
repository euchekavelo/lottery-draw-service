FROM ibm-semeru-runtimes:open-17-jdk-jammy
RUN apt-get update && apt-get install -yq tzdata
ENV TZ="Europe/Moscow"
RUN mkdir /app
COPY ./build/libs/*.jar /app/lottery-draw-service.jar
EXPOSE 8080
WORKDIR /app
CMD java -jar lottery-draw-service.jar