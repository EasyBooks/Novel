FROM adoptopenjdk/openjdk11

MAINTAINER lsm1998

ADD book-service-v1.jar book-service.jar

EXPOSE 20020

ENTRYPOINT ["java","-Xmx64m","-Xms64m","-Duser.timezone=UTC+8","-jar","book-service.jar"]