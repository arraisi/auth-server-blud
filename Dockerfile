FROM openjdk:8-jdk-alpine
MAINTAINER Dimas Maryanto (software.dimas_m@icloud.com)

VOLUME /tmp
ARG JAR_FILE
COPY target/${JAR_FILE} ${JAR_FILE}
