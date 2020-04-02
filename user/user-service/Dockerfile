FROM adoptopenjdk/openjdk11

MAINTAINER lsm1998

ADD user-service-v1.jar user-service.jar

EXPOSE 20010

ENTRYPOINT ["java","-Xmx64m","-Xms64m","-Duser.timezone=UTC+8","-jar","user-service.jar"]