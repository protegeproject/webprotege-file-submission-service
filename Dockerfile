FROM openjdk:17
MAINTAINER protege.stanford.edu

ARG JAR_FILE
COPY target/${JAR_FILE} webprotege-file-submission-service.jar
ENTRYPOINT ["java","-jar","/webprotege-file-submission-service.jar"]