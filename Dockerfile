FROM openjdk:17-jdk-slim
VOLUME /data
EXPOSE 8888
COPY game-1.0.0-SNAPSHOT-fat.jar app.jar
COPY target/Shanghai /etc/localtime
ENV TZ=Asia/Shanghai
ENTRYPOINT java -jar /app.jar
